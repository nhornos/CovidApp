package Clases;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.example.respirapp.R;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class cEstructuras {

    public static class cUsuario{
        private int dni;
        private String nombre;
        private String apellido;
        private String email;
        private String password;
        private String token;
        private String tokenRefresh;

        //GETTERS
        public int getDni() {
            return this.dni;
        }
        public String getNombre(){
            return this.nombre;
        }
        public String getApellido(){ return this.apellido; }
        public String getEmail(){
            return this.email;
        }
        public String getPassword(){
            return this.password;
        }
        public String getToken() {
            return this.token;
        }
        public String getTokenRefresh() {
            return tokenRefresh;
        }

        //SETTERS
        public void setDni(int dni) {
            this.dni = dni;
        }
        public void setEmail(String email){
            this.email = email;
        }
        public void setNombre(String nombre){
            this.nombre = nombre;
        }
        public void setApellido(String apellido){
            this.apellido = apellido;
        }
        public void setPassword(String password){
            this.password = password;
        }
        public void setToken(String token) {
            this.token = token;
        }
        public void setTokenRefresh(String tokenRefresh) {
            this.tokenRefresh = tokenRefresh;
        }

        public void setDataUsuario(int dni, String nombre, String apellido, String email, String password, String token, String tokenRefresh){
            setDni(dni);
            setNombre(nombre);
            setApellido(apellido);
            setEmail(email);
            setPassword(password);
            setToken(token);
            setTokenRefresh(tokenRefresh);
        }

        public void registrar(Activity activity, Context context, int dni, String nombre, String apellido, String email, String password){
            Map<String, String> parameters = new HashMap<>();
            parameters.put("env", context.getString(R.string.env));
            parameters.put("name", nombre);
            parameters.put("lastname", apellido);
            parameters.put("dni", String.valueOf(dni));
            parameters.put("email", email);
            parameters.put("password", password);
            parameters.put("commission", context.getString(R.string.commission));
            parameters.put("group", context.getString(R.string.group));
            String params = cParametros.getParamsString(parameters);

            AsyncTask<String, String, JSONObject> registerAsyncTask = new cAPI(activity, context);
            registerAsyncTask.execute("POST","register",params);
        }

        public void loguear(Activity activity, Context context, String email, String password){
            //Guardo los datos en la estructura de usuario
            cObjetos.oUsuario.setEmail(email);
            cObjetos.oUsuario.setPassword(password);

            Map<String, String> parameters = new HashMap<>();
            parameters.put("email", email);
            parameters.put("password", password);
            String params = cParametros.getParamsString(parameters);

//            try {
                AsyncTask<String, String, JSONObject> registerAsyncTask = new cAPI(activity, context);
                registerAsyncTask.execute("POST","login",params);
//            }catch (Exception e){
//                Log.i("LOGIN", e.getMessage());
//            }

        }
        public void registrarEvento(Activity activity, Context context, String environment, String method, String description){
            Map<String, String> parameters = new HashMap<>();
            parameters.put("env", environment);
            parameters.put("type_events", method);
            parameters.put("description", "Descripción");
            String params = cParametros.getParamsString(parameters);

//            try {
                AsyncTask<String, String, JSONObject> registerAsyncTask = new cAPI(activity, context);
                registerAsyncTask.execute("POST","event",params);
//            }catch (Exception e){
//                Log.i("EVENT", e.getMessage());
//            }

        }
    }

    public static class cEvento{
        private int id;
        private String typeEvent;
        private String description;
        private int dni;

        //SETTERS
        public void setId(int id) { this.id = id; }
        public void setTypeEvent(String typeEvent) { this.typeEvent = typeEvent;  }
        public void setDescription(String description) { this.description = description; }

        //GETTERS
        public int getId() { return this.id; }
        public String getTypeEvent() { return this.typeEvent; }
        public String getDescription() { return this.description; }
    }
}
