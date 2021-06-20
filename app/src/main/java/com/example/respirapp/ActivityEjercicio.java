package com.example.respirapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

public class ActivityEjercicio extends AppCompatActivity implements SensorEventListener {

    private SensorManager mSensorManager;
    private Sensor mProximidad;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ejercicio);

        // Accedemos al servicio de sensores
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mProximidad = mSensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);

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

    @Override
    public void onSensorChanged(SensorEvent event) {
//        Log.i("Sensor:", event.sensor.getName());
//        Log.i("Tipo:", String.valueOf(event.sensor.getType()));
//        if(event.sensor.getType() == Sensor.TYPE_PROXIMITY){
//            String txt = "";
//            txt += "Proximidad:\n";
//            txt += event.values[0] + "\n";
//
//            proximity.setText(txt);
//
//            // Si detecta 0 lo represento
//            if( event.values[0] == 0 )
//            {
//                detecta.setBackgroundColor(Color.parseColor("#cf091c"));
//                detecta.setText("Proximidad Detectada");
//            }
//        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
