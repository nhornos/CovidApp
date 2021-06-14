package Clases;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Build;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.respirapp.activity_menu_2;

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

    private String verbo;
    private String metodo;
    private String params;
    private Activity activity;
    private Context context = cObjetos.oActivity.getApplicationContext();

    private boolean conexion = true;

    public cAPI(Activity activity, Context context){
        this.context = context;
        this.activity = activity;
    }

    @Override
    protected JSONObject doInBackground(String... strings) {


        this.json = new JSONObject();

        this.verbo = strings[0];
        this.metodo = strings[1];
        this.params = strings[2];

        Log.i("Dentro del thread", this.params);

//        if(checkConectionFacu(context)){
        if(checkConection(context)){
            try {
                this.json = realizarPeticionServidor(verbo, metodo, params);
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

    private JSONObject realizarPeticionServidor(String verbo, String metodo, String params) throws URISyntaxException, IOException, JSONException {
        URI uri = new URI("http://so-unlam.net.ar/api/api/" + metodo);
        URL url = new URL(uri.toURL().toString());

        HttpURLConnection conn = (HttpURLConnection) url.openConnection();

        if(verbo == "GET")
            conn.setRequestProperty("Content-Type", "application/json; charset=utf-8");
        else
            conn.setRequestProperty("Accept", "application/json; charset=utf-8");

        conn.setRequestMethod(verbo); //GET o POST
        conn.setConnectTimeout(5000);
        conn.setReadTimeout(5000);

        conn.setDoOutput(true);
        DataOutputStream outPutStream = new DataOutputStream(conn.getOutputStream());

        //Conversion del map de parametros a string:
        outPutStream.writeBytes(this.params);

        outPutStream.flush();
        outPutStream.close();

        estado = conn.getResponseCode();
        Reader streamReader = null;

        if (estado == 400) {
            streamReader = new InputStreamReader(conn.getErrorStream());
        } else {
            streamReader = new InputStreamReader(conn.getInputStream());
        }

        BufferedReader in = new BufferedReader(streamReader);
        String inputLine;
        StringBuffer content = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            content.append(inputLine);
        }
        in.close();
        conn.disconnect();

        Log.i("Resultado conexion:", content.toString());

        return new JSONObject(content.toString());
    }

    @Override
    protected void onPostExecute(JSONObject jsonObject) {
        try {
            if(!conexion)
                errorConnection();
            else
                switchBetweenMethods(this.metodo);
        } catch (JSONException e) {
            Log.i("onPostExecute", e.getMessage());
        }
    }

    private void errorConnection() {
        hideBar();
        Toast.makeText(context, "Error. No hay conexión a internet", Toast.LENGTH_LONG).show();
    }

    private void switchBetweenMethods(String method) throws JSONException {
        switch (method){
            case "login":
                login();
                break;
            case "register":
                register();
                break;
            case "event":
                event();
            default:
                break;
        }
    }

    private void login() throws JSONException {
        hideBar();

        if(this.json.getBoolean("success")) {
            String token = this.json.getString("token");
            String tokenRefresh = this.json.getString("token_refresh");

            cObjetos.oUsuario.setToken(token);
            cObjetos.oUsuario.setTokenRefresh(tokenRefresh);
            cParametros.addCache("usuario_email", cObjetos.oUsuario.getEmail());
            cParametros.addCache("usuario_password", cObjetos.oUsuario.getPassword());
            cParametros.addCache("usuario_token", token);
            cParametros.addCache("usuario_token_refresh", tokenRefresh);

            Log.i("Datos param:", this.params);

            Intent intent = new Intent(this.activity, activity_menu_2.class);
            cObjetos.oActivity.startActivity(intent);
            cObjetos.oActivity.finish();
        }
        else {
            Toast.makeText(context, this.json.getString("msg"), Toast.LENGTH_LONG).show();
        }
    }

    private void register() throws JSONException {
        hideBar();

        if(this.estado == 200) {
            String token = this.json.getString("token");
            String tokenRefresh = this.json.getString("token_refresh");
            cObjetos.oUsuario.setToken(token);
            cObjetos.oUsuario.setTokenRefresh(tokenRefresh);
            cParametros.addCache("usuario_email", cObjetos.oUsuario.getEmail());
            cParametros.addCache("usuario_password", cObjetos.oUsuario.getPassword());
            cParametros.addCache("usuario_token", token);
            cParametros.addCache("usuario_token_refresh", tokenRefresh);
            Toast.makeText(context, "Registro exitoso!", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this.activity, activity_menu_2.class);
            cObjetos.oActivity.startActivity(intent);
            cObjetos.oActivity.finish();
        }else {
            Toast.makeText(context, this.json.getString("msg"), Toast.LENGTH_LONG).show();
        }
    }

    private void event() throws JSONException {
        if(this.json.getBoolean("success")){
            String environment = this.json.getString("env");
            JSONObject event = this.json.getJSONObject("event");

            //parameters.put("env", cObjetos.oActivity.getApplicationContext().getString(R.string.env));
            if(environment == getString(R.string.PROD)){
                cObjetos.oUsuario.setDni(event.getInt("dni"));

                cObjetos.oEvento.setId(event.getInt("id"));
            }
            cObjetos.oEvento.setTypeEvent(event.getString("type_events"));
            cObjetos.oEvento.setDescription(event.getString("description"));
        }else{
            Toast.makeText(context, this.json.getString("msg"), Toast.LENGTH_LONG).show();
        }
        Toast.makeText(context, "Se registró en el servidor la accion", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(cObjetos.oActivity, activity_menu_2.class);
        cObjetos.oActivity.startActivity(intent);
        cObjetos.oActivity.finish();
    }

    private void hideBar(){
        if (cObjetos.oProgressBar.isShown()) {
            cObjetos.oProgressBar.setVisibility(View.GONE);
        }
    }

    public static boolean checkConection(Context context){
            ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

            // For 29 api or above
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                NetworkCapabilities capabilities = connectivityManager.getNetworkCapabilities(connectivityManager.getActiveNetwork());

                if(capabilities == null)
                    return false;

                if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)){
                    Log.i("checkConection", "Wifi detectado");
                    return true;
                }
                if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)) {
                    Log.i("checkConection", "Ethernet detectado");
                    return true;
                }
                if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
                    Log.i("checkConection", "Internet celular detectado");
                    return true;
                }

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

    public static boolean checkConectionFacu(Context context) {
        ConnectivityManager cm = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
    }


}




