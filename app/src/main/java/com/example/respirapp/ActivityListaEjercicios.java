package com.example.respirapp;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
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

import java.text.DecimalFormat;
import java.util.List;

import Clases.cEstructuras;
import Clases.cListViewAdapter;

public class ActivityListaEjercicios extends AppCompatActivity implements SensorEventListener {

    private cListViewAdapter adapter;
    private ListView listView;
    private SensorManager mSensorManager;
    private Sensor mAccelerometer;
    DecimalFormat dosdecimales = new DecimalFormat("###.###");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_ejercicios);

        this.listView = (ListView) findViewById(R.id.lista_ejercicios);
        configurarPantalla();
        Log.i("Registro", "On create");

        // Accedemos al servicio de sensores
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(App.pasarEjercicio){
            App.pasarEjercicio = false;
            this.listView.getAdapter().getView(App.idEjercicio, null, null).performClick();
        }
        iniSensores();
//
    }

    @Override
    protected void onPause()
    {
        super.onPause();
        pararSensores();
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

    protected void iniSensores(){
        mSensorManager.registerListener(this, mAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);
    }
    protected void pararSensores(){
        mSensorManager.unregisterListener(this);
    }

    // Metodo que escucha el cambio de sensibilidad de los sensores
    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy)
    {

    }

    //     Metodo que escucha el cambio de los sensores
    @Override
    public void onSensorChanged(SensorEvent event) {
        Log.i("Sensor:", event.sensor.getName());
        Log.i("Tipo:", String.valueOf(event.sensor.getType()));
        if(event.sensor.getType() == Sensor.TYPE_ACCELEROMETER){
            float valorX = event.values[0];
            float valorY = event.values[1];
            float valorZ = event.values[2];

            if(valorX > 8.8 && getRequestedOrientation() != ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE){
                //Izquierda
                Log.i("env:", getString(R.string.env));
                cEstructuras.cEvento.registrar(this, this.getApplicationContext(), getString(R.string.env), "rotacion pantalla", "El usuario giro la pantalla a la izquierda");
                App.posicionPantallaEjercicio = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE;
                setRequestedOrientation(App.posicionPantallaEjercicio);
            }
            if(valorX < -8.8 && getRequestedOrientation() != ActivityInfo.SCREEN_ORIENTATION_REVERSE_LANDSCAPE){
                //Derecha
                cEstructuras.cEvento.registrar(this, this.getApplicationContext(), getString(R.string.env), "rotacion pantalla", "El usuario giro la pantalla a la derecha");
                App.posicionPantallaEjercicio = ActivityInfo.SCREEN_ORIENTATION_REVERSE_LANDSCAPE;
                setRequestedOrientation(App.posicionPantallaEjercicio);
            }
            if(valorY > 8.8 && getRequestedOrientation() != ActivityInfo.SCREEN_ORIENTATION_PORTRAIT){
                //Abajo
                cEstructuras.cEvento.registrar(this, this.getApplicationContext(), getString(R.string.env), "rotacion pantalla", "El usuario puso la pantalla a verticalmente");
                App.posicionPantallaEjercicio = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT;
                setRequestedOrientation(App.posicionPantallaEjercicio);
            }
        }

    }

}

