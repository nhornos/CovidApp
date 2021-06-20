package com.example.respirapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class ActivityEjercicio extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ejercicio);

        TextView textEjercicio = (TextView) findViewById(R.id.text_titulo_ejercicio);
        ImageView imagenEjercicio = (ImageView) findViewById(R.id.img_instruccion_ejercicio);
        TextView textInstrucciones = (TextView) findViewById(R.id.text_instrucciones_ejercicio);
        TextView textContraindicaciones = (TextView) findViewById(R.id.text_contraindicaciones_ejercicio);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        String nombreEjercicio = extras.getString("nombreEjercicio");
        String nombreImagen = extras.getString("nombreImagenEjercicio");
        String explicacion = extras.getString("explicacionEjercicio");
        String contraindicaciones = extras.getString("contraindicacionesEjercicio");

        textEjercicio.setText(nombreEjercicio);
        imagenEjercicio.setBackgroundResource(getApplicationContext().getResources().getIdentifier(nombreImagen, "drawable", getApplicationContext().getPackageName()));
        textInstrucciones.setText(explicacion);
        textContraindicaciones.setText(contraindicaciones);
    }
}
