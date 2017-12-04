package com.example.luisf.chimichamba.Datos.Match;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.luisf.chimichamba.Datos.BuscoTrabajador.BuscoTrabajador;
import com.example.luisf.chimichamba.Datos.DataBaseHelper;
import com.example.luisf.chimichamba.HTTP.SendPost;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by luisf on 06/11/2017.
 */

public class MatchCRUD {
    private DataBaseHelper helper;
    private boolean local = true;
    public MatchCRUD(Context context) {
        helper = new DataBaseHelper(context);
    }



    public void newMatch(Match match) {

            SQLiteDatabase db = helper.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(MatchContract.Entrada.COLUMNA_IDFBBUSCO, match.getIdFbBusco());
            values.put(MatchContract.Entrada.COLUMNA_IDFBTRABAJADOR, match.getIdFbTrabajador());
            values.put(MatchContract.Entrada.COLUMNA_FECHA, match.getFecha());

            long newRowId = db.insert(MatchContract.Entrada.NOMBRE_TABLA, null, values);

            db.close();

    }

    public boolean hayMatch(String idFbBusco, String idFbTrabajador) {
        boolean hayMatch = false;

        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " + MatchContract.Entrada.NOMBRE_TABLA +
                " WHERE idfbbusco = " + idFbBusco + " AND idfbtrabajador = " + idFbTrabajador, null);
        while (c.moveToNext()) {
            hayMatch = true;
        }
        db.close();

        return hayMatch;
    }

    public ArrayList<Match> getMatchesBusco(String idFbBuscoTrabajador) {
        ArrayList<Match> matchesList = new ArrayList<Match>();

        SQLiteDatabase db = helper.getReadableDatabase();

        String[] columnas = {
                MatchContract.Entrada.COLUMNA_ID,
                MatchContract.Entrada.COLUMNA_IDFBBUSCO,
                MatchContract.Entrada.COLUMNA_IDFBTRABAJADOR,
                MatchContract.Entrada.COLUMNA_FECHA,
        };

        Cursor c = db.query(MatchContract.Entrada.NOMBRE_TABLA,
                columnas,
                "idfbbusco = " + idFbBuscoTrabajador,
                null,
                null,
                null,
                null);
        //String query = "SELECT * FROM match WHERE idfbbusco = " + idFbBuscoTrabajador;
        //Cursor c = db.rawQuery(query, null);
        while (c.moveToNext()) {
            matchesList.add(new Match(
                    c.getInt(c.getColumnIndexOrThrow(MatchContract.Entrada.COLUMNA_ID)),
                    c.getString(c.getColumnIndexOrThrow(MatchContract.Entrada.COLUMNA_IDFBBUSCO)),
                    c.getString(c.getColumnIndexOrThrow(MatchContract.Entrada.COLUMNA_IDFBTRABAJADOR)),
                    c.getString(c.getColumnIndexOrThrow(MatchContract.Entrada.COLUMNA_FECHA))
            ));
        }
        db.close();

        return matchesList;
    }

    public ArrayList<Match> getMatchesTrabajador(String idFbTrabajador) {

        ArrayList<Match> matchesList = new ArrayList<Match>();

        SQLiteDatabase db = helper.getReadableDatabase();

        String[] columnas = {
                MatchContract.Entrada.COLUMNA_ID,
                MatchContract.Entrada.COLUMNA_IDFBBUSCO,
                MatchContract.Entrada.COLUMNA_IDFBTRABAJADOR,
                MatchContract.Entrada.COLUMNA_FECHA,
        };

        Cursor c = db.query(MatchContract.Entrada.NOMBRE_TABLA,
                columnas,
                "idfbtrabajador = " + idFbTrabajador,
                null,
                null,
                null,
                null);
        while (c.moveToNext()) {
            matchesList.add(new Match(
                    c.getInt(c.getColumnIndexOrThrow(MatchContract.Entrada.COLUMNA_ID)),
                    c.getString(c.getColumnIndexOrThrow(MatchContract.Entrada.COLUMNA_IDFBBUSCO)),
                    c.getString(c.getColumnIndexOrThrow(MatchContract.Entrada.COLUMNA_IDFBTRABAJADOR)),
                    c.getString(c.getColumnIndexOrThrow(MatchContract.Entrada.COLUMNA_FECHA))
            ));
        }
        db.close();

        return matchesList;
    }

}
