package es.ua.eduardo.duack.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.github.library.bubbleview.BubbleTextView;

import java.util.List;

import es.ua.eduardo.duack.AyudaTiposActivity;
import es.ua.eduardo.duack.DescripcionLugarActivity;
import es.ua.eduardo.duack.MainActivity;
import es.ua.eduardo.duack.Models.ChatModel;
import es.ua.eduardo.duack.R;

/**
 * Created by reale on 2/28/2017.
 */

public class CustomAdapter extends BaseAdapter {

    private List<ChatModel> list_chat_models;
    private Context context;
    private LayoutInflater layoutInflater;
    private EditText editText;
    private FloatingActionButton button_main;

    public CustomAdapter(List<ChatModel> list_chat_models, Context context) {
        this.list_chat_models = list_chat_models;
        this.context = context;
        layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public void setEdit(EditText edit) {
        editText = edit;
    }

    public void setButton(FloatingActionButton button_main) {
        this.button_main = button_main;
    }

    @Override
    public int getCount() {
        return list_chat_models.size();
    }

    @Override
    public Object getItem(int position) {
        return list_chat_models.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if(view == null)
        {
            // Caso mensaje pregunta
            if(list_chat_models.get(position).isPregunta() == 0) {
                view = layoutInflater.inflate(R.layout.list_item_message_pregunta, null);
                Button button_si = (Button) view.findViewById(R.id.boton_si);
                final Button button_no = (Button) view.findViewById(R.id.boton_no);

                button_si.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        editText.setText("Sí"); // Si que lo pilla pero no lo imprime
                        button_main.callOnClick();
                    }
                });

                button_no.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        editText.setText("No"); // Si que lo pilla pero no lo imprime
                        button_main.callOnClick();
                    }
                });
            }
            // Caso mensaje informacion tipos
            else if(list_chat_models.get(position).isPregunta() == 1) {
                view = layoutInflater.inflate(R.layout.list_item_message_informacion, null);
                ImageButton button_inf = (ImageButton) view.findViewById(R.id.boton_inf);

                button_inf.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        editText.setText(">*<AyudaTiposActivity<644/2|714>"); // Si que lo pilla pero no lo imprime
                        button_main.callOnClick();
                    }
                });
            }
            // Caso mensaje informacion sub tipos
            else if(list_chat_models.get(position).isPregunta() == 2) {

            }
            else {
                if (list_chat_models.get(position).isSend)
                    view = layoutInflater.inflate(R.layout.list_item_message_send, null);
                else if (!list_chat_models.get(position).isSend)
                    view = layoutInflater.inflate(R.layout.list_item_message_recv, null);


                BubbleTextView text_message = (BubbleTextView) view.findViewById(R.id.text_message);
                text_message.setText(list_chat_models.get(position).message);
            }

        }
        return view;
    }
}
