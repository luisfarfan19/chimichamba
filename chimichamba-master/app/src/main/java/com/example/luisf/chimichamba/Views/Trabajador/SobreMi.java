package com.example.luisf.chimichamba.Views.Trabajador;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.luisf.chimichamba.Datos.Trabajador.Trabajador;
import com.example.luisf.chimichamba.Datos.Trabajador.TrabajadorCRUD;
import com.example.luisf.chimichamba.HTTP.SendPost;
import com.example.luisf.chimichamba.HTTP.Sender;
import com.example.luisf.chimichamba.R;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

public class SobreMi extends AppCompatActivity implements Sender {
    private Trabajador trabajador;
    private TrabajadorCRUD trabajadorCRUD;
    String lat, lon;
    EditText etSobreMi;
    SendPost send1;
    private String nombreUsuario, idUsuarioFb, fotoUrlUsuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sobre_mi);
        trabajadorCRUD = new TrabajadorCRUD(this);
        Bundle bundle = getIntent().getExtras();
        nombreUsuario = bundle.getString("NombreUsuario");
        idUsuarioFb = bundle.getString("IDUsuario");
        fotoUrlUsuario = bundle.getString("FotoUrlUsuario");
        lat = bundle.getString("lat");
        lon = bundle.getString("lon");
        send1 = new SendPost(this);

        FloatingActionButton fabNuevoItem = (FloatingActionButton) findViewById(R.id.fabGuardarSobreMi);
        fabNuevoItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 etSobreMi = (EditText) findViewById(R.id.etSobreMi);
                if(!etSobreMi.equals("")) {
                    if (hayInternet()&&checarRed()){
                        send1.execute("/Tget","{\"ID\":\""+idUsuarioFb+"\"}");
                    }else {
                        Toast toast = Toast.makeText(getApplicationContext(),"No se detecto conexion a internet",Toast.LENGTH_LONG);
                        toast.show();
                }

            }else{
                Toast toast = Toast.makeText(getApplicationContext(),"Ingresa algo sobre ti",Toast.LENGTH_LONG);
                toast.show();
            }



            }
        });
    }

    public Boolean hayInternet() {

        try {
            Process p = java.lang.Runtime.getRuntime().exec("ping -c 1 www.google.es");

            int val           = p.waitFor();
            boolean reachable = (val == 0);
            return reachable;

        } catch (Exception e) {
            Log.e("Conexion", "No se detecto conexion a internet : ",e );
        }
        return false;
    }
    private boolean checarRed() {

        ConnectivityManager connectivityManager = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo actNetInfo = connectivityManager.getActiveNetworkInfo();

        return (actNetInfo != null && actNetInfo.isConnected());
    }
    public  void answerFromServer(String s) {

        Log.i("Server",s);
        try{
            JSONObject reader = new JSONObject(s);
            Boolean ans = reader.getBoolean("valid");
            if(ans){

                int tipo = Integer.parseInt(reader.getString("tipo"));

                if(tipo == 1) { //confirmaciones crear editar borrar
                    Toast.makeText(SobreMi.this, "La informacion se guardó correctamente",
                            Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(SobreMi.this, MiPerfil.class);
                    intent.putExtra("lat", lat);
                    intent.putExtra("lon", lon);
                    intent.putExtra("NombreUsuario", nombreUsuario);
                    intent.putExtra("IDUsuario", idUsuarioFb);
                    intent.putExtra("FotoUrlUsuario", fotoUrlUsuario);
                    startActivity(intent);
                }

                if(tipo == 2) { //json get
                    JSONObject res = reader.getJSONObject("DATA");
                    Log.e("JsonDATA",res.toString());
                    if(res!=null){
                        try{
                            Trabajador trabajadorAux = new Trabajador(res);
                            Trabajador trabajador = new Trabajador(idUsuarioFb, trabajadorAux.getNombre(),
                                    trabajadorAux.getEmail(), trabajadorAux.getProfesion(),
                                    trabajadorAux.getCategoria(), trabajadorAux.getExperiencia(),
                                    trabajadorAux.getLatActual(), trabajadorAux.getLonActual(),
                                    trabajadorAux.getRadio_ubicacion(), trabajadorAux.getPuedo_viajar(),
                                    etSobreMi.getText().toString(), trabajadorAux.getUrl_foto());


                            SendPost send = new SendPost(this);
                            send.execute("/Tupdate","{\"datos\":"+trabajador.toJson()+"}");
                        }catch (JSONException e){
                            Log.e("ERROR",e.toString());
                        }

                    }else{
                        Toast toast1 =
                                Toast.makeText(getApplicationContext(),
                                        "0 registros", Toast.LENGTH_LONG);

                        toast1.show();
                    }

                }


            }else{
                String err = reader.getString("error");
                Toast toast1 =
                        Toast.makeText(getApplicationContext(),
                                err, Toast.LENGTH_LONG);

                toast1.show();

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }
}
