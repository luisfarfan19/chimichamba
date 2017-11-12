package com.example.luisf.chimichamba.Views.Busco_Trabajador;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.luisf.chimichamba.Chat;
import com.example.luisf.chimichamba.Datos.Match.Match;
import com.example.luisf.chimichamba.Datos.Match.MatchCRUD;
import com.example.luisf.chimichamba.Datos.Match.MatchContract;
import com.example.luisf.chimichamba.Datos.Trabajador.Trabajador;
import com.example.luisf.chimichamba.Datos.Trabajador.TrabajadorCRUD;
import com.example.luisf.chimichamba.R;
import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class PerfilTrabajador extends AppCompatActivity {
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
        Trabajador trabajador = trabajadorCRUD.getTrabajador(idFbTrabajador);

        ivFoto = (ImageView) findViewById(R.id.ivTrabImage);
        tvTrabNombre = (TextView) findViewById(R.id.tvTrabNombre);
        tvTrabProfesion = (TextView) findViewById(R.id.tvTrabTrabajo);
        tvTrabCategoria = (TextView) findViewById(R.id.tvTrabCategoria);
        tvTrabAExperiencia = (TextView) findViewById(R.id.tvTrabAExperiencia);
        tvTrabSobre_mi = (TextView) findViewById(R.id.tvTrabSobreMi);

        Picasso.with(this).load(trabajador.getUrl_foto()).into(ivFoto);
        tvTrabNombre.setText(trabajador.getNombre());
        tvTrabProfesion.setText(trabajador.getProfesion());
        tvTrabCategoria.setText(trabajador.getCategoria());
        tvTrabAExperiencia.setText(String.valueOf(trabajador.getExperiencia()));
        tvTrabSobre_mi.setText(trabajador.getSobre_mi());
    }

    public void aceptoTrabajador(View view) {
        switch (view.getId()) {
            case R.id.ibAceptoTrabajador:
                if (matchCRUD.hayMatch(idFbBuscoTrabajador, idFbTrabajador)) {
                    Log.d("Ya existe el match","");
                } else {
                    DateFormat df = new SimpleDateFormat("EEE, d MMM yyyy, HH:mm");
                    String date = df.format(Calendar.getInstance().getTime());
                    matchCRUD.newMatch(new Match(idFbBuscoTrabajador, idFbTrabajador, date));
                }
                Intent intent = new Intent(PerfilTrabajador.this, Chat.class);
                startActivity(intent);
                break;
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
}
