package com.farmagrup.sif;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.PreferenceManager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;

import java.util.Locale;

import yuku.ambilwarna.AmbilWarnaDialog;

public class MainActivity extends AppCompatActivity {

    LinearLayout bottom;
    Toolbar toolbar;
    Fragment mainFragment;
    FragmentTransaction transaction;
    TextView usuario;
    int default_color;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = findViewById(R.id.toolbar);
        bottom = findViewById(R.id.bottom_layout);
        usuario = findViewById(R.id.txt_usuario);
        setSupportActionBar(toolbar);
        mainFragment = new MainFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.contenedor, mainFragment).commit();


        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        String select_color = sharedPref.getString("Tema", "");
        String str_nombre = sharedPref.getString("Nombre","");
        Window window = getWindow();
        window.setStatusBarColor(Color.parseColor(select_color));
        toolbar.setBackgroundColor(Color.parseColor(select_color));
        default_color = Color.parseColor(select_color);
        bottom.setBackgroundColor(Color.parseColor(select_color));
        usuario.setText("USUARIO: " +str_nombre.toUpperCase(Locale.ROOT));


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_ajustes:
                Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
                startActivity(intent);
                break;
            case R.id.menu_tema:
                onColorPicker();
                break;
            case R.id.menu_user:
                SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
                sharedPreferences.edit().remove("Usuario").commit();
                sharedPreferences.edit().remove("Pass").commit();
                Intent intent1 = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent1);
                finish();
                break;
            case R.id.menu_foot:
                FootrintsFragment footrintsFragment = new FootrintsFragment();
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.contenedor, footrintsFragment)
                        .addToBackStack(null)
                        .commit();

                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void onColorPicker() {
        AmbilWarnaDialog colorpicker = new AmbilWarnaDialog(this, default_color, new AmbilWarnaDialog.OnAmbilWarnaListener() {
            @Override
            public void onCancel(AmbilWarnaDialog dialog) {

            }

            @Override
            public void onOk(AmbilWarnaDialog dialog, int color) {
                String hex = "#"+ Integer.toHexString(color);
                SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("Tema", hex);
                editor.apply();
                Toast.makeText(MainActivity.this, "Reinicia para mostrar cambios", Toast.LENGTH_SHORT).show();
            }
        });
        colorpicker.show();
    }


}