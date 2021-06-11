package com.example.respirapp;

import android.app.Activity;
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
import Clases.cObjetos;
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
        setContentView(R.layout.activity_login);

        Log.i("El texto", "Entro a ActivityLogin");

        // Defino los botones
        btnSubmit = (Button) findViewById(R.id.btnSubmit);

        btnSubmit.setOnClickListener(botonesListeners);
        btnRegistrarse = (Button) findViewById(R.id.btnRegistro);
        btnRegistrarse.setOnClickListener(botonesListeners);

        // Defino los campos de ingreso de datos
        inUser = (EditText) findViewById(R.id.inUserLogin);
        inPassword = (EditText) findViewById(R.id.inPassLogin);

        //Defino barra de cargando
        mProgressBar = (ProgressBar) findViewById(R.id.progressLoaderLogin);
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
            //Se determina que componente genero un evento
            switch (v.getId()) {
                case R.id.btnSubmit:
                    Log.i("El texto", "Se detectó boton de submit");
                    realizarLogin();
                    break;
                case R.id.btnRegistro:
                    Log.i("El texto", "Se detectó boton de registro");
                    Intent intent=new Intent(ActivityLogin.this, ActivityRegister.class);
                    startActivity(intent);
                    break;
                default:
                    Toast.makeText(getApplicationContext(), "Error en Listener de botones", Toast.LENGTH_LONG).show();
            }


        }
    };

    private void realizarLogin() {
        mProgressBar.setVisibility(View.VISIBLE);

//        String email = inUser.getText().toString();
        String email = "nhornos@alumno.unlam.edu.ar";
//        String password = inPassword.getText().toString();
        String password = "abcd1234";

        //Generamos los parametros para el AsyncTask
        Map<String, String> parameters = new HashMap<>();
        parameters.put("email", email);
        parameters.put("password", password);
        String params = cParametros.getParamsString(parameters);

        //Guardamos el email del usuario que intenta loguearse
        cObjetos.oUsuario.setEmail(email);

        AsyncTask<String, String, JSONObject> loginAsyncTask = new cAPI(ActivityLogin.this, getApplicationContext(), mProgressBar);
        //cAPI.mutex.acquire();
        loginAsyncTask.execute("POST","login", params);
        //cAPI.mutex.acquire();
    }
}
