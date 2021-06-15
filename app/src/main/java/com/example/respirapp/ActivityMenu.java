package com.example.respirapp;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import Clases.cObjetos;

public class ActivityMenu extends Activity {

    private EditText inPassword;
    private TextView txtError;
    private Button btnIngresar;
    private String pass;
    private String errorMsg = "Error. El código es incorrecto.";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

//            AsyncTask.execute(new Runnable() {
//                @Override
//                public void run() {
//                    Map<String, String> parameters = new HashMap<>();
//                    parameters.put("password", "abcd1234");
//                    parameters.put("email", "nhornos@alumno.unlam.edu.ar");
//                    try {
//                        JSONObject json = cAPI.APIRestRequest("POST", "ActivityLogin", parameters);
//                        Log.i("RespuestaAPIRest", json.toString());
//                    } catch (IOException | URISyntaxException e) {
//                        Log.i("RespuestaAPIRest", e.getMessage());
//                    }
//                }
//            });


    }

    @Override
    protected void onResume()
    {
        super.onResume();
    }

    @Override
    public void onBackPressed() {
        return;
    }

}


