package Clases;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.respirapp.R;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TreeMap;

public class cEstructuras {

    public static class cUsuario{
        public static int dni;
        public static String nombre;
        public static String apellido;

        public static void registrar(Activity activity, Context context, String dni, String nombre, String apellido, String email, String password){
            cFunciones.setCache(activity, "usuario_email", email);
            cFunciones.setCache(activity, "usuario_password", password);
            Map<String, String> parameters = new TreeMap<String, String>();
            parameters.put("env", context.getString(R.string.env));
            parameters.put("name", nombre);
            parameters.put("lastname", apellido);
            parameters.put("dni", dni);
            parameters.put("email", email);
            parameters.put("password", password);
            parameters.put("commission", context.getString(R.string.commission));
            parameters.put("group", context.getString(R.string.group));
            String params = cFunciones.getParamsString(parameters);
            AsyncTask<String, String, JSONObject> registerAsyncTask = new cAPI(activity, context, "POST", "register");
            registerAsyncTask.execute(params);

        }

        public static void loguear(Activity activity, Context context, String email, String password){
            cFunciones.setCache(activity, "usuario_email", email);
            cFunciones.setCache(activity, "usuario_password", password);
            Map<String, String> parameters = new TreeMap<String, String>();
            parameters.put("email", email);
            parameters.put("password", password);
            String params = cFunciones.getParamsString(parameters);
            AsyncTask<String, String, JSONObject> registerAsyncTask = new cAPI(activity, context, "POST","login");
            registerAsyncTask.execute(params);
        }
    }

    public static class cEvento{
        public static int id;
        public static String typeEvent;
        public static String description;

        public static void registrar(Activity activity, Context context, String environment, String method, String description){
            Map<String, String> parameters = new TreeMap<>();
            parameters.put("env", environment);
            parameters.put("type_events", method);
            parameters.put("description", description);
            String params = cFunciones.getParamsString(parameters);

            AsyncTask<String, String, JSONObject> registerAsyncTask = new cAPI(activity, context, "POST","event");
            registerAsyncTask.execute(params);
        }
    }

    public static class cRefresh{
        public static void refrescar(Activity activity, Context context){
            AsyncTask<String, String, JSONObject> registerAsyncTask = new cAPI(activity, context, "PUT","refresh");
            registerAsyncTask.execute("");
        }
    }

