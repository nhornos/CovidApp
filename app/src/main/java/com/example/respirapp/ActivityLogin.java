package com.example.respirapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;

import Clases.cObjetos;
import Clases.cParametros;

//public class ActivityLogin extends Activity implements SensorEventListener {
public class ActivityLogin extends Activity {

    private EditText inUser;
    private EditText inPassword;
    private Button btnSubmit;
    private Button btnRegistrarse;
    private TextView txtMsg;
    public static ProgressBar mProgressBar;
//    private SensorManager mSensorManager;

    DecimalFormat dosdecimales = new DecimalFormat("###.###");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btnSubmit = (Button)findViewById(R.id.btnSubmit);
        btnRegistrarse = (Button)findViewById(R.id.btnRegistro);

        inUser = (EditText)findViewById(R.id.inUserLogin);
        inPassword = (EditText)findViewById(R.id.inPassLogin);

        txtMsg = (TextView)findViewById(R.id.lbl_msg);

        mProgressBar = (ProgressBar) findViewById(R.id.progressLoaderLogin);

        // Accedemos al servicio de sensores
//        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);

        btnSubmit.setOnClickListener(botonesListeners);
        btnRegistrarse.setOnClickListener(botonesListeners);

        cObjetos.oActivity = this;

        inUser.setText(cParametros.getCache("usuario_email"));
        inPassword.setText(cParametros.getCache("usuario_email"));

        loguear();

        //Toast.makeText(this, String.valueOf(Build.VERSION.SDK_INT), Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onStop()
    {
        super.onStop();
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
    }

    @Override
    protected void onPause()
    {
        super.onPause();
//        pararSensores();
    }

    @Override
    protected void onRestart()
    {
        super.onRestart();
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        cObjetos.oActivity = this;
        cObjetos.oProgressBar = mProgressBar;
//        iniSensores();
    }

    private final View.OnClickListener botonesListeners = new View.OnClickListener() {

        public void onClick(View v) {
            //Se determina que componente genero un evento
            switch (v.getId()) {
                case R.id.btnSubmit:
                    Log.i("El texto", "Se detectó boton de submit");
                    if(inUser.getText().toString().length() == 0){
                        txtMsg.setText("Ingrese su usuario y contraseña.");
                        inUser.setError("Debe ingresar un usuario válido");
                        break;
                    }
                    if(inPassword.getText().toString().length() == 0 ){
                        txtMsg.setText("Ingrese su usuario y contraseña.");
                        inPassword.setError("Debe ingresar una contraseña válida");
                        break;
                    }
                    loguear();
                    break;
                case R.id.btnRegistro:
                    Log.i("El texto", "Se detectó boton de registro");
                    Intent intent=new Intent(ActivityLogin.this, ActivityRegister.class);
                    startActivity(intent);
//                    if(cAPI.checkConection(getApplicationContext()))
//                        Toast.makeText(getApplicationContext(), "Conectado", Toast.LENGTH_LONG).show();
//                    else
//                        Toast.makeText(getApplicationContext(), "Desconectado", Toast.LENGTH_LONG).show();
                    break;
                default:
                    Toast.makeText(getApplicationContext(), "Error en Listener de botones", Toast.LENGTH_LONG).show();
            }


        }
    };

    private void loguear() {

        if(inUser.getText().toString().length() == 0 || inPassword.getText().toString().length() == 0 ){
            txtMsg.setText("Ingrese su usuario y contraseña.");
            return;
        }

        mProgressBar.setVisibility(View.VISIBLE);
        //cObjetos.oUsuario.loguear(inUser.getText().toString().trim(), inPassword.getText().toString().trim());
        cObjetos.oUsuario.loguear("nhornos@alumno.unlam.edu.ar", "abcd1234");
    }

    //Nuevas cosas agregadas para sensores:
//
//    protected void iniSensores(){
//        mSensorManager.registerListener((SensorEventListener) this, mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL);
//    }
//    protected void pararSensores(){
//        mSensorManager.unregisterListener((SensorEventListener) this, mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER));
//    }
//
//    // Metodo que escucha el cambio de sensibilidad de los sensores
//    @Override
//    public void onAccuracyChanged(Sensor sensor, int accuracy)
//    {
//
//    }

    // Metodo que escucha el cambio de los sensores
//    @Override
//    public void onSensorChanged(SensorEvent event) {
//        Log.i("Sensor:", event.sensor.getName());
//        Log.i("Tipo:", String.valueOf(event.sensor.getType()));
//        if(event.sensor.getType() == Sensor.TYPE_ACCELEROMETER){
//            String txt = "Acelerometro:\n";
//            txt += "x: " + dosdecimales.format(event.values[0]) + " m/seg2 \n";
//            txt += "y: " + dosdecimales.format(event.values[1]) + " m/seg2 \n";
//            txt += "z: " + dosdecimales.format(event.values[2]) + " m/seg2 \n";
//            Log.i("Datos acelerometro:", txt);
//        }
//
//    }
}
