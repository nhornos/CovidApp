package com.example.respirapp;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;

import java.text.DecimalFormat;

import Clases.cEstructuras;
import Clases.cFunciones;

public class ActivityMenu extends AppCompatActivity { //implements NavigationView.OnNavigationItemSelectedListener{ //} implements SensorEventListener {

    private AppBarConfiguration mAppBarConfiguration;
    private TextView navEmail;
    private TextView navTitle;
    private boolean flagPresionado = false;
//    private SensorManager mSensorManager;
//    private Sensor mAccelerometer;
    DecimalFormat dosdecimales = new DecimalFormat("###.###");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getOnBackPressedDispatcher().addCallback(this, callback);

        setContentView(R.layout.activity_menu_2);

        NavigationView navigationView = findViewById(R.id.nav_view);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_respiracion, R.id.nav_ejercicios_del_dia,
                R.id.nav_patron, R.id.nav_sensores, R.id.nav_cerrar_sesion)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(ActivityMenu.this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(ActivityMenu.this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        //Inicializo los textos del navigation
        View headerView = navigationView.getHeaderView(0);
        navEmail = (TextView) headerView.findViewById(R.id.textViewEmailNav);
        navEmail.setText(cFunciones.getCache(getApplicationContext(), "usuario_email"));
        navTitle = (TextView) headerView.findViewById(R.id.textViewTitleNav);
        String nombre = getNombreEmail(cFunciones.getCache(getApplicationContext(), "usuario_email"));
        navTitle.setText("Hola " + nombre + "!");

//        navigationView.setNavigationItemSelectedListener(this);

        //Inicializo boton cerrar sesion
//        TextView cerrarSesion = (TextView) findViewById(R.id.cerrar_sesion);
//        cerrarSesion.setOnClickListener(botonesListeners);

        // Accedemos al servicio de sensores
//        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
//        mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
    }


//    @Override
//    protected void onPause()
//    {
//        super.onPause();
//        pararSensores();
//    }
//
//    @Override
//    protected void onResume()
//    {
//        super.onResume();
//        iniSensores();
//        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
//    }

    private String getNombreEmail(String email) {
        return email.substring(0, email.indexOf("@"));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_cerrar_sesion, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.nav_cerrar_sesion){
            new AlertDialog.Builder(ActivityMenu.this)
                    .setTitle("Alerta")
                    .setMessage("Desea cerrar sesión?")
                    .setNegativeButton(R.string.msgDenegar, new DialogInterface.OnClickListener() { public void onClick(DialogInterface dialog, int which) { } })
                    .setPositiveButton(R.string.msgAceptar, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(getApplicationContext(), ActivityLogin.class);
                            startActivity(intent);
                            finish();
                        }
                    })
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(ActivityMenu.this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    // Comportamiento del boton de back
    OnBackPressedCallback callback = new OnBackPressedCallback(true) {
        @Override
        public void handleOnBackPressed() {
            if(!flagPresionado){
                Toast.makeText(getApplicationContext(), "Presione de nuevo para salir de la app", Toast.LENGTH_LONG).show();
                flagPresionado = true;
            } else {
                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_HOME);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }

            //Se espera tres segundos a detectar nuevamente el boton de back
            new CountDownTimer(3000, 1000){

                @Override
                public void onTick(long millisUntilFinished) {}

                @Override
                public void onFinish() {
                    flagPresionado = false;
                }
            }.start();
        }
    };

//    @Override
//    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
//        switch (menuItem.getItemId()) {
//            case R.id.nav_cerrar_sesion: {
//                Intent intent = new Intent(getApplicationContext(), ActivityLogin.class);
//                startActivity(intent);
//                finish();
//                break;
//            }
//        }
//        menuItem
//        //close navigation drawer
//        drawer.closeDrawer(GravityCompat.START);
//        return super.onOptionsItemSelected(menuItem);
//    }

//    private final View.OnClickListener botonesListeners = new View.OnClickListener() {
//
//        public void onClick(View v) {
//            //Se determina que componente genero un evento
//            switch (v.getId()) {
//                case R.id.cerrar_sesion:
//                    Intent intent = new Intent(getApplicationContext(), ActivityLogin.class);
//                    startActivity(intent);
//                    finish();
//                    break;
//                default:
//                    Toast.makeText(getApplicationContext(), "Error en Listener de botones", Toast.LENGTH_LONG).show();
//            }
//
//
//        }
//    };

//    protected void iniSensores(){
//        mSensorManager.registerListener(this, mAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);
//    }
//    protected void pararSensores(){
//        mSensorManager.unregisterListener(this);
//    }
//
//    // Metodo que escucha el cambio de sensibilidad de los sensores
//    @Override
//    public void onAccuracyChanged(Sensor sensor, int accuracy)
//    {
//
//    }
//
//    //     Metodo que escucha el cambio de los sensores
//    @Override
//    public void onSensorChanged(SensorEvent event) {
//        Log.i("Sensor:", event.sensor.getName());
//        Log.i("Tipo:", String.valueOf(event.sensor.getType()));
//        if(event.sensor.getType() == Sensor.TYPE_ACCELEROMETER){
//            float valorX = Float.parseFloat(dosdecimales.format(event.values[0]));
//            float valorY = Float.parseFloat(dosdecimales.format(event.values[1]));
//            float valorZ = Float.parseFloat(dosdecimales.format(event.values[2]));
//
//            if(valorX > 8.8 && getRequestedOrientation() != ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE){
//                //Izquierda
//                Log.i("env:", getString(R.string.env));
//                cEstructuras.cEvento.registrar(this, this.getApplicationContext(), getString(R.string.env), "rotacion pantalla", "El usuario giro la pantalla a la izquierda");
//                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
//            }
//            if(valorX < -8.8 && getRequestedOrientation() != ActivityInfo.SCREEN_ORIENTATION_REVERSE_LANDSCAPE){
//                //Derecha
//                cEstructuras.cEvento.registrar(this, this.getApplicationContext(), getString(R.string.env), "rotacion pantalla", "El usuario giro la pantalla a la derecha");
//                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_REVERSE_LANDSCAPE);
//            }
//            if(valorY > 8.8 && getRequestedOrientation() != ActivityInfo.SCREEN_ORIENTATION_PORTRAIT){
//                //Abajo
//                cEstructuras.cEvento.registrar(this, this.getApplicationContext(), getString(R.string.env), "rotacion pantalla", "El usuario puso la pantalla a verticalmente");
//                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
//            }
//        }
//
//    }
}
