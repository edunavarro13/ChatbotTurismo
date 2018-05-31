package es.ua.eduardo.duack;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static es.ua.eduardo.duack.MainActivity.bd;

public class VariosResultadosHotelesActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    public AdaptadorHoteles adaptador;
    private RecyclerView.LayoutManager layoutManager;
    List<Hoteles> hoteles;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.varios_resultados_hoteles);

        Bundle extras = getIntent().getExtras();
        String url = extras.getString("url");
        ejecutar(url);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        adaptador = new AdaptadorHoteles(this, hoteles);
        recyclerView.setAdapter(adaptador);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        adaptador.setOnItemClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                long pos = (long) recyclerView.getChildAdapterPosition(v);
                Intent i = new Intent(VariosResultadosHotelesActivity.this, DescripcionLugarActivity.class);
                i.putExtra("id", adaptador.idLugarPosicion((int) pos));
                startActivity(i);
            }
        });
    }

    // -------------------- Hoteles -----------
    public void ejecutar(String cadenaurl) {
        try {
            URL url = new URL(cadenaurl + "/api/Hoteles");
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
            BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()));
            String str = "";
            String xml = "";
            while (null != (str = br.readLine())) {
                xml = str;
            }
            llenarArray(xml);

        } catch (Exception ex) {
            ex.printStackTrace();
            Log.d("MyError", ex.getMessage());
        }
    }

    public void llenarArray(String cadena) {
        hoteles = new ArrayList<Hoteles>();

        // Eso es que hay un hotel aun por añadir
        while (cadena.contains("Id")) {
            Hoteles hotel = new Hoteles();

            // Primero Id
            int start = cadena.indexOf("Id");
            cadena = cadena.substring(start+4);
            start = cadena.indexOf("nombre");
            hotel.setId(Integer.parseInt(cadena.substring(0, start-2)));
            // Segundo nombre
            cadena = cadena.substring(start+9);
            start = cadena.indexOf("latitud");
            hotel.setNombre(cadena.substring(0, start-3));
            // Tercero latitud
            cadena = cadena.substring(start+9);
            start = cadena.indexOf("longitud");
            hotel.setLatitud(Double.parseDouble(cadena.substring(0, start-2)));
            // Cuarto longitud
            cadena = cadena.substring(start+10);
            start = cadena.indexOf("direccion");
            hotel.setLatitud(Double.parseDouble(cadena.substring(0, start-2)));
            // Quinto direccion
            cadena = cadena.substring(start+12);
            start = cadena.indexOf("localidad");
            hotel.setDireccion(cadena.substring(0, start-3));
            // Sexto localidad
            cadena = cadena.substring(start+12);
            start = cadena.indexOf("provincia");
            hotel.setLocalidad(cadena.substring(0, start-3));
            // Septimo Provincia
            cadena = cadena.substring(start+12);
            start = cadena.indexOf("pais");
            hotel.setProvincia(cadena.substring(0, start-3));
            // Octavo Pais
            cadena = cadena.substring(start+7);
            start = cadena.indexOf("precio");
            hotel.setPais(cadena.substring(0, start-3));
            // Noveno precio
            cadena = cadena.substring(start+8);
            start = cadena.indexOf("idioma");
            hotel.setPrecio(Double.parseDouble(cadena.substring(0, start-2)));
            // Decimo idioma
            cadena = cadena.substring(start+9);
            start = cadena.indexOf("foto");
            hotel.setFoto(cadena.substring(0, start-3));
            // Onceavo Foto AQUI PUEDEN SER NULL
            cadena = cadena.substring(start+6);
            start = cadena.indexOf("telefono");
            String auxiliar = cadena.substring(0, start-2);
            if(auxiliar.equals("null"))
                hotel.setFoto(null);
            else
                hotel.setFoto(auxiliar);
            // Doceavo telefono
            cadena = cadena.substring(start+10);
            start = cadena.indexOf("url");
            auxiliar = cadena.substring(0, start-2);
            if(auxiliar.equals("null"))
                hotel.setTelefono(null);
            else
                hotel.setTelefono(auxiliar);
            // Treceavo Url
            cadena = cadena.substring(start+5);
            start = cadena.indexOf("}");
            auxiliar = cadena.substring(0, start-2);
            if(auxiliar.equals("null"))
                hotel.setUrl(null);
            else
                hotel.setUrl(auxiliar);

            hoteles.add(hotel);
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
