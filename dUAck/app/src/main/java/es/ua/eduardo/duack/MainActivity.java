package es.ua.eduardo.duack;

import android.os.AsyncTask;
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
                if(!text.isEmpty()) {
                    ChatModel model = new ChatModel(text, true); // user send message
                    list_chat.add(model);

                    // Cogemos el mensaje de IBM
                    mensajeIBM(text);
                    // Si outputText esta vacio es que aun no ha cargado el mensaje
                    // asi que esperamos hasta que lo este y lo cargamos
                    while (outputText == null || outputText == "") {
                        try {
                            Thread.sleep(1000);
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

                    //remove user message
                    editText.setText("");
                }
                else {
                    Toast.makeText (MainActivity.this ,getString(R.string.error_no_mensaje),Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    protected void mensajeIBM(String inputText) {
        MessageRequest request = new MessageRequest.Builder().inputText(inputText).build();
        try {
            myConversationService
                    .message(getString(R.string.workspace), request)
                    .enqueue(new ServiceCallback<MessageResponse>() {
                        @Override
                        public void onResponse(MessageResponse response) {
                            outputText = response.getText().get(0);

                            /*if(response.getIntents().get(0).getIntent()
                                    .endsWith("Solicitudes")) {
                                String quotesURL =
                                        "https://api.forismatic.com/api/1.0/" +
                                                "?method=getQuote&format=text&lang=en";

                                Fuel.get(quotesURL)
                                        .responseString(new Handler<String>() {
                                            @Override
                                            public void success(Request request,
                                                                Response response, String quote) {
                                                conversation.append(
                                                        Html.fromHtml("<p><b>Bot:</b> " +
                                                                quote + "</p>")
                                                );
                                            }

                                            @Override
                                            public void failure(Request request,
                                                                Response response,
                                                                FuelError fuelError) {
                                            }
                                        });
                            }
                            else {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        conversation.append(
                                                Html.fromHtml("<p><b>Bot:</b> " +
                                                        outputText + "</p>")
                                        );
                                    }
                                });
                            }*/
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
}
