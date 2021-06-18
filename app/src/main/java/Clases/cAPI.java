package Clases;

import com.example.respirapp.*;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkCapabilities;
import android.os.AsyncTask;
import android.os.Build;
import android.util.Log;
import android.widget.TextView;
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
    private int estado;
    private JSONObject json;

    private ProgressDialog dialog;

    private String verbo;
    private String metodo;
    private String params;
    private Activity activity;
    private Context context;

    private boolean conexion = true;

    public cAPI(Activity activity, Context context, String verbo, String metodo){
        this.context = context;
        this.activity = activity;
        this.verbo = verbo;
        this.metodo = metodo;
    }

    @Override
    protected void onPreExecute() {
        if(this.metodo == "login" || this.metodo == "register"){
            showBar("Por favor espere");
        }
    }

    @Override
    protected JSONObject doInBackground(String... strings) {
        this.json = new JSONObject();

        this.params = strings[0];

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

    private synchronized JSONObject realizarPeticionServidor() throws URISyntaxException, IOException, JSONException {
        URI uri = new URI("http://so-unlam.net.ar/api/api/" + this.metodo);
        URL url = new URL(uri.toURL().toString());

        HttpURLConnection conn = (HttpURLConnection) url.openConnection();

        conn.setRequestMethod(this.verbo);
        conn.setRequestProperty("Content-Type", "application/json; charset=utf-8");

        if(this.metodo.equals("event")){
            conn.setRequestProperty("Authorization", "Bearer " + cFunciones.getCache(this.context, "usuario_token"));
            Log.i("TokenPeticion", cFunciones.getCache(this.context, "usuario_token"));
        }
        else if(this.metodo.equals("refresh"))
            conn.setRequestProperty("Authorization", "Bearer " + cFunciones.getCache(this.context, "usuario_token_refresh"));

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

        Log.i("Resultado conexion:", content.toString());

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
                break;
            case "refresh":
                refreshToken();
                break;
            default:
                break;
        }
        hideBar();
    }

    private void showBar(String msg){
        this.dialog = new ProgressDialog(this.activity);
        this.dialog.setMessage(msg);
        this.dialog.show();
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

    private void refreshToken() throws JSONException {
        if(this.estado == 200) {
            cFunciones.setCache(this.context,"usuario_token", this.json.getString("token"));
            cFunciones.setCache(this.context,"usuario_token_refresh", this.json.getString("token_refresh"));
            Log.i("TokenDespues:", cFunciones.getCache(context, "usuario_token"));
            Log.i("TokenRefreshDespues:", cFunciones.getCache(context, "usuario_token_refresh"));
        } else{
            Log.i("Error refresh token", this.json.getString("msg"));
        }
    }

    private void login() throws JSONException {
        if(this.estado == 200) {
            cFunciones.setCache(this.context,"usuario_token", this.json.getString("token"));
            cFunciones.setCache(this.context,"usuario_token_refresh", this.json.getString("token_refresh"));

            Intent intent = new Intent(this.activity, activity_menu_2.class);
            this.activity.startActivity(intent);
            this.activity.finish();
        }else if(this.estado == 400){
            TextView txtMsg = (TextView)this.activity.findViewById(R.id.lbl_msg);
            txtMsg.setText(this.json.getString("msg"));
        }
        else
            Log.i("Resultado evento:", "Codigo de respuesta \"" + this.estado + "\" no reconocido");
    }

    private void register() throws JSONException {
        if(this.estado == 200) {

            cFunciones.setCache(this.context, "usuario_token", this.json.getString("token"));
            cFunciones.setCache(this.context, "usuario_token_refresh", this.json.getString("token_refresh"));

            Intent intent = new Intent(this.activity, activity_menu_2.class);
            this.activity.startActivity(intent);
            this.activity.finish();
        }else if(this.estado == 400){
            TextView txtMsg = (TextView)this.activity.findViewById(R.id.lbl_msgReg);
            txtMsg.setText(this.json.getString("msg"));
        }
        else
            Log.i("Resultado evento:", "Codigo de respuesta \"" + this.estado + "\" no reconocido");
    }

    private void event() throws JSONException {

        if(this.estado == 200 || this.estado == 201){
            String environment = this.json.getString("env");
            JSONObject event = this.json.getJSONObject("event");

            if(environment.equals(this.context.getString(R.string.prod))){
                cEstructuras.cUsuario.dni = event.getInt("dni");
                cEstructuras.cEvento.id = event.getInt("id");
            }
            cEstructuras.cEvento.typeEvent = event.getString("type_events");
            cEstructuras.cEvento.description = event.getString("description");

            if(cEstructuras.cEvento.typeEvent.equals("patron")){
                if(this.estado == 200 || this.estado == 201){
                    cFunciones.actualizarPatron(this.context);
                    Toast.makeText(this.context, "Patron guardado!", Toast.LENGTH_SHORT).show();
                }
            }

            Log.i("Resultado evento:", this.json.toString());
        }else{
            if(this.estado == 400)
                Toast.makeText(context, this.json.getString("msg"), Toast.LENGTH_LONG).show();
            else if(this.estado == 401) {
                new AlertDialog.Builder(this.context)
                        .setTitle("Sesión expirada")
                        .setMessage("Desea continuar dentro de la aplicación?")
                        .setNegativeButton(R.string.msgDenegar, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                Intent i = new Intent(context, ActivityLogin.class);
                                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                i.putExtra("EXIT", true);
                                activity.startActivity(i);
                                activity.finish();
                            }
                        })
                        .setPositiveButton(R.string.msgAceptar, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                cEstructuras.cRefresh.refrescar(null, context);
                                Log.i("TokenAntes:", cFunciones.getCache(context, "usuario_token"));
                                Log.i("TokenRefreshAntes:", cFunciones.getCache(context, "usuario_token_refresh"));
                            }
                        })
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
            }
            else
                Log.i("Resultado evento:", "Codigo de respuesta \"" + this.estado + "\" no reconocido");
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




