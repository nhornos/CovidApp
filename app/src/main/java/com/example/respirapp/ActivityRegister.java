package com.example.respirapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ActivityRegister extends AppCompatActivity {

    private EditText inUser;
    private EditText inPassword;
    private Button btnRegistrarse;
    private Button btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);

        Log.i("El texto", "Entro a registro");

        // Defino los botones
        btnRegistrarse = (Button) findViewById(R.id.btnRegistrarse);
        btnRegistrarse.setOnClickListener(botonesListeners);
        btnBack = (Button) findViewById(R.id.btnBackRegister);
        btnBack.setOnClickListener(botonesListeners);

        // Defino los campos de ingreso de datos
        inUser = (EditText) findViewById(R.id.inUserLogin);
        inPassword = (EditText) findViewById(R.id.inPassLogin);
    }

    private final View.OnClickListener botonesListeners = new View.OnClickListener() {

        public void onClick(View v) {
            Intent intent;

            //Se determina que componente genero un evento
            switch (v.getId()) {
                case R.id.btnRegistrarse:
                    Log.i("El texto", "Se detectó boton de registrarse");
                    //Aca hacer el registro y volver al login
                    break;
                case R.id.btnBackRegister:
                    Log.i("El texto", "Se detectó boton de back");
                    intent=new Intent(ActivityRegister.this, ActivityLogin.class);
                    //se regresa a la activity de Login
                    startActivity(intent);
                    break;
                default:
                    Toast.makeText(getApplicationContext(), "Error en Listener de botones", Toast.LENGTH_LONG).show();
            }

        }
    };
}
