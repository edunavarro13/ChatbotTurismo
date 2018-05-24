package es.ua.eduardo.duack;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.preference.PreferenceManager;

import java.util.ArrayList;
import java.util.List;

import es.ua.eduardo.duack.Models.SubTipoLugar;

public class BaseDatos extends SQLiteOpenHelper {

    Context contexto;
    Cursor ultimocursor;
    boolean ultimocursor_nombre = false; // Si es true no comprobamos preferencias, false, si

    // ### Datos preferencias ###
    private boolean prefubicacion;
    private String prefpais, prefprovincia, preflocalidad;
    private double prefdistancia;
    private Double latitud, longitud = null;

    public BaseDatos(Context contexto) {
        super(contexto, "duack", null, 1);
        this.contexto = contexto;
        this.ultimocursor = null;
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
        insertarEdificios(bd);
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
        // Parque el Cocoliche
        bd.execSQL("INSERT INTO lugares VALUES (null, " +
                "'Parque el cocoliche', 'Cocoliche', 'Bonito parque de Elda', " +
                "38.471774412619, -0.7914078999999674, 'Parque el Cocoliche', 'Elda', " +
                "'Alicante', 'España', 0.0, 0, null, " +
                "'" + TipoLugar.JARDIN.getTexto() + "', null, 'parquecocoliche.png', null, null)");

        // -------------- Petrer ------------------
        // Parque 9 de Octubre
        bd.execSQL("INSERT INTO lugares VALUES (null, " +
                "'Parque 9 de octubre', '9 de octubre', 'Bonito parque de Petrer', " +
                "38.4802477126228, -0.7722310000000334, 'Avenida de Salinetas', 'Petrer', " +
                "'Alicante', 'España', 0.0, 0, null, " +
                "'" + TipoLugar.JARDIN.getTexto() + "', null, 'parque9octubre.png', 966989400, null)");

        // -------------- San vicente ------------------
        // Universidad de Alicante
        bd.execSQL("INSERT INTO lugares VALUES (null, " +
                "'Universidad de alicante', 'Ua', 'Universidad de san vicente considerada zona verde', " +
                "38.3852246125799, -0.5132248999999547, 'Carrer San Vicente del Raspeig', 'San vicente', " +
                "'Alicante', 'España', 0.0, 0, null, " +
                "'" + TipoLugar.JARDIN.getTexto() + "', null, 'universidadalicante.png', 965903400, 'eps.ua.es')");
        // Parc L'hort de Torrent
        bd.execSQL("INSERT INTO lugares VALUES (null, " +
                "'Parc l`hort de torrent', null, 'Bonito parque de san vicente', " +
                "38.39407501258391, -0.5118817000000035, 'Parc L`hort de Torrent', 'San vicente', " +
                "'Alicante', 'España', 0.0, 0, null, " +
                "'" + TipoLugar.JARDIN.getTexto() + "', null, 'parctorrent.png', 965675065, 'www.raspeig.es/pagina/la-ciudad-y-sus-rincones')");
        // Parque Presidente Adolfo Suarez
        bd.execSQL("INSERT INTO lugares VALUES (null, " +
                "'Parque presidente adolfo suarez', 'Parque adolfo suarez', 'Bonito parque de san vicente', " +
                "38.40389331258833, -0.5342500000000427, 'Parque Presidente Adolfo Suarez', 'San vicente', " +
                "'Alicante', 'España', 0.0, 0, null, " +
                "'" + TipoLugar.JARDIN.getTexto() + "', null, 'parqueadolfosuarez.png', 965660104, null)");
        // Parque juan xxiii
        bd.execSQL("INSERT INTO lugares VALUES (null, " +
                "'Parque juan xxiii', 'Parc joan xxiii', 'Bonito parque de san vicente', " +
                "38.39881531258606, -0.5286238999999568, 'Parque Juan XXIII', 'San vicente', " +
                "'Alicante', 'España', 0.0, 0, null, " +
                "'" + TipoLugar.JARDIN.getTexto() + "', null, 'parquejuan23.png', null, null)");

    }

