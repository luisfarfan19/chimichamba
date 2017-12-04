package com.example.luisf.chimichamba.Datos.BuscoTrabajador;

import android.provider.BaseColumns;

/**
 * Created by luisf on 06/11/2017.
 */

public final class BuscoTrabajadorContract {
    private BuscoTrabajadorContract(){};
    public static class Entrada implements BaseColumns {
        public static final String NOMBRE_TABLA = "buscotrabajador";
        public static final String COLUMNA_ID = "id";
        public static final String COLUMNA_IDFB = "idfb";
        public static final String COLUMNA_NOMBRE = "nombre";
        public static final String COLUMNA_DISTANCIA = "distancia";
        public static final String COLUMNA_CATEGORIA = "categoria";
        public static final String COLUMNA_URL_FOTO = "urlFoto";
    }
}
