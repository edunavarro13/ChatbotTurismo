package es.ua.eduardo.duack.Models;

public enum SubTipoLugar {
    OTROS("Otros"),
    CALZADO("Calzado"),
    ARTE("Arte"),
    ARQUEOLOGICO("Arqueologico"),
    ETNOLOGICO("Etnologico");

    private final String texto;

    SubTipoLugar(String texto) {
        this.texto = texto;
    }
    public String getTexto() { return texto; }
}
