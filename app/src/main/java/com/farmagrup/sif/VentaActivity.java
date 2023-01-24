package com.farmagrup.sif;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.util.ArrayList;

public class VentaActivity extends AppCompatActivity {

    TextView suma;
    ListView lista;
    ArrayList<String> productos = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_venta);
        lista = findViewById(R.id.lista_productos);
        suma = findViewById(R.id.txt_totalproductos);

    }

    public void add_product(View view) {
        IntentIntegrator integrator = new IntentIntegrator(VentaActivity.this);
        integrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES);
        integrator.setPrompt("Checador precios");
        integrator.setCameraId(0);
        integrator.setBeepEnabled(true);
        integrator.setBarcodeImageEnabled(true);
        integrator.initiateScan();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if(result!= null){
            if(result.getContents() == null){
                Toast.makeText(this, "Lectura cancelada", Toast.LENGTH_SHORT).show();
            }else{
                productos.add(result.getContents());
                ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.listblack, R.id.list_content, productos);
                lista.setAdapter(adapter);
                suma.setText(String.valueOf(productos.size()));

            }
        }else{
            super.onActivityResult(requestCode, resultCode, data);
        }

    }

    public void limpiar_venta(View view) {
        productos.clear();ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.listblack, R.id.list_content, productos);
        lista.setAdapter(adapter);
        suma.setText(" : " + String.valueOf(productos.size()));

    }
}