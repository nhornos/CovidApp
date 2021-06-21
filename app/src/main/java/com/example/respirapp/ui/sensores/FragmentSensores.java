package com.example.respirapp.ui.sensores;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.respirapp.R;

import Clases.cFunciones;

public class FragmentSensores extends Fragment {

    private SendViewModel sendViewModel;
    private TextView textAcelerometroIzquierda;
    private TextView textAcelerometroVertical;
    private TextView textAcelerometroDerecha;
    private TextView textPresencia;
    private TextView textEventoDetectado;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        sendViewModel =
                ViewModelProviders.of(this).get(SendViewModel.class);
        View root = inflater.inflate(R.layout.fragment_sensores, container, false);

        textAcelerometroIzquierda = root.findViewById(R.id.valor_acel_izq);
        textAcelerometroVertical = root.findViewById(R.id.valor_acel_vert);
        textAcelerometroDerecha = root.findViewById(R.id.valor_acel_der);
        textPresencia = root.findViewById(R.id.valor_presencia);
        textEventoDetectado = root.findViewById(R.id.ult_evento_detectado);

        return root;
    }

    @Override
    public void onResume() {
        super.onResume();
        String proximidad = cFunciones.getCache(getContext(), "proximidad");
        String acelerometroIzquierda = "Valor x: " + cFunciones.getCache(getContext(), "izquierda_x") +
                "\nValor y: " + cFunciones.getCache(getContext(), "izquierda_y") +
                "\nValor z: " + cFunciones.getCache(getContext(), "izquierda_z");
        String acelerometroDerecha = "Valor x: " + cFunciones.getCache(getContext(), "derecha_x") +
                "\nValor y: " + cFunciones.getCache(getContext(), "derecha_y") +
                "\nValor z: " + cFunciones.getCache(getContext(), "derecha_z");
        String acelerometroVertical = "Valor x: " + cFunciones.getCache(getContext(), "vertical_x") +
                "\nValor y: " + cFunciones.getCache(getContext(), "vertical_y") +
                "\nValor z: " + cFunciones.getCache(getContext(), "vertical_z");
        String ultEvento = cFunciones.getCache(getContext(), "ultimo_evento");
        Log.i("ultEvento", ultEvento);

        textAcelerometroIzquierda.setText(acelerometroIzquierda);
        textAcelerometroDerecha.setText(acelerometroDerecha);
        textAcelerometroVertical.setText(acelerometroVertical);
        textPresencia.setText(proximidad);
        textEventoDetectado.setText(ultEvento);
    }
}