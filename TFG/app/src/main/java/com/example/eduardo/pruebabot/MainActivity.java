package com.example.eduardo.pruebabot;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.github.kittinunf.fuel.Fuel;
import com.github.kittinunf.fuel.core.FuelError;
import com.github.kittinunf.fuel.core.Handler;
import com.github.kittinunf.fuel.core.Request;
import com.github.kittinunf.fuel.core.Response;
import com.ibm.watson.developer_cloud.conversation.v1.ConversationService;
import com.ibm.watson.developer_cloud.conversation.v1.model.MessageRequest;
import com.ibm.watson.developer_cloud.conversation.v1.model.MessageResponse;
import com.ibm.watson.developer_cloud.http.ServiceCallback;

import java.util.Date;

public class MainActivity extends AppCompatActivity {


    //The ConversationService class of the Watson SDK has all the methods you'll need to
    // communicate with the Conversation service. Therefore, the first thing you need to do
    // in your Activity class is create an instance of it. Its constructor expects a version
    // date, the service's username, and its password.
    ConversationService myConversationService;

    //Next, to be able to work with the widgets present in the layout XML file, you must get
    // references to them using the findViewById() method.
    EditText userInput;
    TextView conversation;
    Button boton;
    String outputText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        userInput = (EditText)findViewById(R.id.user_input);
        conversation = (TextView)findViewById(R.id.conversation);
        boton = (Button) findViewById(R.id.button);

        Date hoy = new Date();

        myConversationService = new ConversationService(
                "2017-05-26",//hoy.toString(),
                getString(R.string.username),
                getString(R.string.password)
        );

        boton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String inputText = userInput.getText().toString();
                conversation.append(
                        Html.fromHtml("<p><b>Tu:</b> " + inputText + "</p>")
                );

                // Optionally, clear edittext
                userInput.setText("");

                MessageRequest request = new MessageRequest.Builder().inputText(inputText).build();

                try {
                    myConversationService
                            .message(getString(R.string.workspace), request)
                            .enqueue(new ServiceCallback<MessageResponse>() {
                                @Override
                                public void onResponse(MessageResponse response) {
                                    outputText = response.getText().get(0);

                                    if(response.getIntents().get(0).getIntent()
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
                                    }
                                }

                                @Override
                                public void onFailure(Exception e) {
                                }
                            });
                } catch (Exception ex) {
                    conversation.append(
                            Html.fromHtml("<p>" + ex.toString() + "</p>")
                    );
                }
            }
        });
    }
}
