package com.example.luisf.chimichamba.Views.Trabajador;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.luisf.chimichamba.Datos.Trabajador.Trabajador;
import com.example.luisf.chimichamba.Datos.Trabajador.TrabajadorCRUD;
import com.example.luisf.chimichamba.R;
import com.squareup.picasso.Picasso;

public class MiPerfil extends AppCompatActivity {
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

        trabajadorCRUD = new TrabajadorCRUD(this);
        Bundle bundle = getIntent().getExtras();
        lat = bundle.getString("lat");
        lon = bundle.getString("lon");
        nombreUsuario = bundle.getString("NombreUsuario");
        idUsuarioFb = bundle.getString("IDUsuario");
        fotoUrlUsuario = bundle.getString("FotoUrlUsuario");

        tvNombre.setText(nombreUsuario);
        Picasso.with(this).load(fotoUrlUsuario).into(ivFoto);

        if (trabajadorCRUD.hayTrabajador(idUsuarioFb)) {
            trabajador = trabajadorCRUD.getTrabajador(idUsuarioFb);
            tvNombre.setText(trabajador.getNombre());
            tvProfesion.setText(trabajador.getProfesion());
            tvCategoria.setText(trabajador.getCategoria());
            tvAExperiencia.setText(String.valueOf(trabajador.getExperiencia()));
            tvSobre_mi.setText(trabajador.getSobre_mi());

        } else {
            trabajadorCRUD.newTrabajador(new Trabajador(idUsuarioFb, nombreUsuario,
                    "luisfarfanlara@hotmail.com", "", "", 1, "", "", 1, 1, "", fotoUrlUsuario));
        }
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
}
