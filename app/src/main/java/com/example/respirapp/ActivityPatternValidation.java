package com.example.respirapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.andrognito.patternlockview.PatternLockView;
import com.andrognito.patternlockview.listener.PatternLockViewListener;
import com.andrognito.patternlockview.utils.PatternLockUtils;

import java.util.List;

import Clases.cFunciones;
import io.paperdb.Paper;

public class ActivityPatternValidation extends Activity implements PatternLockViewListener {
    String save_pattern_key = "pattern_code";
    String patternEntered;
    String save_pattern;
    String msgLevelBattery;

    PatternLockView mPatternLockView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pattern_validation);

        //Seteo el view del patron
        mPatternLockView = (PatternLockView)findViewById(R.id.pattern_lock_validation);
        mPatternLockView.addPatternLockListener(this);

        Log.i("Pattron:","On create");

        //Inicializamos el paper donde guardamos el patron
        Paper.init(this);
        actualizarPassPatron();
    }

    @Override
    protected void onStart() {
        super.onStart();
        actualizarPassPatron();
    }

    @Override
    protected void onResume() {
        super.onResume();
        informarNivelBateria();
        verificarPatron();
    }

    private void informarNivelBateria() {
        msgLevelBattery = "Nivel de batería: " + String.valueOf(getBatteryPercentage(this)) + "%";
        Toast.makeText(this, msgLevelBattery, Toast.LENGTH_LONG).show();
    }

    private int getBatteryPercentage(Context context) {

        if (Build.VERSION.SDK_INT >= 21) {

            BatteryManager bm = (BatteryManager) context.getSystemService(BATTERY_SERVICE);
            return bm.getIntProperty(BatteryManager.BATTERY_PROPERTY_CAPACITY);

        } else {

            IntentFilter iFilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
            Intent batteryStatus = context.registerReceiver(null, iFilter);

            int level = batteryStatus != null ? batteryStatus.getIntExtra(BatteryManager.EXTRA_LEVEL, -1) : -1;
            int scale = batteryStatus != null ? batteryStatus.getIntExtra(BatteryManager.EXTRA_SCALE, -1) : -1;

            double batteryPct = level / (double) scale;

            return (int) (batteryPct * 100);
        }

    }

    private void actualizarPassPatron() {
//        save_pattern = Paper.book().read(save_pattern_key);
        save_pattern = cFunciones.getCache(getApplicationContext(), getApplicationContext().getString(R.string.patron_actual));
    }

    private void verificarPatron() {
        //Me fijo si no hay patrón, y mando a crearlo

        Log.i("Entro a:", "verificar Patron");
        if(cFunciones.getCache(getApplicationContext(), getApplicationContext().getString(R.string.patron_actual)).equals("")) {
            //Voy al activity de la creación del patrón
            Intent intent = new Intent(ActivityPatternValidation.this, ActivityPatternCreation.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    public void onStarted() {

    }

    @Override
    public void onProgress(List<PatternLockView.Dot> progressPattern) {

    }

    @Override
    public void onCleared() {

    }

    @Override
    public void onComplete(List<PatternLockView.Dot> pattern) {
        Log.i("Pattron:","On complete");
        patternEntered = PatternLockUtils.patternToString(mPatternLockView,pattern);
//        if(patternEntered.equals(save_pattern)){
        if(patternEntered.equals(cFunciones.getCache(getApplicationContext(), getApplicationContext().getString(R.string.patron_actual)))){
            //Toast.makeText(ActivityPatternValidation.this, "Password Correcta!", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(ActivityPatternValidation.this, ActivityLogin.class);
            startActivity(intent);
            finish();
        }else{
            Toast.makeText(ActivityPatternValidation.this, "Password Incorrecta!", Toast.LENGTH_SHORT).show();
        }
        mPatternLockView.clearPattern();
    }
}
