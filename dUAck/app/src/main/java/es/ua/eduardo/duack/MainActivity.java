package es.ua.eduardo.duack;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.design.widget.FloatingActionButton;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
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

public class MainActivity extends AppCompatActivity implements LocationListener {

    //The ConversationService class of the Watson SDK has all the methods you'll need to
    // communicate with the Conversation service. Therefore, the first thing you need to do
    // in your Activity class is create an instance of it. Its constructor expects a version
    // date, the service's username, and its password.
    ConversationService myConversationService;

    ListView listView;
    EditText editText;
    List<ChatModel> list_chat = new ArrayList<>();
    FloatingActionButton btn_send_message;

    private double latitud_oficina = 38.47790161262177;
    private double longitud_oficina = -0.7960233999999673;
    private int TIEMPO_BUCLE = 500;

    // IBM
    String outputText;

    // ###### Localizacion #######
    private static final int SOLICITUD_PERMISO_LOCALIZACION = 0;
    private static final long DOS_MINUTOS = 2 * 60 * 1000;
    private LocationManager manejador;
    private Location mejorLocaliz;
    // ###########################

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

        // ###### Localizacion #######
        manejador = (LocationManager) getSystemService(LOCATION_SERVICE);
        // ###########################
    }

    protected void mensajeIBM(final String inputText) {
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
                                    ultimaLocalizazion();
                                    //manager = (LocationManager) getSystemService( Context.LOCATION_SERVICE );
                                    if ( !manejador.isProviderEnabled(LocationManager.GPS_PROVIDER) ||
                                            !manejador.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
                                        outputText += "\n" + getString(R.string.sin_gps);
                                    }
                                    else {
                                        if (ActivityCompat.checkSelfPermission(MainActivity.this,
                                                Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                                            // Obtenemos la localizacion
                                            if(mejorLocaliz != null)
                                                outputText += "\nDistancia = " + distancia(latitud_oficina, longitud_oficina,
                                                        mejorLocaliz.getLatitude(), mejorLocaliz.getLongitude()) + " km.";
                                            else
                                                outputText += "\n" + getString(R.string.location_null);
                                        }
                                        else {
                                            outputText += "\n" + getString(R.string.permisos_gps);
                                        }
                                    }
                                }
                                else if(response.getIntents().get(0).getIntent()
                                        .endsWith("Nombre")) {
                                    // Primero esperamos que output no este vacio
                                    while (outputText == null || outputText == "") {
                                        try {
                                            Thread.sleep(TIEMPO_BUCLE);
                                        } catch (Exception ex) {
                                            outputText = "El hilo ha dado el error: " + ex.toString();
                                        }
                                    }
                                    String aux_nombre = inputText;
                                    aux_nombre = convertirANombre(aux_nombre);

                                    // La primera ocurrencia de '-' empezando por el final del string es donde empieza
                                    // el substring
                                    int pos_nombre = -1;
                                    for(int i_nombre = aux_nombre.length() - 1; i_nombre >= 0; i_nombre-- ){
                                        if(aux_nombre.charAt(i_nombre) == '-' && pos_nombre < 0) {
                                            pos_nombre = i_nombre + 1;
                                            break;
                                        }
                                    }
                                    if(pos_nombre >= 0) {
                                        aux_nombre = aux_nombre.substring(pos_nombre);
                                        outputText += " " + aux_nombre;
                                        outputText += "\nSELECT * FROM x WHERE nombre='" + aux_nombre + "'";
                                        // Plaza mayor de elda aunque tambien puede ser universidad de alicante
                                        if(aux_nombre.contains(" de ")) {
                                            aux_nombre = aux_nombre.replace(" de ", "-");
                                            String ciudad = aux_nombre.substring(aux_nombre.indexOf("-") + 1);
                                            String nombre = aux_nombre.substring(0, aux_nombre.indexOf("-"));
                                            outputText += " || (WHERE nombre='" + nombre + "' && ciudad='" + ciudad + "')";
                                        }
                                    }
                                    else
                                        outputText = "La cadena no contenía del, la ni el.";
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

    public String convertirANombre(String nombre) {
        // Existe la posibilidad de que por autocorrector, en vez de las posibles soluciones
        // que son la, el o del, hayan mayusculas y por tanto, aqui lo comprobamos
        // Le ponemos despues de los 'la', 'el', 'del' ya que ahi es donde separaremos
        nombre = nombre.replace(" del ", " del-");
        nombre = nombre.replace(" Del ", " Del-");
        nombre = nombre.replace(" dEl ", " dEl-");
        nombre = nombre.replace(" deL ", " deL-");
        nombre = nombre.replace(" DEl ", " DEl-");
        nombre = nombre.replace(" dEL ", " dEL-");
        nombre = nombre.replace(" DeL ", " DeL-");

        nombre = nombre.replace(" la ", " la-");
        nombre = nombre.replace(" La ", " La-");
        nombre = nombre.replace(" lA ", " lA-");

        nombre = nombre.replace(" el ", " el-");
        nombre = nombre.replace(" El ", " El-");

        nombre = nombre.replace(" eL ", " eL-");
        return nombre;
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

    // ########## Localizacion ###########
    void ultimaLocalizazion(){
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.
                ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            if (manejador.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                actualizaMejorLocaliz(manejador.getLastKnownLocation(
                        LocationManager.GPS_PROVIDER));
            }
            if (manejador.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
                actualizaMejorLocaliz(manejador.getLastKnownLocation(
                        LocationManager.NETWORK_PROVIDER));
            }
        }
        else  {
            solicitarPermiso(Manifest.permission.ACCESS_FINE_LOCATION,
                    getString(R.string.permisos_gps), SOLICITUD_PERMISO_LOCALIZACION, this);
        }
    }

        // Metodo que solicita cierto permiso (por ejemplo, Ubicacion)
    public static void solicitarPermiso(final String permiso, String justificacion,
                                        final int requestCode, final Activity actividad) {
        if (ActivityCompat.shouldShowRequestPermissionRationale(actividad,
                permiso)){
            new AlertDialog.Builder(actividad)
                    .setTitle("Solicitud de permiso")
                    .setMessage(justificacion)
                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            ActivityCompat.requestPermissions(actividad,
                                    new String[]{permiso}, requestCode);
                        }})
                    .show();
        } else {
            ActivityCompat.requestPermissions(actividad,
                    new String[]{permiso}, requestCode);
        }
    }

        // Una vez que el usuario escoja en el metodo solicitarpermiso se realizará una
        // llamada a este metodo
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String[] permissions, int[] grantResults) {
        if (requestCode == SOLICITUD_PERMISO_LOCALIZACION) {
            if (grantResults.length== 1 &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                ultimaLocalizazion();
                activarProveedores();
            }
        }
    }

    @Override protected void onResume() {
        super.onResume();
        activarProveedores();
    }

    private void activarProveedores() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.
                ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            if (manejador.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                manejador.requestLocationUpdates(LocationManager.GPS_PROVIDER,
                        20 * 1000, 5, this);
            }
            if (manejador.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
                manejador.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,
                        10 * 1000, 10, this);
            }
        } else {
            solicitarPermiso(Manifest.permission.ACCESS_FINE_LOCATION,
                    getString(R.string.permisos_gps), SOLICITUD_PERMISO_LOCALIZACION, this);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.
                ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            manejador.removeUpdates(this);
        }
    }
    @Override
    public void onLocationChanged(Location location) {
        actualizaMejorLocaliz(location);
    }
    @Override
    public void onProviderDisabled(String proveedor) {
        activarProveedores();
    }
    @Override
    public void onProviderEnabled(String proveedor) {
        activarProveedores();
    }
    @Override
    public void onStatusChanged(String proveedor, int estado, Bundle extras) {
        activarProveedores();
    }

    private void actualizaMejorLocaliz(Location localiz) {
        if (localiz != null && (mejorLocaliz == null
                || localiz.getAccuracy() < 2*mejorLocaliz.getAccuracy()
                || localiz.getTime() - mejorLocaliz.getTime() > DOS_MINUTOS)) {
            mejorLocaliz = localiz;
        }
    }

    // ###################################

}
