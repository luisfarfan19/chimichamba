package com.example.luisf.chimichamba.Datos;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.luisf.chimichamba.Datos.Trabajador.TrabajadorContract;

/**
 * Created by luisf on 19/10/2017.
 */

public class DataBaseHelper extends SQLiteOpenHelper {
    private static final String DB_NOMBRE = "chimichamba.db";
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

    private static final String SQL_DELETE_TRABAJADOR = "DROP TABLE IF EXISTS "
            + TrabajadorContract.Entrada.NOMBRE_TABLA;

    public DataBaseHelper(Context context) {
        super(context, DB_NOMBRE, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_TRABAJADOR_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL(SQL_DELETE_TRABAJADOR);
        onCreate(sqLiteDatabase);
    }
}

