public class pieza {
    private String tipo;
    private String color;

    public pieza(String tipo, String color){
        this.color = color;
        this.tipo = tipo;
    }

    public String getTipo() {
        return tipo;
    }

    public String getColor() {
        return color;
    }
}
