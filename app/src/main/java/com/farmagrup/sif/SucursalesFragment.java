package com.farmagrup.sif;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;


public class SucursalesFragment extends Fragment {

    MaterialButton manual;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sucursales, container, false);
        manual = view.findViewById(R.id.btn_manual);
        manual.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ManualFragment manualFragment = new ManualFragment();
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.contenedor, manualFragment)
                        .addToBackStack(null)
                        .commit();
            }
        });



        return  view;
    }


}