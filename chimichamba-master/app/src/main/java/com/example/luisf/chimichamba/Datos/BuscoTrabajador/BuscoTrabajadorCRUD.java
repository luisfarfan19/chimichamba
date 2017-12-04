package com.example.luisf.chimichamba.Datos.BuscoTrabajador;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

import com.example.luisf.chimichamba.Datos.DataBaseHelper;
import com.example.luisf.chimichamba.HTTP.SendPost;
import com.example.luisf.chimichamba.HTTP.Sender;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by luisf on 06/11/2017.
 */

public class BuscoTrabajadorCRUD  {
    private DataBaseHelper helper;
    private Context context;
    private boolean local = true;
    public BuscoTrabajadorCRUD(Context context) {
        helper = new DataBaseHelper(context);
        this.context = context;
    }

    public void newBuscoTrabajador(BuscoTrabajador buscoTrabajador) {

            SQLiteDatabase db = helper.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(BuscoTrabajadorContract.Entrada.COLUMNA_IDFB, buscoTrabajador.getIdFb());
            values.put(BuscoTrabajadorContract.Entrada.COLUMNA_NOMBRE, buscoTrabajador.getNombre());
            values.put(BuscoTrabajadorContract.Entrada.COLUMNA_DISTANCIA, buscoTrabajador.getDistancia());
            values.put(BuscoTrabajadorContract.Entrada.COLUMNA_CATEGORIA, buscoTrabajador.getCategoria());
            values.put(BuscoTrabajadorContract.Entrada.COLUMNA_URL_FOTO, buscoTrabajador.getUrlFoto());

            long newRowId = db.insert(BuscoTrabajadorContract.Entrada.NOMBRE_TABLA, null, values);

            db.close();

    }

    public boolean hayBuscoTrabajador(String idFb) {

        boolean hayTrabajador = false;


        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " + BuscoTrabajadorContract.Entrada.NOMBRE_TABLA + " WHERE idfb = " + idFb, null);
        while (c.moveToNext()) {
            hayTrabajador = true;
        }
        db.close();


        return hayTrabajador;
    }

    public BuscoTrabajador getBuscoTrabajador(String idFb) {
        BuscoTrabajador buscoTrabajador = null;

    SQLiteDatabase db = helper.getReadableDatabase();
    String[] columnas = {
            BuscoTrabajadorContract.Entrada.COLUMNA_ID,
            BuscoTrabajadorContract.Entrada.COLUMNA_IDFB,
            BuscoTrabajadorContract.Entrada.COLUMNA_NOMBRE,
            BuscoTrabajadorContract.Entrada.COLUMNA_DISTANCIA,
            BuscoTrabajadorContract.Entrada.COLUMNA_CATEGORIA,
            BuscoTrabajadorContract.Entrada.COLUMNA_URL_FOTO,
    };

    Cursor c = db.query(BuscoTrabajadorContract.Entrada.NOMBRE_TABLA,
            columnas,
            "idfb = ?",
            new String[]{String.valueOf(idFb)},
            null,
            null,
            null);
    while (c.moveToNext()) {
        buscoTrabajador = new BuscoTrabajador(
                c.getInt(c.getColumnIndexOrThrow(BuscoTrabajadorContract.Entrada.COLUMNA_ID)),
                c.getString(c.getColumnIndexOrThrow(BuscoTrabajadorContract.Entrada.COLUMNA_IDFB)),
                c.getString(c.getColumnIndexOrThrow(BuscoTrabajadorContract.Entrada.COLUMNA_NOMBRE)),
                c.getInt(c.getColumnIndexOrThrow(BuscoTrabajadorContract.Entrada.COLUMNA_DISTANCIA)),
                c.getString(c.getColumnIndexOrThrow(BuscoTrabajadorContract.Entrada.COLUMNA_CATEGORIA)),
                c.getString(c.getColumnIndexOrThrow(BuscoTrabajadorContract.Entrada.COLUMNA_URL_FOTO))
        );
    }
    db.close();


        return buscoTrabajador;
    }




}
