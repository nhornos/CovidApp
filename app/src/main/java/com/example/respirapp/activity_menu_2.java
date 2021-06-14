package com.example.respirapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;

import Clases.cObjetos;

public class activity_menu_2 extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private TextView navEmail;
    private TextView navTitle;
    private boolean flagPresionado = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getOnBackPressedDispatcher().addCallback(this, callback);

        setContentView(R.layout.activity_menu_2);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow,
                R.id.nav_tools, R.id.nav_share, R.id.nav_send)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(activity_menu_2.this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(activity_menu_2.this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        //Inicializo los textos del navigation
        View headerView = navigationView.getHeaderView(0);
        navEmail = (TextView) headerView.findViewById(R.id.textViewEmailNav);
        navEmail.setText(cObjetos.oUsuario.getEmail());
        navTitle = (TextView) headerView.findViewById(R.id.textViewTitleNav);
        navTitle.setText("RespirApp");

        //Inicializo boton cerrar sesion
        TextView cerrarSesion = (TextView) findViewById(R.id.cerrar_sesion);
        cerrarSesion.setOnClickListener(botonesListeners);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_menu_2, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(activity_menu_2.this, R.id.nav_host_fragment);
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

    private final View.OnClickListener botonesListeners = new View.OnClickListener() {

        public void onClick(View v) {
            //Se determina que componente genero un evento
            switch (v.getId()) {
                case R.id.cerrar_sesion:
                    Intent intent = new Intent(getApplicationContext(), ActivityLogin.class);
                    startActivity(intent);
                    finish();
                    break;
                default:
                    Toast.makeText(getApplicationContext(), "Error en Listener de botones", Toast.LENGTH_LONG).show();
            }


        }
    };
}
