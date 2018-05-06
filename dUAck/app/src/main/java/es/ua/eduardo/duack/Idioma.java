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

    public static boolean esTipoValido(String cadena) {
        switch(cadena) {
            case "Español" :
                return true;
            case "Ingles" :
                return true;
            case "Valenciano" :
                return true;
            case "Catalan" :
                return true;
            default:
                return false;
        }
    }
}
