package com.example.luisf.chimichamba.Datos.Trabajador;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.luisf.chimichamba.Datos.BuscoTrabajador.BuscoTrabajador;
import com.example.luisf.chimichamba.Datos.DataBaseHelper;
import com.example.luisf.chimichamba.Datos.Match.Match;
import com.example.luisf.chimichamba.HTTP.SendPost;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by luisf on 19/10/2017.
 */

public class TrabajadorCRUD {
    private DataBaseHelper helper;

    private boolean local = false;
    public TrabajadorCRUD(Context context) {
        helper = new DataBaseHelper(context);
    }


    public void newTrabajador(Trabajador trabajador) {

            SQLiteDatabase db = helper.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(TrabajadorContract.Entrada.COLUMNA_IDFB, trabajador.getIdFb());
            values.put(TrabajadorContract.Entrada.COLUMNA_NOMBRE, trabajador.getNombre());
            values.put(TrabajadorContract.Entrada.COLUMNA_EMAIL, trabajador.getEmail());
            values.put(TrabajadorContract.Entrada.COLUMNA_PROFESION, trabajador.getProfesion());
            values.put(TrabajadorContract.Entrada.COLUMNA_CATEGORIA, trabajador.getCategoria());
            values.put(TrabajadorContract.Entrada.COLUMNA_EXPERIENCIA, trabajador.getExperiencia());
            values.put(TrabajadorContract.Entrada.COLUMNA_LAT_ACTUAL, trabajador.getLatActual());
            values.put(TrabajadorContract.Entrada.COLUMNA_LON_ACTUAL, trabajador.getLonActual());
            values.put(TrabajadorContract.Entrada.COLUMNA_RADIO_UBICACION, trabajador.getRadio_ubicacion());
            values.put(TrabajadorContract.Entrada.COLUMNA_PUEDE_VIAJAR, trabajador.getPuedo_viajar());
            values.put(TrabajadorContract.Entrada.COLUMNA_SOBRE_MI, trabajador.getSobre_mi());
            values.put(TrabajadorContract.Entrada.COLUMNA_URL_FOTO, trabajador.getUrl_foto());

            long newRowId = db.insert(TrabajadorContract.Entrada.NOMBRE_TABLA, null, values);

            db.close();
        }



    public boolean hayTrabajador(String idFb) {
        boolean hayTrabajador = false;

            SQLiteDatabase db = helper.getReadableDatabase();
            Cursor c = db.rawQuery("SELECT * FROM " + TrabajadorContract.Entrada.NOMBRE_TABLA + " WHERE idfb = " + idFb, null);
            while (c.moveToNext()) {
                hayTrabajador = true;
            }
            db.close();

        return hayTrabajador;
    }

    public Trabajador getTrabajador(String idFb) {
        Trabajador trabajador = null;

    SQLiteDatabase db = helper.getReadableDatabase();
    String[] columnas = {
            TrabajadorContract.Entrada.COLUMNA_ID,
            TrabajadorContract.Entrada.COLUMNA_IDFB,
            TrabajadorContract.Entrada.COLUMNA_NOMBRE,
            TrabajadorContract.Entrada.COLUMNA_EMAIL,
            TrabajadorContract.Entrada.COLUMNA_PROFESION,
            TrabajadorContract.Entrada.COLUMNA_CATEGORIA,
            TrabajadorContract.Entrada.COLUMNA_EXPERIENCIA,
            TrabajadorContract.Entrada.COLUMNA_LAT_ACTUAL,
            TrabajadorContract.Entrada.COLUMNA_LON_ACTUAL,
            TrabajadorContract.Entrada.COLUMNA_RADIO_UBICACION,
            TrabajadorContract.Entrada.COLUMNA_PUEDE_VIAJAR,
            TrabajadorContract.Entrada.COLUMNA_SOBRE_MI,
            TrabajadorContract.Entrada.COLUMNA_URL_FOTO,
    };

    Cursor c = db.query(TrabajadorContract.Entrada.NOMBRE_TABLA,
            columnas,
            "idFb = ?",
            new String[]{String.valueOf(idFb)},
            null,
            null,
            null);
    while (c.moveToNext()) {
        trabajador = new Trabajador(
                c.getInt(c.getColumnIndexOrThrow(TrabajadorContract.Entrada.COLUMNA_ID)),
                c.getString(c.getColumnIndexOrThrow(TrabajadorContract.Entrada.COLUMNA_IDFB)),
                c.getString(c.getColumnIndexOrThrow(TrabajadorContract.Entrada.COLUMNA_NOMBRE)),
                c.getString(c.getColumnIndexOrThrow(TrabajadorContract.Entrada.COLUMNA_EMAIL)),
                c.getString(c.getColumnIndexOrThrow(TrabajadorContract.Entrada.COLUMNA_PROFESION)),
                c.getString(c.getColumnIndexOrThrow(TrabajadorContract.Entrada.COLUMNA_CATEGORIA)),
                c.getInt(c.getColumnIndexOrThrow(TrabajadorContract.Entrada.COLUMNA_EXPERIENCIA)),
                c.getString(c.getColumnIndexOrThrow(TrabajadorContract.Entrada.COLUMNA_LAT_ACTUAL)),
                c.getString(c.getColumnIndexOrThrow(TrabajadorContract.Entrada.COLUMNA_LON_ACTUAL)),
                c.getInt(c.getColumnIndexOrThrow(TrabajadorContract.Entrada.COLUMNA_RADIO_UBICACION)),
                c.getInt(c.getColumnIndexOrThrow(TrabajadorContract.Entrada.COLUMNA_PUEDE_VIAJAR)),
                c.getString(c.getColumnIndexOrThrow(TrabajadorContract.Entrada.COLUMNA_SOBRE_MI)),
                c.getString(c.getColumnIndexOrThrow(TrabajadorContract.Entrada.COLUMNA_URL_FOTO))
        );
    }
    db.close();

        return trabajador;
    }

