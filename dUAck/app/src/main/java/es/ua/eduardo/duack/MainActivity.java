package es.ua.eduardo.duack;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.support.design.widget.FloatingActionButton;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import es.ua.eduardo.duack.Adapter.CustomAdapter;
import es.ua.eduardo.duack.Helper.HttpDataHandler;
import es.ua.eduardo.duack.Models.ChatModel;
import es.ua.eduardo.duack.Models.DuackModel;

public class MainActivity extends AppCompatActivity {

    ListView listView;
    EditText editText;
    List<ChatModel> list_chat = new ArrayList<>();
    FloatingActionButton btn_send_message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = (ListView)findViewById(R.id.list_of_message);
        editText = (EditText)findViewById(R.id.user_message);
        btn_send_message = (FloatingActionButton)findViewById(R.id.fab);

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

                    ChatModel respuesta = new ChatModel("Hola", false);

                    list_chat.add(respuesta);

                    CustomAdapter adapter2 = new CustomAdapter(list_chat, getApplicationContext());
                    listView.setAdapter(adapter2);

                    //remove user message
                    editText.setText("");
                }
                else {
                    Toast.makeText (MainActivity.this ,getString(R.string.error_no_mensaje),2).show();
                }
            }
        });
    }
}
