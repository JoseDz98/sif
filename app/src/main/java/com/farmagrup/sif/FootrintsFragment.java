package com.farmagrup.sif;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.button.MaterialButton;

public class FootrintsFragment extends Fragment {


    MaterialButton btn_seguimiento;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_footrints, container, false);
        btn_seguimiento = view.findViewById(R.id.btn_seguimiento);
        btn_seguimiento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TicketsFragment ticketsFragment = new TicketsFragment();
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.contenedor, ticketsFragment)
                        .addToBackStack(null)
                        .commit();
            }
        });
        return view;
    }

}