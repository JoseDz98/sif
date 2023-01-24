package com.farmagrup.sif;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.button.MaterialButton;


public class MainFragment extends Fragment {






    public MainFragment() {
        // Required empty public constructor

    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        MaterialButton sucursal = view.findViewById(R.id.btn_sucursal);
        sucursal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SucursalesFragment sucursalFragment = new SucursalesFragment();
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.contenedor, sucursalFragment)
                        .addToBackStack(null)
                        .commit();
            }
        });

        MaterialButton tickets = view.findViewById(R.id.btn_foot);
        tickets.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FootrintsFragment footrintsFragment = new FootrintsFragment();
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.contenedor, footrintsFragment)
                        .addToBackStack(null)
                        .commit();
            }
        });

        return view;
    }

}