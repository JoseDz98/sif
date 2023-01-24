package com.farmagrup.sif;

import android.animation.LayoutTransition;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ImageReader;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.google.android.material.card.MaterialCardView;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

public class list_adapter extends RecyclerView.Adapter<list_adapter.ViewHolder> {
    private ArrayList<productos_datos> mDatos;
    private ArrayList<productos_datos> original_list;
    private int resource;


    public list_adapter(ArrayList<productos_datos> mDatos,int resource ){
        this.mDatos = mDatos;
        this.resource = resource;
        this.original_list = new ArrayList<>();
        original_list.addAll(mDatos);
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(resource, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull list_adapter.ViewHolder holder, int position) {
        final productos_datos productos_datos = mDatos.get(position);
        holder.nombre.setText(productos_datos.getNombre());
        Handler uiHandler = new Handler(Looper.getMainLooper());
        uiHandler.post(new Runnable(){
            @Override
            public void run() {
                Picasso.get()
                        .load(productos_datos.getUrl())
                        .into(holder.producto);
            }
        });
        holder.carta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(view.getContext(), productos_datos.getUrl(), Toast.LENGTH_SHORT).show();
            }
        });


    }



    @Override
    public int getItemCount() {
        return mDatos.size();
    }


    public void filtro(String str_filtro){
        if(str_filtro.length() == 0){
            mDatos.clear();
            mDatos.addAll(original_list);
        }else{
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
                mDatos.clear();
                mDatos.addAll(original_list);
                List<productos_datos> collect = mDatos.stream()
                        .filter(i -> i.getNombre().toLowerCase().contains(str_filtro))
                        .collect(Collectors.toList());
                mDatos.clear();
                mDatos.addAll(collect);
            }else {
                mDatos.clear();
                mDatos.addAll(original_list);
                for(productos_datos i: original_list){
                    if(i.getNombre().toLowerCase().contains(str_filtro)){
                        mDatos.add(i);
                    }
                }
            }
        }
        notifyDataSetChanged();


    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private ImageView producto;
        private TextView nombre;
        public  View view;
        public RelativeLayout layout;
        MaterialCardView carta;

        ViewHolder(View view){
            super(view);
            this.view = view;
            this.layout = view.findViewById(R.id.card_layout);
            layout.getLayoutTransition().enableTransitionType(LayoutTransition.CHANGING);
            this.producto = view.findViewById(R.id.img_producto);
            this.carta = view.findViewById(R.id.cartas);
            this.nombre = view.findViewById(R.id.product_name);

        }
}





}


