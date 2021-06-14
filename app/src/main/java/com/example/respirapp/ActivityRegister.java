package com.example.respirapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import Clases.cObjetos;

public class ActivityRegister extends AppCompatActivity {

    private EditText inUser;
    private EditText inPassword;
    private EditText inDNI;
    private EditText inName;
    private EditText inLastName;
    private Button btnRegistrarse;
    public static ProgressBar mProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        Log.i("El texto", "Entro a registro");

        // Defino los botones
        btnRegistrarse = (Button) findViewById(R.id.btnRegistrarse);
        btnRegistrarse.setOnClickListener(botonesListeners);

        // Defino los campos de ingreso de datos
        inDNI = (EditText) findViewById(R.id.inDNIRegister);
        inName = (EditText) findViewById(R.id.inNameRegister);
        inLastName = (EditText) findViewById(R.id.inLastNameRegister);
        inUser = (EditText) findViewById(R.id.inUserRegister);
        inPassword = (EditText) findViewById(R.id.inPassRegister);

        //Defino barra de cargando
        mProgressBar = (ProgressBar) findViewById(R.id.progressLoaderRegister);
        cObjetos.oProgressBar = mProgressBar;
    }

    @Override
    protected void onResume() {
        cObjetos.oActivity = this;
        cObjetos.oProgressBar = mProgressBar;
        super.onResume();
    }

    private final View.OnClickListener botonesListeners = new View.OnClickListener() {

        public void onClick(View v) {
            Intent intent;

            //Se determina que componente genero un evento
            switch (v.getId()) {
                case R.id.btnRegistrarse:
                    Log.i("El texto", "Se detectó boton de registrarse");
                    realizarRegistro();
                default:
                    Toast.makeText(getApplicationContext(), "Error en Listener de botones", Toast.LENGTH_LONG).show();
            }

        }
    };

    private void realizarRegistro() {
        mProgressBar.setVisibility(View.VISIBLE);

        cObjetos.oUsuario.registrar(Integer.parseInt(inDNI.getText().toString()), inName.getText().toString(), inLastName.getText().toString(), inUser.getText().toString(), inPassword.getText().toString());
        //cObjetos.oUsuario.registrar(36076620, "Nicolas", "Hornos", "nhornos@alumno.unlam.edu.ar", "abcd1234");
    }
}
