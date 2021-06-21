package com.example.respirapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import Clases.cEstructuras;

public class ActivityRegister extends AppCompatActivity implements View.OnClickListener{
    private EditText inUser;
    private EditText inPassword;
    private EditText inDNI;
    private EditText inName;
    private EditText inLastName;
    private Button btnRegistrarse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        btnRegistrarse = (Button) findViewById(R.id.btnRegistrarse);
        inDNI = (EditText) findViewById(R.id.inDNIRegister);
        inName = (EditText) findViewById(R.id.inNameRegister);
        inLastName = (EditText) findViewById(R.id.inLastNameRegister);
        inUser = (EditText) findViewById(R.id.inUserRegister);
        inPassword = (EditText) findViewById(R.id.inPassRegister);

        Log.i("Registro", "On create");

        btnRegistrarse.setOnClickListener(this);
    }

    private void realizarRegistro() {
        cEstructuras.cUsuario.registrar(this, getApplicationContext(), inDNI.getText().toString(), inName.getText().toString(), inLastName.getText().toString(), inUser.getText().toString(), inPassword.getText().toString());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnRegistrarse:
                Log.i("El texto", "Se detectó boton de registrarse");
                realizarRegistro();
                break;
            default:
                Toast.makeText(getApplicationContext(), "Error en Listener de botones", Toast.LENGTH_LONG).show();
        }
    }
}
