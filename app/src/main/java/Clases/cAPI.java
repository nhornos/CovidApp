package Clases;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.respirapp.ActivityLogin;
import com.example.respirapp.ActivityMain;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Map;
import java.util.concurrent.Semaphore;


public class cAPI extends AsyncTask<String, String, JSONObject> {
    //public static Semaphore mutex = new Semaphore(1);
    private int estado;
    private JSONObject json;

    private String metodo;
    private Activity activity;
    private Context context;
    private ProgressBar bar;

    public cAPI(Activity activity, Context context, ProgressBar progressBar){
        this.activity = activity;
        this.context = context;
        this.bar = progressBar;
    }

    @Override
    protected JSONObject doInBackground(String... strings) {

//        if(android.os.Debug.isDebuggerConnected())
//            android.os.Debug.waitForDebugger();

        json = new JSONObject();

        String verbo = strings[0];
        this.metodo = strings[1];
        String params = strings[2];

        try
        {
            Log.i("Dentro del thread", json.toString());
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
            outPutStream.writeBytes(params);
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

            json = new JSONObject(content.toString());

            //this.mutex.release();

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

    private void switchBetweenMethods(String method) throws JSONException {
        switch (method){
            case "login":
                login();
                break;
            default:
                break;
        }
    }

    private void login() throws JSONException {
        if (bar.isShown()) {
            bar.setVisibility(View.GONE);
        }

        if(estado != 400) {
            cObjetos.oUsuario.token = this.json.getString("token");
            cObjetos.oUsuario.tokenRefresh = this.json.getString("token_refresh");
            Intent intent = new Intent(this.activity, ActivityMain.class);
            this.activity.startActivity(intent);
        }
        else
            Toast.makeText(this.context, this.json.getString("msg"), Toast.LENGTH_LONG).show();
    }


}




