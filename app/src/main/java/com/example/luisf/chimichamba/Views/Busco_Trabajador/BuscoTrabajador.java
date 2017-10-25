package com.example.luisf.chimichamba.Views.Busco_Trabajador;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;

import com.example.luisf.chimichamba.Chat;
import com.example.luisf.chimichamba.Adaptadores.ExpandableCustomAdapter;
import com.example.luisf.chimichamba.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class BuscoTrabajador extends AppCompatActivity {
    ExpandableListAdapter listAdapter;
    ExpandableListView expListView;
    List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_busco_trabajador);
        // get the listview
        expListView = (ExpandableListView) findViewById(R.id.lvFiltros);

        // preparing list data
        prepareListData();

        listAdapter = new ExpandableCustomAdapter(this, listDataHeader, listDataChild);

        // setting list adapter
        expListView.setAdapter(listAdapter);
    }

    private void prepareListData() {
        listDataHeader = new ArrayList<>();
        listDataChild = new HashMap<String, List<String>>();

        // Adding child data
        listDataHeader.add("Tipo");

        // Adding child data
        List<String> tipo = new ArrayList<String>();
        tipo.add("Tecnologia");
        tipo.add("Contaduria");
        tipo.add("Leyes");
        tipo.add("Vida y Estilo");
        tipo.add("Redes");
        tipo.add("Hogar");
        tipo.add("Otros");

        listDataChild.put(listDataHeader.get(0), tipo);
    }

    public void verPerfilTrabajador(View view) {
        switch (view.getId()) {
            case R.id.ibUserImage:
                Intent intent = new Intent(BuscoTrabajador.this, PerfilTrabajador.class);
                startActivity(intent);
                break;
        }
    }
    public void aceptoTrabajador(View view) {
        switch (view.getId()) {
            case R.id.ibPaloma:
                Intent intent = new Intent(BuscoTrabajador.this, Chat.class);
                startActivity(intent);
                break;
        }
    }
}
