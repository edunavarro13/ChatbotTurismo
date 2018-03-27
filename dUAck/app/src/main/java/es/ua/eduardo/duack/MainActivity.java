package es.ua.eduardo.duack;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.design.widget.FloatingActionButton;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import es.ua.eduardo.duack.Adapter.CustomAdapter;
import es.ua.eduardo.duack.Helper.HttpDataHandler;
import es.ua.eduardo.duack.Models.ChatModel;
import es.ua.eduardo.duack.Models.DuackModel;

import com.github.kittinunf.fuel.Fuel;
import com.github.kittinunf.fuel.core.FuelError;
import com.github.kittinunf.fuel.core.Handler;
import com.github.kittinunf.fuel.core.Request;
import com.github.kittinunf.fuel.core.Response;
import com.ibm.watson.developer_cloud.conversation.v1.ConversationService;
import com.ibm.watson.developer_cloud.conversation.v1.model.MessageRequest;
import com.ibm.watson.developer_cloud.conversation.v1.model.MessageResponse;
import com.ibm.watson.developer_cloud.http.ServiceCallback;

public class MainActivity extends AppCompatActivity {

    //The ConversationService class of the Watson SDK has all the methods you'll need to
    // communicate with the Conversation service. Therefore, the first thing you need to do
    // in your Activity class is create an instance of it. Its constructor expects a version
    // date, the service's username, and its password.
    ConversationService myConversationService;

    ListView listView;
    EditText editText;
    List<ChatModel> list_chat = new ArrayList<>();
    FloatingActionButton btn_send_message;

    private LocationManager manager;

    private double latitud_oficina = 38.47790161262177;
    private double longitud_oficina = -0.7960233999999673;
    private int TIEMPO_BUCLE = 500;

    // IBM
    String outputText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = (ListView)findViewById(R.id.list_of_message);
        editText = (EditText)findViewById(R.id.user_message);
        btn_send_message = (FloatingActionButton)findViewById(R.id.fab);

        myConversationService = new ConversationService(
                "2017-05-26",//hoy.toString(),
                getString(R.string.username),
                getString(R.string.password)
        );

        // Saludo inicial
        ChatModel saludo = new ChatModel(getString(R.string.saludo), false);

        list_chat.add(saludo);

        CustomAdapter adapter = new CustomAdapter(list_chat,getApplicationContext());
        listView.setAdapter(adapter);

        btn_send_message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = editText.getText().toString();
                //remove user message
                editText.setText("");
                if(!text.isEmpty()) {
                    ChatModel model = new ChatModel(text, true); // user send message
                    list_chat.add(model);

                    // Cogemos el mensaje de IBM
                    mensajeIBM(text);
                    // Si outputText esta vacio es que aun no ha cargado el mensaje
                    // asi que esperamos hasta que lo este y lo cargamos
                    while (outputText == null || outputText == "") {
                        try {
                            Thread.sleep(TIEMPO_BUCLE);
                        } catch (Exception ex) {
                            outputText = "El hilo ha dado el error: " + ex.toString();
                        }
                    }

                    ChatModel respuesta = new ChatModel(outputText, false);

                    // Lo vacio para que no salga del while en el proximo mensaje hasta que tenga un mensaje
                    outputText = "";

                    list_chat.add(respuesta);

                    CustomAdapter adapter2 = new CustomAdapter(list_chat, getApplicationContext());
                    listView.setAdapter(adapter2);
                }
                else {
                    Toast.makeText (MainActivity.this ,getString(R.string.error_no_mensaje),Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    protected void mensajeIBM(String inputText) {
        if (compruebaConexion(this)) {
            MessageRequest request = new MessageRequest.Builder().inputText(inputText).build();
            try {
                myConversationService
                        .message(getString(R.string.workspace), request)
                        .enqueue(new ServiceCallback<MessageResponse>() {
                            @Override
                            public void onResponse(MessageResponse response) {
                                // ######## No funciona, hay que arreglarlo #######################3
                                //int num_respuesta = (int) (Math.random()*(response.getText().size()));
                                outputText = response.getText().get(0);

                                if(response.getIntents().get(0).getIntent()
                                        .endsWith("OficinaTurismo")) {
                                    // Primero esperamos que output no este vacio
                                    while (outputText == null || outputText == "") {
                                        try {
                                            Thread.sleep(TIEMPO_BUCLE);
                                        } catch (Exception ex) {
                                            outputText = "El hilo ha dado el error: " + ex.toString();
                                        }
                                    }
                                    // Comprobamos si esta conectado
                                    manager = (LocationManager) getSystemService( Context.LOCATION_SERVICE );
                                    if ( !manager.isProviderEnabled( LocationManager.GPS_PROVIDER ) ) {
                                        outputText += "\n" + getString(R.string.sin_gps);
                                    }
                                    else {
                                        if (ActivityCompat.checkSelfPermission(MainActivity.this,
                                                Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                                            // Obtenemos la localizacion
                                            List<String> providers = manager.getProviders(true);
                                            Location bestLocation = null;
                                            for (String provider : providers) {
                                                Location l = manager.getLastKnownLocation(provider);
                                                if (l == null) {
                                                    continue;
                                                }
                                                if (bestLocation == null || l.getAccuracy() < bestLocation.getAccuracy()) {
                                                    // Found best last known location: %s", l);
                                                    bestLocation = l;
                                                }
                                            }
                                            if(bestLocation != null)
                                                outputText += "\nDistancia = " + distancia(latitud_oficina, longitud_oficina,
                                                        bestLocation.getLatitude(), bestLocation.getLongitude()) + " km.";
                                            else
                                                outputText += "\nERROR: Location es null";
                                        }
                                        else {
                                            outputText += "\n" + getString(R.string.permisos_gps);
                                        }
                                    }
                                }
                            }

                            @Override
                            public void onFailure(Exception e) {
                            }
                        });
                //Thread.sleep(5000);
            } catch (Exception ex) {
                outputText = getString(R.string.ibm_no_responde);
            }
        }
        else {
            outputText = getString(R.string.ibm_no_responde);
        }
    }

    /**
     * Función para comprobar si hay conexión a Internet
     * @param context
     * @return boolean
     */

    public static boolean compruebaConexion(Context context) {

        boolean connected = false;

        ConnectivityManager connec = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        // Recupera todas las redes (tanto móviles como wifi)
        NetworkInfo[] redes = connec.getAllNetworkInfo();

        for (int i = 0; i < redes.length; i++) {
            // Si alguna red tiene conexión, se devuelve true
            if (redes[i].getState() == NetworkInfo.State.CONNECTED) {
                connected = true;
            }
        }
        return connected;
    }

    public double distancia(double lat1, double lon1, double lat2, double lon2)
    {
        double R = 6378.137; //Radio de la tierra en km
        double dLat  = ( lat2 - lat1 )*Math.PI/180;
        double dLong = ( lon2 - lon1 )*Math.PI/180;

        double a = Math.sin(dLat/2) * Math.sin(dLat/2) + Math.cos(lat1*Math.PI/180) * Math.cos(lat2*Math.PI/180) * Math.sin(dLong/2) * Math.sin(dLong/2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
        double d = R * c;

        return (double)Math.round(d * 1000d) / 1000d;
    }
}
