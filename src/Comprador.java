
import jade.content.ContentManager;
import jade.content.lang.Codec;
import jade.content.lang.sl.SLCodec;
import jade.content.onto.Ontology;
import jade.content.onto.OntologyException;
import jade.content.onto.basic.Action;
import jade.core.Agent;
import jade.core.behaviours.*;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import java.util.HashMap;

public class Comprador extends Agent {

    private HashMap<String, Float> intereses;
    private HashMap<String, Subasta> subastas;
    private CompradorGUI gui;

    // Variables para la ontologia
    private final ContentManager manager = getContentManager();
    private final Codec codec = new SLCodec();
    private final Ontology ontology = SubastaLibrosOntology.getInstance();

    // Inicializamos el agente
    @Override
    protected void setup() {

        intereses = new HashMap<>();
        subastas = new HashMap<>();

        // Lanzamos la GUI
        gui = new CompradorGUI(this);
        gui.lanzarVentana();

        // Preparamos la ontologia
        manager.registerLanguage(codec);
        manager.registerOntology(ontology);

        // Registramos al agente como comprador en el servicio de paginas amarillas
        DFAgentDescription desAg = new DFAgentDescription();
        desAg.setName(getAID());
        ServiceDescription desSer = new ServiceDescription();
        desSer.setType("comprador-libros");
        desSer.setName("JADE-comprador-libros");
        desAg.addServices(desSer);
        try {
            DFService.register(this, desAg);
        } catch (FIPAException fe) {
            System.out.print(fe.getMessage());
        }

        // Añadimos el compartamiento de respuesta a propuestas
        addBehaviour(new Recibir());
    }

    // Finalizamos el agente
    @Override
    protected void takeDown() {
        // Se elimina al agente del servicio de páginas amarillas
        try {
            DFService.deregister(this);
        } catch (FIPAException fe) {
            System.out.print(fe.getMessage());
        }
        // Cerramos la GUI
        gui.dispose();
    }

    public void updateIntereses(String titulo, Float precio) {
        addBehaviour(new OneShotBehaviour() {
            @Override
            public void action() {
                if (!intereses.containsKey(titulo)) {
                    intereses.put(titulo, precio);
                    gui.notificarInteres(titulo, true);
                } else {
                    intereses.remove(titulo);
                    gui.notificarInteres(titulo, false);
                }
            }
        });
    }

    public void actualizarTabla() {
        gui.actualizarTabla(subastas);
    }

    private class Recibir extends CyclicBehaviour {

        private String titulo;
        private String vendedor;
        private String comprador;
        private Float precio;
        private final ACLMessage respuesta = new ACLMessage(ACLMessage.PROPOSE);

        @Override
        public void action() {

            actualizarTabla();

            ACLMessage propuesta;
            // Tratamos de recibir un CFP
            if ((propuesta = myAgent.receive(MessageTemplate.MatchPerformative(ACLMessage.CFP))) != null) {
                try {
                    Action contenido = (Action) manager.extractContent(propuesta);
                    if (contenido.getAction() instanceof Subastar) {
                        Subastar subasta = (Subastar) contenido.getAction();
                        titulo = subasta.getLibro().getTitulo();
                        precio = subasta.getLibro().getPrecio();
                        // Si le interesa el libro y no supera al precio máximo, respondemos con un PROPOSE
                        if (intereses.containsKey(titulo)) {
                            if (precio <= intereses.get(titulo)) {
                                respuesta.clearAllReceiver();
                                respuesta.addReceiver(propuesta.getSender());
                                respuesta.setInReplyTo(propuesta.getReplyWith());
                                myAgent.send(respuesta);
                                if (subastas.containsKey(propuesta.getReplyWith())) {
                                    subastas.get(propuesta.getReplyWith()).setPrecio(precio);
                                } else {
                                    subastas.put(propuesta.getReplyWith(), new Subasta(titulo, precio, propuesta.getSender().getName()));
                                }
                            } else if (subastas.containsKey(propuesta.getReplyWith())) {
                                subastas.remove(propuesta.getReplyWith());
                            }
                        }
                    }
                } catch (Codec.CodecException | OntologyException ex) {
                    System.out.println(ex.getMessage());
                }
            } // Tratamos de recibir un Accept Proposal
            else if ((propuesta = myAgent.receive(MessageTemplate.MatchPerformative(ACLMessage.ACCEPT_PROPOSAL))) != null) {
                subastas.get(propuesta.getReplyWith()).setGanador(getName());
            } // Tratamos de recibir un Reject Proposal
            else if ((propuesta = myAgent.receive(MessageTemplate.MatchPerformative(ACLMessage.REJECT_PROPOSAL))) != null) {
                subastas.get(propuesta.getReplyWith()).setGanador(null);
            } // Tratamos de recibir un Inform Proposal
            else if ((propuesta = myAgent.receive(MessageTemplate.MatchPerformative(ACLMessage.INFORM))) != null) {
                try {
                    Action contenido = (Action) manager.extractContent(propuesta);
                    if (contenido.getAction() instanceof Vender) {
                        Vender venta = (Vender) contenido.getAction();
                        titulo = venta.getLibro().getTitulo();
                        precio = venta.getLibro().getPrecio();
                        vendedor = propuesta.getSender().getName();
                        comprador = venta.getComprador();
                        
                        if (comprador.equals(getName())) {
                            gui.notificar(titulo, precio, vendedor);
                            System.out.print(titulo);
                            if(intereses.containsKey(titulo)) intereses.remove(titulo);
                        }
                    }
                    
                    if (subastas.containsKey(propuesta.getReplyWith())) {
                        subastas.remove(propuesta.getReplyWith());
                    }
                } catch (Codec.CodecException | OntologyException ex) {
                    System.out.println(ex.getMessage());
                }
            } else {
                block();
            }

        }
    }
}
