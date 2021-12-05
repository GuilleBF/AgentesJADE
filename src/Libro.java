

public class Libro implements jade.content.Concept {

  private static final long serialVersionUID = -4119894240794314469L;

  private String _internalInstanceName = null;

  public Libro() {
    this._internalInstanceName = "";
  }

  public Libro(String instance_name) {
    this._internalInstanceName = instance_name;
  }

  @Override
  public String toString() {
    return _internalInstanceName;
  }

   /**
   * Protege name: titulo
   */
   private String titulo;
   public void setTitulo(String value) { 
    this.titulo=value;
   }
   public String getTitulo() {
     return this.titulo;
   }

   /**
   * Protege name: precio
   */
   private float precio;
   public void setPrecio(float value) { 
    this.precio=value;
   }
   public float getPrecio() {
     return this.precio;
   }

}
