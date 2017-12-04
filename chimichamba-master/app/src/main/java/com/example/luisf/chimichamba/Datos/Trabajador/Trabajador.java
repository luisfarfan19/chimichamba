package com.example.luisf.chimichamba.Datos.Trabajador;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by luisf on 19/10/2017.
 */

public class Trabajador {
    private int id;
    private String idFb;
    private String nombre;
    private String email;
    private String profesion;
    private String categoria;
    private int experiencia;
    private String latActual;
    private String lonActual;
    private int radio_ubicacion;
    private int puedo_viajar;
    private String sobre_mi;
    private String url_foto;

    public Trabajador(int id, String idFb, String nombre, String email,
                      String profesion, String categoria, int experiencia,
                      String latActual, String lonActual, int radio_ubicacion,
                      int puedo_viajar, String sobre_mi, String url_foto) {
        this.id = id;
        this.idFb = idFb;
        this.nombre = nombre;
        this.email = email;
        this.profesion = profesion;
        this.categoria = categoria;
        this.experiencia = experiencia;
        this.latActual = latActual;
        this.lonActual = lonActual;
        this.radio_ubicacion = radio_ubicacion;
        this.puedo_viajar = puedo_viajar;
        this.sobre_mi = sobre_mi;
        this.url_foto = url_foto;
    }

    public Trabajador(String idFb, String nombre, String email,
                      String profesion, String categoria, int experiencia,
                      String latActual, String lonActual, int radio_ubicacion,
                      int puedo_viajar, String sobre_mi, String url_foto) {

        this.idFb = idFb;
        this.nombre = nombre;
        this.email = email;
        this.profesion = profesion;
        this.categoria = categoria;
        this.experiencia = experiencia;
        this.latActual = latActual;
        this.lonActual = lonActual;
        this.radio_ubicacion = radio_ubicacion;
        this.puedo_viajar = puedo_viajar;
        this.sobre_mi = sobre_mi;
        this.url_foto = url_foto;
    }

    public Trabajador(String idFb, String nombre, String url_foto, String distancia) {
        this.idFb = idFb;
        this.nombre = nombre;
        this.url_foto = url_foto;
    }

    public  Trabajador(JSONObject trabajador) throws JSONException{
        this.idFb = trabajador.getString("idFb");
        this.nombre =  trabajador.getString("nombre");
        this.email =  trabajador.getString("email");
        this.profesion =  trabajador.getString("profesion");
        this.categoria =  trabajador.getString("categoria");
        this.experiencia =  trabajador.getInt("experiencia");
        this.latActual =  trabajador.getString("lat");
        this.lonActual =  trabajador.getString("lon");
        this.radio_ubicacion =  trabajador.getInt("radio");
        this.puedo_viajar =  trabajador.getInt("puedo");
        this.sobre_mi =  trabajador.getString("sobre");
        this.url_foto =  trabajador.getString("url");
    }
    public String toJson(){
        String str="";

        str="{";
        str+= "\"idFb\":\""+getIdFb()+"\"";
        str+=",";
        str+= "\"nombre\":\""+getNombre()+"\"";
        str+=",";
        str+= "\"email\":\""+getEmail()+"\"";
        str+=",";
        str+= "\"profesion\":\""+getProfesion()+"\"";
        str+=",";
        str+= "\"categoria\":\""+getCategoria()+"\"";
        str+=",";
        str+= "\"experiencia\":\""+getExperiencia()+"\"";
        str+=",";
        str+= "\"lat\":\""+getLatActual()+"\"";
        str+=",";
        str+= "\"lon\":\""+getLonActual()+"\"";
        str+=",";
        str+= "\"radio\":\""+getRadio_ubicacion()+"\"";
        str+=",";
        str+= "\"puedo\":\""+getPuedo_viajar()+"\"";
        str+=",";
        str+= "\"sobre\":\""+getSobre_mi()+"\"";
        str+=",";
        str+= "\"url\":\""+getUrl_foto()+"\"";

        str+="}";

        return str;
    }


    //GETERS AND SETTERS


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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getProfesion() {
        return profesion;
    }

    public void setProfesion(String profesion) {
        this.profesion = profesion;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public int getExperiencia() {
        return experiencia;
    }

    public void setExperiencia(int experiencia) {
        this.experiencia = experiencia;
    }

    public String getLatActual() {
        return latActual;
    }

    public void setLatActual(String latActual) {
        this.latActual = latActual;
    }

    public String getLonActual() {
        return lonActual;
    }

    public void setLonActual(String lonActual) {
        this.lonActual = lonActual;
    }

    public int getRadio_ubicacion() {
        return radio_ubicacion;
    }

    public void setRadio_ubicacion(int radio_ubicacion) {
        this.radio_ubicacion = radio_ubicacion;
    }

    public int getPuedo_viajar() {
        return puedo_viajar;
    }

    public void setPuedo_viajar(int puedo_viajar) {
        this.puedo_viajar = puedo_viajar;
    }

    public String getSobre_mi() {
        return sobre_mi;
    }

    public void setSobre_mi(String sobre_mi) {
        this.sobre_mi = sobre_mi;
    }

    public String getUrl_foto() {
        return url_foto;
    }

    public void setUrl_foto(String url_foto) {
        this.url_foto = url_foto;
    }
}
