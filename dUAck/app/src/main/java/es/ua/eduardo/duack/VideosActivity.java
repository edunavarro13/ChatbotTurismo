package es.ua.eduardo.duack;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.MediaController;
import android.widget.VideoView;

public class VideosActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.videolugarubicacion);

        Uri uri = Uri.parse("android.resource://"+getPackageName()+"/"+R.raw.lugubi);
        VideoView simpleVideoView = (VideoView) findViewById(R.id.VideoView); // initiate a video view
        simpleVideoView.setVideoURI(uri);
        //simpleVideoView.start();

        // create an object of media controller
        MediaController mediaController = new MediaController(this);
        // set media controller object for a video view
        simpleVideoView.setMediaController(mediaController);
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
