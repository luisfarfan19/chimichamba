package com.example.luisf.chimichamba.Views.Trabajador;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.luisf.chimichamba.Datos.Trabajador.Trabajador;
import com.example.luisf.chimichamba.Datos.Trabajador.TrabajadorCRUD;
import com.example.luisf.chimichamba.R;

public class SobreMi extends AppCompatActivity {
    private Trabajador trabajador;
    private TrabajadorCRUD trabajadorCRUD;
    String lat, lon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sobre_mi);
        trabajadorCRUD = new TrabajadorCRUD(this);
        Bundle bundle = getIntent().getExtras();
        lat = bundle.getString("lat");
        lon = bundle.getString("lon");
        FloatingActionButton fabNuevoItem = (FloatingActionButton) findViewById(R.id.fabGuardarSobreMi);
        fabNuevoItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText etSobreMi = (EditText) findViewById(R.id.etSobreMi);
                Trabajador trabajadorAux = trabajadorCRUD.getTrabajador("idFb1");
                Trabajador trabajador = new Trabajador("idFb1", trabajadorAux.getNombre(),
                        trabajadorAux.getEmail(), trabajadorAux.getProfesion(),
                        trabajadorAux.getCategoria(), trabajadorAux.getExperiencia(),
                        trabajadorAux.getLatActual(), trabajadorAux.getLonActual(),
                        trabajadorAux.getRadio_ubicacion(), trabajadorAux.getPuedo_viajar(),
                        etSobreMi.getText().toString(), trabajadorAux.getUrl_foto());
                trabajadorCRUD.updateTrabajador(trabajador);
                Toast.makeText(SobreMi.this, "La informacion se guardó correctamente",
                        Toast.LENGTH_LONG).show();
                Intent intent = new Intent(SobreMi.this, MiPerfil.class);
                intent.putExtra("lat", lat);
                intent.putExtra("lon", lon);
                startActivity(intent);
            }
        });
    }
}
