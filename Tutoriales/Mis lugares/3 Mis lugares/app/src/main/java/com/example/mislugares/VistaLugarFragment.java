package com.example.mislugares;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.DateFormat;
import java.util.Date;

public class VistaLugarFragment extends Fragment {
    private long id;
    private Lugar lugar;
    final static int RESULTADO_GALERIA= 2;
    final static int RESULTADO_FOTO= 3;
    private Uri uriFoto;

    final static int RESULTADO_EDITAR = 1;

    private View v;

    @Override
    public View onCreateView(LayoutInflater inflador, ViewGroup contenedor,
                             Bundle savedInstanceState) {
        View vista = inflador.inflate(R.layout.vista_lugar,contenedor,false);
        setHasOptionsMenu(true);
        LinearLayout pUrl = (LinearLayout) vista.findViewById(R.id.vl_url);
        pUrl.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                pgWeb(null);
            }
        });
        return vista;
    }
    @Override
    public void onActivityCreated(Bundle state) {
        super.onActivityCreated(state);
        v = getView();
        Bundle extras = getActivity().getIntent().getExtras();
        if (extras != null) {
            id = extras.getLong("id", -1);
            if (id != -1) {
                actualizarVistas(id);
            }
        }
    }

    void actualizaLugar(){
        int _id = SelectorFragment.adaptador.idPosicion((int) id);
        MainActivity.lugares.actualiza(_id, lugar);
        SelectorFragment.adaptador.setCursor(MainActivity.lugares.extraeCursor());
        SelectorFragment.adaptador.notifyItemChanged((int) id);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.vista_lugar, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.accion_compartir:
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_TEXT,
                        lugar.getNombre() + " - " + lugar.getUrl());
                startActivity(intent);
                return true;
            case R.id.accion_llegar:
                verMapa(null);
                return true;
            case R.id.accion_editar:
                editarLugar(null);
                return true;
            case R.id.accion_borrar:
                int _id = SelectorFragment.adaptador.idPosicion((int) id);
                borrarLugar((int) _id);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void galeria(View view) {
        Intent intent = new Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, RESULTADO_GALERIA);
    }

    public void verMapa(View view) {
        Uri uri;
        double lat = lugar.getPosicion().getLatitud();
        double lon = lugar.getPosicion().getLongitud();
        if (lat != 0 || lon != 0) {
            uri = Uri.parse("geo:" + lat + "," + lon);
        } else {
            uri = Uri.parse("geo:0,0?q=" + lugar.getDireccion());
        }
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);
    }

    public void llamadaTelefono(View view) {
        startActivity(new Intent(Intent.ACTION_DIAL,
                Uri.parse("tel:" + lugar.getTelefono())));
    }
    public void pgWeb(View view) {
        startActivity(new Intent(Intent.ACTION_VIEW,
                Uri.parse(lugar.getUrl())));
    }



    public void actualizarVistas(final long id) {
        this.id = id;
        lugar = SelectorFragment.adaptador.lugarPosicion((int) id);
        if (lugar != null) {
            TextView nombre = (TextView) v.findViewById(R.id.vl_nombre);
            nombre.setText(lugar.getNombre());
            ImageView logo_tipo = (ImageView) v.findViewById(R.id.vl_logo_tipo);
            logo_tipo.setImageResource(lugar.getTipo().getRecurso());
            TextView tipo = (TextView) v.findViewById(R.id.vl_tipo);
            tipo.setText(lugar.getTipo().getTexto());
            if (lugar.getDireccion().isEmpty()) {
                v.findViewById(R.id.vl_direccion).setVisibility(View.GONE);
            } else {
                TextView direccion = (TextView) v.findViewById(R.id.vl_direccion);
                direccion.setText(lugar.getDireccion());
            }
            if (lugar.getTelefono() == 0) {
                v.findViewById(R.id.vl_telefono).setVisibility(View.GONE);
            } else {
                TextView telefono = (TextView) v.findViewById(R.id.vl_telefono);
                telefono.setText(Integer.toString(lugar.getTelefono()));
            }
            if (lugar.getUrl().isEmpty()) {
                v.findViewById(R.id.vl_url).setVisibility(View.GONE);
            } else {
                TextView url = (TextView) v.findViewById(R.id.vl_url);
                url.setText(lugar.getUrl());
            }
            if (lugar.getComentario().isEmpty()) {
                v.findViewById(R.id.vl_comentario).setVisibility(View.GONE);
            } else {
                TextView comentario = (TextView) v.findViewById(R.id.vl_comentario);
                comentario.setText(lugar.getComentario());
            }
            TextView fecha = (TextView) v.findViewById(R.id.vl_fecha);
            fecha.setText(DateFormat.getDateInstance().format(
                    new Date(lugar.getFecha())));
            TextView hora = (TextView) v.findViewById(R.id.vl_hora);
            hora.setText(DateFormat.getTimeInstance().format(
                    new Date(lugar.getFecha())));
            RatingBar valoracion = (RatingBar) v.findViewById(R.id.vl_valoracion);
            valoracion.setOnRatingBarChangeListener(null);
            valoracion.setRating(lugar.getValoracion());
            valoracion.setOnRatingBarChangeListener(
                    new RatingBar.OnRatingBarChangeListener() {
                        @Override
                        public void onRatingChanged(RatingBar ratingBar,
                                                    float valor, boolean fromUser) {
                            lugar.setValoracion(valor);
                        }
                    });
            ponerFoto((ImageView)v.findViewById(R.id.foto), lugar.getFoto());
        }
    }

    public void editarLugar(View view){
        Intent intent = new Intent(getActivity(), EdicionLugarActivity.class);
        startActivityForResult(intent, RESULTADO_EDITAR);
    }

    public void tomarFoto(View view) {
        Intent intent = new Intent("MediaStore.ACTION_IMAGE_CAPTURE");
        uriFoto = Uri.fromFile(new File(
                Environment.getExternalStorageDirectory() + File.separator
                        + "img_" + (System.currentTimeMillis() / 1000) + ".jpg"));
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uriFoto);
        startActivityForResult(intent, RESULTADO_FOTO);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent
            data) {
        if (requestCode == RESULTADO_EDITAR) {
            actualizarVistas(id);
            v.findViewById(R.id.scrollView1).invalidate();
        } else if (requestCode == RESULTADO_GALERIA) {
            if (resultCode == Activity.RESULT_OK) {
                lugar.setFoto(data.getDataString());
                ponerFoto((ImageView)v.findViewById(R.id.foto), lugar.getFoto());
                actualizaLugar();
            } else {
                Toast.makeText(getActivity(), "Foto no cargada", Toast.LENGTH_LONG).show();
            }
        } else if (requestCode == RESULTADO_FOTO) {
            if (resultCode == Activity.RESULT_OK
                    && lugar!=null && uriFoto!=null) {
                lugar.setFoto(uriFoto.toString());
                ponerFoto((ImageView)v.findViewById(R.id.foto), lugar.getFoto());
                actualizaLugar();
            } else {
                Toast.makeText(getActivity(), "Error en captura", Toast.LENGTH_LONG).show();
            }
        }
    }

    protected void ponerFoto(ImageView imageView, String uri) {
        if (uri != null) {
            imageView.setImageBitmap(reduceBitmap(getActivity(), uri, 1024,   1024));
        } else{
            imageView.setImageBitmap(null);
        }
    }

    public static Bitmap reduceBitmap(Context contexto, String uri,
                                      int maxAncho, int maxAlto) {
        try {
            final BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeStream(contexto.getContentResolver()
                    .openInputStream(Uri.parse(uri)), null, options);
            options.inSampleSize = (int) Math.max(
                    Math.ceil(options.outWidth / maxAncho),
                    Math.ceil(options.outHeight / maxAlto));
            options.inJustDecodeBounds = false;
            return BitmapFactory.decodeStream(contexto.getContentResolver()
                    .openInputStream(Uri.parse(uri)), null, options);
        } catch (FileNotFoundException e) {
            Toast.makeText(contexto, "Fichero/recurso no encontrado",
                    Toast.LENGTH_LONG).show();
            e.printStackTrace();
            return null;
        }
    }

    public void eliminarFoto(View view) {
        lugar.setFoto(null);
        ponerFoto((ImageView)v.findViewById(R.id.foto), null);
        actualizaLugar();
    }

    public void borrarLugar(final int id) {
        final EditText entrada = new EditText(getActivity());
        entrada.setText("0");
        new AlertDialog.Builder(getActivity())
                .setTitle("Borrado de lugar")
                .setMessage("¿Estás seguro que quieres eliminar este lugar?")
                .setView(entrada)
                .setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        MainActivity.lugares.borrar(id);
                        SelectorFragment.adaptador.setCursor(
                                MainActivity.lugares.extraeCursor());
                        SelectorFragment.adaptador.notifyDataSetChanged();
                        getActivity().finish();
                    }})
                .setNegativeButton("Cancelar", null)
                .show();
    }
}
