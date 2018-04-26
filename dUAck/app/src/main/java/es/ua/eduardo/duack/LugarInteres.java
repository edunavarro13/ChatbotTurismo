package es.ua.eduardo.duack;

public class LugarInteres {
    private Double coste;
    private Boolean guia;
    private String idioma;
    private String tipo;
    private String sub_tipo;
    // tambien tiene, nombre, nombre2, ubicacion

    // El constructor por defecto lo pone todo a null
    public LugarInteres() {
        coste = null;
        guia = null;
        idioma = null;
        tipo = null;
        sub_tipo = null;
    }

    @Override
    public String toString() {
        return "LugarInteres{" +
                "coste=" + coste +
                ", guia=" + guia +
                ", idioma='" + idioma + '\'' +
                ", tipo='" + tipo + '\'' +
                ", sub_tipo='" + sub_tipo + '\'' +
                '}';
    }

    public double getCoste() {
        return coste;
    }

    public void setCoste(double coste) {
        this.coste = coste;
    }

    public boolean isGuia() {
        return guia;
    }

    public void setGuia(boolean guia) {
        this.guia = guia;
    }

    public String getIdioma() {
        return idioma;
    }

    public void setIdioma(String idioma) {
        this.idioma = idioma;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getSub_tipo() {
        return sub_tipo;
    }

    public void setSub_tipo(String sub_tipo) {
        this.sub_tipo = sub_tipo;
    }

}
