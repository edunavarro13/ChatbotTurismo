package es.ua.eduardo.duack;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileNotFoundException;

import es.ua.eduardo.duack.Models.SubTipoLugar;

public class DescripcionLugarActivity extends AppCompatActivity {

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
        ImageView foto = (ImageView)findViewById(R.id.imagen_lugar);
        elegirFoto(foto, lugar.getFoto());
        TextView direccion = (TextView)findViewById(R.id.direccion_lugar);
        direccion.setText(lugar.getDireccion());
        // Localidad es localidad, provincia, pais
        TextView localidad = (TextView)findViewById(R.id.localidad_lugar);
        String localidad_total =  lugar.getLocalidad() + ", "
                + lugar.getProvincia() + ", " + lugar.getPais();
        localidad.setText(localidad_total);
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
        TextView telefono = (TextView)findViewById(R.id.telefono_lugar);
        if(lugar.getTelefono() == 0)
            telefono.setText("");
        else
            telefono.setText("" + lugar.getTelefono());
        TextView url = (TextView)findViewById(R.id.url_lugar);
        url.setText(lugar.getUrl());

        // Incializamos los Imagebutton
        ImageButton maps = (ImageButton)findViewById(R.id.logo_direccion);
        maps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String geo = "geo:0,0?q=" + lugar.getLatitud() + "," + lugar.getLongitud()
                        + "(" + lugar.getNombre() + ")";
                Intent intent = new Intent(Intent.ACTION_VIEW,
                        Uri.parse(geo));
                startActivity(intent);
            }
        });
        ImageButton web = (ImageButton)findViewById(R.id.logo_url);
        web.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(lugar.getUrl() != null && lugar.getUrl() != "") {
                    Intent intent = new Intent(Intent.ACTION_VIEW,
                            Uri.parse("http://" + lugar.getUrl()));
                    startActivity(intent);
                }
            }
        });

    }

    protected void elegirFoto(ImageView imageView, String uri) {
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
        }
    }

    // --------------- Menu -----------------
    @Override public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.descripcion_lugar, menu);
        return true; /** true -> el menú ya está visible */
    }

    @Override public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.menu_atras) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    // ------------------------------
}
