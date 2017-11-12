package com.example.luisf.chimichamba.Views.Busco_Trabajador;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.luisf.chimichamba.Adaptadores.CustomAdapterTrabajadoresCercanos;
import com.example.luisf.chimichamba.Chat;
import com.example.luisf.chimichamba.Datos.Match.Match;
import com.example.luisf.chimichamba.Datos.Match.MatchCRUD;
import com.example.luisf.chimichamba.Datos.Trabajador.Trabajador;
import com.example.luisf.chimichamba.Datos.Trabajador.TrabajadorCRUD;
import com.example.luisf.chimichamba.R;
import com.example.luisf.chimichamba.Views.Trabajador.MisChats;

import java.util.ArrayList;

public class MisChatsBusco extends AppCompatActivity {

    String idFbBuscoTrabajador = "";
    private MatchCRUD matchCRUD;
    private Match match;
    private Trabajador trabajador;
    private TrabajadorCRUD trabajadorCRUD;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mis_chats_busco);
        Bundle bundle = getIntent().getExtras();
        idFbBuscoTrabajador = bundle.getString("buscotrabajadoridfb");
        matchCRUD = new MatchCRUD(this);
        trabajadorCRUD = new TrabajadorCRUD(this);

        ListView lvMisChatsBusco = (ListView) findViewById(R.id.lvMisChatsBusco);
        ArrayList<Match> matchesList = new ArrayList<Match>();
        ArrayList<Trabajador> listaTrabajadoresConMatch = new ArrayList<Trabajador>();
        matchesList = matchCRUD.getMatchesBusco(idFbBuscoTrabajador);
        for (int i = 0; i < matchesList.size(); i++) {
            String idFbTrabajador = matchesList.get(i).getIdFbTrabajador().toString();
            Trabajador trabajador = trabajadorCRUD.getTrabajador(idFbTrabajador);
            listaTrabajadoresConMatch.add(trabajador);
        }
        //Ya tengo todos los trabajadores con match

        ArrayAdapter<String> adapter1 = new CustomAdapterTrabajadoresCercanos(MisChatsBusco.this, listaTrabajadoresConMatch);
        lvMisChatsBusco.setAdapter(adapter1);
        lvMisChatsBusco.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MisChatsBusco.this, Chat.class);
                //intent.putExtra("trabajadoridfb", listaTrabajadoresCercanos.get(position).getIdFb());
                startActivity(intent);
            }
        });
    }
}
