package es.ua.eduardo.duack;

public enum TipoLugar {
    OTROS("Otros"),
    MUSEO("Museo"),
    TEATRO("Teatro"),
    JARDIN("Jardin"),
    PLAZA("Plaza"),
    IGLESIA("Iglesia"),
    OFICINA("OficinaT");

    private final String texto;

    TipoLugar(String texto) {
        this.texto = texto;
    }
    public String getTexto() { return texto; }
}
