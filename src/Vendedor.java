
import jade.content.ContentManager;
import jade.content.lang.Codec;
import jade.content.lang.sl.SLCodec;
import jade.content.onto.Ontology;
import jade.content.onto.OntologyException;
import jade.content.onto.basic.Action;
import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.*;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAException;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import java.util.*;

public class Vendedor extends Agent {

    HashMap<String, Subasta> subastas;
    private VendedorGUI gui;

    // Variables para la ontologia
    private final ContentManager manager = getContentManager();
    private final Codec codec = new SLCodec();
    private final Ontology ontology = SubastaLibrosOntology.getInstance();

    // Se inicia el agente
    @Override
    protected void setup() {
        // Inicializamos el catálogo vacío
        subastas = new HashMap<>();

        // Preparamos la ontologia
        manager.registerLanguage(codec);
        manager.registerOntology(ontology);

        // Lanzamos la GUI
        gui = new VendedorGUI(this);
        gui.lanzarVentana();
    }

    // Finalizamos el agente
    @Override
    protected void takeDown() {
        // Cerramos la GUI
        gui.dispose();
    }

    public void lanzarSubasta(String titulo, Float precio, Float incremento) {
        addBehaviour(new Puja(this, 10000, titulo, precio, incremento));
    }

    public void actualizarSubastas() {
        gui.actualizarSubastas(subastas);
    }

    private void notificarSubasta(String titulo, Float precio, AID ganador) {
        gui.notificarSubasta(titulo, precio, ganador);
    }

    private class Puja extends TickerBehaviour {

        private final String titulo;
        private Float precio;
        private final Float incremento;
        private final ACLMessage mensaje = new ACLMessage(ACLMessage.ACCEPT_PROPOSAL);
        private ArrayList<AID> participantes = null;
        private final String idSubasta;
        private boolean setup = false;
        private final MessageTemplate plantilla;
        private final Subastar subasta = new Subastar();
        private final Vender venta = new Vender();

        public Puja(Agent a, long period, String titulo, Float precio, Float incremento) {
            super(a, period);
            this.titulo = titulo;
            this.precio = precio;
            this.incremento = incremento;
            idSubasta = System.currentTimeMillis() + myAgent.getAID().getName();
            mensaje.setReplyWith(idSubasta);
            mensaje.setSender(a.getAID());
            plantilla = MessageTemplate.and(MessageTemplate.MatchPerformative(ACLMessage.PROPOSE), MessageTemplate.MatchInReplyTo(idSubasta));
            ((Vendedor) myAgent).subastas.put(idSubasta, new Subasta(titulo, precio, myAgent.getName()));
            ((Vendedor) myAgent).actualizarSubastas();
            subasta.setLibro(new Libro());
            subasta.getLibro().setTitulo(titulo);
            venta.setLibro(subasta.getLibro());
        }

        @Override
        protected void onTick() {

            // Procesamos los resultados de la ronda anterior
            if (!setup) {

                // Obtenemos los parcipantes de la ronda
                ACLMessage respuesta;
                ArrayList<AID> aux = new ArrayList<>();
                while ((respuesta = myAgent.receive(plantilla)) != null) {
                    aux.add(respuesta.getSender());
                }

                if (aux.size() > 0) {
                    participantes = aux;
                }

                if (aux.size() < 2) {
                    if (participantes != null) {
                        // Informamos a los participantes del ganador y el precio
                        if (aux.isEmpty()) precio -= incremento; // Si nadie respondió, el precio de venta es el de la ronda anterior
                        ACLMessage inform = new ACLMessage(ACLMessage.INFORM);
                        participantes.forEach(participante -> {
                            inform.addReceiver(participante);
                        });
                        inform.setLanguage(codec.getName());
                        inform.setOntology(ontology.getName());
                        inform.setSender(myAgent.getAID());
                        inform.setReplyWith(idSubasta);
                        venta.getLibro().setPrecio(precio);
                        venta.setComprador(participantes.get(0).getName());
                        try {
                            manager.fillContent(inform, new Action(getAID(), venta));
                        } catch (Codec.CodecException | OntologyException ex) {
                            System.out.println(ex.getMessage());
                        }
                        myAgent.send(inform);

                        // Le mandamos al ganador el request (lo ignorará, no hace nada, es solo por ajustarse al modelo)
                        mensaje.setPerformative(ACLMessage.REQUEST);
                        mensaje.clearAllReceiver();
                        mensaje.addReceiver(participantes.get(0));
                        myAgent.send(mensaje);

                        // Eliminamos la subasta y alertamos su finalización
                        ((Vendedor) myAgent).subastas.remove(idSubasta);
                        ((Vendedor) myAgent).actualizarSubastas();
                        ((Vendedor) myAgent).notificarSubasta(titulo, precio, participantes.get(0));

                        // Eliminamos el comportamiento de esta subasta
                        myAgent.removeBehaviour(this);
                        return;
                    }
                } else {
                    // Informamos al ganador parcial
                    mensaje.setPerformative(ACLMessage.ACCEPT_PROPOSAL);
                    mensaje.clearAllReceiver();
                    mensaje.addReceiver(participantes.get(0));
                    myAgent.send(mensaje);
                    // Informamos al resto de participantes
                    mensaje.setPerformative(ACLMessage.REJECT_PROPOSAL);
                    mensaje.clearAllReceiver();
                    for (int i = 1; i < participantes.size(); i++) {
                        mensaje.addReceiver(participantes.get(i));
                    }
                    myAgent.send(mensaje);

                    ((Vendedor) myAgent).subastas.get(idSubasta).setGanador(participantes.get(0).getName());

                    // Actualizamos el precio para la siguiente ronda de propuestas
                    precio += incremento;
                    ((Vendedor) myAgent).subastas.get(idSubasta).setPrecio(precio);
                    ((Vendedor) myAgent).actualizarSubastas();
                }
            }

            // Informamos a los compradores del nuevo ciclo de la subasta
            DFAgentDescription desAg = new DFAgentDescription();
            ServiceDescription desSer = new ServiceDescription();

            desSer.setType("comprador-libros");
            desAg.addServices(desSer);

            // Preparamos el CFP
            ACLMessage cfp = new ACLMessage(ACLMessage.CFP);
            cfp.setPerformative(ACLMessage.CFP);
            cfp.setLanguage(codec.getName());
            cfp.setOntology(ontology.getName());
            cfp.setReplyWith(idSubasta);
            cfp.setSender(myAgent.getAID());
            subasta.getLibro().setPrecio(precio);
            try {
                manager.fillContent(cfp, new Action(getAID(), subasta));
            } catch (Codec.CodecException | OntologyException ex) {
                System.out.println(ex.getMessage());
            }

            try {
                // Obtenemos los compradores de las páginas amarillas
                DFAgentDescription[] resultado = DFService.search(myAgent, desAg);

                for (int i = 0; i < resultado.length; ++i) {
                    cfp.addReceiver(resultado[i].getName());
                }
            } catch (FIPAException fe) {
                System.out.println(fe.getMessage());
            }

            // Enviamos las propuestas
            myAgent.send(cfp);

            setup = false;
        }
    }
}
