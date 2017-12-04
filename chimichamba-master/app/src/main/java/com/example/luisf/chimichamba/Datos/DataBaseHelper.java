package com.example.luisf.chimichamba.Datos;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.luisf.chimichamba.Datos.BuscoTrabajador.BuscoTrabajadorContract;
import com.example.luisf.chimichamba.Datos.Match.MatchContract;
import com.example.luisf.chimichamba.Datos.Trabajador.TrabajadorContract;

/**
 * Created by luisf on 19/10/2017.
 */

public class DataBaseHelper extends SQLiteOpenHelper {
    private static final String DB_NOMBRE = "chimi.db";
    private static final int DB_VERSION = 1;

    public static final String CREATE_TRABAJADOR_TABLE = "CREATE TABLE "
            + TrabajadorContract.Entrada.NOMBRE_TABLA + "("
            + TrabajadorContract.Entrada.COLUMNA_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + TrabajadorContract.Entrada.COLUMNA_IDFB + " TEXT, "
            + TrabajadorContract.Entrada.COLUMNA_NOMBRE + " TEXT, "
            + TrabajadorContract.Entrada.COLUMNA_EMAIL + " TEXT, "
            + TrabajadorContract.Entrada.COLUMNA_PROFESION + " TEXT, "
            + TrabajadorContract.Entrada.COLUMNA_CATEGORIA + " TEXT, "
            + TrabajadorContract.Entrada.COLUMNA_EXPERIENCIA + " INTEGER, "
            + TrabajadorContract.Entrada.COLUMNA_LAT_ACTUAL + " TEXT, "
            + TrabajadorContract.Entrada.COLUMNA_LON_ACTUAL + " TEXT, "
            + TrabajadorContract.Entrada.COLUMNA_RADIO_UBICACION + " INTEGER, "
            + TrabajadorContract.Entrada.COLUMNA_PUEDE_VIAJAR + " INTEGER, "
            + TrabajadorContract.Entrada.COLUMNA_SOBRE_MI + " TEXT, "
            + TrabajadorContract.Entrada.COLUMNA_URL_FOTO + " TEXT " + ");";

    public static final String CREATE_BUSCOTRABAJADOR_TABLE = "CREATE TABLE "
            + BuscoTrabajadorContract.Entrada.NOMBRE_TABLA + "("
            + BuscoTrabajadorContract.Entrada.COLUMNA_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + BuscoTrabajadorContract.Entrada.COLUMNA_IDFB + " TEXT, "
            + BuscoTrabajadorContract.Entrada.COLUMNA_NOMBRE + " TEXT, "
            + BuscoTrabajadorContract.Entrada.COLUMNA_DISTANCIA + " INTEGER, "
            + BuscoTrabajadorContract.Entrada.COLUMNA_CATEGORIA + " TEXT, "
            + BuscoTrabajadorContract.Entrada.COLUMNA_URL_FOTO + " TEXT " + ");";

    public static final String CREATE_MATCH_TABLE = "CREATE TABLE "
            + MatchContract.Entrada.NOMBRE_TABLA + "("
            + MatchContract.Entrada.COLUMNA_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + MatchContract.Entrada.COLUMNA_IDFBBUSCO + " TEXT, "
            + MatchContract.Entrada.COLUMNA_IDFBTRABAJADOR + " TEXT, "
            + MatchContract.Entrada.COLUMNA_FECHA + " TEXT " + ");";

    private static final String SQL_DELETE_MATCH = "DROP TABLE IF EXISTS "
            + MatchContract.Entrada.NOMBRE_TABLA;

    private static final String SQL_DELETE_BUSCOTRABAJADOR = "DROP TABLE IF EXISTS "
            + BuscoTrabajadorContract.Entrada.NOMBRE_TABLA;

    private static final String SQL_DELETE_TRABAJADOR = "DROP TABLE IF EXISTS "
            + TrabajadorContract.Entrada.NOMBRE_TABLA;

    public DataBaseHelper(Context context) {
        super(context, DB_NOMBRE, null, DB_VERSION);
        Log.i("Tabla1",CREATE_TRABAJADOR_TABLE);
        Log.i("Tabla2",CREATE_BUSCOTRABAJADOR_TABLE);
        Log.i("Tabla3",CREATE_MATCH_TABLE);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_TRABAJADOR_TABLE);
        sqLiteDatabase.execSQL(CREATE_BUSCOTRABAJADOR_TABLE);
        sqLiteDatabase.execSQL(CREATE_MATCH_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL(SQL_DELETE_TRABAJADOR);
        sqLiteDatabase.execSQL(SQL_DELETE_BUSCOTRABAJADOR);
        sqLiteDatabase.execSQL(SQL_DELETE_MATCH);
        onCreate(sqLiteDatabase);
    }
}

