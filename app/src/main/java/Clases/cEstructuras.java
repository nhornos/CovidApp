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
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class cEstructuras {

    public static class cUsuario{
        public static int dni;
        public static String nombre;
        public static String apellido;
        public static String email;
        public static String password;
        public static String token;
        public static String tokenRefresh;

//        //GETTERS
//        public static int getDni() { return dni; }
//        public static String getNombre(){ return nombre; }
//        public static String getApellido(){ return apellido; }
//        public static String getEmail(){ return email; }
//        public static String getPassword(){ return password; }
//        public static String getToken() { return token; }
//        public static String getTokenRefresh() {
//            return tokenRefresh;
//        }
//
//        //SETTERS
//        public void setDni(int dni) { cUsuario.dni = dni; }
//        public void setEmail(String email){
//            cUsuario.email = email;
//        }
//        public void setNombre(String nombre){
//            cUsuario.nombre = nombre;
//        }
//        public void setApellido(String apellido){
//            cUsuario.apellido = apellido;
//        }
//        public void setPassword(String password){
//            cUsuario.password = password;
//        }
//        public void setToken(String token) {
//            cUsuario.token = token;
//        }
//        public void setTokenRefresh(String tokenRefresh) {
//            cUsuario.tokenRefresh = tokenRefresh;
//        }

//        public void setDataUsuario(int dni, String nombre, String apellido, String email, String password, String token, String tokenRefresh){
//            setDni(dni);
//            setNombre(nombre);
//            setApellido(apellido);
//            setEmail(email);
//            setPassword(password);
//            setToken(token);
//            setTokenRefresh(tokenRefresh);
//        }

        public static void registrar(Activity activity, Context context, String dni, String nombre, String apellido, String email, String password){

            if(nombre.equals("") || apellido.equals("") || email.equals("") || password.equals("") || dni.equals("")){
                Toast.makeText(context, "Ingrese los campos faltantes", Toast.LENGTH_SHORT).show();
            }else{
                Map<String, String> parameters = new TreeMap<>();
                parameters.put("env", context.getString(R.string.env));
                parameters.put("name", nombre);
                parameters.put("lastname", apellido);
                parameters.put("dni", dni);
                parameters.put("email", email);
                parameters.put("password", password);
                parameters.put("commission", context.getString(R.string.commission));
                parameters.put("group", context.getString(R.string.group));
                String params = cFunciones.getParamsString(parameters);
                AsyncTask<String, String, JSONObject> registerAsyncTask = new cAPI(activity, context);
                registerAsyncTask.execute("POST","register",params);
            }

        }

        public static void loguear(Activity activity, Context context, String email, String password){
            //Guardo los datos en la estructura de usuario
            cUsuario.password = password;
            cUsuario.email = email;
            Map<String, String> parameters = new TreeMap<String, String>();
            parameters.put("email", email);
            parameters.put("password", password);
            String params = cFunciones.getParamsString(parameters);
            Log.i("Json:", params);
//            try {
                AsyncTask<String, String, JSONObject> registerAsyncTask = new cAPI(activity, context);
                registerAsyncTask.execute("POST","login",params);
//                registerAsyncTask.execute("POST","login","{\"email\":\"nhornos@alumno.unlam.edu.ar\",\"password\":\"abcd1234\"}");
//            {
//                "email": <<String>>,
//                    "password": <<String>>
//            }
//            }catch (Exception e){
//                Log.i("LOGIN", e.getMessage());
//            }

        }
    }

    public static class cEvento{
        public static int id;
        public static String typeEvent;
        public static String description;
        public static int dni;

//        //SETTERS
//        public void setId(int id) { this.id = id; }
//        public void setTypeEvent(String typeEvent) { this.typeEvent = typeEvent;  }
//        public void setDescription(String description) { this.description = description; }
//
//        //GETTERS
//        public int getId() { return this.id; }
//        public String getTypeEvent() { return this.typeEvent; }
//        public String getDescription() { return this.description; }

        public static void registrar(Activity activity, Context context, String environment, String method, String description){
            Map<String, String> parameters = new HashMap<>();
            parameters.put("env", environment);
            parameters.put("type_events", method);
            parameters.put("description", description);
            String params = cFunciones.getParamsString(parameters);

            AsyncTask<String, String, JSONObject> registerAsyncTask = new cAPI(activity, context);
            registerAsyncTask.execute("POST","event",params);
        }
    }
}