    public void insertarOficinasTurismo(SQLiteDatabase bd) {
        // -------------- Elda ------------------
        // Tourist info elda
        bd.execSQL("INSERT INTO lugares VALUES (null, " +
                "'Tourist info elda', null, 'Oficina de turismo de la ciudad de Elda', " +
                "38.47790161262177, -0.7960233999999673, 'Calle nueva, 14', 'Elda', " +
                "'Alicante', 'España', 0.0, 0, '" + Idioma.ESPAÑOL.getTexto() + "', " +
                "'" + TipoLugar.OFICINA.getTexto() + "', null, 'touristinfo.png', 966980300, null)");

        // -------------- Petrer ------------------
        // Tourist info petrer
        bd.execSQL("INSERT INTO lugares VALUES (null, " +
                "'Tourist info petrer', null, 'Oficina de turismo de la ciudad de Petrer', " +
                "38.48475461262487, -0.7698967000000039, 'Carrer Rector Bartolomé Munoz, 2', 'Petrer', " +
                "'Alicante', 'España', 0.0, 0, '" + Idioma.ESPAÑOL.getTexto() + "', " +
                "'" + TipoLugar.OFICINA.getTexto() + "', null, 'touristinfopetrer.png', 966989401, 'petrer.es/cas/oficina_de_turismo.html')");

        // -------------- San vicente ------------------
        // Oficina de Turismo Sant Vicent del Raspeig
        bd.execSQL("INSERT INTO lugares VALUES (null, " +
                "'Oficina de turismo sant vicent del raspeig', 'Oficina de turismo', 'Oficina de turismo de la ciudad de San Vicente', " +
                "38.3963047125849, -0.524886400000014, 'Plaza España, 1', 'San vicente', " +
                "'Alicante', 'España', 0.0, 0, '" + Idioma.ESPAÑOL.getTexto() + "', " +
                "'" + TipoLugar.OFICINA.getTexto() + "', null, 'touristinfosanvicente.png', 965660104, 'www.raspeig.es')");

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

        // -------------- Petrer ------------------
        // Museo Dámaso Navarro
        bd.execSQL("INSERT INTO lugares VALUES (null, " +
                "'Museo damaso navarro', null, 'Museo del calzado de Petrer', " +
                "38.48479201262489, -0.7700035000000298, 'Plaça de Baix, 10', 'Petrer', " +
                "'Alicante', 'España', 0.0, 0, '" + Idioma.ESPAÑOL.getTexto() + "', " +
                "'" + TipoLugar.MUSEO.getTexto() + "', '" + SubTipoLugar.ARQUEOLOGICO.getTexto() + "', 'museodamaso.png', 966989400, 'museodamasonavarro.blogspot.com.es')");

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

        // -------------- Petrer ------------------
        // Teatro Cervantes
        bd.execSQL("INSERT INTO lugares VALUES (null, " +
                "'Teatro Cervantes', null, 'Teatro de la ciudad de Petrer', " +
                "38.48430331262465, -0.7721100999999635, 'Carrer Gabriel Payà, 18', 'Petrer', " +
                "'Alicante', 'España', 0.0, 0, '" + Idioma.ESPAÑOL.getTexto() + "', " +
                "'" + TipoLugar.TEATRO.getTexto() + "', null, 'teatrocervantes.png', 966989409, null)");

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
                "'Parroquia de la inmaculada concepcion', 'Inmaculada', 'Iglesia Inmaculada de Elda', " +
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

        // -------------- San vicente ------------------
        // Parroquia De San Vicente Ferrer
        bd.execSQL("INSERT INTO lugares VALUES (null, " +
                "'Parroquia de san vicente ferrer', null, 'Parroquia de la ciudad de San vicente', " +
                "38.39636550399276, -0.5251848699973607, 'Plaza España, 1,', 'San vicente', " +
                "'Alicante', 'España', 0.0, 0, '" + Idioma.ESPAÑOL.getTexto() + "', " +
                "'" + TipoLugar.IGLESIA.getTexto() + "', null, 'parroquiavicenteferrer.png', 965661087, null)");
        // Parroquia de Santa Isabel
        bd.execSQL("INSERT INTO lugares VALUES (null, " +
                "'Parroquia de santa isabel', 'Santa isabel', 'Parroquia de la ciudad de San vicente', " +
                "38.3816371125783, -0.5002769999999828, 'Calle Colonia Sta. Isabel', 'San vicente', " +
                "'Alicante', 'España', 0.0, 0, '" + Idioma.ESPAÑOL.getTexto() + "', " +
                "'" + TipoLugar.IGLESIA.getTexto() + "', null, 'parroquiasantaisabel.png', 965660369, 'www.conferenciaepiscopal.es')");

    }

