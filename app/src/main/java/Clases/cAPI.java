package Clases;

import com.example.respirapp.*;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import com.example.respirapp.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;


public class cAPI extends AsyncTask<String, String, JSONObject> {
    //public static Semaphore mutex = new Semaphore(1);
    private int estado;
    private JSONObject json;

    private ProgressDialog dialog;

    private String verbo;
    private String metodo;
    private String params;
    private Activity activity;
    private Context context;

    private boolean conexion = true;

    public cAPI(Activity activity, Context context){
        this.context = context;
        this.activity = activity;
        if(activity != null)
            this.dialog = new ProgressDialog(activity);
    }

    @Override
    protected void onPreExecute() {
        if(activity != null){
            this.dialog.setMessage("Por favor espere");
            this.dialog.show();
        }
    }

    @Override
    protected JSONObject doInBackground(String... strings) {
        this.json = new JSONObject();

        this.verbo = strings[0];
        this.metodo = strings[1];
        this.params = strings[2];

        if(checkConection(this.context)){
            try {
                this.json = realizarPeticionServidor();
            } catch(Exception e)
            {
                Log.i("cAPI", e.getMessage());
            }
        }
        else {
            conexion = false;
            return null;
        }
        return this.json;
    }

    private JSONObject realizarPeticionServidor() throws URISyntaxException, IOException, JSONException {
        URI uri = new URI("http://so-unlam.net.ar/api/api/" + this.metodo);
        URL url = new URL(uri.toURL().toString());

        HttpURLConnection conn = (HttpURLConnection) url.openConnection();

        conn.setRequestMethod(this.verbo);
        conn.setRequestProperty("Content-Type", "application/json; charset=utf-8");

        if(this.metodo.equals("event"))
            conn.setRequestProperty("Authorization", "Bearer " + cFunciones.getCache(this.context, "usuario_token"));

        conn.setConnectTimeout(5000);
        conn.setReadTimeout(5000);

        conn.setDoOutput(true);
        try{
            DataOutputStream outPutStream = new DataOutputStream(conn.getOutputStream());
            outPutStream.writeBytes(this.params);

            outPutStream.flush();
            outPutStream.close();
        }catch(IOException e){
            Log.i("cAPI", e.getMessage());
        }

        this.estado = conn.getResponseCode();
        Reader streamReader = null;

        if (this.estado == 400) {
            streamReader = new InputStreamReader(conn.getErrorStream());
        } else {
            streamReader = new InputStreamReader(conn.getInputStream());
        }

        BufferedReader in = new BufferedReader(streamReader);
        StringBuffer content = new StringBuffer();

        String inputLine;
        while ((inputLine = in.readLine()) != null) {
            content.append(inputLine);
        }
        in.close();
        conn.disconnect();

        return new JSONObject(content.toString());
    }

    @Override
    protected void onPostExecute(JSONObject jsonObject) {
        try {
            if(!conexion){
                hideBar();
                Toast.makeText(context, "Error. No hay conexión a internet", Toast.LENGTH_LONG).show();
            }
            else
                switchBetweenMethods(this.metodo);
        } catch (JSONException e) {
            Log.i("onPostExecute", e.getMessage());
        }
    }

    private void switchBetweenMethods(String method) throws JSONException {
        switch (method){
            case "login":
                login();
                registerEvent(method, "Usuario logueado exitosamente.");
                break;
            case "register":
                register();
                registerEvent(method, "Usuario registrado exitosamente.");
                break;
            case "event":
                event();
            default:
                break;
        }
        hideBar();
    }

    private void hideBar(){
        if (this.dialog != null && this.dialog.isShowing()) {
            this.dialog.dismiss();
        }
    }

    private void registerEvent(String method, String description) throws JSONException {
        if(this.json.getBoolean("success")){
            cEstructuras.cEvento.registrar(null, this.context, this.context.getString(R.string.env), method, description);
        }
    }

    private void login() throws JSONException {
        if(this.json.getBoolean("success")) {
            String token = this.json.getString("token");
            String tokenRefresh = this.json.getString("token_refresh");

            cEstructuras.cUsuario.token = token;
            cEstructuras.cUsuario.tokenRefresh = tokenRefresh;

            cFunciones.addCache(this.context,"usuario_email", cEstructuras.cUsuario.email);
            cFunciones.addCache(this.context,"usuario_password", cEstructuras.cUsuario.password);
            cFunciones.addCache(this.context,"usuario_token", token);
            cFunciones.addCache(this.context,"usuario_token_refresh", tokenRefresh);

            Log.i("Datos param:", this.params);

            Intent intent = new Intent(this.activity, activity_menu_2.class);
            this.activity.startActivity(intent);
            this.activity.finish();
        }
        else {
            ActivityLogin.txtMsg.setText(this.json.getString("msg"));
//            Toast.makeText(context, this.json.getString("msg"), Toast.LENGTH_LONG).show();
        }
    }

    private void register() throws JSONException {
        if(this.estado == 200) {
            String token = this.json.getString("token");
            String tokenRefresh = this.json.getString("token_refresh");
            cEstructuras.cUsuario.token = token;
            cEstructuras.cUsuario.tokenRefresh = tokenRefresh;
            cFunciones.addCache(this.context, "usuario_email", cEstructuras.cUsuario.email);
            cFunciones.addCache(this.context, "usuario_password", cEstructuras.cUsuario.password);
            cFunciones.addCache(this.context, "usuario_token", token);
            cFunciones.addCache(this.context, "usuario_token_refresh", tokenRefresh);
            Intent intent = new Intent(this.activity, activity_menu_2.class);
            this.activity.startActivity(intent);
            this.activity.finish();
        }else {
            Toast.makeText(context, this.json.getString("msg"), Toast.LENGTH_LONG).show();
        }
    }

    private void event() throws JSONException {
        if(this.json.getBoolean("success")){
            String environment = this.json.getString("env");
            JSONObject event = this.json.getJSONObject("event");

            String prod = this.context.getString(R.string.prod);
            if(environment == prod){
                cEstructuras.cUsuario.dni = event.getInt("dni");
                cEstructuras.cEvento.id = event.getInt("id");
            }
            cEstructuras.cEvento.typeEvent = event.getString("type_events");
            cEstructuras.cEvento.description = event.getString("description");
            Log.i("Registro de evento:", "Se registro la acción de " + event.getString("type_events"));
            Log.i("Resultado evento:", this.json.toString());
            Toast.makeText(context, "Se registró en el servidor la accion", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(context, this.json.getString("msg"), Toast.LENGTH_LONG).show();
        }
    }

    public static boolean checkConection(Context context){
            ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            // For 29 api or above
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                NetworkCapabilities capabilities = connectivityManager.getNetworkCapabilities(connectivityManager.getActiveNetwork());

                if(capabilities == null) return false;

                if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) return true;
                if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)) return true;
                if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) return true;

                return false;
            }
            // For below 29 api
            else {
                if (connectivityManager.getActiveNetworkInfo() != null && connectivityManager.getActiveNetworkInfo().isConnectedOrConnecting()) {
                    return true;
                }
            }
            return false;
    }

}




