package com.example.luisf.chimichamba.Views.Trabajador;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.luisf.chimichamba.Datos.BuscoTrabajador.BuscoTrabajador;
import com.example.luisf.chimichamba.Datos.Trabajador.Trabajador;
import com.example.luisf.chimichamba.Datos.Trabajador.TrabajadorCRUD;
import com.example.luisf.chimichamba.HTTP.SendPost;
import com.example.luisf.chimichamba.HTTP.Sender;
import com.example.luisf.chimichamba.Image.CircleTransform;
import com.example.luisf.chimichamba.R;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

public class MiPerfil extends AppCompatActivity implements Sender  {
    String lat, lon;
    private String nombreUsuario, idUsuarioFb, fotoUrlUsuario;
    private TrabajadorCRUD trabajadorCRUD;
    private Trabajador trabajador;
    private ImageView ivFoto;
    private TextView tvNombre, tvProfesion, tvCategoria, tvAExperiencia, tvSobre_mi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mi_perfil);

        ivFoto = (ImageView) findViewById(R.id.ivFotoUser);
        tvNombre = (TextView) findViewById(R.id.tvNombre);
        tvProfesion = (TextView) findViewById(R.id.tvTrabajo);
        tvCategoria = (TextView) findViewById(R.id.tvExperiancia);
        tvAExperiencia = (TextView) findViewById(R.id.tvAExperiencia);
        tvSobre_mi = (TextView) findViewById(R.id.tvSobreMi);

        //trabajadorCRUD = new TrabajadorCRUD(this);
        Bundle bundle = getIntent().getExtras();
        lat = bundle.getString("lat");
        lon = bundle.getString("lon");
        nombreUsuario = bundle.getString("NombreUsuario");
        idUsuarioFb = bundle.getString("IDUsuario");
        fotoUrlUsuario = bundle.getString("FotoUrlUsuario");

        trabajadorCRUD = new TrabajadorCRUD(this);

        tvNombre.setText(nombreUsuario);
        Picasso.with(this).load(fotoUrlUsuario).transform(new CircleTransform()).into(ivFoto);
      //  Picasso.with(this).load(fotoUrlUsuario).into(ivFoto);
        SendPost send = new SendPost(this);

        //checo si hay trabajadores-> answerfromserver tipo 4
        send.execute("/Thay","{\"ID\":\""+idUsuarioFb+"\"}");

    }

    public void configuracion(View view) {
        switch (view.getId()) {
            case R.id.ibConfiguracion:
                Intent intent1 = new Intent(MiPerfil.this, Configuracion.class);
                intent1.putExtra("lat", lat);
                intent1.putExtra("lon", lon);
                intent1.putExtra("NombreUsuario", nombreUsuario);
                intent1.putExtra("IDUsuario", idUsuarioFb);
                intent1.putExtra("FotoUrlUsuario", fotoUrlUsuario);
                startActivity(intent1);
                break;
        }
    }

    public void goToChat(View view) {
        switch (view.getId()) {
            case R.id.ibMisChats:
                Intent intent1 = new Intent(MiPerfil.this, MisChats.class);
                intent1.putExtra("trabajadoridfb", idUsuarioFb);
                startActivity(intent1);
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
                                    "Exito insertando el registro", Toast.LENGTH_LONG);

                    toast1.show();
                }

                if(tipo == 2) { //json get
                    JSONObject res = reader.getJSONObject("DATA");
                    Log.e("JsonDATA",res.toString());
                    if(res != null){
                        try{
                            trabajador = new Trabajador(res);
                            tvNombre.setText(trabajador.getNombre());
                            tvProfesion.setText(trabajador.getProfesion());
                            tvCategoria.setText(trabajador.getCategoria());
                            tvAExperiencia.setText(String.valueOf(trabajador.getExperiencia()));
                            tvSobre_mi.setText(trabajador.getSobre_mi());

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

                            SendPost send = new SendPost(this);
                            send.execute("/Tget","{\"ID\":\""+idUsuarioFb+"\"}");//conseguir usuario->answerfromserver tipo 2

                        }else{//no existe

                            SendPost send = new SendPost(this);
                            Trabajador trab = new Trabajador(idUsuarioFb, nombreUsuario,
                                    "luisfarfanlara@hotmail.com", "", "", 1, "", "", 1, 1, "", fotoUrlUsuario);
                            send.execute("/Tinsert","{\"datos\":"+trab.toJson()+"}");//crear usuario->answerfromserver tipo 1

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
