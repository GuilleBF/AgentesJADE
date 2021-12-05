

import jade.content.onto.*;
import jade.content.schema.*;


public class SubastaLibrosOntology extends jade.content.onto.Ontology  {

  private static final long serialVersionUID = -4119894240794314469L;

  //NAME
  public static final String ONTOLOGY_NAME = "subastaLibros";
  // The singleton instance of this ontology
  private static final Ontology theInstance = new SubastaLibrosOntology();
  public static Ontology getInstance() {
     return theInstance;
  }


   // VOCABULARY
    public static final String SUBASTAR_LIBRO="libro";
    public static final String SUBASTAR="Subastar";
    public static final String LIBRO_PRECIO="precio";
    public static final String LIBRO_TITULO="titulo";
    public static final String LIBRO="Libro";
    public static final String VENDER_COMPRADOR="comprador";
    public static final String VENDER_TITULO="libro";
    public static final String VENDER="Vender";

  /**
   * Constructor
  */
  private SubastaLibrosOntology(){ 
    super(ONTOLOGY_NAME, BasicOntology.getInstance());
    try { 

    // adding Concept(s)
    ConceptSchema libroSchema = new ConceptSchema(LIBRO);
    add(libroSchema, Libro.class);

    // adding AgentAction(s)
    AgentActionSchema subastarSchema = new AgentActionSchema(SUBASTAR);
    add(subastarSchema, Subastar.class);
    AgentActionSchema venderSchema = new AgentActionSchema(VENDER);
    add(venderSchema, Vender.class);

    // adding fields
    libroSchema.add(LIBRO_TITULO, (TermSchema)getSchema(BasicOntology.STRING), ObjectSchema.MANDATORY);
    libroSchema.add(LIBRO_PRECIO, (TermSchema)getSchema(BasicOntology.FLOAT), ObjectSchema.MANDATORY);
    subastarSchema.add(SUBASTAR_LIBRO, libroSchema, ObjectSchema.MANDATORY);
    venderSchema.add(VENDER_TITULO, libroSchema, ObjectSchema.MANDATORY);
    venderSchema.add(VENDER_COMPRADOR, (TermSchema)getSchema(BasicOntology.STRING), ObjectSchema.MANDATORY);

   }catch (OntologyException e) {
       System.out.println(e.getMessage());
   }
  }
}
