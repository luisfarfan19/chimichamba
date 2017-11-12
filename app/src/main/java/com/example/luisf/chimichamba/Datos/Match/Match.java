package com.example.luisf.chimichamba.Datos.Match;

/**
 * Created by luisf on 06/11/2017.
 */

public class Match {
    private int id;
    private String idFbBusco;
    private String idFbTrabajador;
    private String fecha;

    public Match(int id, String idFbBusco, String idFbTrabajador, String fecha) {
        this.id = id;
        this.idFbBusco = idFbBusco;
        this.idFbTrabajador = idFbTrabajador;
        this.fecha = fecha;
    }

    public Match(String idFbBusco, String idFbTrabajador, String fecha) {
        this.idFbBusco = idFbBusco;
        this.idFbTrabajador = idFbTrabajador;
        this.fecha = fecha;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIdFbBusco() {
        return idFbBusco;
    }

    public void setIdFbBusco(String idFbBusco) {
        this.idFbBusco = idFbBusco;
    }

    public String getIdFbTrabajador() {
        return idFbTrabajador;
    }

    public void setIdFbTrabajador(String idFbTrabajador) {
        this.idFbTrabajador = idFbTrabajador;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }
}
