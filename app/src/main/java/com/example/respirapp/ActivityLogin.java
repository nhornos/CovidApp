package com.example.respirapp;

import android.app.Activity;
import android.app.ProgressDialog;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;

import Clases.*;

//public class ActivityLogin extends Activity implements SensorEventListener {
public class ActivityLogin extends Activity implements View.OnClickListener, SensorEventListener {

    private EditText inUser;
    private EditText inPassword;
    private Button btnSubmit;
    private Button btnRegistrarse;
    private SensorManager mSensorManager;
    private Sensor mAccelerometer;

    DecimalFormat dosdecimales = new DecimalFormat("###.###");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i("Oncreate","ejecutado");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btnSubmit = (Button)findViewById(R.id.btnSubmit);
        btnRegistrarse = (Button)findViewById(R.id.btnRegistro);

        inUser = (EditText)findViewById(R.id.inUserLogin);
        inPassword = (EditText)findViewById(R.id.inPassLogin);

        // Accedemos al servicio de sensores
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        btnSubmit.setOnClickListener(this);
        btnRegistrarse.setOnClickListener(this);

        inUser.setText(cFunciones.getCache(this.getApplicationContext(), "usuario_email"));
        inPassword.setText(cFunciones.getCache(this.getApplicationContext(), "usuario_password"));
    }

    @Override
    protected void onPause()
    {
        super.onPause();
//        pararSensores();
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        iniSensores();
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }

    private void loguear() {
        cEstructuras.cUsuario.loguear(this, this.getApplicationContext(), inUser.getText().toString().trim(), inPassword.getText().toString().trim());
//        cEstructuras.cUsuario.loguear(this, getApplicationContext(), "nhornos@alumno.unlam.edu.ar", "abcd1234");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnSubmit:
                Log.i("El texto", "Se detectó boton de submit");
                loguear();
                break;
            case R.id.btnRegistro:
                Log.i("El texto", "Se detectó boton de registro");
                Intent intent=new Intent(ActivityLogin.this, ActivityRegister.class);
                startActivity(intent);
                break;
            default:
                Toast.makeText(getApplicationContext(), "Error en Listener de botones", Toast.LENGTH_LONG).show();
        }
    }

    //Nuevas cosas agregadas para sensores:
//
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
            String txt = "Acelerometro:\n";
            txt += "x: " + dosdecimales.format(event.values[0]) + " m/seg2 \n";
            txt += "y: " + dosdecimales.format(event.values[1]) + " m/seg2 \n";
            txt += "z: " + dosdecimales.format(event.values[2]) + " m/seg2 \n";
            Log.i("Datos acelerometro:", txt);
            float valorX = Float.parseFloat(dosdecimales.format(event.values[0]));
            float valorY = Float.parseFloat(dosdecimales.format(event.values[1]));
            float valorZ = Float.parseFloat(dosdecimales.format(event.values[2]));

            if(valorX > 8.8){
                //Izquierda
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
            }
            if(valorX < -8.8){
                //Derecha
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_REVERSE_LANDSCAPE);
            }
            if(valorY > 8.8){
                //Abajo
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            }
        }

    }
}
