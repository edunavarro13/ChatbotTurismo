package es.ua.eduardo.duack;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class AdaptadorHoteles extends
        RecyclerView.Adapter<AdaptadorHoteles.ViewHolder> {
    protected List<Hoteles> hoteles;           //Lugares a mostrar
    protected LayoutInflater inflador;   //Crea Layouts a partir del XML
    protected Context contexto;          //Lo necesitamos para el inflador
    Cursor cursor;

    protected View.OnClickListener onClickListener;

    public AdaptadorHoteles(Context contexto, List<Hoteles> hoteles) {
        this.contexto = contexto;
        this.hoteles = hoteles;
        inflador = (LayoutInflater) contexto
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    //Creamos nuestro ViewHolder, con los tipos de elementos a modificar
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView nombre, direccion;
        public ImageView foto;
        public ViewHolder(View itemView) {
            super(itemView);
            nombre = (TextView) itemView.findViewById(R.id.nombre);
            direccion = (TextView) itemView.findViewById(R.id.direccion);
            foto = (ImageView) itemView.findViewById(R.id.foto);
        }
    }

    // Creamos el ViewHolder con las vista de un elemento sin personalizar
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Inflamos la vista desde el xml
        View v = inflador.inflate(R.layout.elemento_lista_hoteles, null);
        v.setOnClickListener(onClickListener);
        return new ViewHolder(v);
    }

    // Usando como base el ViewHolder y lo personalizamos
    @Override
    public void onBindViewHolder(ViewHolder holder, int posicion) {
        Hoteles lugar = hoteles.get(posicion);
        personalizaVista(holder, lugar);
    }

    // Personalizamos un ViewHolder a partir de un lugar
    public void personalizaVista(ViewHolder holder, Hoteles lugar) {
        holder.nombre.setText(lugar.getNombre());
        holder.direccion.setText(lugar.getDireccion());
        Hoteles.elegirFoto(holder.foto, lugar.getFoto());

    }

    public Cursor getCursor() {
        return cursor;
    }
    public void setCursor(Cursor cursor) {
        this.cursor = cursor;
    }
    public int idLugarPosicion(int posicion) {
        return hoteles.get(posicion).getId();
    }
    public int idPosicion(int posicion) {
        cursor.moveToPosition(posicion);
        return cursor.getInt(0);
    }

    // Indicamos el número de elementos de la lista
    @Override public int getItemCount() {
        return hoteles.size();
    }

    public void setOnItemClickListener(View.OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }
}
