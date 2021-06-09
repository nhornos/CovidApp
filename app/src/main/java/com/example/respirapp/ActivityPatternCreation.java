package com.example.respirapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.andrognito.patternlockview.PatternLockView;
import com.andrognito.patternlockview.listener.PatternLockViewListener;
import com.andrognito.patternlockview.utils.PatternLockUtils;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import io.paperdb.Paper;

public class ActivityPatternCreation extends Activity {

    String save_pattern_key = "pattern_code";
    String final_pattern;
    PatternLockView mPatternLockView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pattern_creation);

        //Seteo el patron
        mPatternLockView = (PatternLockView)findViewById(R.id.pattern_lock_creation);
        mPatternLockView.addPatternLockListener(patronListener);

        //Seteo los botones
        Button btnSetup = (Button)findViewById(R.id.btnSetearPatron);
        btnSetup.setOnClickListener(botonesListeners);

        //Inicializo el paper donde voy a guardar el patron
        Paper.init(this);

    }

    private final PatternLockViewListener patronListener = new PatternLockViewListener() {


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
    };

    private final View.OnClickListener botonesListeners = new View.OnClickListener() {

        public void onClick(View v) {
            Intent intent;

            //Se determina que componente genero un evento
            switch (v.getId()) {
                //Si se ocurrio un evento en el boton Registrar Patron
                case R.id.btnSetearPatron:
                    if(final_pattern.length() > 3){
                        Paper.book().write(save_pattern_key, final_pattern);
                        Toast.makeText(ActivityPatternCreation.this, "Patron guardado!", Toast.LENGTH_SHORT).show();
                        intent = new Intent(ActivityPatternCreation.this, ActivityLogin.class);
                        startActivity(intent);
                        ActivityPatternCreation.this.finish();
                    } else{
                        Toast.makeText(ActivityPatternCreation.this, "El patron es muy corto!", Toast.LENGTH_LONG).show();
                        mPatternLockView.clearPattern();
                    }
                    break;
                default:
                    Toast.makeText(getApplicationContext(), "Error en Listener de botones", Toast.LENGTH_LONG).show();
            }


        }
    };

}
