package com.example.luisf.chimichamba.Views.Busco_Trabajador;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.luisf.chimichamba.Adaptadores.CustomAdapterTrabajadoresCercanos;
import com.example.luisf.chimichamba.Adaptadores.RecyclerViewClickListener;
import com.example.luisf.chimichamba.Adaptadores.RecyclerViewCustomAdapter;
import com.example.luisf.chimichamba.Chat;
import com.example.luisf.chimichamba.Adaptadores.ExpandableCustomAdapter;
import com.example.luisf.chimichamba.Datos.BuscoTrabajador.BuscoTrabajador;
import com.example.luisf.chimichamba.Datos.BuscoTrabajador.BuscoTrabajadorCRUD;
import com.example.luisf.chimichamba.Datos.Trabajador.Trabajador;
import com.example.luisf.chimichamba.Datos.Trabajador.TrabajadorCRUD;
import com.example.luisf.chimichamba.HTTP.SendPost;
import com.example.luisf.chimichamba.HTTP.Sender;
import com.example.luisf.chimichamba.R;
import com.example.luisf.chimichamba.Views.Trabajador.Configuracion;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class BuscoTrabajadorV extends AppCompatActivity implements Sender {
    ExpandableListAdapter listAdapter;
    ExpandableListView expListView;
    SeekBar seekBarUbicacion;
    TextView tvDistanciaBusco;
    List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;
    private String lat, lon;
    private String nombreUsuario, idUsuarioFb, fotoUrlUsuario;
    private Trabajador trabajador;
    private TrabajadorCRUD trabajadorCRUD;
    private BuscoTrabajadorCRUD buscoTrabajadorCRUD;
    private BuscoTrabajador buscoTrabajador;
    private Integer radio = 20;
    private String categoria = "";
    int progressUbicacion = 0;
    ArrayList<Trabajador> listaTrabajadores;
    ArrayList<Trabajador> listaTrabajadoresCercanos;
   // ListView lvTrabajadoresCercaDeMi;
    RecyclerView  rvTrabajadoresCercaDeMi;
    LinearLayoutManager linearLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_busco_trabajador);
        expListView = (ExpandableListView) findViewById(R.id.lvFiltros);
        seekBarUbicacion = (SeekBar) findViewById(R.id.seekBar3);
        tvDistanciaBusco = (TextView) findViewById(R.id.tvDistanciaBusco);
       // lvTrabajadoresCercaDeMi = (ListView) findViewById(R.id.lvTrabajadoresCercaDeMi);
        rvTrabajadoresCercaDeMi = (RecyclerView) findViewById(R.id.rvTrabajadoresCercaDeMi);
        linearLayoutManager = new LinearLayoutManager(this);
        rvTrabajadoresCercaDeMi.setLayoutManager(linearLayoutManager);

        prepareListData();
        buscoTrabajadorCRUD = new BuscoTrabajadorCRUD(this);
        trabajadorCRUD = new TrabajadorCRUD(this);
        listAdapter = new ExpandableCustomAdapter(this, listDataHeader, listDataChild);
        expListView.setAdapter(listAdapter);
        Bundle bundle = getIntent().getExtras();
        lat = bundle.getString("lat");
        lon = bundle.getString("lon");
        nombreUsuario = bundle.getString("NombreUsuario");
        idUsuarioFb = bundle.getString("IDUsuario");
        fotoUrlUsuario = bundle.getString("FotoUrlUsuario");

        SendPost send = new SendPost(this);
        send.execute("/BThay","{\"ID\":\""+idUsuarioFb+"\"}");

       listaTrabajadores = new ArrayList<Trabajador>();
       listaTrabajadoresCercanos = new ArrayList<Trabajador>();

        listaTrabajadores = trabajadorCRUD.getTrabajadores();

        SendPost send2 = new SendPost(this);
        send2.execute("/TgetAll","{}");

        FloatingActionButton fabChatsBusco = (FloatingActionButton) findViewById(R.id.fabChatsBusco);
        fabChatsBusco.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(BuscoTrabajadorV.this, MisChatsBusco.class);
                /*Snackbar.make(view, "CantidadTodosItems: " + String.valueOf(cantidadTodosItems), Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();*/
                intent.putExtra("buscotrabajadoridfb", idUsuarioFb);
                startActivity(intent);
            }
        });

        expListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {

            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {
                categoria = listDataChild.get(listDataHeader.get(groupPosition)).get(childPosition);
                Toast.makeText(BuscoTrabajadorV.this, categoria.toString() + " fue seleccionada.",
                        Toast.LENGTH_LONG).show();
                return false;
            }
        });

        seekBarUbicacion.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progressVal, boolean fromUser) {
                progressUbicacion = progressVal;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                tvDistanciaBusco.setText(String.valueOf(progressUbicacion));
            }
        });
    }

    public static int getDistance(float lat_a, float lng_a, float lat_b, float lon_b) {
        double Radius = 6371000; //Radio de la tierra
        double lat1 = lat_a / 1E6;
        double lat2 = lat_b / 1E6;
        double lon1 = lng_a / 1E6;
        double lon2 = lon_b / 1E6;
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) * Math.sin(dLon / 2) * Math.sin(dLon / 2);
        double c = 2 * Math.asin(Math.sqrt(a));
        return (int) (Radius * c);
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
    public  void answerFromServer(String s) {

        Log.i("Server",s);
        try{
            JSONObject reader = new JSONObject(s);
            Boolean ans = reader.getBoolean("valid");
            if(ans){

                int tipo = Integer.parseInt(reader.getString("tipo"));

                if(tipo == 3) { //confirmaciones crear editar borrar
                    String res = reader.getJSONArray("DATA").toString();
                    Log.e("JsonDATA",res);

                    if(!res.equals("")){
                        try{
                            JSONArray anse = new JSONArray(res);
                            for(int i=0; i<anse.length();i++){
                                Trabajador aux = new Trabajador(anse.getJSONObject(i));
                                if(!aux.getLatActual().equals("")){
                                listaTrabajadores.add( aux);}
                            }
                            for (int i = 0; i < listaTrabajadores.size(); i++) {
                                int distance = getDistance(Float.parseFloat(lat), Float.parseFloat(lon), Float.parseFloat(listaTrabajadores.get(i).getLatActual().toString()), Float.parseFloat(listaTrabajadores.get(i).getLonActual().toString()));
                                if (distance < (radio * 1000)) {
                                    listaTrabajadoresCercanos.add(listaTrabajadores.get(i));
                                }
                            }

                            /*ArrayAdapter<String> adapter1 = new CustomAdapterTrabajadoresCercanos(BuscoTrabajadorV.this, listaTrabajadoresCercanos);
                            lvTrabajadoresCercaDeMi.setAdapter(adapter1);
                            lvTrabajadoresCercaDeMi.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                    Intent intent = new Intent(BuscoTrabajadorV.this, PerfilTrabajador.class);
                                    intent.putExtra("trabajadoridfb", listaTrabajadoresCercanos.get(position).getIdFb());
                                    intent.putExtra("buscotrabajadoridfb", idUsuarioFb);
                                    startActivity(intent);
                                }
                            });*/

                            RecyclerViewCustomAdapter adapter = new RecyclerViewCustomAdapter(listaTrabajadoresCercanos, new RecyclerViewClickListener() {
                                @Override
                                public void onClick(View view, int position) {
                                    Intent intent = new Intent(BuscoTrabajadorV.this, PerfilTrabajador.class);
                                    intent.putExtra("trabajadoridfb", listaTrabajadoresCercanos.get(position).getIdFb());
                                    intent.putExtra("buscotrabajadoridfb", idUsuarioFb);
                                    startActivity(intent);
                                }
                            });
                            rvTrabajadoresCercaDeMi.setAdapter(adapter);
                        }catch (JSONException e){
                            Log.e("error",e.toString());
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
                            Log.d("Si", "Hay");


                        }else{//no existe
                         BuscoTrabajador aux = new BuscoTrabajador(idUsuarioFb, nombreUsuario, radio, "", fotoUrlUsuario);

                            SendPost send = new SendPost(this);

                            send.execute("/BTinsert","{\"datos\":"+aux.toJson()+"}");//crear usuario->answerfromserver tipo 1

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
