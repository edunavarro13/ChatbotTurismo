package es.ua.eduardo.duack;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.List;

import static es.ua.eduardo.duack.MainActivity.bd;

public class AdaptadorLugares extends
        RecyclerView.Adapter<AdaptadorLugares.ViewHolder> {
    protected List<LugarInteres> lugares;           //Lugares a mostrar
    protected LayoutInflater inflador;   //Crea Layouts a partir del XML
    protected Context contexto;          //Lo necesitamos para el inflador
    Cursor cursor;

    protected View.OnClickListener onClickListener;

    public AdaptadorLugares(Context contexto, List<LugarInteres> lugares, Cursor cursor) {
        this.contexto = contexto;
        this.lugares = lugares;
        inflador = (LayoutInflater) contexto
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.cursor = cursor;
    }
    //Creamos nuestro ViewHolder, con los tipos de elementos a modificar
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView nombre, direccion, tipo;
        public ImageView foto;
        public ViewHolder(View itemView) {
            super(itemView);
            nombre = (TextView) itemView.findViewById(R.id.nombre);
            direccion = (TextView) itemView.findViewById(R.id.direccion);
            foto = (ImageView) itemView.findViewById(R.id.foto);
            tipo = (TextView) itemView.findViewById(R.id.tipo);
        }
    }

    // Creamos el ViewHolder con las vista de un elemento sin personalizar
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Inflamos la vista desde el xml
        View v = inflador.inflate(R.layout.elemento_lista, null);
        v.setOnClickListener(onClickListener);
        return new ViewHolder(v);
    }

    // Usando como base el ViewHolder y lo personalizamos
    @Override
    public void onBindViewHolder(ViewHolder holder, int posicion) {
        LugarInteres lugar = lugares.get(posicion);
        personalizaVista(holder, lugar);
    }

    // Personalizamos un ViewHolder a partir de un lugar
    public void personalizaVista(ViewHolder holder, LugarInteres lugar) {
        holder.nombre.setText(lugar.getNombre());
        holder.direccion.setText(lugar.getDireccion());
        LugarInteres.elegirFoto(holder.foto, lugar.getFoto());

        String tipo = lugar.getTipo().getTexto();
        if(lugar.getSub_tipo() != null)
            tipo += " - " + lugar.getSub_tipo().getTexto();
        holder.tipo.setText(tipo);
    }

    public Cursor getCursor() {
        return cursor;
    }
    public void setCursor(Cursor cursor) {
        this.cursor = cursor;
    }
    public int idLugarPosicion(int posicion) {
        return (bd.extraeLugarInteres(cursor)).get(posicion).getId();
    }
    public int idPosicion(int posicion) {
        cursor.moveToPosition(posicion);
        return cursor.getInt(0);
    }

    // Indicamos el número de elementos de la lista
    @Override public int getItemCount() {
        return lugares.size();
    }

    public void setOnItemClickListener(View.OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }
}
