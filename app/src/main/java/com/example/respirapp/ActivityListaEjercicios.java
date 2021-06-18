package com.example.respirapp;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import Clases.cEstructuras;

public class ActivityListaEjercicios extends AppCompatActivity{


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_ejercicios);

        //btnRegistrarse = (Button) findViewById(R.id.btnRegistrarse);


        Log.i("Registro", "On create");
    }

}

