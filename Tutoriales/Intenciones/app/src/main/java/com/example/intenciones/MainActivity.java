package com.example.intenciones;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button web = (Button) findViewById(R.id.web);
        web.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pgWeb(view);
            }
        });

        Button telefono = (Button) findViewById(R.id.telefono);
        telefono.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                llamadaTelefono(view);
            }
        });

        Button maps = (Button) findViewById(R.id.maps);
        maps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                googleMaps(view);
            }
        });

        Button foto = (Button) findViewById(R.id.foto);
        foto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tomarFoto(view);
            }
        });

        Button correo = (Button) findViewById(R.id.correo);
        correo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mandarCorreo(view);
            }
        });
    }

    public void pgWeb(View view) {
        Intent intent = new Intent(Intent.ACTION_VIEW,
                Uri.parse("http://www.androidcurso.com/"));
        startActivity(intent);
    }
    public void llamadaTelefono(View view) {
        try {
            Intent intent = new Intent(Intent.ACTION_CALL,
                    Uri.parse("tel:962849347"));
            //startActivity(intent);
        } catch (Exception ex) {

        }
    }
    public void googleMaps(View view) {
        Intent intent = new Intent(Intent.ACTION_VIEW,
                Uri.parse("geo:41.656313,-0.877351"));
        startActivity(intent);
    }
    public void tomarFoto(View view) {
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        startActivity(intent);
    }
    public void mandarCorreo(View view) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_SUBJECT, "asunto");
        intent.putExtra(Intent.EXTRA_TEXT, "texto del correo");
        intent.putExtra(Intent.EXTRA_EMAIL, new String[] {"edunavarro13@gmail.com"});
        startActivity(intent);
    }
}
