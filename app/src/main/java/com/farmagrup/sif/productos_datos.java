package com.farmagrup.sif;

import android.security.identity.IdentityCredentialStore;
import android.widget.ImageView;

public class productos_datos {

    String url;
    String nombre;


    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }



    public productos_datos(String nombre, String url) {
        this.nombre = nombre;
        this.url = url;

    }


}