    public void insertarEstadios(SQLiteDatabase bd) {
        // -------------- Elda ------------------
        // Estadio nuevo pepico amat
        bd.execSQL("INSERT INTO lugares VALUES (null, " +
                "'Estadio nuevo pepico amat', 'Nuevo pepico amat', 'Campo de fútbol de Elda', " +
                "38.46748411261705, -0.7961378999999624, 'Avenida de Ronda, 72', 'Elda', " +
                "'Alicante', 'España', 0.0, 0, '" + Idioma.ESPAÑOL.getTexto() + "', " +
                "'" + TipoLugar.ESTADIO.getTexto() + "', null, 'nuevopepicoamat.png', 965380402, 'www.elda.es')");

        // -------------- Petrer ------------------
        // Estadio nuevo pepico amat
        bd.execSQL("INSERT INTO lugares VALUES (null, " +
                "'Campo municipal el barxell', 'Barxell', 'Campo de fútbol de Petrer', " +
                "38.48738921262604, -0.7712540999999646, 'Autovía de Alicante', 'Petrer', " +
                "'Alicante', 'España', 0.0, 0, '" + Idioma.ESPAÑOL.getTexto() + "', " +
                "'" + TipoLugar.ESTADIO.getTexto() + "', null, 'campobarxell.png', 966955013, 'www.udpetrelensecf.com/Club/Instalaciones')");

    }

