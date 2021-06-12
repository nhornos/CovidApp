package com.example.respirapp.ui.tools;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.ui.AppBarConfiguration;

import com.andrognito.patternlockview.PatternLockView;
import com.andrognito.patternlockview.listener.PatternLockViewListener;
import com.andrognito.patternlockview.utils.PatternLockUtils;
import com.example.respirapp.ActivityLogin;
import com.example.respirapp.ActivityPatternCreation;
import com.example.respirapp.R;
import com.example.respirapp.ui.home.HomeFragment;

import java.util.List;

import io.paperdb.Paper;

public class ToolsFragment extends Fragment {

    private ToolsViewModel toolsViewModel;
    String save_pattern_key = "pattern_code";
    private PatternLockView mPatternLockView;
    private String final_pattern;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        toolsViewModel =
                ViewModelProviders.of(this).get(ToolsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_tools, container, false);
//        final TextView textView = root.findViewById(R.id.text_tools);
//        toolsViewModel.getText().observe(this, new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String s) {
//                textView.setText(s);
//            }
//        });


        //Seteo el patron
        mPatternLockView = (PatternLockView)root.findViewById(R.id.pattern_lock_creation);
        mPatternLockView.addPatternLockListener(patronListener);

        //Seteo los botones
        Button btnSetup = (Button)root.findViewById(R.id.btnSetearPatron);
        btnSetup.setOnClickListener(botonesListeners);

        //Inicializo el paper donde voy a guardar el patron
//        Paper.init(this);
        Paper.init(this.getContext());
        return root;
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
                        Toast.makeText(getContext(), "Patron guardado!", Toast.LENGTH_SHORT).show();
//                        intent = new Intent(ActivityPatternCreation.this, ActivityLogin.class);
//                        intent = new Intent(getContext(), ActivityLogin.class);
//
//                        startActivity(intent);

//                        Fragment fragment = new HomeFragment();
//                        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
//                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//                        fragmentTransaction.replace(R.id.container, fragment);
//                        fragmentTransaction.addToBackStack(null);
//                        fragmentTransaction.commit();
                    } else{
                        Toast.makeText(ToolsFragment.this.getContext(), "El patron es muy corto!", Toast.LENGTH_LONG).show();
                    }
                    mPatternLockView.clearPattern();
                    break;
                default:
                    Toast.makeText(getContext(), "Error en Listener de botones", Toast.LENGTH_LONG).show();
            }


        }
    };
}