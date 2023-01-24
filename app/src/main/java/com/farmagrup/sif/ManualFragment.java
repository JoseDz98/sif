package com.farmagrup.sif;

import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ManualFragment extends Fragment implements SearchView.OnQueryTextListener {

    SearchView searchView;
    ImageView imageView;
    private list_adapter list_adapter;
    private ArrayList<productos_datos> arrayList = new ArrayList<>();
    private RecyclerView recyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_manual, container, false);

        searchView = view.findViewById(R.id.buscar);
        searchView.setOnQueryTextListener(this);
        recyclerView = view.findViewById(R.id.products_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        ImageView searchIcon=
                searchView.findViewById(androidx.appcompat.R.id.search_button);
        searchIcon.setColorFilter(getResources().getColor(R.color.black));
        ImageView searchClose=
                searchView.findViewById(androidx.appcompat.R.id.search_close_btn);
        searchClose.setColorFilter(getResources().getColor(R.color.black));
        SearchView.SearchAutoComplete searchAutoComplete = searchView.findViewById(androidx.appcompat.R.id.search_src_text);
        searchAutoComplete.setHintTextColor(getResources().getColor(R.color.black));
        searchAutoComplete.setTextColor(getResources().getColor(android.R.color.black));


        String url = "http://192.168.1.20/api/obtener_lista_naturales.php";
        StringRequest stringRequest = new StringRequest(Request.Method.GET,url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                String[] productos = response.split("\n");
                List<String> stringList = new ArrayList<>(Arrays.asList(productos));
                int tamaño = stringList.size();
                for(int x=0; x<tamaño; x++){
                    String name =  productos[x].substring(0,productos[x].indexOf(":"));
                    String id = productos[x].substring(productos[x].indexOf(":")+1, productos[x].length());
                    String url_imagen = "http://192.168.1.20/imagenes/"+id+".png";
                        arrayList.add(new productos_datos(name,url_imagen));
                }
                list_adapter = new list_adapter(arrayList, R.layout.cards_products);
                recyclerView.setAdapter(list_adapter);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(), error.toString(), Toast.LENGTH_SHORT).show();
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest);


        return view;
    }




    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        list_adapter.filtro(newText);
        return false;
    }
}