    public static class cDB{

//        public static ArrayList<cListViewColumns> ejerciciosEnfriantes = new ArrayList<cListViewColumns>() {{add(new cListViewColumns("Sitali Pranayama", "icono", "Este ejercicio comienza sentado con la espalda recta.\n Comenzamos a respirar profundo, y luego de 15 segundos exalamos todo el aire", "Este ejercicio no es recomendado para personas con baja frecuencia card??aca debido a su baja acci??n"));
//                                                                                                            add(new cListViewColumns("Sitali dos", "img_home", "desc", "info"));
//                                                                                                            add(new cListViewColumns("Sitali tres", "ejercicios_calentantes", "desc", "info"));
//                                                                                                            add(new cListViewColumns("Sitali cuatro", "icono", "desc", "info"));
//                                                                                                            add(new cListViewColumns("Sitali cinco", "icono", "desc", "info"));}};
//        public static ArrayList<cListViewColumns> ejerciciosEquilibrantes = new ArrayList<cListViewColumns>() {{add(new cListViewColumns("Respiraci??n cuadrada", "icono", "desc", "info"));
//                                                                                                            add(new cListViewColumns("Respiraci??n circular", "icono", "desc", "info"));
//                                                                                                            add(new cListViewColumns("Respiraci??n equilibrada", "icono", "desc", "info"));
//                                                                                                            add(new cListViewColumns("Respiraci??n cuatro", "icono", "desc", "info"));
//                                                                                                            add(new cListViewColumns("Respiraci??n cinco", "icono", "desc", "info"));}};
//        public static ArrayList<cListViewColumns> ejerciciosCalentantes = new ArrayList<cListViewColumns>() {{add(new cListViewColumns("Calentante 1", "icono", "desc", "info"));
//                                                                                                            add(new cListViewColumns("Calentante 2", "icono", "desc", "info"));
//                                                                                                            add(new cListViewColumns("Calentante 3", "icono", "desc", "info"));
//                                                                                                            add(new cListViewColumns("Calentante 4", "icono", "desc", "info"));
//                                                                                                            add(new cListViewColumns("Calentante 5", "icono", "desc", "info"));}};
public static ArrayList<cListViewColumns> ejerciciosEnfriantes = new ArrayList<cListViewColumns>() {{add(new cListViewColumns("Chandra Bedhana", "imagen1", "Nos sentamos de piernas cruzadas en una posici??n c??moda o sobre una silla, con la columna erguida.\n" +
        "Mano izquierda: la colocamos sobre la rodilla con la palma hacia arriba. Dedo ??ndice y pulgar toc??ndose entre s??.\n" +
        "Mano derecha: colocamos el dedo ??ndice y mayor en la palma de la mano o sobre la frente en el entrecejo. Dedo pulgar en el orificio nasal derecho y dedo anular sobre el orificio nasal izquierdo.\n" +
        "Presionamos el orificio nasal derecho, se inhala por el izquierdo, se tapa el izquierdo y se exhala por el derecho.\n" +
        "Todas las inhalaciones ocurren por el lado izquierdo y todas las que exhalaciones ocurren por el lado derecho.\n" +
        "La secuencia se puede repetir de 5 a 10 veces y, con el tiempo, se puede incrementar la cantidad de repeticiones.", "Este ejercicio no tiene contraindicaciones."));
    add(new cListViewColumns("Sitali", "imagen5", "Nos sentamos en una posici??n c??moda en un espacio tranquilo.\n" +
            "Realizamos 3 inhalaciones y exhalaciones por la nariz. \n" +
            "Sacamos la lengua hacia afuera con forma de rulo.\n" +
            "Se inhala por la boca, se cierra la boca, se retiene el aire en el mismo tiempo de la inhalaci??n y se exhala suavemente por la nariz en la misma cantidad de tiempo.\n" +
            "Esta respiraci??n se puede repetir de 3 a 5 veces aproximadamente.\n", "Nno se recomienda para personas con bronquitis, asma, presi??n baja, problemas de coraz??n o embarazadas."));
    add(new cListViewColumns("Shektari", "imagen10", "Nos sentamos en una posici??n c??moda en un espacio tranquilo con el rostro relajado.\n" +
            "Realizamos 3 inhalaciones y exhalaciones por la nariz. \n" +
            "Se inhala por la boca con los dientes apoyados entre s??, se cierra la boca, se retiene el aire en el mismo tiempo de la inhalaci??n y se exhala suavemente por la nariz en la misma cantidad de tiempo.\n" +
            "Esta respiraci??n se puede repetir de 3 a 5 veces aproximadamente.\n", "No se recomienda para personas con bronquitis, asma, presi??n baja, problemas de coraz??n o embarazadas."));
    add(new cListViewColumns("Ommursa", "imagen6", "Nos sentamos en una posici??n c??moda en un espacio tranquilo con el rostro relajado.\n" +
            "Se inhala por la nariz llevando la cabeza hacia atr??s y se exhala por la nariz volviendo con la cabeza hacia adelante llevando el ment??n hacia el pecho.\n" +
            "Esta respiraci??n se puede repetir de 3 a 5 veces aproximadamente.\n", "En caso de tener problemas cervicales, no exigir demasiado al cuello.")); }};
        public static ArrayList<cListViewColumns> ejerciciosEquilibrantes = new ArrayList<cListViewColumns>() {{add(new cListViewColumns("Respiraci??n cuadrada", "imagen3", "Nos sentamos de piernas cruzadas en una posici??n c??moda o sobre una silla, con la columna erguida. O tambi??n nos podemos recostar en una superficie c??moda.\n" +
                "Se inhala por la nariz en 4 tiempos, se retiene el aire a pulm??n lleno en 4 tiempos, se exhala por la nariz en 4 tiempos y se retiene a pulm??n vac??o durante 4 tiempos.\n" +
                "En caso de notar mucha exigencia en las retenciones de pulm??n lleno y pulm??n vac??o, se pueden reducir a la mitad, es decir, 2. Es importante no exigir al cuerpo.\n" +
                "Esta respiraci??n se puede repetir de 3 a 5 veces aproximadamente.", "Este ejercicio no tiene contraindicaciones."));
            add(new cListViewColumns("Respiraci??n circular", "imagen4", "Nos sentamos de piernas cruzadas en una posici??n c??moda o sobre una silla, con la columna erguida. O tambi??n nos podemos recostar en una superficie c??moda.\n" +
                    "Se inhala por la nariz en 4 tiempos llenando los pulmones y se vac??an en la misma cantidad de tiempos exhalando suavemente por la nariz.\n" +
                    "Esta respiraci??n se puede repetir de 3 a 5 veces aproximadamente.", "Este ejercicio no tiene contraindicaciones."));
            add(new cListViewColumns("Respiraci??n completa", "imagen10", "Nos sentamos de piernas cruzadas en una posici??n c??moda o sobre una silla, con la columna erguida. O tambi??n nos podemos recostar en una superficie c??moda.\n" +
                    "Se inhala por la nariz llenando los pulmones de aire desde abajo hacia arriba en 3 tiempos (parte baja, media y alta de los pulmones) y se exhala por la nariz vaciando los pulmones de la misma manera en 3 tiempos (parte baja, media y alta de los pulmones).\n" +
                    "Esta respiraci??n se puede repetir de 3 a 5 veces aproximadamente.", "Este ejercicio no tiene contraindicaciones."));
            add(new cListViewColumns("Respiraci??n Yoguica", "imagen9", "Nos sentamos de piernas cruzadas en una posici??n c??moda o sobre una silla, con la columna erguida. O tambi??n nos podemos recostar en una superficie c??moda.\n" +
                    "Se inhala por la nariz llenando los pulmones de aire desde abajo hacia arriba en 3 tiempos (parte baja, media y alta de los pulmones) y se exhala por la nariz vaciando los pulmones de la misma manera en 3 tiempos (parte alta, media y alta de los pulmones).\n" +
                    "Esta respiraci??n se puede repetir de 3 a 5 veces aproximadamente.", "Este ejercicio no tiene contraindicaciones."));
            add(new cListViewColumns("Brahmari", "imagen8", "Nos sentamos de piernas cruzadas en una posici??n c??moda o sobre una silla, con la columna erguida. \n" +
                    "Apoyamos suavemente los dedos ??ndice y mayor de ambas manos sobre los p??rpados.\n" +
                    "Apoyamos suavemente los dedos anular de ambas manos sobre los orificios nasales.\n" +
                    "Apoyamos los dedos me??ique de ambas manos sobre el labio superior.\n" +
                    "Apoyamos los dedos pulgar de ambas manos sobre las orejas.\n" +
                    "Se hace una inhalaci??n profunda por la nariz, se tapan los o??dos con los dedos pulgar y exhalamos emitiendo el sonido OM (imitando el sonido de una abeja).\n" +
                    "Esta respiraci??n se puede repetir de 3 a 5 veces aproximadamente.", "Este ejercicio no tiene contraindicaciones."));
            add(new cListViewColumns("Respiraci??n alterna (Nadi Sodhana)", "imagen1", "Nos sentamos de piernas cruzadas en una posici??n c??moda o sobre una silla, con la columna erguida.\n" +
                    "Mano izquierda: la colocamos sobre la rodilla con la palma hacia arriba. Dedo ??ndice y pulgar toc??ndose entre s??.\n" +
                    "Mano derecha: colocamos el dedo ??ndice y mayor en la palma de la mano o sobre la frente en el entrecejo. Dedo pulgar en el orificio nasal derecho y dedo anular sobre el orificio nasal izquierdo.\n" +
                    "Presionamos el orificio nasal derecho, se inhala por el izquierdo.\n" +
                    "Presionamos el orificio nasal izquierdo, se exhala por el derecho.\n" +
                    "Presionamos el orificio nasal izquierdo, se inhala por el derecho.\n" +
                    "Presionamos el orificio nasal derecho, se exhala por el izquierdo.\n" +
                    "Esta secuencia siempre comienza y termina por el lado izquierdo.\n" +
                    "La secuencia se puede repetir de 5 a 10 veces y, con el tiempo, se puede incrementar la cantidad de repeticiones.", "Este ejercicio no tiene contraindicaciones."));}};
        public static ArrayList<cListViewColumns> ejerciciosCalentantes = new ArrayList<cListViewColumns>() {{add(new cListViewColumns("Suria bedhana", "imagen1", "Nos sentamos de piernas cruzadas en una posici??n c??moda o sobre una silla, con la columna erguida.\n" +
                "Mano izquierda: la colocamos sobre la rodilla con la palma hacia arriba. Dedo ??ndice y pulgar toc??ndose entre s??.\n" +
                "Mano derecha: colocamos el dedo ??ndice y mayor en la palma de la mano o sobre la frente en el entrecejo. Dedo pulgar en el orificio nasal derecho y dedo anular sobre el orificio nasal izquierdo.\n" +
                "Presionamos el orificio nasal izquierdo, se inhala por el derecho, se tapa el derecho y se exhala por el izquierdo.\n" +
                "Todas las instalaciones ocurren por el lado derecho y todas las que exhalaciones ocurren por el lado izquierdo.\n" +
                "La secuencia se puede repetir de 5 a 10 veces y, con el tiempo, se puede incrementar la cantidad de repeticiones.", "Este ejercicio no tiene contraindicaciones."));
            add(new cListViewColumns("Ujjayi", "imagen10", "Esta respiraci??n se puede realizar en cualquier posici??n que se desee.\n" +
                    "Se inhala por la nariz con el ment??n elevado y se exhala por la nariz manteniendo una aproximaci??n del ment??n hacia el pecho. Est?? exhalaci??n debe ser sonora a la altura de la garganta. Esto se puede lograr imaginando que queremos empa??ar un vidrio con la boca cerrada.\n" +
                    "Est?? respiraci??n la pod??s utilizar cuando est??s realizando una actividad f??sica ya que ayuda a calentar tu garganta, tu cuerpo y a concentrarte.", "Este ejercicio no tiene contraindicaciones."));
            add(new cListViewColumns("Kapalabhati", "imagen7", "Nos sentamos de piernas cruzadas en una posici??n c??moda o sobre una silla, con la columna erguida.\n" +
                    "Se inhala por la nariz llenando los pulmones de aire, expandiendo bien el abdomen.\n" +
                    "Se exhala por la nariz de a chorritos (con enfasis), acercando el ombligo hacia la columna hasta vaciar completamente los pulmones.\n" +
                    "Esta respiraci??n se puede repetir de 3 a 5 veces aproximadamente.", "No se recomienda para personas embarazadas, con presi??n arterial, hernias discales, ulseras o problemas card??acos. "));
            add(new cListViewColumns("Bastrica", "imagen7", "Nos sentamos de piernas cruzadas en una posici??n c??moda o sobre una silla, con la columna erguida.\n" +
                    "Se inhala por la nariz (con enfasis) llenando los pulmones de aire, y se exhala por la nariz de la misma manera vaciando los pulmones.\n" +
                    "Esta respiraci??n se puede repetir de 3 a 5 veces aproximadamente.", "No se recomienda para personas embarazadas, con presi??n arterial, hernias discales, ulseras o problemas card??acos. \n" +
                    "Es normal marearse por la hiperventilaci??n. Es aconsejable trabajarla progresivamente."));}};
    }

}
