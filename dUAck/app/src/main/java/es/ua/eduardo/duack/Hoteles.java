package es.ua.eduardo.duack;

import android.widget.ImageView;

public class Hoteles {
    int Id;
    String nombre;
    String direccion;
    String foto;
    Idioma idioma;
    Double latitud;
    Double longitud;
    String localidad;
    String provincia;
    String pais;
    Double precio;
    int telefono;
    String url;
    private double distancia = 0.0;

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public String getIdioma() {
        if(idioma == null)
            return null;
        return idioma.getTexto();
    }

    public void setIdioma(Idioma idioma) {
        this.idioma = idioma;
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

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
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

    public double getDistancia() {
        return distancia;
    }

    public void setDistancia(double distancia) {
        this.distancia = distancia;
    }

    public static void elegirFoto(ImageView imageView, String uri) {
        switch(uri) {
            case "marriot.png" :
                imageView.setImageResource(R.drawable.marriot);
                break;
            default :
                imageView.setImageResource(R.drawable.nofoto);
        }
    }
}