    public void updateTrabajador(Trabajador trabajador) {

    SQLiteDatabase db = helper.getReadableDatabase();
    ContentValues values = new ContentValues();
    values.put(TrabajadorContract.Entrada.COLUMNA_IDFB, trabajador.getIdFb());
    values.put(TrabajadorContract.Entrada.COLUMNA_NOMBRE, trabajador.getNombre());
    values.put(TrabajadorContract.Entrada.COLUMNA_EMAIL, trabajador.getEmail());
    values.put(TrabajadorContract.Entrada.COLUMNA_PROFESION, trabajador.getProfesion());
    values.put(TrabajadorContract.Entrada.COLUMNA_CATEGORIA, trabajador.getCategoria());
    values.put(TrabajadorContract.Entrada.COLUMNA_EXPERIENCIA, trabajador.getExperiencia());
    values.put(TrabajadorContract.Entrada.COLUMNA_LAT_ACTUAL, trabajador.getLatActual());
    values.put(TrabajadorContract.Entrada.COLUMNA_LON_ACTUAL, trabajador.getLonActual());
    values.put(TrabajadorContract.Entrada.COLUMNA_RADIO_UBICACION, trabajador.getRadio_ubicacion());
    values.put(TrabajadorContract.Entrada.COLUMNA_PUEDE_VIAJAR, trabajador.getPuedo_viajar());
    values.put(TrabajadorContract.Entrada.COLUMNA_SOBRE_MI, trabajador.getSobre_mi());
    values.put(TrabajadorContract.Entrada.COLUMNA_URL_FOTO, trabajador.getUrl_foto());
    db.update(
            TrabajadorContract.Entrada.NOMBRE_TABLA,
            values,
            "idfb = ?",
            new String[]{String.valueOf(trabajador.getIdFb())}
    );
    db.close();

    }

    public void deleteTrabajador(Trabajador trabajador) {

        SQLiteDatabase db = helper.getWritableDatabase();
        db.delete(
                TrabajadorContract.Entrada.NOMBRE_TABLA,
                "id = ?",
                new String[]{String.valueOf(trabajador.getId())}
        );
        db.close();

    }

    public ArrayList<Trabajador> getTrabajadores() {
        ArrayList<Trabajador> trabajadores = new ArrayList<Trabajador>();


            SQLiteDatabase db = helper.getReadableDatabase();

            String[] columnas = {
                    TrabajadorContract.Entrada.COLUMNA_ID,
                    TrabajadorContract.Entrada.COLUMNA_IDFB,
                    TrabajadorContract.Entrada.COLUMNA_NOMBRE,
                    TrabajadorContract.Entrada.COLUMNA_EMAIL,
                    TrabajadorContract.Entrada.COLUMNA_PROFESION,
                    TrabajadorContract.Entrada.COLUMNA_CATEGORIA,
                    TrabajadorContract.Entrada.COLUMNA_EXPERIENCIA,
                    TrabajadorContract.Entrada.COLUMNA_LAT_ACTUAL,
                    TrabajadorContract.Entrada.COLUMNA_LON_ACTUAL,
                    TrabajadorContract.Entrada.COLUMNA_RADIO_UBICACION,
                    TrabajadorContract.Entrada.COLUMNA_PUEDE_VIAJAR,
                    TrabajadorContract.Entrada.COLUMNA_SOBRE_MI,
                    TrabajadorContract.Entrada.COLUMNA_URL_FOTO,
            };

            Cursor c = db.query(TrabajadorContract.Entrada.NOMBRE_TABLA,
                    columnas,
                    null,
                    null,
                    null,
                    null,
                    null);
            while (c.moveToNext()) {
                trabajadores.add(new Trabajador(
                        c.getInt(c.getColumnIndexOrThrow(TrabajadorContract.Entrada.COLUMNA_ID)),
                        c.getString(c.getColumnIndexOrThrow(TrabajadorContract.Entrada.COLUMNA_IDFB)),
                        c.getString(c.getColumnIndexOrThrow(TrabajadorContract.Entrada.COLUMNA_NOMBRE)),
                        c.getString(c.getColumnIndexOrThrow(TrabajadorContract.Entrada.COLUMNA_EMAIL)),
                        c.getString(c.getColumnIndexOrThrow(TrabajadorContract.Entrada.COLUMNA_PROFESION)),
                        c.getString(c.getColumnIndexOrThrow(TrabajadorContract.Entrada.COLUMNA_CATEGORIA)),
                        c.getInt(c.getColumnIndexOrThrow(TrabajadorContract.Entrada.COLUMNA_EXPERIENCIA)),
                        c.getString(c.getColumnIndexOrThrow(TrabajadorContract.Entrada.COLUMNA_LAT_ACTUAL)),
                        c.getString(c.getColumnIndexOrThrow(TrabajadorContract.Entrada.COLUMNA_LON_ACTUAL)),
                        c.getInt(c.getColumnIndexOrThrow(TrabajadorContract.Entrada.COLUMNA_RADIO_UBICACION)),
                        c.getInt(c.getColumnIndexOrThrow(TrabajadorContract.Entrada.COLUMNA_PUEDE_VIAJAR)),
                        c.getString(c.getColumnIndexOrThrow(TrabajadorContract.Entrada.COLUMNA_SOBRE_MI)),
                        c.getString(c.getColumnIndexOrThrow(TrabajadorContract.Entrada.COLUMNA_URL_FOTO))
                ));
            }
            db.close();

        return trabajadores;
    }

}
