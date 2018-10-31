package es.ua.eduardo.duack;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import static es.ua.eduardo.duack.MainActivity.bd;

public class DescripcionHotelActivity extends AppCompatActivity {

    Hoteles hotel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.descripcion_hotel);

        hotel = new Hoteles();
        Bundle extras = getIntent().getExtras();
        int id = extras.getInt("id");
        hotel = bd.hotelPorId(id);

        // Inicializamos los textview
        TextView nombre = (TextView)findViewById(R.id.nombre_lugar);
        nombre.setText(hotel.getNombre());
        ImageView foto = (ImageView)findViewById(R.id.imagen_lugar);
        Hoteles.elegirFoto(foto, hotel.getFoto());
        TextView direccion = (TextView)findViewById(R.id.direccion_lugar);
        direccion.setText(hotel.getDireccion());
        // Localidad es localidad, provincia, pais
        TextView localidad = (TextView)findViewById(R.id.localidad_lugar);
        String localidad_total =  hotel.getLocalidad() + ", "
                + hotel.getProvincia() + ", " + hotel.getPais();
        localidad.setText(localidad_total);
        TextView precio = (TextView)findViewById(R.id.precio_lugar);
        if(hotel.getPrecio() == 0.0)
            precio.setText("Gratis");
        else
            precio.setText("" + hotel.getPrecio());
        TextView idioma = (TextView)findViewById(R.id.idioma_lugar);
        if(hotel.getIdioma() != null)
            idioma.setText(hotel.getIdioma());
        else
            idioma.setText("");
        TextView telefono = (TextView)findViewById(R.id.telefono_lugar);
        if(hotel.getTelefono() == 0)
            telefono.setText("");
        else
            telefono.setText("" + hotel.getTelefono());
        TextView url = (TextView)findViewById(R.id.url_lugar);
        url.setText(hotel.getUrl());

        // Incializamos los Imagebutton
        ImageButton maps = (ImageButton)findViewById(R.id.logo_direccion);
        maps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String geo = "geo:0,0?q=" + hotel.getLatitud() + "," + hotel.getLongitud()
                        + "(" + hotel.getNombre() + ")";
                Intent intent = new Intent(Intent.ACTION_VIEW,
                        Uri.parse(geo));
                startActivity(intent);
            }
        });
        ImageButton web = (ImageButton)findViewById(R.id.logo_url);
        web.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(hotel.getUrl() != null && hotel.getUrl() != "") {
                    Intent intent = new Intent(Intent.ACTION_VIEW,
                            Uri.parse("http://" + hotel.getUrl()));
                    startActivity(intent);
                }
            }
        });

        ImageButton phone = (ImageButton)findViewById(R.id.logo_telefono);
        ImageView lengua = (ImageView)findViewById(R.id.logo_idioma);

        // Quitamos los null
        if(hotel.getUrl() == null || hotel.getUrl().equals("")) {
            LinearLayout linear_url = (LinearLayout) findViewById(R.id.linear_url);
            linear_url.setVisibility(View.GONE);
            web.setVisibility(View.GONE);
            url.setVisibility(View.GONE);
            View view_url = (View)findViewById(R.id.view_url);
            view_url.setVisibility(View.GONE);
        }
        if(hotel.getTelefono() == 0) {
            LinearLayout linear_phone = (LinearLayout) findViewById(R.id.linear_telefono);
            linear_phone.setVisibility(View.GONE);
            phone.setVisibility(View.GONE);
            telefono.setVisibility(View.GONE);
            View view_telefono = (View)findViewById(R.id.view_telefono);
            view_telefono.setVisibility(View.GONE);
        }
        if(hotel.getIdioma() == null) {
            LinearLayout linear_idioma = (LinearLayout) findViewById(R.id.linear_idioma);
            linear_idioma.setVisibility(View.GONE);
            lengua.setVisibility(View.GONE);
            idioma.setVisibility(View.GONE);
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
