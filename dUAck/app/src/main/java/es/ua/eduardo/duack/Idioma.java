package es.ua.eduardo.duack;

public enum Idioma {
    ESPAÑOL("Español"),
    INGLES("Ingles"),
    VALENCIANO("Valenciano"),
    CATALAN("Catalan");

    private final String texto;

    Idioma(String texto) {
        this.texto = texto;
    }

    public String getTexto() { return texto; }
}
