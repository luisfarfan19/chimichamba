package com.example.luisf.chimichamba.Views.Trabajador;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.luisf.chimichamba.Chat;
import com.example.luisf.chimichamba.Datos.BuscoTrabajador.BuscoTrabajador;
import com.example.luisf.chimichamba.Datos.BuscoTrabajador.BuscoTrabajadorCRUD;
import com.example.luisf.chimichamba.Datos.Match.Match;
import com.example.luisf.chimichamba.Datos.Match.MatchCRUD;
import com.example.luisf.chimichamba.R;

import java.util.ArrayList;

public class MisChats extends AppCompatActivity {
    String idFbTrabajador = "";
    private MatchCRUD matchCRUD;
    private Match match;
    private BuscoTrabajador buscoTrabajador;
    private BuscoTrabajadorCRUD buscoTrabajadorCRUD;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mis_chats);
        ListView lvMisChatsTrabajador = (ListView) findViewById(R.id.lvMisChats);
        Bundle bundle = getIntent().getExtras();
        idFbTrabajador = bundle.getString("trabajadoridfb");
        matchCRUD = new MatchCRUD(this);
        buscoTrabajadorCRUD = new BuscoTrabajadorCRUD(this);

        ArrayList<Match> matchesList = new ArrayList<Match>();
        ArrayList<String> listaBuscoTrabajadoresConMatch = new ArrayList<String>();
        matchesList = matchCRUD.getMatchesTrabajador(idFbTrabajador);
        for (int i = 0; i < matchesList.size(); i++) {
            String idFbBusco = matchesList.get(i).getIdFbBusco().toString();
            BuscoTrabajador buscoTrabajador = buscoTrabajadorCRUD.getBuscoTrabajador(idFbBusco);
            listaBuscoTrabajadoresConMatch.add(buscoTrabajador.getNombre().toString());
        }
        //Ya tengo todos los buscoTrabajadores con match

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_list_item_1, listaBuscoTrabajadoresConMatch
        );
        lvMisChatsTrabajador.setAdapter(adapter);

        lvMisChatsTrabajador.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MisChats.this, Chat.class);
                //intent.putExtra("trabajadoridfb", listaTrabajadoresCercanos.get(position).getIdFb());
                startActivity(intent);
            }
        });
    }
}
