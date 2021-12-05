

public class Vender implements jade.content.AgentAction {

  private static final long serialVersionUID = -4119894240794314469L;

  private String _internalInstanceName = null;

  public Vender() {
    this._internalInstanceName = "";
  }

  public Vender(String instance_name) {
    this._internalInstanceName = instance_name;
  }

  @Override
  public String toString() {
    return _internalInstanceName;
  }

   /**
   * Protege name: libro
   */
   private Libro ejemplar;
   public void setLibro(Libro value) { 
    this.ejemplar=value;
   }
   public Libro getLibro() {
     return this.ejemplar;
   }
   
   /**
   * Protege name: comprador
   */
   private String comprador;
   public void setComprador(String value) { 
    this.comprador=value;
   }
   public String getComprador() {
     return this.comprador;
   }

}
