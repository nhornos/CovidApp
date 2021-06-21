package com.example.respirapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

import Clases.cEstructuras;
import Clases.cFunciones;
import Clases.cListViewAdapter;

public class ActivityEjercicio extends AppCompatActivity implements SensorEventListener {

    private SensorManager mSensorManager;
    private Sensor mProximidad;
    private Sensor mAccelerometer;
    DecimalFormat dosdecimales = new DecimalFormat("###.###");
    private int idProxEjercicio;
    private Calendar fecha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ejercicio);

        // Accedemos al servicio de sensores
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mProximidad = mSensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

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

        Log.i("Pantalla", String.valueOf(App.posicionPantallaEjercicio));
        Log.i("Izquierda", String.valueOf(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE));
        Log.i("Derecha", String.valueOf(ActivityInfo.SCREEN_ORIENTATION_REVERSE_LANDSCAPE));
        Log.i("Recto", String.valueOf(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT));
        setRequestedOrientation(App.posicionPantallaEjercicio);
    }

    @Override
    protected void onResume() {
        super.onResume();
        iniSensores();
        setRequestedOrientation(App.posicionPantallaEjercicio);
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
        String ultEventoDetectado = "";
        synchronized (this) {
            Log.d("sensor", event.sensor.getName());

            switch (event.sensor.getType()) {
                case Sensor.TYPE_PROXIMITY:
                    if (event.values[0] == 0 & App.permitePasarEjercicio) {
                        ultEventoDetectado = "Presencia detectada";
                        Thread thread = new Thread() {
                            @Override
                            public void run() {
                                try {
                                    sleep(500);
                                    App.permitePasarEjercicio = true;
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            }
                        };
                        App.permitePasarEjercicio = false;
                        fecha = GregorianCalendar.getInstance();
                        String timeStamp = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new java.util.Date());
                        cFunciones.setCache(this.getApplicationContext(), "proximidad", "Detectada " + timeStamp);
                        App.pasarEjercicio = true;
                        App.idEjercicio = idProxEjercicio;

                        //Registrar evento presencia detectada
                        cEstructuras.cEvento.registrar(this, this.getApplicationContext(), getString(R.string.env), "sensor proximidad", "El usuario paso el ejercicio usando el sensor de proximidad");

                        thread.start();
                        this.finish();
                    }
                    break;
                case Sensor.TYPE_ACCELEROMETER:
                    float valorX = Float.parseFloat(dosdecimales.format(event.values[0]));
                    float valorY = Float.parseFloat(dosdecimales.format(event.values[1]));
                    float valorZ = Float.parseFloat(dosdecimales.format(event.values[2]));
                    if(valorX > 8.8 && getRequestedOrientation() != ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE){
                        //Izquierda
                        Log.i("env:", getString(R.string.env));
                        cEstructuras.cEvento.registrar(this, this.getApplicationContext(), getString(R.string.env), "rotacion pantalla", "El usuario giro la pantalla a la izquierda");
                        App.posicionPantallaEjercicio = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE;
                        setRequestedOrientation(App.posicionPantallaEjercicio);
                        cFunciones.setCache(this.getApplicationContext(), "izquierda_x", String.valueOf(valorX));
                        cFunciones.setCache(this.getApplicationContext(), "izquierda_y", String.valueOf(valorY));
                        cFunciones.setCache(this.getApplicationContext(), "izquierda_z", String.valueOf(valorZ));
                        ultEventoDetectado = "Orientacion izquierda detectada";
                    }
                    if(valorX < -8.8 && getRequestedOrientation() != ActivityInfo.SCREEN_ORIENTATION_REVERSE_LANDSCAPE){
                        //Derecha
                        cEstructuras.cEvento.registrar(this, this.getApplicationContext(), getString(R.string.env), "rotacion pantalla", "El usuario giro la pantalla a la derecha");
                        App.posicionPantallaEjercicio = ActivityInfo.SCREEN_ORIENTATION_REVERSE_LANDSCAPE;
                        setRequestedOrientation(App.posicionPantallaEjercicio);
                        cFunciones.setCache(this.getApplicationContext(), "derecha_x", String.valueOf(valorX));
                        cFunciones.setCache(this.getApplicationContext(), "derecha_y", String.valueOf(valorY));
                        cFunciones.setCache(this.getApplicationContext(), "derecha_z", String.valueOf(valorZ));
                        ultEventoDetectado = "Orientacion derecha detectada";
                    }
                    if(valorY > 8.8 && getRequestedOrientation() != ActivityInfo.SCREEN_ORIENTATION_PORTRAIT){
                        //Abajo
                        cEstructuras.cEvento.registrar(this, this.getApplicationContext(), getString(R.string.env), "rotacion pantalla", "El usuario puso la pantalla a verticalmente");
                        App.posicionPantallaEjercicio = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT;
                        setRequestedOrientation(App.posicionPantallaEjercicio);
                        cFunciones.setCache(this.getApplicationContext(), "vertical_x", String.valueOf(valorX));
                        cFunciones.setCache(this.getApplicationContext(), "vertical_y", String.valueOf(valorY));
                        cFunciones.setCache(this.getApplicationContext(), "vertical_z", String.valueOf(valorZ));
                        ultEventoDetectado = "Orientacion vertical detectada";
                    }
                    break;
                default:
                    ultEventoDetectado = "Evento desconocido";
                    break;
            }
            if(!ultEventoDetectado.equals("")){
                Log.i("EventoDetectado:", ultEventoDetectado);
                cFunciones.setCache(this.getApplicationContext(), "ultimo_evento", ultEventoDetectado);
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    protected void iniSensores(){
        mSensorManager.registerListener(this, this.mProximidad, SensorManager.SENSOR_DELAY_NORMAL);
        mSensorManager.registerListener(this, mAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);
    }

    protected void pararSensores(){
        mSensorManager.unregisterListener(this);
    }
}
