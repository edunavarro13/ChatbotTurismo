package es.ua.eduardo.duack;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class AyudaTiposActivity extends AppCompatActivity {

    public static EditText editText;
    public static FloatingActionButton button_main;
    boolean tipo;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle extras = getIntent().getExtras();
        tipo = (extras.getBoolean("tipo"));
        if(tipo) {
            setContentView(R.layout.ayuda_tipos);
            Button button_museo = (Button) findViewById(R.id.boton_museo);
            Button button_teatro = (Button) findViewById(R.id.boton_teatro);
            Button button_jardin = (Button) findViewById(R.id.boton_jardin);
            Button button_plaza = (Button) findViewById(R.id.boton_plaza);
            Button button_iglesia = (Button) findViewById(R.id.boton_iglesia);
            Button button_estadio = (Button) findViewById(R.id.boton_estadio);
            Button button_castillo = (Button) findViewById(R.id.boton_castillo);
            Button button_yacimiento = (Button) findViewById(R.id.boton_yacimiento);
            Button button_edificio = (Button) findViewById(R.id.boton_edificio);
            button_museo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    editText.setText("Museo");
                    button_main.callOnClick();
                    finish();
                }
            });
            button_teatro.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    editText.setText("Teatro");
                    button_main.callOnClick();
                    finish();
                }
            });
            button_jardin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    editText.setText("Jardin");
                    button_main.callOnClick();
                    finish();
                }
            });
            button_plaza.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    editText.setText("Plaza");
                    button_main.callOnClick();
                    finish();
                }
            });
            button_iglesia.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    editText.setText("Iglesia");
                    button_main.callOnClick();
                    finish();
                }
            });
            button_estadio.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    editText.setText("Estadio");
                    button_main.callOnClick();
                    finish();
                }
            });
            button_castillo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    editText.setText("Castillo");
                    button_main.callOnClick();
                    finish();
                }
            });
            button_yacimiento.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    editText.setText("Yacimiento");
                    button_main.callOnClick();
                    finish();
                }
            });
            button_edificio.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    editText.setText("Edificio");
                    button_main.callOnClick();
                    finish();
                }
            });
        }
        else {
            setContentView(R.layout.ayuda_subtipos);
            Button button_calzado = (Button) findViewById(R.id.boton_calzado);
            Button button_arte = (Button) findViewById(R.id.boton_arte);
            Button button_arqueologico = (Button) findViewById(R.id.boton_arqueologico);
            Button button_etnologico = (Button) findViewById(R.id.boton_etnologico);
            button_calzado.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    editText.setText("Calzado");
                    button_main.callOnClick();
                    finish();
                }
            });
            button_arte.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    editText.setText("Arte");
                    button_main.callOnClick();
                    finish();
                }
            });
            button_arqueologico.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    editText.setText("Arqueologico");
                    button_main.callOnClick();
                    finish();
                }
            });
            button_etnologico.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    editText.setText("Etnologico");
                    button_main.callOnClick();
                    finish();
                }
            });
        }
    }

    // --------------- Menu -----------------
    @Override public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.ayuda_tipos, menu);
        return true; /** true -> el menú ya está visible */
    }

    @Override public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.menu_atras_tipos) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    // ------------------------------
}
