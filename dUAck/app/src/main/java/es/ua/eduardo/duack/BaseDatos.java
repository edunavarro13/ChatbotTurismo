package es.ua.eduardo.duack;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import es.ua.eduardo.duack.Models.SubTipoLugar;

public class BaseDatos extends SQLiteOpenHelper {

    Context contexto;

    public BaseDatos(Context contexto) {
        super(contexto, "duack", null, 1);
        this.contexto = contexto;
    }

    @Override
    public void onCreate(SQLiteDatabase bd) {
        bd.execSQL("CREATE TABLE lugares ("+
                "_id INTEGER PRIMARY KEY AUTOINCREMENT, "+
                "nombre TEXT, " +
                "nombre2 TEXT, " +
                "descripcion TEXT, " +
                "latitud REAL, " +
                "longitud REAL, " +
                "direccion TEXT, " +
                "localidad TEXT, " +
                "provincia TEXT, " +
                "pais TEXT, " +
                "precio REAL, " +
                "guia INTEGER, " +
                "idioma TEXT, " +
                "tipo INTEGER, " +
                "subtipo INTEGER, " +
                "foto TEXT, " +
                "telefono INTEGER, " +
                "url TEXT)");
        // Insertar tipos
        insertarJardines(bd);
        insertarOficinasTurismo(bd);
        insertarMuseos(bd);
        insertarTeatros(bd);
        insertarPlazas(bd);
        insertarIglesias(bd);
    }

    public void insertarJardines(SQLiteDatabase bd) {
        // -------------- Elda ------------------
        // Jardin de la musica
        bd.execSQL("INSERT INTO lugares VALUES (null, " +
                "'Jardin de la musica', null, 'Bonito parque de Elda', " +
                "38.476249412621016, -0.7917687000000342, 'Jardin de la musica', 'Elda', " +
                "'Alicante', 'España', 0.0, 0, null, " +
                "'" + TipoLugar.JARDIN.getTexto() + "', null, null, 965380402, null)");
        // Plaza castelar
        bd.execSQL("INSERT INTO lugares VALUES (null, " +
                "'Plaza castelar', null, 'Bonito parque de Elda', " +
                "38.47866806262212, -0.7898506999999881, 'Plaza castelar', 'Elda', " +
                "'Alicante', 'España', 0.0, 0, null, " +
                "'" + TipoLugar.JARDIN.getTexto() + "', null, null, null, null)");
        // Plaza del zapatero
        bd.execSQL("INSERT INTO lugares VALUES (null, " +
                "'Plaza trabajadores del calzado', 'Plaza del zapatero', 'Bonito parque de Elda', " +
                "38.476193712621, -0.7943401999999651, 'Plaza trabajadores del calzado, 11', 'Elda', " +
                "'Alicante', 'España', 0.0, 0, null, " +
                "'" + TipoLugar.JARDIN.getTexto() + "', null, null, null, null)");
        // Parque de la concordia
        bd.execSQL("INSERT INTO lugares VALUES (null, " +
                "'Parque de la concordia', null, 'Bonito parque de Elda', " +
                "38.48092106262314, -0.7904552999999623, 'Parque de la Concordia', 'Elda', " +
                "'Alicante', 'España', 0.0, 0, null, " +
                "'" + TipoLugar.JARDIN.getTexto() + "', null, null, null, null)");
    }

    public void insertarOficinasTurismo(SQLiteDatabase bd) {
        // -------------- Elda ------------------
        // Tourist info elda
        bd.execSQL("INSERT INTO lugares VALUES (null, " +
                "'Tourist info elda', null, 'Oficina de turismo de la ciudad de Elda', " +
                "38.47790161262177, -0.7960233999999673, 'Calle nueva, 14', 'Elda', " +
                "'Alicante', 'España', 0.0, 0, '" + Idioma.ESPAÑOL.getTexto() + "', " +
                "'" + TipoLugar.OFICINA.getTexto() + "', null, null, 966980300, null)");
    }

    public void insertarMuseos(SQLiteDatabase bd) {
        // -------------- Elda ------------------
        // Museo del calzado
        bd.execSQL("INSERT INTO lugares VALUES (null, " +
                "'Museo del calzado', null, 'Museo del calzado de Elda', " +
                "38.474002312619994, -0.7952750999999125, 'Avenida chapí, 32', 'Elda', " +
                "'Alicante', 'España', 0.0, 0, '" + Idioma.ESPAÑOL.getTexto() + "', " +
                "'" + TipoLugar.MUSEO.getTexto() + "', '" + SubTipoLugar.CALZADO.getTexto() + "', null, 965383021, 'museocalzado.com')");
    }

    public void insertarTeatros(SQLiteDatabase bd) {
        // -------------- Elda ------------------
        // Teatro castelar
        bd.execSQL("INSERT INTO lugares VALUES (null, " +
                "'Teatro castelar', null, 'Teatro de la ciudad de Elda', " +
                "38.47704941262138, -0.7952086000000236, 'Calle jardines, 24', 'Elda', " +
                "'Alicante', 'España', 0.0, 0, '" + Idioma.ESPAÑOL.getTexto() + "', " +
                "'" + TipoLugar.TEATRO.getTexto() + "', null, null, 966982222, 'teatrocastelar.wordpress.com')");
    }

