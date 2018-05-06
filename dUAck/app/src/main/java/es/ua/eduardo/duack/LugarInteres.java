package es.ua.eduardo.duack;

import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

import es.ua.eduardo.duack.Models.SubTipoLugar;

public class LugarInteres {
    private int id;
    private String nombre;
    private String nombre2;
    private String descripcion;
    private Double latitud;
    private Double longitud;
    private String direccion;
    private String localidad;
    private String provincia;
    private String pais;
    private Double coste;
    private Boolean guia;
    private Idioma idioma;
    private TipoLugar tipo;
    private SubTipoLugar sub_tipo;
    private String foto;
    private int telefono;
    private String url;


    // Este es para salir del bucle que comprueba los null
    private List<Boolean> comprobado;

    // El constructor por defecto lo pone todo a null
    // solo lo que vayamos a usar
    public LugarInteres() {
        nombre = null;
        nombre2 = null;
        latitud = null;
        longitud = null;
        localidad = null;
        provincia = null;
        pais = null;
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
        nombre = null;
        nombre2 = null;
        latitud = null;
        longitud = null;
        localidad = null;
        provincia = null;
        pais = null;
        coste = null;
        guia = null;
        idioma = null;
        tipo = null;
        sub_tipo = null;
        ponerANoComprobado();
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

    public Idioma getIdioma() {
        return idioma;
    }

    public void setIdioma(Idioma idioma) {
        this.idioma = idioma;
        comprobado.set(2, true);
    }

    public TipoLugar getTipo() {
        return tipo;
    }

    public void setTipo(TipoLugar tipo) {
        this.tipo = tipo;
        comprobado.set(3, true);
    }

    public SubTipoLugar getSub_tipo() {
        return sub_tipo;
    }

    public void setSub_tipo(SubTipoLugar sub_tipo) {
        this.sub_tipo = sub_tipo;
        comprobado.set(4, true);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre2() {
        return nombre2;
    }

    public void setNombre2(String nombre2) {
        this.nombre2 = nombre2;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Double getLatitud() {
        return latitud;
    }

    public void setLatitud(Double latitud) {
        this.latitud = latitud;
    }

    public Double getLongitud() {
        return longitud;
    }

    public void setLongitud(Double longitud) {
        this.longitud = longitud;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getLocalidad() {
        return localidad;
    }

    public void setLocalidad(String localidad) {
        this.localidad = localidad;
    }

    public String getProvincia() {
        return provincia;
    }

    public void setProvincia(String provincia) {
        this.provincia = provincia;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public Boolean getGuia() {
        return guia;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public int getTelefono() {
        return telefono;
    }

    public void setTelefono(int telefono) {
        this.telefono = telefono;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Boolean getComprobadoId(int id) {
        return comprobado.get(id);
    }

    public static void elegirFoto(ImageView imageView, String uri) {
        switch(uri) {
            case "plazacastelar.png" :
                imageView.setImageResource(R.drawable.plazacastelar);
                break;
            case "jardindelamusica.png" :
                imageView.setImageResource(R.drawable.jardindelamusica);
                break;
            case "plazadelzapatero.png" :
                imageView.setImageResource(R.drawable.plazadelzapatero);
                break;
            case "parquedelaconcordia.png" :
                imageView.setImageResource(R.drawable.parquedelaconcordia);
                break;
            case "touristinfo.png" :
                imageView.setImageResource(R.drawable.touristinfo);
                break;
            case "museodelcalzado.png" :
                imageView.setImageResource(R.drawable.museodelcalzado);
                break;
            case "teatrocastelar.png" :
                imageView.setImageResource(R.drawable.teatrocastelar);
                break;
            case "plazamayor.png" :
                imageView.setImageResource(R.drawable.plazamayor);
                break;
            case "inmaculada.png" :
                imageView.setImageResource(R.drawable.inmaculada);
                break;
            case "santaana.png" :
                imageView.setImageResource(R.drawable.santaana);
                break;
            case "nuevopepicoamat.png" :
                imageView.setImageResource(R.drawable.nuevopepicoamat);
                break;
            case "castillopalacio.png" :
                imageView.setImageResource(R.drawable.castillopalacio);
                break;
            case "yacimientomonastil.png" :
                imageView.setImageResource(R.drawable.yacimientomonastil);
                break;
            case "ermitasananton.png" :
                imageView.setImageResource(R.drawable.ermitasananton);
                break;
            case "museoarqueologicomunicipal.png" :
                imageView.setImageResource(R.drawable.museoarqueologicomunicipal);
                break;
            case "museoetnologicoelda.png" :
                imageView.setImageResource(R.drawable.museoetnologicoelda);
                break;
            case "auditorioadoc.png" :
                imageView.setImageResource(R.drawable.auditorioadoc);
                break;
        }
    }

}
