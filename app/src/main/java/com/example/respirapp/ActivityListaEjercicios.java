package com.example.respirapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

import Clases.cEstructuras;
import Clases.cListViewAdapter;

public class ActivityListaEjercicios extends AppCompatActivity{

    private cListViewAdapter adapter;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_ejercicios);

        this.listView = (ListView) findViewById(R.id.lista_ejercicios);
        configurarPantalla();
        Log.i("Registro", "On create");
    }

    private void configurarPantalla(){
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        String tipoEjercicio = extras.getString("tipoEjercicio");
        TextView titulo = (TextView) findViewById(R.id.text_ejercicios);
        //tituloEjerciciosEnfriantes
        int tituloID = getApplicationContext().getResources().getIdentifier("titulo_ejercicios_" + tipoEjercicio, "string", getApplicationContext().getPackageName());
        titulo.setText(getApplicationContext().getString(tituloID));
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.layout_ejercicios);
        int imagenID = getApplicationContext().getResources().getIdentifier("ejercicios_" + tipoEjercicio, "drawable", getApplicationContext().getPackageName());
        linearLayout.setBackgroundResource(imagenID);
        llenarListaEjercicios(tipoEjercicio);
    }

    private void llenarListaEjercicios(String tipoEjercicio) {
        switch (tipoEjercicio){
            case "enfriantes":
                this.adapter = new cListViewAdapter(this.getApplicationContext(), cEstructuras.cDB.ejerciciosEnfriantes, listView);
                break;
            case "equilibrantes":
                this.adapter = new cListViewAdapter(this.getApplicationContext(), cEstructuras.cDB.ejerciciosEquilibrantes, listView);
                break;
            case "calentantes":
                this.adapter = new cListViewAdapter(this.getApplicationContext(), cEstructuras.cDB.ejerciciosCalentantes, listView);
                break;
            default:
                Toast.makeText(this.getApplicationContext(), "Error en el tipo de ejercicio", Toast.LENGTH_LONG).show();
        }

        this.listView.setAdapter(this.adapter);
    }

}

