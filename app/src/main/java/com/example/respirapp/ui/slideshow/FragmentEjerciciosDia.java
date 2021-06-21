package com.example.respirapp.ui.slideshow;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.respirapp.App;
import com.example.respirapp.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import Clases.cEstructuras;
import Clases.cFunciones;
import Clases.cListViewAdapter;
import Clases.cListViewColumns;

public class FragmentEjerciciosDia extends Fragment {

    private EjerciciosViewModel slideshowViewModel;
    private cListViewAdapter adapter;
    private ArrayList<cListViewColumns> ejerciciosDia;
    private ListView listView;
    private Calendar fecha;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        slideshowViewModel =
                ViewModelProviders.of(this).get(EjerciciosViewModel.class);
        View root = inflater.inflate(R.layout.fragment_ejercicios_dia, container, false);

        this.listView = (ListView) root.findViewById(R.id.lista_ejercicios_del_dia);
        llenarListaEjercicios();


        return root;

    }

    @Override
    public void onResume() {
        super.onResume();
        if(App.pasarEjercicio){
            App.pasarEjercicio = false;
            this.listView.getAdapter().getView(App.idEjercicio, null, null).performClick();
        }
//
    }

    private void llenarListaEjercicios() {
        ArrayList<cListViewColumns> ejerciciosEnfriantes = cEstructuras.cDB.ejerciciosEnfriantes;
        ArrayList<cListViewColumns> ejerciciosCalentantes = cEstructuras.cDB.ejerciciosCalentantes;
        ArrayList<cListViewColumns> ejerciciosEquilibrantes = cEstructuras.cDB.ejerciciosEquilibrantes;
        cListViewColumns enfriante;
        cListViewColumns calentante;
        cListViewColumns equilibrante;
        int indexEnfriante;
        int indexCalentante;
        int indexEquilibrante;

        if(cambiarEjercicios()){
            indexEnfriante = (int) (Math.random() * ejerciciosEnfriantes.size());
            indexCalentante = (int) (Math.random() * ejerciciosCalentantes.size());
            indexEquilibrante = (int) (Math.random() * ejerciciosEquilibrantes.size());

            //guardo en SharedPreferences los index de ejercicio
            cFunciones.setCache(this.getContext(), "ejercicio_dia_enfriante", String.valueOf(indexEnfriante));
            cFunciones.setCache(this.getContext(), "ejercicio_dia_calentante", String.valueOf(indexCalentante));
            cFunciones.setCache(this.getContext(), "ejercicio_dia_enfriante", String.valueOf(indexEquilibrante));

        }else{
            //tomo los index guardados
            indexEnfriante = Integer.parseInt(cFunciones.getCache(this.getContext(),"ejercicio_dia_enfriante"));
            indexCalentante = Integer.parseInt(cFunciones.getCache(this.getContext(),"ejercicio_dia_calentante"));
            indexEquilibrante = Integer.parseInt(cFunciones.getCache(this.getContext(),"ejercicio_dia_enfriante"));

        }

        //seteo los ejercicios
        enfriante = ejerciciosEnfriantes.get(indexEnfriante);
        calentante = ejerciciosCalentantes.get(indexCalentante);
        equilibrante = ejerciciosEquilibrantes.get(indexEquilibrante);

        ejerciciosDia = new ArrayList<>();
        ejerciciosDia.add(enfriante);
        ejerciciosDia.add(calentante);
        ejerciciosDia.add(equilibrante);

        this.adapter = new cListViewAdapter(this.getContext(), ejerciciosDia, listView);
        this.listView.setAdapter(this.adapter);
    }

    private boolean cambiarEjercicios(){
        Date dateTime = new Date();
        fecha = GregorianCalendar.getInstance();
        fecha.setTime(dateTime);
        String diaActual = String.valueOf(fecha.get(Calendar.DAY_OF_YEAR));

        return comprobarDia(diaActual);
    }

    private boolean comprobarDia(String diaActual){
        String diaGuardado = cFunciones.getCache(this.getContext(), "dia_ejercicios");
        if(!diaActual.equals(diaGuardado)){
            cFunciones.setCache(this.getContext(), "dia_ejercicios", diaActual);
            return true;
        }
        return false;
    }


}