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
            Map<String, String> parameters = new HashMap<>();
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

        public static ArrayList<cListViewColumns> ejerciciosEnfriantes = new ArrayList<cListViewColumns>() {{add(new cListViewColumns("Sitali Pranayama", "icono", "Este ejercicio comienza sentado con la espalda recta.\n Comenzamos a respirar profundo, y luego de 15 segundos exalamos todo el aire", "Este ejercicio no es recomendado para personas con baja frecuencia cardíaca debido a su baja acción"));
                                                                                                            add(new cListViewColumns("Sitali dos", "img_home", "desc", "info"));
                                                                                                            add(new cListViewColumns("Sitali tres", "ejercicios_calentantes", "desc", "info"));
                                                                                                            add(new cListViewColumns("Sitali cuatro", "icono", "desc", "info"));
                                                                                                            add(new cListViewColumns("Sitali cinco", "icono", "desc", "info"));}};
        public static ArrayList<cListViewColumns> ejerciciosEquilibrantes = new ArrayList<cListViewColumns>() {{add(new cListViewColumns("Respiración cuadrada", "icono", "desc", "info"));
                                                                                                            add(new cListViewColumns("Respiración circular", "icono", "desc", "info"));
                                                                                                            add(new cListViewColumns("Respiración equilibrada", "icono", "desc", "info"));
                                                                                                            add(new cListViewColumns("Respiración cuatro", "icono", "desc", "info"));
                                                                                                            add(new cListViewColumns("Respiración cinco", "icono", "desc", "info"));}};
        public static ArrayList<cListViewColumns> ejerciciosCalentantes = new ArrayList<cListViewColumns>() {{add(new cListViewColumns("Calentante 1", "icono", "desc", "info"));
                                                                                                            add(new cListViewColumns("Calentante 2", "icono", "desc", "info"));
                                                                                                            add(new cListViewColumns("Calentante 3", "icono", "desc", "info"));
                                                                                                            add(new cListViewColumns("Calentante 4", "icono", "desc", "info"));
                                                                                                            add(new cListViewColumns("Calentante 5", "icono", "desc", "info"));}};
    }

}
