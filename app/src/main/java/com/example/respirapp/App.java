package com.example.respirapp;

import android.app.Application;
import android.content.pm.ActivityInfo;

public class App extends Application {
    public static boolean pasarEjercicio;
    public static int idEjercicio;
    public static int posicionPantallaEjercicio = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT;
    public static boolean permitePasarEjercicio = true;
}
