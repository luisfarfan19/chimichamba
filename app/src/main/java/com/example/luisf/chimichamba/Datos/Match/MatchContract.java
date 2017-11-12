package com.example.luisf.chimichamba.Datos.Match;

import android.provider.BaseColumns;

/**
 * Created by luisf on 06/11/2017.
 */

public final class MatchContract {
    private MatchContract() {
    }

    ;

    public static class Entrada implements BaseColumns {
        public static final String NOMBRE_TABLA = "match";
        public static final String COLUMNA_ID = "id";
        public static final String COLUMNA_IDFBBUSCO = "idfbbusco";
        public static final String COLUMNA_IDFBTRABAJADOR = "idfbtrabajador";
        public static final String COLUMNA_FECHA = "fecha";
    }
}