    public void insertarPlazas(SQLiteDatabase bd) {
        // -------------- Elda ------------------
        // Plaza mayor
        bd.execSQL("INSERT INTO lugares VALUES (null, " +
                "'Plaza mayor', null, 'Plaza mayor de Elda', " +
                "38.478351812621966, -0.7938149000000294, 'Calle jardines perpendicular, 30', 'Elda', " +
                "'Alicante', 'España', 0.0, 0, null, " +
                "'" + TipoLugar.PLAZA.getTexto() + "', null, null, 966982222, 'elda.es')");
    }

    public void insertarIglesias(SQLiteDatabase bd) {
        // -------------- Elda ------------------
        // Inmaculada
        bd.execSQL("INSERT INTO lugares VALUES (null, " +
                "'Parroquia de la inmaculada concepción', 'Inmaculada', 'Iglesia Inmaculada de Elda', " +
                "38.478092912621854, -0.7878170000000182, 'Calle Donoso Cortés', 'Elda', " +
                "'Alicante', 'España', 0.0, 0, '" + Idioma.ESPAÑOL.getTexto() + "', " +
                "'" + TipoLugar.IGLESIA.getTexto() + "', null, null, 965380823, null)");
        // Santa Ana
        bd.execSQL("INSERT INTO lugares VALUES (null, " +
                "'Parroquia de santa ana', 'Santa ana', 'Iglesia Santa Ana de Elda', " +
                "38.47920391262236, -0.7958945999999969, 'Calle los Giles, 0', 'Elda', " +
                "'Alicante', 'España', 0.0, 0, '" + Idioma.ESPAÑOL.getTexto() + "', " +
                "'" + TipoLugar.IGLESIA.getTexto() + "', null, null, 965385208, 'https://parroquiadesantaana.wordpress.com/')");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion,
                                    int newVersion) {
    }

    public LugarInteres extraeLugarInteres(Cursor cursor) {
        if (!cursor.moveToFirst()){
            //el cursor está vacío
            return null;
        }
        LugarInteres lugar = new LugarInteres();
        lugar.setId(cursor.getInt(0));
        lugar.setNombre(cursor.getString(1));
        lugar.setNombre2(cursor.getString(2));
        lugar.setDescripcion(cursor.getString(3));
        lugar.setLatitud(cursor.getDouble(4));
        lugar.setLongitud(cursor.getDouble(5));
        lugar.setDireccion(cursor.getString(6));
        lugar.setLocalidad(cursor.getString(7));
        lugar.setProvincia(cursor.getString(8));
        lugar.setPais(cursor.getString(9));
        lugar.setCoste(cursor.getDouble(10));
        if(cursor.getInt(11) == 0)
            lugar.setGuia(false);
        else
            lugar.setGuia(true);
        String auxiliar = cursor.getString(12);
        if(auxiliar != null)
            lugar.setIdioma(Idioma.valueOf(auxiliar.toUpperCase()));
        else
            lugar.setIdioma(null);
        auxiliar = cursor.getString(13);
        if(auxiliar != null)
            lugar.setTipo(TipoLugar.valueOf(auxiliar.toUpperCase()));
        else
            lugar.setTipo(null);
        auxiliar = cursor.getString(14);
        if(auxiliar != null)
            lugar.setSub_tipo(SubTipoLugar.valueOf(auxiliar.toUpperCase()));
        else
            lugar.setSub_tipo(null);
        lugar.setFoto(cursor.getString(15));
        lugar.setTelefono(cursor.getInt(16));
        lugar.setUrl(cursor.getString(17));
        return lugar;
    }

    public Cursor extraeCursor() {
        String consulta = "SELECT * FROM lugares";
        SQLiteDatabase bd = getReadableDatabase();
        return bd.rawQuery(consulta, null);
    }

    public Cursor extraeCursorConsulta(LugarInteres lugar) {
        String consulta = "SELECT * FROM lugares";
        String aux_consulta = " WHERE";
        boolean hayAntes = false;
        if(lugar.getCoste() != null) {
            aux_consulta += " precio<=" + lugar.getCoste();
            hayAntes = true;
        }
        if(lugar.getGuia() != null) {
            if(hayAntes)
                aux_consulta += " AND";
            if(lugar.getGuia())
                aux_consulta += " guia=1";
            else
                aux_consulta += " guia=0";
            hayAntes = true;
        }
        if(lugar.getIdioma() != null) {
            if(hayAntes)
                aux_consulta += " AND";
            aux_consulta += " idioma='" + lugar.getIdioma().getTexto() + "'";
            hayAntes = true;
        }
        // Si no hay tipo, obviamente no hay subtipo
        if(lugar.getTipo() != null) {
            if(hayAntes)
                aux_consulta += " AND";
            aux_consulta += " tipo='" + lugar.getTipo().getTexto() + "'";
            if(lugar.getSub_tipo() != null)
                aux_consulta += " AND subtipo='" + lugar.getSub_tipo().getTexto() + "'";
        }

        // Comprobamos si se ha añadido algo al string y lo añadimos
        if(aux_consulta.length() > 6)
            consulta += aux_consulta;

        SQLiteDatabase bd = getReadableDatabase();
        return bd.rawQuery(consulta, null);
    }

    public Cursor extraeCursorNombre(String consultanombre) {
        SQLiteDatabase bd = getReadableDatabase();
        Cursor cursor = bd.rawQuery(consultanombre, null);
        return cursor;
    }
}
