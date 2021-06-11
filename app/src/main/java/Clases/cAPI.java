package Clases;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.respirapp.ActivityMenu;
import com.example.respirapp.activity_menu_2;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;


public class cAPI extends AsyncTask<String, String, JSONObject> {
    //public static Semaphore mutex = new Semaphore(1);
    private int estado;
    private JSONObject json;

    private String metodo;
    private Activity activity;
    private Context context;
    private ProgressBar bar;

    private String params;

    public cAPI(Activity activity, Context context, ProgressBar progressBar){
        this.activity = activity;
        this.context = context;
        this.bar = progressBar;
    }

    @Override
    protected JSONObject doInBackground(String... strings) {


        json = new JSONObject();

        String verbo = strings[0];
        this.metodo = strings[1];
        this.params = strings[2];

        Log.i("Dentro del thread", this.params);
        try
        {
            json = realizarPeticionServidor(verbo, metodo, params);
            return json;
        }
        catch(Exception e)
        {
            Log.i("cAPI", e.getMessage());
            return null;
        }
    }

    @Override
    protected void onPostExecute(JSONObject jsonObject) {
        //TODO agregar validaciones de error de conexión
        try {
            switchBetweenMethods(this.metodo);
        } catch (JSONException e) {
            Log.i("onPostExecute", e.getMessage());
        }
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

    private void switchBetweenMethods(String method) throws JSONException {
        switch (method){
            case "login":
                login();
                break;
            case "register":
                register();
                break;
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

            Log.i("Datos param:", this.params);

//            Intent intent = new Intent(this.activity, ActivityMenu.class);
            Intent intent = new Intent(this.activity, activity_menu_2.class);
            this.activity.startActivity(intent);
        }
        else {
            Toast.makeText(this.context, this.json.getString("msg"), Toast.LENGTH_LONG).show();
        }
    }

    private void register() throws JSONException {
        hideBar();

        if(this.json.getBoolean("success")) {
            String token = this.json.getString("token");
            String tokenRefresh = this.json.getString("token_refresh");
            cObjetos.oUsuario.setToken(token);
            cObjetos.oUsuario.setTokenRefresh(tokenRefresh);
//            Intent intent = new Intent(this.activity, ActivityMenu.class);
            Toast.makeText(this.context, "Registro exitoso!", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this.activity, activity_menu_2.class);
//            Intent intent = new Intent(this.activity, ActivityMenu.class);
            this.activity.startActivity(intent);
            this.activity.finish();
        }else {
            Toast.makeText(this.context, this.json.getString("msg"), Toast.LENGTH_LONG).show();
        }
    }

    private void hideBar(){
        if (bar.isShown()) {
            bar.setVisibility(View.GONE);
        }
    }


}




