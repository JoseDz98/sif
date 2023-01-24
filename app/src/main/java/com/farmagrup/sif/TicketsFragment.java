package com.farmagrup.sif;

import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;

public class TicketsFragment extends Fragment {

    ArrayList<String> area = new ArrayList<>();
    Spinner spinner_areas;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_tickets, container, false);
        spinner_areas = view.findViewById(R.id.spinner_area);

        area.add("TODAS");
        area.add("CEDIS");
        area.add("SISTEMAS");
        area.add("FACTURACION");


        //Adapters
        ArrayAdapter<String> area_adapter = new ArrayAdapter<>(getActivity(), androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, area);



        //Set Adapters
        spinner_areas.setAdapter(area_adapter);


        spinner_areas.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                ((TextView) adapterView.getChildAt(0)).setTextColor(Color.parseColor("#000000"));
                ((TextView) adapterView.getChildAt(0)).setTextSize(20);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        return  view;
    }
}