package es.ua.eduardo.duack;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.preference.PreferenceManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.design.widget.FloatingActionButton;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import es.ua.eduardo.duack.Adapter.CustomAdapter;
import es.ua.eduardo.duack.Models.ChatModel;
import es.ua.eduardo.duack.Models.SubTipoLugar;

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
    int tamLista = 0;

    private double latitud_oficina = 38.47790161262177;
    private double longitud_oficina = -0.7960233999999673;
    private int TIEMPO_BUCLE = 500;

    private LugarInteres clase_lugar;
    private boolean fin = false;
    private boolean datos_interes = true;
    private int modificar_datos_lugar = -1; // 0=coste;1=guia;2=idioma;3=tipo;4=sub_tipo
    public static BaseDatos bd;
    private AdaptadorLugares adaptador;

    // ### Datos preferencias ###
    private boolean prefubicacion;
    private String prefpais, prefprovincia, preflocalidad;
    private double prefdistancia;

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

        bd = new BaseDatos(this);
        PreferenceManager.setDefaultValues(this, R.xml.preferencias, false);
        mostrarPreferencias();

        clase_lugar = new LugarInteres();

        listView = (ListView)findViewById(R.id.list_of_message);
        editText = (EditText)findViewById(R.id.user_message);
        btn_send_message = (FloatingActionButton)findViewById(R.id.fab);

        myConversationService = new ConversationService(
                "2017-05-26",//hoy.toString(),
                getString(R.string.username),
                getString(R.string.password)
        );

        // Saludo inicial
        ChatModel saludo = new ChatModel(getString(R.string.saludo), false, 3);

        list_chat.add(saludo);

        CustomAdapter adapter = new CustomAdapter(list_chat,getApplicationContext());
        listView.setAdapter(adapter);

        btn_send_message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(editText.getText().toString().equals(">*<AyudaTiposActivity<644/2|714>")) {
                    // Caso ">*<AyudaTiposActivity<644/2|714>" es decir ejecutar Tipos
                    editText.setText("");
                    iniciarAyudaTipo(true);
                }
                else if (editText.getText().toString().equals(">*<AyudaSubTiposActivity<644/2|714>")) {
                    // Caso ">*<AyudaTiposActivity<644/2|714>" es decir ejecutar Tipos
                    editText.setText("");
                    iniciarAyudaTipo(false);
                }
                else {
                    if (!fin) {
                        if (datos_interes) {
                            String text = quitarAcentosMayus(editText.getText().toString());
                            if (!text.isEmpty()) {
                                ChatModel model = new ChatModel(editText.getText().toString(), true, 3); // user send message
                                list_chat.add(model);

                                //remove user message
                                editText.setText("");
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
                                if (datos_interes) {
                                    if (fin) {
                                        outputText += "\n" + getString(R.string.fin_chat);
                                    }

                                    ChatModel respuesta = new ChatModel(outputText, false, 3);

                                    // Lo vacio para que no salga del while en el proximo mensaje hasta que tenga un mensaje
                                    outputText = "";

                                    list_chat.add(respuesta);

                                    CustomAdapter adapter2 = new CustomAdapter(list_chat, getApplicationContext());
                                    listView.setAdapter(adapter2);

                                    // Si hay que decir el mensaje de fin
                                    if (fin) {
                                        ChatModel fin_chat_pregunta = new ChatModel("", false, 0);
                                        list_chat.add(fin_chat_pregunta);
                                        adapter2 = new CustomAdapter(list_chat, getApplicationContext());
                                        adapter2.setEdit(editText);
                                        adapter2.setButton(btn_send_message);
                                        listView.setAdapter(adapter2);
                                        if (clase_lugar != null) {
                                            if(tamLista <= 1)
                                                iniciarDescripcion();
                                            else {
                                                iniciarVariosResultados();
                                            }
                                        }
                                    }
                                } else {
                                    ejecutarDatosLugarInteres(outputText);
                                    CustomAdapter adapter2 = new CustomAdapter(list_chat, getApplicationContext());
                                    adapter2.setEdit(editText);
                                    adapter2.setButton(btn_send_message);
                                    listView.setAdapter(adapter2);
                                }
                            } else {
                                Toast.makeText(MainActivity.this, getString(R.string.error_no_mensaje), Toast.LENGTH_LONG).show();
                            }
                            // Caso en el que preguntamos al usuario los datos de LugaresInteres
                            // que faltan o hasta que pone FIN o Fin
                        } else {
                            String aux_edit = editText.getText().toString();
                            String text = quitarAcentosMayus(aux_edit);
                            //remove user message
                            editText.setText("");
                            if (!text.isEmpty()) {
                                // Han de ser palabras sueltas, sin espacios
                                if (!text.contains(" ")) {
                                    if (!text.equals("fin")) {
                                        boolean correcto = true;
                                        // Modificar coste
                                        if (modificar_datos_lugar == 0) {
                                            // Si no es irrelevante
                                            if (!text.equals("paso")) {
                                                if (text.equals("gratis"))
                                                    clase_lugar.setCoste(0.0);
                                                else if (esDouble(text)) {
                                                    text = text.replace(',', '.'); // Los doubles necesitan '.'
                                                    clase_lugar.setCoste(Double.parseDouble(text));
                                                } else {
                                                    Toast.makeText(MainActivity.this, getString(R.string.error_precio_incorrecto), Toast.LENGTH_LONG).show();
                                                    correcto = false;
                                                }
                                            } else
                                                clase_lugar.setCoste(null);
                                        }
                                        // Modificar guia
                                        else if (modificar_datos_lugar == 1) {
                                            if (text.equals("si"))
                                                clase_lugar.setGuia(true);
                                            else if (text.equals("no"))
                                                clase_lugar.setGuia(false);
                                            else if (!text.equals("paso")) {
                                                Toast.makeText(MainActivity.this, getString(R.string.error_salir_incorrecto), Toast.LENGTH_LONG).show();
                                                correcto = false;
                                            } else {
                                                clase_lugar.setGuia(null);
                                            }
                                        }
                                        // Modificar idioma
                                        else if (modificar_datos_lugar == 2) {
                                            if (!text.equals("paso")) {
                                                if(Idioma.esTipoValido(primeraMayus(text)))
                                                    clase_lugar.setIdioma(Idioma.valueOf(text.toUpperCase()));
                                                else {
                                                    Toast.makeText(MainActivity.this, getString(R.string.error_idioma_incorrecto), Toast.LENGTH_LONG).show();
                                                    correcto = false;
                                                }
                                            }
                                            else
                                                clase_lugar.setIdioma(null);
                                        }
                                        // Modificar tipo
                                        else if (modificar_datos_lugar == 3) {
                                            if (!text.equals("paso")) {
                                                if(TipoLugar.esTipoValido(primeraMayus(text)))
                                                    clase_lugar.setTipo(TipoLugar.valueOf(text.toUpperCase()));
                                                else {
                                                    Toast.makeText(MainActivity.this, getString(R.string.error_tipo_incorrecto), Toast.LENGTH_LONG).show();
                                                    correcto = false;
                                                }
                                            }
                                            else
                                                clase_lugar.setTipo(null);
                                        }
                                        // Modificar sub_tipo
                                        else if (modificar_datos_lugar == 4) {
                                            if (!text.equals("paso")) {
                                                if(SubTipoLugar.esTipoValido(primeraMayus(text)))
                                                    clase_lugar.setSub_tipo(SubTipoLugar.valueOf(text.toUpperCase()));
                                                else {
                                                    Toast.makeText(MainActivity.this, getString(R.string.error_subtipo_incorrecto), Toast.LENGTH_LONG).show();
                                                    correcto = false;
                                                }
                                            }
                                            else
                                                clase_lugar.setSub_tipo(null);
                                        } else {
                                            Toast.makeText(MainActivity.this, getString(R.string.beta_error_modificar_datos), Toast.LENGTH_LONG).show();
                                        }

                                        if (correcto) {
                                            ChatModel datos = new ChatModel(aux_edit, true, 3);
                                            list_chat.add(datos);
                                            CustomAdapter adapter4 = new CustomAdapter(list_chat, getApplicationContext());
                                            adapter4.setEdit(editText);
                                            adapter4.setButton(btn_send_message);
                                            listView.setAdapter(adapter4);

                                            ejecutarDatosLugarInteres("");
                                            CustomAdapter adapter2 = new CustomAdapter(list_chat, getApplicationContext());
                                            adapter2.setEdit(editText);
                                            adapter2.setButton(btn_send_message);
                                            listView.setAdapter(adapter2);
                                        }
                                    }
                                    // Caso pone fin
                                    else if (text.equals("fin")) {
                                        ChatModel datos = new ChatModel(aux_edit, true, 3);
                                        list_chat.add(datos);
                                        CustomAdapter adapter4 = new CustomAdapter(list_chat, getApplicationContext());
                                        adapter4.setEdit(editText);
                                        adapter4.setButton(btn_send_message);
                                        listView.setAdapter(adapter4);

                                        datos_interes = true;
                                        finDatosLugar();
                                    }
                                } else {
                                    Toast.makeText(MainActivity.this, getString(R.string.error_dato_espacio), Toast.LENGTH_LONG).show();
                                }
                            } else {
                                Toast.makeText(MainActivity.this, getString(R.string.error_no_mensaje), Toast.LENGTH_LONG).show();
                            }
                        }
                    } else {
                        salirDuack();
                    }
                }
            }
        });

        // ###### Localizacion #######
        manejador = (LocationManager) getSystemService(LOCATION_SERVICE);
        // ###########################
    }

    public void iniciarVariosResultados() {
        Intent intent = new Intent(this, VariosResultadosActivity.class);
        startActivity(intent);
    }

    public void iniciarAyudaTipo(boolean tipo) {
        Intent intent = new Intent(this, AyudaTiposActivity.class);
        intent.putExtra("tipo", tipo); // true carga Tipo y false carga Subtipo
        AyudaTiposActivity.editText = editText;
        AyudaTiposActivity.button_main = btn_send_message;
        startActivity(intent);
    }

    public void iniciarDescripcion() {
        Intent intent = new Intent(this, DescripcionLugarActivity.class);
        intent.putExtra("id", clase_lugar.getId());
        startActivity(intent);
    }

    public void finDatosLugar() {
        Cursor cursor_aux = bd.extraeCursorConsulta(clase_lugar);
        List<LugarInteres> lista = bd.extraeLugarInteres(cursor_aux);
        if(lista != null) {
            if(lista.size() == 1) {
                String datos_li = getString(R.string.un_dato);
                datos_li += "\n" + getString(R.string.fin_chat);
                ChatModel fin_datos = new ChatModel(datos_li, false, 3);
                list_chat.add(fin_datos);
                ChatModel fin_chat_pregunta = new ChatModel("", false, 0);
                list_chat.add(fin_chat_pregunta);
                clase_lugar = lista.get(0);
                iniciarDescripcion();
                fin = true;
            }
            else {
                String datos_li = getString(R.string.varios_datos_1) + " " + lista.size()
                        + " " + getString(R.string.varios_datos_2);
                datos_li += "\n" + getString(R.string.fin_chat);
                ChatModel fin_datos = new ChatModel(datos_li, false, 3);
                list_chat.add(fin_datos);
                ChatModel fin_chat_pregunta = new ChatModel("", false, 0);
                list_chat.add(fin_chat_pregunta);
                Intent intent = new Intent(this, VariosResultadosActivity.class);
                startActivity(intent);
                fin = true;
            }
        } else {
            String datos_li = getString(R.string.error_no_datos);
            if(prefubicacion)
                    datos_li += "\n" + getString(R.string.error_no_datos_ubicacion);
            datos_li += "\n" + getString(R.string.fin_chat);
            ChatModel fin_datos = new ChatModel(datos_li, false, 3);
            list_chat.add(fin_datos);
            ChatModel fin_chat_pregunta = new ChatModel("", false, 0);
            list_chat.add(fin_chat_pregunta);
            fin = true;
        }
    }

    public void ejecutarDatosLugarInteres(String cadena) {
        // Comprobamos si estan todos los datos
        datos_interes = clase_lugar.todosDatos();
        if(!datos_interes) {
            // Si algun valor es null es que aun no lo hemos definido
            // Coste
            if (clase_lugar.getCoste() == null && !clase_lugar.getComprobadoId(0)) {
                ChatModel datos_coste= new ChatModel(cadena + getString(R.string.datos_coste)
                        + "\n" + getString(R.string.irrelevante), false, 3);
                list_chat.add(datos_coste);
                modificar_datos_lugar = 0;
            } // Guia
            else if (clase_lugar.isGuia() == null && !clase_lugar.getComprobadoId(1)) {
                ChatModel datos_guia = new ChatModel(cadena + getString(R.string.datos_guia)
                        + "\n" + getString(R.string.irrelevante), false, 3);
                list_chat.add(datos_guia);
                ChatModel fin_chat_opciones = new ChatModel("", false, 0);
                list_chat.add(fin_chat_opciones);
                modificar_datos_lugar = 1;
            } // Idioma
            else if (clase_lugar.getIdioma() == null && !clase_lugar.getComprobadoId(2)) {
                ChatModel datos_idioma = new ChatModel(cadena + getString(R.string.datos_idioma)
                        + "\n" + getString(R.string.irrelevante), false, 3);
                list_chat.add(datos_idioma);
                modificar_datos_lugar = 2;
            } // Tipo
            else if (clase_lugar.getTipo() == null && !clase_lugar.getComprobadoId(3)) {
                ChatModel datos_tipo = new ChatModel(cadena + getString(R.string.datos_tipo)
                        + "\n" + getString(R.string.irrelevante), false, 3);
                list_chat.add(datos_tipo);
                ChatModel fin_chat_opciones = new ChatModel("", false, 1);
                list_chat.add(fin_chat_opciones);
                modificar_datos_lugar = 3;
            } // Sub tipo
            else if (clase_lugar.getSub_tipo() == null && !clase_lugar.getComprobadoId(4)) {
                ChatModel datos_sub_tipo = new ChatModel(cadena + getString(R.string.datos_sub_tipo)
                        + "\n" + getString(R.string.irrelevante), false, 3);
                list_chat.add(datos_sub_tipo);
                ChatModel fin_chat_opciones = new ChatModel("", false, 2);
                list_chat.add(fin_chat_opciones);
                modificar_datos_lugar = 4;
            }
        } else {
            modificar_datos_lugar = -1;
            finDatosLugar();
        }
    }

    public void salirDuack() {
        // Ponemos clase_lugar a null
        if(clase_lugar == null)
            clase_lugar = new LugarInteres();
        else
            clase_lugar.vaciarLugar();
        datos_interes = true;
        outputText = "";
        // Caso de queremos hacer otra consulta
        if(quitarAcentosMayus(editText.getText().toString()).equals("si")) {
            ChatModel final_duack = new ChatModel(getString(R.string.nueva_consulta), false, 3);
            list_chat.add(final_duack);
            CustomAdapter adapter2 = new CustomAdapter(list_chat, getApplicationContext());
            adapter2.setEdit(editText);
            adapter2.setButton(btn_send_message);
            listView.setAdapter(adapter2);
            fin = false;
            editText.setText("");
        }
        // Caso de queremos salir
        else if(quitarAcentosMayus(editText.getText().toString()).equals("no")) {
            //fin = false;
            try {
                ChatModel final_duack = new ChatModel(getString(R.string.salir), false, 3);
                list_chat.add(final_duack);
                CustomAdapter adapter2 = new CustomAdapter(list_chat, getApplicationContext());
                adapter2.setEdit(editText);
                adapter2.setButton(btn_send_message);
                listView.setAdapter(adapter2);
                // No se cómo hacer que espere tiempo, si pongo Thread solo tarda mas en cargar
            } catch (Exception ex) {
                outputText = "El hilo ha dado el error: " + ex.toString();
            }
            finish();
        }
        // Caso incorrecto
        else {
            Toast.makeText(MainActivity.this, getString(R.string.error_salir_incorrecto), Toast.LENGTH_LONG).show();
        }
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
                                // ######## No funciona, hay que arreglarlo #######################
                                //int num_respuesta = (int) (Math.random()*(response.getText().size()));
                                outputText = response.getText().get(0);

                                if(response.getIntents().get(0).getIntent()
                                        .endsWith("OficinaTurismo")) {
                                    // Primero esperamos que output no este vacio
                                    while (outputText == null || outputText.equals("")) {
                                        try {
                                            Thread.sleep(TIEMPO_BUCLE);
                                        } catch (Exception ex) {
                                            outputText = "El hilo ha dado el error: " + ex.toString();
                                        }
                                    }
                                    boolean error_gps = false;
                                    // Si ubicacion es true comprobamos si esta conectado
                                    if(prefubicacion) {
                                        ultimaLocalizazion();
                                        if (!manejador.isProviderEnabled(LocationManager.GPS_PROVIDER) ||
                                                !manejador.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
                                            outputText = getString(R.string.sin_gps);
                                            error_gps = true;
                                        } else {
                                            if (ActivityCompat.checkSelfPermission(MainActivity.this,
                                                    Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                                                // Obtenemos la localizacion
                                                if (mejorLocaliz != null)
                                                    bd.setLatLong(mejorLocaliz.getLatitude(), mejorLocaliz.getLongitude());
                                                else {
                                                    outputText = getString(R.string.location_null);
                                                    error_gps = true;
                                                }
                                            } else {
                                                outputText = getString(R.string.permisos_gps);
                                                error_gps = true;
                                            }
                                        }
                                    }
                                    if(!error_gps) {
                                        clase_lugar.setTipo(TipoLugar.OFICINA);
                                        Cursor cursor_aux = bd.extraeCursorConsulta(clase_lugar);
                                        List<LugarInteres> lista = bd.extraeLugarInteres(cursor_aux);
                                        if (lista != null) {
                                            tamLista = lista.size();
                                            if (tamLista == 1) {
                                                outputText = getString(R.string.un_dato);
                                                clase_lugar = lista.get(0);
                                            } else {
                                                outputText = getString(R.string.varios_datos_1) + " " + lista.size()
                                                        + " " + getString(R.string.varios_datos_2);
                                            }
                                        } else {
                                            outputText = getString(R.string.error_no_datos);
                                            if(prefubicacion)
                                                outputText += "\n" + getString(R.string.error_no_datos_ubicacion);
                                        }
                                        fin = true;
                                    }
                                }
                                else if(response.getIntents().get(0).getIntent()
                                        .endsWith("Nombre")) {
                                    String para_bd = "";
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
                                        // Puede ser Universidad de Alicante o UA
                                        para_bd = "SELECT * FROM lugares WHERE nombre='" + primeraMayus(aux_nombre)
                                                + "' OR nombre2='" + primeraMayus(aux_nombre) + "'";
                                        // Plaza mayor de elda aunque tambien puede ser universidad de alicante
                                        if(aux_nombre.contains(" de ")) {
                                            aux_nombre = aux_nombre.replace(" de ", "-");
                                            String ciudad = aux_nombre.substring(aux_nombre.indexOf("-") + 1);
                                            String nombre = aux_nombre.substring(0, aux_nombre.indexOf("-"));
                                            para_bd += " OR (nombre='" + primeraMayus(nombre) + "' AND localidad='" + primeraMayus(ciudad) + "')";
                                        }
                                    }
                                    else
                                        outputText = getString(R.string.beta_error_nombre);

                                    Cursor cursor_aux = bd.extraeCursorNombre(para_bd);
                                    List<LugarInteres> lista = bd.extraeLugarInteres(cursor_aux);
                                    if(lista != null) {
                                        tamLista = lista.size();
                                        if(tamLista == 1) {
                                            outputText = getString(R.string.un_dato);
                                            clase_lugar = lista.get(0);
                                        } else {
                                            outputText = getString(R.string.varios_datos_1) + " " + lista.size()
                                                    + " " + getString(R.string.varios_datos_2);
                                        }
                                    }
                                    else
                                        outputText += "\n" + getString(R.string.error_no_datos);
                                    fin = true;
                                }
                                else if(response.getIntents().get(0).getIntent()
                                        .endsWith("LugarInteres")) {
                                    // Primero comprobamos que no sea null
                                    if(response.getEntities() != null) {
                                        // Iniciamos el bucle
                                        for (int t = 0; t < response.getEntities().size(); t++) {
                                            // Caso gratis
                                            if(response.getEntities().get(t).getValue().equals("gratis")) {
                                                clase_lugar.setCoste(0.0);
                                            }
                                            // Caso guia
                                            else if(response.getEntities().get(t).getValue().equals("guia")) {
                                                // Puede ser 'con' o 'sin'
                                                // En caso de no poner nada entendemos que si quiere guia
                                                if(inputText.contains(" sin "))
                                                    clase_lugar.setGuia(false);
                                                else
                                                    clase_lugar.setGuia(true);
                                            }
                                            // Caso idioma
                                            else if(response.getEntities().get(t).getEntity().equals("idioma")) {
                                                clase_lugar.setIdioma(Idioma.valueOf(response.getEntities().get(t).getValue().toUpperCase()));
                                            }
                                            // Caso tipo
                                            else if(response.getEntities().get(t).getEntity().equals("tipo")) {
                                                clase_lugar.setTipo(TipoLugar.valueOf(response.getEntities().get(t).getValue().toUpperCase()));
                                            }
                                            // Caso sub_tipo
                                            else if(response.getEntities().get(t).getEntity().equals("sub_tipo")) {
                                                clase_lugar.setSub_tipo(SubTipoLugar.valueOf(response.getEntities().get(t).getValue().toUpperCase()));
                                            }
                                        }
                                    }
                                    else {
                                        outputText += " es null ";
                                    }
                                    boolean error_gps = false;
                                    // Si ubicacion es true comprobamos si esta conectado
                                    if(prefubicacion) {
                                        ultimaLocalizazion();
                                        if (!manejador.isProviderEnabled(LocationManager.GPS_PROVIDER) ||
                                                !manejador.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
                                            outputText = getString(R.string.sin_gps);
                                            error_gps = true;
                                        } else {
                                            if (ActivityCompat.checkSelfPermission(MainActivity.this,
                                                    Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                                                // Obtenemos la localizacion
                                                if (mejorLocaliz != null)
                                                    bd.setLatLong(mejorLocaliz.getLatitude(), mejorLocaliz.getLongitude());
                                                else {
                                                    outputText = getString(R.string.location_null);
                                                    error_gps = true;
                                                }
                                            } else {
                                                outputText = getString(R.string.permisos_gps);
                                                error_gps = true;
                                            }
                                        }
                                    }
                                    if(!error_gps) {
                                        // Comprobamos si estan todos los datos
                                        datos_interes = clase_lugar.todosDatos();
                                        if (!datos_interes) {
                                            outputText = getString(R.string.lugar_interes_datos) + "\n";
                                        } else {
                                            outputText = "Buscaré en mi BD: " + clase_lugar.toString();
                                            fin = true;
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

    public String convertirANombre(String nombre) {
        // Por ahora consideramos que empezara por un articulo 'el' o 'la'
        // mientras que 'del' y 'de la' seran partes del nombre (jardín de la música, museo del calzado)
        String auxiliar = nombre;
        auxiliar = auxiliar.replace(" del ", " del#");
        auxiliar = auxiliar.replace(" de la ", " de la#");

        auxiliar = auxiliar.replace(" la ", " la-");
        auxiliar = auxiliar.replace(" el ", " el-");
        // Si tiene 'del' o 'de la'
        if(auxiliar.contains("#")) {
            auxiliar = auxiliar.replace("#", " ");
        }

        // Quitamos tambien los simbolos '?', '!'
        auxiliar = auxiliar.replace("?", "");
        auxiliar = auxiliar.replace("!", "");
        return auxiliar;
    }

    public boolean esDouble(String d) {
        for(int i = 0; i < d.length(); i++) {
            // Pueden ser numeros, ',' y '.'
            if(d.charAt(i) < '0' || d.charAt(i) > '9') {
                if(d.charAt(i) != ',' && d.charAt(i) != '.')
                    return false;
            }
        }
        return true;
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
        mostrarPreferencias();
        invalidateOptionsMenu();
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

    // --------------- Menu -----------------
    @Override public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true; /** true -> el menú ya está visible */
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuItem myItem = menu.findItem(R.id.menu_preferencia);
        if(prefubicacion)
            myItem.setTitle("Ubicacion");
        else
            myItem.setTitle(preflocalidad);
        return true;
    }

    @Override public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.menu_mail) {
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("text/plain");
            intent.putExtra(Intent.EXTRA_SUBJECT, "Encontrado error");
            intent.putExtra(Intent.EXTRA_TEXT, mensaje_mail());
            intent.putExtra(Intent.EXTRA_EMAIL, new String[] {"duackchatbot@gmail.com"});
            startActivity(intent);
            return true;
        }
        else if  (id == R.id.action_settings) {
            Intent intent = new Intent(this, PreferenciasActivity.class);
            startActivity(intent);
            return true;
        }
        else if (id == R.id.salir_settings) {
            finish();
            return true;
        }
        else if (id == R.id.acercaDe) {
            Intent intent = new Intent(this, AcercaDeActivity.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public String mensaje_mail() {
        String mensaje = "";
        for(int i = 0; i < list_chat.size(); i++) {
            mensaje += list_chat.get(i).getMessage() + "\n";
        }
        return mensaje;
    }
    // ------------------------------

    public String primeraMayus(String cadena) {
        String mayus = cadena.substring(0,1).toUpperCase();
        mayus += cadena.substring(1);
        return mayus;
    }

    public String quitarAcentosMayus(String cadena) {
        String nueva_cadena = "";
        cadena = cadena.toLowerCase();
        for(int i = 0; i < cadena.length(); i++) {
            // Si el caracter final es un char, no lo añadimos
            if(i == (cadena.length() - 1) && cadena.charAt(i) == ' ') {}
            else {
                if(cadena.charAt(i) == 'á')
                    nueva_cadena += 'a';
                else if(cadena.charAt(i) == 'é')
                    nueva_cadena += 'e';
                else if(cadena.charAt(i) == 'í')
                    nueva_cadena += 'i';
                else if(cadena.charAt(i) == 'ó')
                    nueva_cadena += 'o';
                else if(cadena.charAt(i) == 'ú')
                    nueva_cadena += 'u';
                else
                    nueva_cadena += cadena.charAt(i);
            }
        }
        return nueva_cadena;
    }

    public void mostrarPreferencias(){
        SharedPreferences pref =
                PreferenceManager.getDefaultSharedPreferences(this);
        prefubicacion = pref.getBoolean("ubicacion",true);
        prefpais = pref.getString("pais","?");
        prefprovincia = pref.getString("provincia","?");
        preflocalidad = pref.getString("localidad","?");
        String aux_dis = pref.getString("distancia","?");
        if(esDouble(aux_dis))
            prefdistancia = Double.parseDouble(aux_dis);
        else
            prefdistancia = 1.0; // Si no, ponemos un kilometro

        // Lo añadimos a la base de datos
        bd.setPrefubicacion(prefubicacion, prefdistancia);
        bd.setPrefpais(prefpais);
        bd.setPrefprovincia(prefprovincia);
        bd.setPreflocalidad(preflocalidad);
    }

}
