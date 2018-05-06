package es.ua.eduardo.duack;

public enum TipoLugar {
    MUSEO("Museo"),
    TEATRO("Teatro"),
    JARDIN("Jardin"),
    PLAZA("Plaza"),
    IGLESIA("Iglesia"),
    OFICINA("Oficina"),
    ESTADIO("Estadio"),
    CASTILLO("Castillo"),
    YACIMIENTO("Yacimiento");

    private final String texto;

    TipoLugar(String texto) {
        this.texto = texto;
    }
    public String getTexto() { return texto; }

    public static boolean esTipoValido(String cadena) {
        switch(cadena) {
            case "Museo" :
                return true;
            case "Teatro" :
                return true;
            case "Jardin" :
                return true;
            case "Plaza" :
                return true;
            case "Iglesia" :
                return true;
            case "Estadio" :
                return true;
            case "Castillo" :
                return true;
            case "Yacimiento" :
                return true;
            default:
                return false;
        }
    }
}
