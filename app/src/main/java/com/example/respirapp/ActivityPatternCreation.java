package com.example.respirapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.andrognito.patternlockview.PatternLockView;
import com.andrognito.patternlockview.listener.PatternLockViewListener;
import com.andrognito.patternlockview.utils.PatternLockUtils;

import java.util.List;

import Clases.cFunciones;
import io.paperdb.Paper;

public class ActivityPatternCreation extends Activity implements PatternLockViewListener {

    String save_pattern_key = "pattern_code";
    String final_pattern;
    PatternLockView mPatternLockView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pattern_creation);

        //Seteo el patron
        mPatternLockView = (PatternLockView)findViewById(R.id.pattern_lock_create);
        mPatternLockView.addPatternLockListener(this);

        //Seteo los botones
        Button btnSetup = (Button)findViewById(R.id.btnSetearPatron);
        btnSetup.setOnClickListener(botonesListeners);

        //Inicializo el paper donde voy a guardar el patron
        Paper.init(this);

    }

    private final View.OnClickListener botonesListeners = new View.OnClickListener() {

        public void onClick(View v) {
            //Se determina que componente genero un evento
            switch (v.getId()) {
                //Si se ocurrio un evento en el boton Registrar Patron
                case R.id.btnSetearPatron:
                    guardarPatron();
                    break;
                default:
                    Toast.makeText(getApplicationContext(), "Error en Listener de botones", Toast.LENGTH_LONG).show();
            }


        }
    };

    private void guardarPatron() {
        if(final_pattern.length() > 3){
//            Paper.book().write(save_pattern_key, final_pattern);
            cFunciones.setCache(getApplicationContext(), getApplicationContext().getString(R.string.patron_actual), final_pattern);
            Toast.makeText(ActivityPatternCreation.this, "Patron guardado!", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(ActivityPatternCreation.this, ActivityLogin.class);
            startActivity(intent);
            finish();
        } else{
            Toast.makeText(ActivityPatternCreation.this, "El patron es muy corto!", Toast.LENGTH_LONG).show();
            mPatternLockView.clearPattern();
        }
    }

    @Override
    public void onStarted() {

    }

    @Override
    public void onProgress(List<PatternLockView.Dot> progressPattern) {

    }

    @Override
    public void onComplete(List<PatternLockView.Dot> pattern) {
        final_pattern = PatternLockUtils.patternToString(mPatternLockView,pattern);
    }

    @Override
    public void onCleared() {

    }
}
