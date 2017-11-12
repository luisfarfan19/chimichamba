package com.example.luisf.chimichamba.Datos.BuscoTrabajador;

/**
 * Created by luisf on 06/11/2017.
 */

public class BuscoTrabajador {
    private int id;
    private String idFb;
    private String nombre;
    private int distancia;
    private String categoria;
    private String urlFoto;

    public BuscoTrabajador(int id, String idFb, String nombre, int distancia, String categoria, String urlFoto) {
        this.id = id;
        this.idFb = idFb;
        this.nombre = nombre;
        this.distancia = distancia;
        this.categoria = categoria;
        this.urlFoto = urlFoto;
    }

    public BuscoTrabajador(String idFb, String nombre, int distancia, String categoria, String urlFoto) {
        this.idFb = idFb;
        this.nombre = nombre;
        this.distancia = distancia;
        this.categoria = categoria;
        this.urlFoto = urlFoto;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIdFb() {
        return idFb;
    }

    public void setIdFb(String idFb) {
        this.idFb = idFb;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getDistancia() {
        return distancia;
    }

    public void setDistancia(int distancia) {
        this.distancia = distancia;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getUrlFoto() {
        return urlFoto;
    }

    public void setUrlFoto(String urlFoto) {
        this.urlFoto = urlFoto;
    }
}
