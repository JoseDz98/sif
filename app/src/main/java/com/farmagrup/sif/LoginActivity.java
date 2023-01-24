package com.farmagrup.sif;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.biometric.BiometricManager;
import androidx.biometric.BiometricPrompt;
import androidx.core.content.ContextCompat;
import androidx.preference.PreferenceManager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.Executor;

public class LoginActivity extends AppCompatActivity {

    TextInputEditText usuario, contrasena;
    TextInputLayout lay_pass, lay_user;
    MaterialButton login;
    BiometricPrompt biometricPrompt;
    BiometricPrompt.PromptInfo promptInfo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        usuario = findViewById(R.id.txt_user);
        contrasena = findViewById(R.id.txt_pass);
        login =findViewById(R.id.btn_login);
        lay_pass = findViewById(R.id.lay_pass);
        lay_user = findViewById(R.id.lay_user);


        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        String user = sharedPref.getString("Usuario","");
        String pass = sharedPref.getString("Pass","");
        String str_nombre = sharedPref.getString("Nombre","");
        usuario.setText(user);

        if(!user.isEmpty() && !pass.isEmpty()){
            BiometricManager biometricManager = BiometricManager.from(this);
            switch (biometricManager.canAuthenticate()){
                case BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE:
                    Toast.makeText(this, "Autenticacion con huella no disponible", Toast.LENGTH_SHORT).show();
                    break;
                case BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE:
                    Toast.makeText(this, "No disponible", Toast.LENGTH_SHORT).show();
                    break;

            }

            Executor executor = ContextCompat.getMainExecutor(this);
            biometricPrompt = new BiometricPrompt(LoginActivity.this, executor, new BiometricPrompt.AuthenticationCallback() {
                @Override
                public void onAuthenticationError(int errorCode, @NonNull CharSequence errString) {
                    super.onAuthenticationError(errorCode, errString);
                    Toast.makeText(LoginActivity.this, "Ocurrió un error, ingresa tu contraseña", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onAuthenticationSucceeded(@NonNull BiometricPrompt.AuthenticationResult result) {
                    super.onAuthenticationSucceeded(result);
                    LoginHuella();
                }

                @Override
                public void onAuthenticationFailed() {
                    super.onAuthenticationFailed();
                }
            });

            promptInfo = new BiometricPrompt.PromptInfo.Builder().setTitle("Bienvenido "+ str_nombre)
                    .setDescription("Ingresa tu huella para iniciar sesion")
                    .setDeviceCredentialAllowed(true)
                    .build();
            biometricPrompt.authenticate(promptInfo);
        }



    }




    public void login(View view) {
        LoginSQL();
    }



    public void LoginHuella(){
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        String str_user = sharedPref.getString("Usuario","");
        String str_pass = sharedPref.getString("Pass","");
        if(str_user.isEmpty()){
            lay_user.setError("Ingresa tu usuario");
        }else{
            if(str_pass.isEmpty()){
                lay_pass.setError("Ingresa la contraseña");
            }else{
                String url = "http://192.168.1.20/api/conexion.php/";
                StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if(response.trim().isEmpty()){
                            Toast.makeText(LoginActivity.this, "Usuario o contraseña incorrecto", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(LoginActivity.this, "Bienvenido "+response.trim(), Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(LoginActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
                    }
                }){
                    @Nullable
                    @Override
                    protected Map<String, String> getParams() {
                        Map<String,String>  params =  new HashMap<String, String>();
                        params.put("codigo", str_user);
                        params.put("pass", str_pass);
                        return params;
                    }
                };
                RequestQueue requestQueue = Volley.newRequestQueue(LoginActivity.this);
                requestQueue.add(stringRequest);

            }
        }
    }


    public void LoginSQL(){
        String str_user = usuario.getText().toString();
        String str_pass = contrasena.getText().toString();
        if(usuario.getText().toString().isEmpty()){
            lay_user.setError("Ingresa tu usuario");
        }else{
            if(contrasena.getText().toString().isEmpty()){
                lay_pass.setError("Ingresa la contraseña");
            }else{
                String url = "http://192.168.1.20/api/conexion.php/";
                StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if(response.trim().isEmpty()){
                            Toast.makeText(LoginActivity.this, "Usuario o contraseña incorrecto", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(LoginActivity.this, "Bienvenido "+response.trim(), Toast.LENGTH_SHORT).show();
                            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(LoginActivity.this);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString("Usuario", str_user);
                            editor.putString("Pass", str_pass);
                            editor.putString("Nombre", response.trim());
                            editor.apply();
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(LoginActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
                    }
                }){
                    @Nullable
                    @Override
                    protected Map<String, String> getParams() {
                        Map<String,String>  params =  new HashMap<String, String>();
                        params.put("codigo", str_user);
                        params.put("pass", str_pass);
                        return params;
                    }
                };
                RequestQueue requestQueue = Volley.newRequestQueue(LoginActivity.this);
                requestQueue.add(stringRequest);

            }
        }
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}

