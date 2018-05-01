package es.ua.eduardo.duack;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import es.ua.eduardo.duack.Models.SubTipoLugar;

public class DescripcionLugarActivity extends Activity {

    LugarInteres lugar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.descripcion_lugar);

        lugar = new LugarInteres();
        Bundle extras = getIntent().getExtras();
        lugar.setNombre(extras.getString("nombre"));
        lugar.setFoto(extras.getString("foto"));
        lugar.setDireccion(extras.getString("direccion"));
        lugar.setLocalidad(extras.getString("localidad"));
        lugar.setProvincia(extras.getString("provincia"));
        lugar.setPais(extras.getString("pais"));
        lugar.setLatitud(extras.getDouble("latitud"));
        lugar.setLongitud(extras.getDouble("longitud"));
        lugar.setCoste(extras.getDouble("precio"));
        lugar.setGuia(extras.getBoolean("guia"));
        String aux = extras.getString("idioma");
        if(aux != null)
            lugar.setIdioma(Idioma.valueOf(aux.toUpperCase()));
        aux = extras.getString("tipo");
        if(aux != null)
            lugar.setTipo(TipoLugar.valueOf(aux.toUpperCase()));
        aux = extras.getString("subtipo");
        if (aux != null)
            lugar.setSub_tipo(SubTipoLugar.valueOf(aux.toUpperCase()));
        lugar.setTelefono(extras.getInt("telefono"));
        lugar.setUrl(extras.getString("url"));

        // Inicializamos los textview
        TextView nombre = (TextView)findViewById(R.id.nombre_lugar);
        nombre.setText(lugar.getNombre());
        // Direccion es direccion, localidad, provincia, pais
        TextView direccion = (TextView)findViewById(R.id.direccion_lugar);
        String direccion_total = lugar.getDireccion() + ", " + lugar.getLocalidad() + ", "
                + lugar.getProvincia() + ", " + lugar.getPais();
        direccion.setText(direccion_total);
        TextView precio = (TextView)findViewById(R.id.precio_lugar);
        if(lugar.getCoste() == 0.0)
            precio.setText("Gratis");
        else
            precio.setText("" + lugar.getCoste());
        TextView guia = (TextView)findViewById(R.id.guia_lugar);
        if(lugar.getGuia())
            guia.setText("Sí");
        else
            guia.setText("No");
        TextView idioma = (TextView)findViewById(R.id.idioma_lugar);
        if(lugar.getIdioma() != null)
            idioma.setText(lugar.getIdioma().getTexto());
        else
            idioma.setText("");
        // Tipo es tipo, subtipo
        TextView tipo = (TextView)findViewById(R.id.tipo_lugar);
        if(lugar.getTipo() != null) {
            String tipo_total = lugar.getTipo().getTexto();
            if (lugar.getSub_tipo() != null)
                tipo_total += ", " + lugar.getSub_tipo().getTexto();
            tipo.setText(tipo_total);
        }
        else
            tipo.setText(null);
        TextView telefono = (TextView)findViewById(R.id.telefono_lugar);
        if(lugar.getTelefono() == 0)
            telefono.setText("");
        else
            telefono.setText("" + lugar.getTelefono());
        TextView url = (TextView)findViewById(R.id.url_lugar);
        url.setText(lugar.getUrl());

    }
}
