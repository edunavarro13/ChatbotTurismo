package es.ua.eduardo.duack.Models;

public enum SubTipoLugar {
    CALZADO("Calzado"),
    ARTE("Arte"),
    ARQUEOLOGICO("Arqueologico"),
    ETNOLOGICO("Etnologico");

    private final String texto;

    SubTipoLugar(String texto) {
        this.texto = texto;
    }
    public String getTexto() { return texto; }

    public static boolean esTipoValido(String cadena) {
        switch(cadena) {
            case "Calzado" :
                return true;
            case "Arte" :
                return true;
            case "Arqueologico" :
                return true;
            case "Etnologico" :
                return true;
            default:
                return false;
        }
    }
}
