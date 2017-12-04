package com.example.luisf.chimichamba.Datos.Trabajador;

import android.provider.BaseColumns;

/**
 * Created by luisf on 19/10/2017.
 */

public final class TrabajadorContract {
    private TrabajadorContract(){};

    public static class Entrada implements BaseColumns{
        public static final String NOMBRE_TABLA = "trabajador";
        public static final String COLUMNA_ID = "id";
        public static final String COLUMNA_IDFB = "idfb";
        public static final String COLUMNA_NOMBRE = "nombre";
        public static final String COLUMNA_EMAIL = "email";
        public static final String COLUMNA_PROFESION = "profesion";
        public static final String COLUMNA_CATEGORIA = "categoria";
        public static final String COLUMNA_EXPERIENCIA = "experiencia";
        public static final String COLUMNA_LAT_ACTUAL = "latAct";
        public static final String COLUMNA_LON_ACTUAL = "lonAct";
        public static final String COLUMNA_RADIO_UBICACION = "radioUbi";
        public static final String COLUMNA_PUEDE_VIAJAR = "puedeViajar";
        public static final String COLUMNA_SOBRE_MI = "sobreMi";
        public static final String COLUMNA_URL_FOTO = "urlFoto";
    }
}
