package Clases;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ProgressBar;

import com.example.respirapp.ActivityRegister;
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
        public void setPassword(String email){
            this.email = email;
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

        public void registrar(int dni, String nombre, String apellido, String email, String password){
            Map<String, String> parameters = new HashMap<>();
            parameters.put("env", cObjetos.oActivity.getApplicationContext().getString(R.string.env));
            parameters.put("name", nombre);
            parameters.put("lastname", apellido);
            parameters.put("dni", String.valueOf(dni));
            parameters.put("email", email);
            parameters.put("password", password);
            parameters.put("commission", cObjetos.oActivity.getApplicationContext().getString(R.string.commission));
            parameters.put("group", cObjetos.oActivity.getApplicationContext().getString(R.string.group));
            String params = cParametros.getParamsString(parameters);

            AsyncTask<String, String, JSONObject> registerAsyncTask = new cAPI();
            registerAsyncTask.execute("POST","register",params);
        }

        public void loguear(String email, String password){
            Map<String, String> parameters = new HashMap<>();
            parameters.put("email", email);
            parameters.put("password", password);
            String params = cParametros.getParamsString(parameters);

            try {
                AsyncTask<String, String, JSONObject> registerAsyncTask = new cAPI();
                registerAsyncTask.execute("POST","login",params);
            }catch (Exception e){
                Log.i("LOGIN", e.getMessage());
            }

        }
    }
}
