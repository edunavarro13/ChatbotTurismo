package es.ua.eduardo.duack;

import java.util.ArrayList;
import java.util.List;

public class LugarInteres {
    private Double coste;
    private Boolean guia;
    private String idioma;
    private String tipo;
    private String sub_tipo;
    // tambien tiene, nombre, nombre2, ubicacion
    private List<Boolean> comprobado;

    // El constructor por defecto lo pone todo a null
    public LugarInteres() {
        coste = null;
        guia = null;
        idioma = null;
        tipo = null;
        sub_tipo = null;
        ponerANoComprobado();
    }

    public void ponerANoComprobado() {
        if(comprobado == null) {
            comprobado = new ArrayList<Boolean>();
            for (int i = 0; i < 5; i++)
                comprobado.add(false);
        }
        else {
            for (int i = 0; i < 5; i++)
                comprobado.set(i, false);
        }
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
        return (coste != null || comprobado.get(0)) && (guia != null || comprobado.get(1))
                && (idioma != null || comprobado.get(2)) && (tipo != null || comprobado.get(3))
                && (sub_tipo != null || comprobado.get(4));
    }

    public Double getCoste() {
        return coste;
    }

    public void setCoste(Double coste) {
        this.coste = coste;
        comprobado.set(0, true);
    }

    public Boolean isGuia() {
        return guia;
    }

    public void setGuia(Boolean guia) {
        this.guia = guia;
        comprobado.set(1, true);
    }

    public String getIdioma() {
        return idioma;
    }

    public void setIdioma(String idioma) {
        this.idioma = idioma;
        comprobado.set(2, true);
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
        comprobado.set(3, true);
    }

    public String getSub_tipo() {
        return sub_tipo;
    }

    public void setSub_tipo(String sub_tipo) {
        this.sub_tipo = sub_tipo;
        comprobado.set(4, true);
    }

    public Boolean getComprobadoId(int id) {
        return comprobado.get(id);
    }

}
