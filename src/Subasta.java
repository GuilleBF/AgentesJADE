public class Subasta {
    private final String titulo;
    private Float precio;
    private final String vendedor;
    private String ganador;
    
    public Subasta(String titulo, Float precio, String vendedor){
        this.titulo = titulo;
        this.precio = precio;
        this.ganador = null;
        this.vendedor = vendedor;
    }

    public String getTitulo() {
        return titulo;
    }


    public Float getPrecio() {
        return precio;
    }

    public void setPrecio(Float precio) {
        this.precio = precio;
    }

    public String getGanador() {
        return ganador;
    }

    public void setGanador(String ganador) {
        this.ganador = ganador;
    }
    
    public String getVendedor() {
        return vendedor;
    }
    
}
