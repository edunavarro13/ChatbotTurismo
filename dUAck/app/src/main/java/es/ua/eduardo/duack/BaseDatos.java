package es.ua.eduardo.duack;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

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
        insertarEstadios(bd);
        insertarCastillos(bd);
        insertarYacimientos(bd);
    }

    public void insertarJardines(SQLiteDatabase bd) {
        // -------------- Elda ------------------
        // Jardin de la musica
        bd.execSQL("INSERT INTO lugares VALUES (null, " +
                "'Jardin de la musica', null, 'Bonito parque de Elda', " +
                "38.476249412621016, -0.7917687000000342, 'Jardin de la musica', 'Elda', " +
                "'Alicante', 'España', 0.0, 0, null, " +
                "'" + TipoLugar.JARDIN.getTexto() + "', null, 'jardindelamusica.png', 965380402, null)");
        // Plaza castelar
        bd.execSQL("INSERT INTO lugares VALUES (null, " +
                "'Plaza castelar', null, 'Bonito parque de Elda', " +
                "38.47866806262212, -0.7898506999999881, 'Plaza castelar', 'Elda', " +
                "'Alicante', 'España', 0.0, 0, null, " +
                "'" + TipoLugar.JARDIN.getTexto() + "', null, 'plazacastelar.png', null, null)");
        // Plaza del zapatero
        bd.execSQL("INSERT INTO lugares VALUES (null, " +
                "'Plaza trabajadores del calzado', 'Plaza del zapatero', 'Bonito parque de Elda', " +
                "38.476193712621, -0.7943401999999651, 'Plaza trabajadores del calzado, 11', 'Elda', " +
                "'Alicante', 'España', 0.0, 0, null, " +
                "'" + TipoLugar.JARDIN.getTexto() + "', null, 'plazadelzapatero.png', null, null)");
        // Parque de la concordia
        bd.execSQL("INSERT INTO lugares VALUES (null, " +
                "'Parque de la concordia', null, 'Bonito parque de Elda', " +
                "38.48092106262314, -0.7904552999999623, 'Parque de la Concordia', 'Elda', " +
                "'Alicante', 'España', 0.0, 0, null, " +
                "'" + TipoLugar.JARDIN.getTexto() + "', null, 'parquedelaconcordia.png', null, null)");
    }

    public void insertarOficinasTurismo(SQLiteDatabase bd) {
        // -------------- Elda ------------------
        // Tourist info elda
        bd.execSQL("INSERT INTO lugares VALUES (null, " +
                "'Tourist info elda', null, 'Oficina de turismo de la ciudad de Elda', " +
                "38.47790161262177, -0.7960233999999673, 'Calle nueva, 14', 'Elda', " +
                "'Alicante', 'España', 0.0, 0, '" + Idioma.ESPAÑOL.getTexto() + "', " +
                "'" + TipoLugar.OFICINA.getTexto() + "', null, 'touristinfo.png', 966980300, null)");
    }

    public void insertarMuseos(SQLiteDatabase bd) {
        // -------------- Elda ------------------
        // Museo del calzado
        bd.execSQL("INSERT INTO lugares VALUES (null, " +
                "'Museo del calzado', 'Museo del calzado elda', 'Museo del calzado de Elda', " +
                "38.474002312619994, -0.7952750999999125, 'Avenida chapí, 32', 'Elda', " +
                "'Alicante', 'España', 0.0, 0, '" + Idioma.ESPAÑOL.getTexto() + "', " +
                "'" + TipoLugar.MUSEO.getTexto() + "', '" + SubTipoLugar.CALZADO.getTexto() + "', 'museodelcalzado.png', 965383021, 'museocalzado.com')");
        // Museo Arqueologico Municipal
        bd.execSQL("INSERT INTO lugares VALUES (null, " +
                "'Museo arqueologico municipal', 'Museo arqueologico municipal elda', 'Museo arqueologico de Elda', " +
                "38.47761431262163, -0.7909325000000536, 'Calle Príncipe de Asturias, 40', 'Elda', " +
                "'Alicante', 'España', 0.0, 0, '" + Idioma.ESPAÑOL.getTexto() + "', " +
                "'" + TipoLugar.MUSEO.getTexto() + "', '" + SubTipoLugar.ARQUEOLOGICO.getTexto() + "', 'museoarqueologicomunicipal.png', 966989233, null)");
        // Museo Etnologico de Elda
        bd.execSQL("INSERT INTO lugares VALUES (null, " +
                "'Museo etnologico elda', 'Museo etnologico', 'Museo etnologico de Elda', " +
                "38.47434801262015, -0.7926439999999957, 'Calle Eduardo Dato, 22', 'Elda', " +
                "'Alicante', 'España', 0.0, 0, '" + Idioma.ESPAÑOL.getTexto() + "', " +
                "'" + TipoLugar.MUSEO.getTexto() + "', '" + SubTipoLugar.ETNOLOGICO.getTexto() + "', 'museoetnologicoelda.png', 966841021, null)");
    }

    public void insertarTeatros(SQLiteDatabase bd) {
        // -------------- Elda ------------------
        // Teatro castelar
        bd.execSQL("INSERT INTO lugares VALUES (null, " +
                "'Teatro castelar', null, 'Teatro de la ciudad de Elda', " +
                "38.47704941262138, -0.7952086000000236, 'Calle jardines, 24', 'Elda', " +
                "'Alicante', 'España', 0.0, 0, '" + Idioma.ESPAÑOL.getTexto() + "', " +
                "'" + TipoLugar.TEATRO.getTexto() + "', null, 'teatrocastelar.png', 966982222, 'teatrocastelar.wordpress.com')");
        // Auditorio Adoc
        bd.execSQL("INSERT INTO lugares VALUES (null, " +
                "'Auditorio adoc', null, 'Auditorio de la ciudad de Elda', " +
                "38.47631981262106, -0.7972641000000067, 'Calle Paquito Vera, 6', 'Elda', " +
                "'Alicante', 'España', 0.0, 0, '" + Idioma.ESPAÑOL.getTexto() + "', " +
                "'" + TipoLugar.TEATRO.getTexto() + "', null, 'auditorioadoc.png', 965383025, 'www.adoc-elda.com')");
    }

    public void insertarPlazas(SQLiteDatabase bd) {
        // -------------- Elda ------------------
        // Plaza mayor
        bd.execSQL("INSERT INTO lugares VALUES (null, " +
                "'Plaza mayor', null, 'Plaza mayor de Elda', " +
                "38.478351812621966, -0.7938149000000294, 'Calle jardines perpendicular, 30', 'Elda', " +
                "'Alicante', 'España', 0.0, 0, null, " +
                "'" + TipoLugar.PLAZA.getTexto() + "', null, 'plazamayor.png', 966982222, 'elda.es')");
    }

    public void insertarIglesias(SQLiteDatabase bd) {
        // -------------- Elda ------------------
        // Inmaculada
        bd.execSQL("INSERT INTO lugares VALUES (null, " +
                "'Parroquia de la inmaculada concepción', 'Inmaculada', 'Iglesia Inmaculada de Elda', " +
                "38.478092912621854, -0.7878170000000182, 'Calle Donoso Cortés', 'Elda', " +
                "'Alicante', 'España', 0.0, 0, '" + Idioma.ESPAÑOL.getTexto() + "', " +
                "'" + TipoLugar.IGLESIA.getTexto() + "', null, 'inmaculada.png', 965380823, null)");
        // Santa Ana
        bd.execSQL("INSERT INTO lugares VALUES (null, " +
                "'Parroquia de santa ana', 'Santa ana', 'Iglesia Santa Ana de Elda', " +
                "38.47920391262236, -0.7958945999999969, 'Calle los Giles, 0', 'Elda', " +
                "'Alicante', 'España', 0.0, 0, '" + Idioma.ESPAÑOL.getTexto() + "', " +
                "'" + TipoLugar.IGLESIA.getTexto() + "', null, 'santaana.png', 965385208, 'parroquiadesantaana.wordpress.com')");
        // Ermita De San Antón Elda
        bd.execSQL("INSERT INTO lugares VALUES (null, " +
                "'Ermita de san anton', 'Ermita de san anton elda', 'Ermita de San Antón de Elda', " +
                "38.48055841262297, -0.7984593999999561, 'Calle Independencia, 39', 'Elda', " +
                "'Alicante', 'España', 0.0, 0, '" + Idioma.ESPAÑOL.getTexto() + "', " +
                "'" + TipoLugar.IGLESIA.getTexto() + "', null, 'ermitasananton.png', null, null)");
    }

    public void insertarEstadios(SQLiteDatabase bd) {
        // -------------- Elda ------------------
        // Estadio nuevo pepico amat
        bd.execSQL("INSERT INTO lugares VALUES (null, " +
                "'Estadio nuevo pepico amat', 'Nuevo pepico amat', 'Campo de fútbol de Elda', " +
                "38.46748411261705, -0.7961378999999624, 'Avenida de Ronda, 72', 'Elda', " +
                "'Alicante', 'España', 0.0, 0, '" + Idioma.ESPAÑOL.getTexto() + "', " +
                "'" + TipoLugar.ESTADIO.getTexto() + "', null, 'nuevopepicoamat.png', 965380402, 'www.elda.es')");
    }

    public void insertarCastillos(SQLiteDatabase bd) {
        // -------------- Elda ------------------
        // Castillo del Conde de Elda
        bd.execSQL("INSERT INTO lugares VALUES (null, " +
                "'Castillo del conde de elda', 'Castillo de elda', 'Castillo de la ciudad de Elda', " +
                "38.48105421262318, -0.7973575999999412, 'Castillo del Conde de Elda', 'Elda', " +
                "'Alicante', 'España', 0.0, 0, null, " +
                "'" + TipoLugar.CASTILLO.getTexto() + "', null, 'castillopalacio.png', null, null)");
    }

    public void insertarYacimientos(SQLiteDatabase bd) {
        // -------------- Elda ------------------
        // Yacimiento Arqueologico Del Monastil
        bd.execSQL("INSERT INTO lugares VALUES (null, " +
                "'Yacimiento arqueologico del monastil', 'Yacimiento del monastil', 'Yacimiento arqueológico de Elda', " +
                "38.49355821262883, -0.788763900000049, 'CV-835', 'Elda', " +
                "'Alicante', 'España', 0.0, 0, null, " +
                "'" + TipoLugar.YACIMIENTO.getTexto() + "', '" + SubTipoLugar.ARQUEOLOGICO.getTexto() + "', 'yacimientomonastil.png', 966980300, 'www.elda.com/empresas/empresa/1292/yacimiento-monastil-1292')");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion,
                                    int newVersion) {
    }

    public List<LugarInteres> extraeLugarInteres(Cursor cursor) {
        List<LugarInteres> lista_lugares = new ArrayList<LugarInteres>();
        if (!cursor.moveToFirst()){
            //el cursor está vacío
            return null;
        }
        boolean no_salir = true;
        do {
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
            if (cursor.getInt(11) == 0)
                lugar.setGuia(false);
            else
                lugar.setGuia(true);
            String auxiliar = cursor.getString(12);
            if (auxiliar != null)
                lugar.setIdioma(Idioma.valueOf(auxiliar.toUpperCase()));
            else
                lugar.setIdioma(null);
            auxiliar = cursor.getString(13);
            if (auxiliar != null)
                lugar.setTipo(TipoLugar.valueOf(auxiliar.toUpperCase()));
            else
                lugar.setTipo(null);
            auxiliar = cursor.getString(14);
            if (auxiliar != null)
                lugar.setSub_tipo(SubTipoLugar.valueOf(auxiliar.toUpperCase()));
            else
                lugar.setSub_tipo(null);
            lugar.setFoto(cursor.getString(15));
            lugar.setTelefono(cursor.getInt(16));
            lugar.setUrl(cursor.getString(17));
            lista_lugares.add(lugar);

            no_salir = cursor.moveToNext(); // Significa que hay mas resultados
        } while (no_salir);
        return lista_lugares;
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
