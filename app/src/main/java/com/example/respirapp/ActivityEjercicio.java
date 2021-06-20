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

import Clases.cListViewAdapter;

public class ActivityEjercicio extends AppCompatActivity implements SensorEventListener {

    private SensorManager mSensorManager;
    private Sensor mProximidad;
    private int idProxEjercicio;

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
        idProxEjercicio = extras.getInt("idProxEjercicio");
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
    protected void onResume() {
        super.onResume();
        iniSensores();
    }

    @Override
    protected void onPause() {
        super.onPause();
        pararSensores();
    }

    @Override
    public void onSensorChanged(SensorEvent event) {

        Log.i("Sensor:", event.sensor.getName());
        Log.i("Tipo:", String.valueOf(event.sensor.getType()));
        if(event.sensor.getType() == Sensor.TYPE_PROXIMITY){
            if( event.values[0] == 0 & App.permitePasarEjercicio)
            {
                Thread thread = new Thread() {
                    @Override
                    public void run() {
                        try {
                            while(true) {
                                sleep(2000);
                                App.permitePasarEjercicio = true;
                            }
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                };

                App.permitePasarEjercicio = false;
                Log.i("Sensores", "Proximidad detectada");
                App.pasarEjercicio = true;
                App.idEjercicio = idProxEjercicio;
                thread.start();
                this.finish();
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    protected void iniSensores(){
        mSensorManager.registerListener(this, this.mProximidad, SensorManager.SENSOR_DELAY_NORMAL);
    }

    protected void pararSensores(){
        mSensorManager.unregisterListener(this);
    }
}
