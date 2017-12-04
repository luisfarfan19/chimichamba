package com.example.luisf.chimichamba.Views.Busco_Trabajador;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.luisf.chimichamba.Chat;
import com.example.luisf.chimichamba.Datos.Match.Match;
import com.example.luisf.chimichamba.Datos.Match.MatchCRUD;
import com.example.luisf.chimichamba.Datos.Match.MatchContract;
import com.example.luisf.chimichamba.Datos.Trabajador.Trabajador;
import com.example.luisf.chimichamba.Datos.Trabajador.TrabajadorCRUD;
import com.example.luisf.chimichamba.HTTP.SendPost;
import com.example.luisf.chimichamba.HTTP.Sender;
import com.example.luisf.chimichamba.Image.CircleTransform;
import com.example.luisf.chimichamba.R;
import com.example.luisf.chimichamba.Views.Trabajador.Configuracion;
import com.example.luisf.chimichamba.Views.Trabajador.MiPerfil;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class PerfilTrabajador extends AppCompatActivity implements Sender {
    private MatchCRUD matchCRUD;
    private Match match;
    private TrabajadorCRUD trabajadorCRUD;
    private Trabajador trabajador;
    private TextView tvTrabNombre, tvTrabProfesion, tvTrabCategoria, tvTrabAExperiencia, tvTrabSobre_mi;
    private ImageView ivFoto;
    String idFbTrabajador = "";
    String idFbBuscoTrabajador = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil_trabajador);
        Bundle bundle = getIntent().getExtras();
        idFbTrabajador = bundle.getString("trabajadoridfb");
        idFbBuscoTrabajador = bundle.getString("buscotrabajadoridfb");

        trabajadorCRUD = new TrabajadorCRUD(this);
        matchCRUD = new MatchCRUD(this);

        SendPost send = new SendPost(this);
        send.execute("/Tget","{\"ID\":\""+idFbTrabajador+"\"}");



        ivFoto = (ImageView) findViewById(R.id.ivTrabImage);
        tvTrabNombre = (TextView) findViewById(R.id.tvTrabNombre);
        tvTrabProfesion = (TextView) findViewById(R.id.tvTrabTrabajo);
        tvTrabCategoria = (TextView) findViewById(R.id.tvTrabCategoria);
        tvTrabAExperiencia = (TextView) findViewById(R.id.tvTrabAExperiencia);
        tvTrabSobre_mi = (TextView) findViewById(R.id.tvTrabSobreMi);


    }
SendPost send2 = new SendPost(this);
    public void aceptoTrabajador(View view) {
        switch (view.getId()) {
            case R.id.ibAceptoTrabajador:

                send2.execute("/Mhay","{\"ID1\":\""+idFbBuscoTrabajador+"\",\"ID2\":\"" + idFbTrabajador + "\"}");


        }
    }

    public void noAceptoTrabajador(View view) {
        switch (view.getId()) {
            case R.id.ibNoAcpetoTrabajador:
                Intent intent = new Intent(PerfilTrabajador.this, BuscoTrabajadorV.class);
                startActivity(intent);
                break;
        }
    }
    public  void answerFromServer(String s) {

        Log.i("Server",s);
        try{
            JSONObject reader = new JSONObject(s);
            Boolean ans = reader.getBoolean("valid");
            if(ans){

                int tipo = Integer.parseInt(reader.getString("tipo"));

                if(tipo == 1) { //confirmaciones crear editar borrar
                    Toast toast1 =
                            Toast.makeText(getApplicationContext(),
                                    "EL match ha sido realizado.", Toast.LENGTH_LONG);
                    Intent intent = new Intent(PerfilTrabajador.this, Chat.class);
                    startActivity(intent);



                }

                if(tipo == 2) { //json get
                    JSONObject res = reader.getJSONObject("DATA");
                    Log.e("JsonDATA",res.toString());
                    if(res!=null){
                        try{

                            trabajador = new Trabajador(res);
                            Picasso.with(this).load(trabajador.getUrl_foto()).transform(new CircleTransform()).into(ivFoto);
                            tvTrabNombre.setText(trabajador.getNombre());
                            tvTrabProfesion.setText(trabajador.getProfesion());
                            tvTrabCategoria.setText(trabajador.getCategoria());
                            tvTrabAExperiencia.setText(String.valueOf(trabajador.getExperiencia()));
                            tvTrabSobre_mi.setText(trabajador.getSobre_mi());

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

                if(tipo == 4) { //hay
                    String res = reader.getString("DATA");
                    Log.e("JsonDATA",res);
                    String ans7 =  res;
                    if(!ans7.equals("")) {
                        if (!ans7.equals("NO")) {//SI existe el trabajador

                            Log.d("Ya existe el match","");
                            Intent intent = new Intent(PerfilTrabajador.this, Chat.class);
                            startActivity(intent);

                        }else{//no existe
                            DateFormat df = new SimpleDateFormat("EEE, d MMM yyyy, HH:mm");
                            String date = df.format(Calendar.getInstance().getTime());
                            Match aux = new Match(idFbBuscoTrabajador, idFbTrabajador, date);
                            SendPost send = new SendPost(this);
                            send.execute("/Minsert","{\"datos\":"+aux.toJson()+"}");
                        }
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
