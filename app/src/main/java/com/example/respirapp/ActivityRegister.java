package com.example.respirapp;

import androidx.appcompat.app.AppCompatActivity;

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

public class ActivityRegister extends AppCompatActivity {

    private EditText inUser;
    private EditText inPassword;
    private EditText inDNI;
    private EditText inName;
    private EditText inLastName;
    private Button btnRegistrarse;
    private Button btnBack;
    public static ProgressBar mProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        Log.i("El texto", "Entro a registro");

        // Defino los botones
        btnRegistrarse = (Button) findViewById(R.id.btnRegistrarse);
        btnRegistrarse.setOnClickListener(botonesListeners);
        btnBack = (Button) findViewById(R.id.btnBackRegister);
        btnBack.setOnClickListener(botonesListeners);

        // Defino los campos de ingreso de datos
        inDNI = (EditText) findViewById(R.id.inDNIRegister);
        inName = (EditText) findViewById(R.id.inNameRegister);
        inLastName = (EditText) findViewById(R.id.inLastNameRegister);
        inUser = (EditText) findViewById(R.id.inUserRegister);
        inPassword = (EditText) findViewById(R.id.inPassRegister);

        //Defino barra de cargando
        mProgressBar = (ProgressBar) findViewById(R.id.progressLoaderRegister);
    }

    private final View.OnClickListener botonesListeners = new View.OnClickListener() {

        public void onClick(View v) {
            Intent intent;

            //Se determina que componente genero un evento
            switch (v.getId()) {
                case R.id.btnRegistrarse:
                    mProgressBar.setVisibility(View.VISIBLE);
                    Log.i("El texto", "Se detectó boton de registrarse");
                    //Aca hacer el registro y volver al ActivityLogin

                    Map<String, String> parameters = new HashMap<>();
                    parameters.put("env", "TEST");
//                    parameters.put("name", inName.getText().toString());
//                    parameters.put("lastname", inLastName.getText().toString());
//                    parameters.put("dni", inDNI.getText().toString());
//                    parameters.put("email", inUser.getText().toString());
//                    parameters.put("password", inPassword.getText().toString());
                    parameters.put("name", "Nicolas");
                    parameters.put("lastname", "Hornos");
                    parameters.put("dni", "36076620");
                    parameters.put("email", "nhornos@alumno.unlam.edu.ar");
                    parameters.put("password", "abcd1234");
                    parameters.put("commission", "2900");
                    parameters.put("group", "9");
                    String params = cParametros.getParamsString(parameters);

                    //Guardamos el email del usuario que intenta loguearse
                    cObjetos.oUsuario.setEmail(inUser.getText().toString());

                    AsyncTask<String, String, JSONObject> registerAsyncTask = new cAPI(ActivityRegister.this, getApplicationContext(), mProgressBar);

                    registerAsyncTask.execute("POST","register",params);
                    break;
                case R.id.btnBackRegister:
                    Log.i("El texto", "Se detectó boton de back");
                    //se regresa a la activity de Login
                    finish();
                    break;
                default:
                    Toast.makeText(getApplicationContext(), "Error en Listener de botones", Toast.LENGTH_LONG).show();
            }

        }
    };
}
