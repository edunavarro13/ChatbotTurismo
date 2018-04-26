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

    public void vaciarLugar() {
        coste = null;
        guia = null;
        idioma = null;
        tipo = null;
        sub_tipo = null;
    }

    @Override
    public String toString() {
        String lugar = "LugarInteres{";
        if(coste != null)
            lugar += "coste=" + coste + ", ";
        if(guia != null)
            lugar += "guia=" + guia + ", ";
        if(idioma != null)
            lugar += "idioma=" + idioma + ", ";
        if(tipo != null)
            lugar += "tipo=" + tipo + ", ";
        if(sub_tipo != null)
            lugar += "sub_tipo=" + sub_tipo;
        lugar += "}";
        return lugar;
    }

    // Devuelve true si ya tiene todos los datos y false si no
    public boolean todosDatos() {
        return coste != null && guia != null && idioma != null
                && tipo != null && sub_tipo != null;
    }

    public Double getCoste() {
        return coste;
    }

    public void setCoste(Double coste) {
        this.coste = coste;
    }

    public Boolean isGuia() {
        return guia;
    }

    public void setGuia(Boolean guia) {
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