    public void insertarCastillos(SQLiteDatabase bd) {
        // -------------- Elda ------------------
        // Castillo del Conde de Elda
        bd.execSQL("INSERT INTO lugares VALUES (null, " +
                "'Castillo del conde de elda', 'Castillo de elda', 'Castillo de la ciudad de Elda', " +
                "38.48105421262318, -0.7973575999999412, 'Castillo del Conde de Elda', 'Elda', " +
                "'Alicante', 'España', 0.0, 0, null, " +
                "'" + TipoLugar.CASTILLO.getTexto() + "', null, 'castillopalacio.png', null, null)");

        // -------------- Petrer ------------------
        // Castillo de Petrer
        bd.execSQL("INSERT INTO lugares VALUES (null, " +
                "'Castillo de petrer', null, 'Castillo de la ciudad de Petrer', " +
                "38.484318112624656, -0.7678058999999848, 'Carrer Pujada al Castell, 10', 'Petrer', " +
                "'Alicante', 'España', 0.0, 0, null, " +
                "'" + TipoLugar.CASTILLO.getTexto() + "', null, 'castillopetrer.png', 966989400, 'www.jdiezarnal.com/castillodepetrer.html')");

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

    public void insertarEdificios(SQLiteDatabase bd) {
        // -------------- San vicente ------------------
        // Los Molinos
        bd.execSQL("INSERT INTO lugares VALUES (null, " +
                "'Caseron los molinos', 'Los molinos', 'Famoso edificio de San vicente', " +
                "38.39114561258257, -0.5220348000000286, 'Calle Enric Valor', 'San vicente', " +
                "'Alicante', 'España', 0.0, 0, null, " +
                "'" + TipoLugar.EDIFICIO.getTexto() + "', null, 'losmolinos.png', null, null)");
        // Casa de Evaristo el Lleig
        bd.execSQL("INSERT INTO lugares VALUES (null, " +
                "'Casa de evaristo el lleig', null, 'Famoso edificio de San vicente', " +
                "38.39574858858059, -0.5238999773207524, 'Calle Pintor Picasso, 7', 'San vicente', " +
                "'Alicante', 'España', 0.0, 0, null, " +
                "'" + TipoLugar.EDIFICIO.getTexto() + "', null, 'casaevaristolleig.png', null, null)");

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
        boolean agregar = true;
        do {
            // Comprobamos la distancia
            if(!ultimocursor_nombre && prefubicacion) {
                double latbd = cursor.getDouble(4);
                double lonbd = cursor.getDouble(5);
                // Si esta mas lejos que prefdistancia, no lo agregamos
                if(distancia(latitud, longitud, latbd, lonbd) > prefdistancia)
                    agregar = false;
            }
            if(agregar) {
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
            }
            agregar = true;
            no_salir = cursor.moveToNext(); // Significa que hay mas resultados
        } while (no_salir);

        if(lista_lugares.size() < 1)
            return null;
        return lista_lugares;
    }

    public Cursor extraeCursor() {
        String consulta = "SELECT * FROM lugares";
        SQLiteDatabase bd = getReadableDatabase();
        ultimocursor = bd.rawQuery(consulta, null);
        return ultimocursor;
    }

    public LugarInteres lugarInteresPorId(int id) {
        ultimocursor_nombre = true;
        String consulta = "SELECT * FROM lugares WHERE _id=" + id;
        SQLiteDatabase bd = getReadableDatabase();
        Cursor cursor = bd.rawQuery(consulta, null);
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
            return lugar;

    }

    public Cursor extraeCursorConsulta(LugarInteres lugar) {
        ultimocursor_nombre = false;
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
        if(lugar.getTipo() != null) {
            if(hayAntes)
                aux_consulta += " AND";
            aux_consulta += " tipo='" + lugar.getTipo().getTexto() + "'";
            hayAntes = true;
        }
        if(lugar.getSub_tipo() != null) {
            if(hayAntes)
                aux_consulta += " AND";
            aux_consulta += " subtipo='" + lugar.getSub_tipo().getTexto() + "'";
        }

        // Comprobamos si se ha añadido algo al string y lo añadimos
        if(aux_consulta.length() > 6) {
            // Si ubicacion no esta marcada, buscamos por localidad
            if(!prefubicacion) {
                aux_consulta += " AND localidad='" + preflocalidad + "' AND provincia='"
                        + prefprovincia + "' AND pais='" + prefpais + "'";
            }
            consulta += aux_consulta;
        }
        else {
            // Si ubicacion no esta marcada, buscamos por localidad
            // Solo pone WHERE
            if(!prefubicacion) {
                aux_consulta += " localidad='" + preflocalidad + "' AND provincia='"
                        + prefprovincia + "' AND pais='" + prefpais + "'";
                consulta += aux_consulta;
            }
        }

        SQLiteDatabase bd = getReadableDatabase();
        ultimocursor = bd.rawQuery(consulta, null);
        return ultimocursor;
    }

    public Cursor extraeCursorNombre(String consultanombre) {
        ultimocursor_nombre = true;
        SQLiteDatabase bd = getReadableDatabase();
        ultimocursor = bd.rawQuery(consultanombre, null);
        return ultimocursor;
    }

    public Cursor getUltimocursor() {
        return ultimocursor;
    }

    public void setUltimocursor(Cursor ultimocursor) {
        this.ultimocursor = ultimocursor;
    }

    public void setPrefubicacion(boolean prefubicacion, double prefdistancia) {
        this.prefubicacion = prefubicacion;
        this.prefdistancia = prefdistancia;
    }

    public void setPrefpais(String prefpais) {
        this.prefpais = prefpais;
    }

    public void setPrefprovincia(String prefprovincia) {
        this.prefprovincia = prefprovincia;
    }

    public void setPreflocalidad(String preflocalidad) {
        this.preflocalidad = preflocalidad;
    }

    public void setLatLong(double latitud, double longitud) {
        this.latitud = latitud;
        this.longitud = longitud;
    }

    public double distancia(double lat1, double lon1, double lat2, double lon2)
    {
        double R = 6378.137; //Radio de la tierra en km
        double dLat  = ( lat2 - lat1 )*Math.PI/180;
        double dLong = ( lon2 - lon1 )*Math.PI/180;

        double a = Math.sin(dLat/2) * Math.sin(dLat/2) + Math.cos(lat1*Math.PI/180) * Math.cos(lat2*Math.PI/180) * Math.sin(dLong/2) * Math.sin(dLong/2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
        double d = R * c;

        return (double)Math.round(d * 1000d) / 1000d;
    }
}
