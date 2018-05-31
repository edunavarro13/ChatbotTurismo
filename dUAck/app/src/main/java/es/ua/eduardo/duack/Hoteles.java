package es.ua.eduardo.duack;

import android.widget.ImageView;

public class Hoteles {
    int Id;
    String nombre;
    String direccion;
    String foto;
    String idioma;
    Double latitud;
    Double longitud;
    String localidad;
    String provincia;
    String pais;
    Double precio;
    int telefono;
    String url;

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
        if(foto != null)
        {
            foto = foto.replace("\"", "");
        }
        this.foto = foto;
    }

    public String getIdioma() {
        return idioma;
    }

    public void setIdioma(String idioma) {
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

    public void setTelefono(String telefono) {
        if(telefono != null)
        {
            telefono = telefono.replace("\"", "");
        }
        this.telefono = Integer.parseInt(telefono);
    }

    public String getUrl() {

        return url;
    }

    public void setUrl(String url) {
        if(url != null)
        {
            url = url.replace("\"", "");
        }
        this.url = url;
    }

    public static void elegirFoto(ImageView imageView, String uri) {
        imageView.setImageResource(R.drawable.plazacastelar);
        /*switch(uri) {
            case "plazacastelar.png" :
                imageView.setImageResource(R.drawable.plazacastelar);
                break;
        }*/
    }
}
