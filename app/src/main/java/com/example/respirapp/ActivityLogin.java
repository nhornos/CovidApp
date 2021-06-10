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
        setContentView(R.layout.login);

        Log.i("El texto", "Entro a login");

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
            Intent intent;

            //Se determina que componente genero un evento
            switch (v.getId()) {
                //Si se ocurrio un evento en el boton OK
                case R.id.btnSubmit:
                    mProgressBar.setVisibility(View.VISIBLE);
                    Log.i("El texto", "Se detectó boton de submit");

                    String email = inUser.getText().toString();
                    String password = inPassword.getText().toString();

                    //Generamos los parametros para el AsyncTask
                    Map<String, String> parameters = new HashMap<>();
                    //parameters.put("email", email);
                    parameters.put("password", password);
                    parameters.put("email", "nhornos@alumno.unlam.edu.ar");
//                    parameters.put("password", "abcd1234");
                    String params = cParametros.getParamsString(parameters);

                    //password=abcd1234&email=nhornos%40alumno.unlam.edu.ar

                    //Guardamos el email del usuario que intenta loguearse
                    cObjetos.oUsuario.setEmail(email);

                    AsyncTask<String, String, JSONObject> loginAsyncTask = new cAPI(ActivityLogin.this, getApplicationContext(), mProgressBar);
                    //cAPI.mutex.acquire();
//                        loginAsyncTask.execute("POST","login", params);
                    loginAsyncTask.execute("POST","login", params);
                    //cAPI.mutex.acquire();
//                    if(cAPI.estado == 400){
//                        Toast.makeText(getApplicationContext(), "Usuario o contraseña incorrectos", Toast.LENGTH_LONG);
//                    }

                    break;
                case R.id.btnRegistro:
                    Log.i("El texto", "Se detectó boton de registro");
                    Log.i("Datos Usuario:", "email:" + cObjetos.oUsuario.getEmail() + "\nToken: " + cObjetos.oUsuario.getToken() + "\nToken Refresh: " + cObjetos.oUsuario.getTokenRefresh());
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
