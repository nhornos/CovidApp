package com.example.respirapp.ui.tools;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.andrognito.patternlockview.PatternLockView;
import com.andrognito.patternlockview.listener.PatternLockViewListener;
import com.andrognito.patternlockview.utils.PatternLockUtils;
import com.example.respirapp.R;

import java.util.List;

import Clases.cEstructuras;
import Clases.cFunciones;
import io.paperdb.Paper;

public class ToolsFragment extends Fragment implements PatternLockViewListener, View.OnClickListener {

    private ToolsViewModel toolsViewModel;
    private PatternLockView mPatternLockView;
    private Button btnSetup;

    private String final_pattern;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        toolsViewModel = ViewModelProviders.of(this).get(ToolsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_tools, container, false);

        //Seteo el patron
        mPatternLockView = (PatternLockView)root.findViewById(R.id.pattern_lock_creation);
        btnSetup = (Button)root.findViewById(R.id.btnSetearPatron);

        mPatternLockView.addPatternLockListener(this);
        btnSetup.setOnClickListener(this);

        return root;
    }

    @Override
    public void onComplete(List<PatternLockView.Dot> pattern) {
        final_pattern = PatternLockUtils.patternToString(mPatternLockView,pattern);
        cFunciones.setCache(getActivity().getApplicationContext(), getActivity().getApplicationContext().getString(R.string.patron_nuevo), final_pattern);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnSetearPatron:
                if(final_pattern.length() > 3){
                    cEstructuras.cEvento.registrar(getActivity(), getContext(), getContext().getString(R.string.env), "patron", "El usuario modifico el patron manualmente");
                } else{
                    Toast.makeText(getContext(), "El patron es muy corto!", Toast.LENGTH_LONG).show();
                }
                mPatternLockView.clearPattern();
                break;
            default:
                Toast.makeText(getContext(), "Error en Listener de botones", Toast.LENGTH_LONG).show();
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
}