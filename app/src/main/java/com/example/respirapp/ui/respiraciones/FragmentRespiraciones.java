package com.example.respirapp.ui.respiraciones;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.respirapp.ActivityListaEjercicios;
import com.example.respirapp.R;

import Clases.cEstructuras;

public class FragmentRespiraciones extends Fragment implements View.OnClickListener {

    private RespiracionesViewModel galleryViewModel;
    private TextView txtCategoria1;
    private TextView txtCategoria2;
    private TextView txtCategoria3;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        galleryViewModel =
                ViewModelProviders.of(this).get(RespiracionesViewModel.class);
        View root = inflater.inflate(R.layout.fragment_respiraciones, container, false);
//        final TextView textView = root.findViewById(R.id.text_gallery);
        txtCategoria1 = root.findViewById(R.id.textCategoria1);
        txtCategoria2 = root.findViewById(R.id.textCategoria2);
        txtCategoria3 = root.findViewById(R.id.textCategoria3);

        txtCategoria1.setOnClickListener(this);
        txtCategoria2.setOnClickListener(this);
        txtCategoria3.setOnClickListener(this);
        return root;
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(getActivity().getApplicationContext(), ActivityListaEjercicios.class);

        switch (v.getId()) {
            case R.id.textCategoria1:
                intent.putExtra("tipoEjercicio", "enfriantes");
                break;
            case R.id.textCategoria2:
                intent.putExtra("tipoEjercicio", "equilibrantes");
                break;
            case R.id.textCategoria3:
                intent.putExtra("tipoEjercicio", "calentantes");
                break;
            default:
                Toast.makeText(getActivity().getApplicationContext(), "Error en Listener de botones", Toast.LENGTH_LONG).show();
                break;
        }
        startActivity(intent);
    }
}