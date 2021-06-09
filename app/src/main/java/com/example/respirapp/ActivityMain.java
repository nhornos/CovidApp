package com.example.respirapp;

import Clases.cAPI;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

import static android.app.PendingIntent.getActivity;

public class ActivityMain extends Activity {

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
//                        JSONObject json = cAPI.APIRestRequest("POST", "login", parameters);
//                        Log.i("RespuestaAPIRest", json.toString());
//                    } catch (IOException | URISyntaxException e) {
//                        Log.i("RespuestaAPIRest", e.getMessage());
//                    }
//                }
//            });


    }

    @Override
    protected void onStop()
    {
        super.onStop();
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
    }

    @Override
    protected void onPause()
    {
        super.onPause();
    }

    @Override
    protected void onRestart()
    {
        super.onRestart();
    }

    @Override
    protected void onResume()
    {
        super.onResume();
    }

}


