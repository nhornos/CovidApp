package com.example.respirapp;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import Clases.cAPI;
import Clases.cParametros;

public class ActivityLogin extends Activity {

    private EditText inUser;
    private EditText inPassword;
    private Button btnSubmit;
    private Button btnRegistrarse;
    public static ProgressBar mProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        Log.i("El texto", "Entro a login");

        // Defino los botones
        btnSubmit = (Button) findViewById(R.id.btnSubmit);
        mProgressBar = (ProgressBar) findViewById(R.id.progress_loader);

        btnSubmit.setOnClickListener(botonesListeners);
        btnRegistrarse = (Button) findViewById(R.id.btnRegistro);
        btnRegistrarse.setOnClickListener(botonesListeners);

        // Defino los campos de ingreso de datos
        inUser = (EditText) findViewById(R.id.inUserLogin);
        inPassword = (EditText) findViewById(R.id.inPassLogin);
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

    private final View.OnClickListener botonesListeners = new View.OnClickListener() {

        public void onClick(View v) {
            Intent intent;

            //Se determina que componente genero un evento
            switch (v.getId()) {
                //Si se ocurrio un evento en el boton OK
                case R.id.btnSubmit:
                    mProgressBar.setVisibility(View.VISIBLE);
                    Log.i("El texto", "Se detectó boton de submit");

                    Map<String, String> parameters = new HashMap<>();
                    //parameters.put("password", inPassword.getText().toString());
                    //parameters.put("email", inUser.getText().toString());
                    parameters.put("password", "abcd123");
                    parameters.put("email", "nhornos@alumno.unlam.edu.ar");
                    String params = cParametros.getParamsString(parameters);
                    try{
                        AsyncTask<String, String, JSONObject> loginAsyncTask = new cAPI(ActivityLogin.this, getApplicationContext(), mProgressBar);
                        //cAPI.mutex.acquire();
                        loginAsyncTask.execute("POST","login", params);
                        //cAPI.mutex.acquire();
                    } catch (Exception e) {
                        Log.i("RespuestaAPIRest", e.getMessage());
                    }
//                    if(cAPI.estado == 400){
//                        Toast.makeText(getApplicationContext(), "Usuario o contraseña incorrectos", Toast.LENGTH_LONG);
//                    }

                    break;
                case R.id.btnRegistro:
                    Log.i("El texto", "Se detectó boton de registro");
                    intent=new Intent(ActivityLogin.this, ActivityRegister.class);
                    //se regresa a la activity de Login
                    startActivity(intent);
                    break;
                default:
                    Toast.makeText(getApplicationContext(), "Error en Listener de botones", Toast.LENGTH_LONG).show();
            }


        }
    };
}
