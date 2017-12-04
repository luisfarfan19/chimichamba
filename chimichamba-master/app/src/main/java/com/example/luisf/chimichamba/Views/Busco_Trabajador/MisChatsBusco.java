package com.example.luisf.chimichamba.Views.Busco_Trabajador;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.luisf.chimichamba.Adaptadores.CustomAdapterTrabajadoresCercanos;
import com.example.luisf.chimichamba.Chat;
import com.example.luisf.chimichamba.Datos.BuscoTrabajador.BuscoTrabajador;
import com.example.luisf.chimichamba.Datos.Match.Match;
import com.example.luisf.chimichamba.Datos.Match.MatchCRUD;
import com.example.luisf.chimichamba.Datos.Trabajador.Trabajador;
import com.example.luisf.chimichamba.Datos.Trabajador.TrabajadorCRUD;
import com.example.luisf.chimichamba.HTTP.SendPost;
import com.example.luisf.chimichamba.HTTP.Sender;
import com.example.luisf.chimichamba.R;
import com.example.luisf.chimichamba.Views.Trabajador.MisChats;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MisChatsBusco extends AppCompatActivity implements Sender {

    String idFbBuscoTrabajador = "";
    private MatchCRUD matchCRUD;
    private Match match;
    private Trabajador trabajador;
    private TrabajadorCRUD trabajadorCRUD;
    ArrayList<Match> matchesList;
    ArrayList<Trabajador> listaTrabajadoresConMatch;
    ListView lvMisChatsBusco;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mis_chats_busco);
        Bundle bundle = getIntent().getExtras();
        idFbBuscoTrabajador = bundle.getString("buscotrabajadoridfb");
        matchCRUD = new MatchCRUD(this);
        trabajadorCRUD = new TrabajadorCRUD(this);


         lvMisChatsBusco = (ListView) findViewById(R.id.lvMisChatsBusco);
        matchesList = new ArrayList<Match>();
        listaTrabajadoresConMatch = new ArrayList<Trabajador>();

        SendPost send = new SendPost(this);
        send.execute("/Mall1","{\"ID\":\""+idFbBuscoTrabajador+"\"}");

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
    public  void answerFromServer(String s) {

        Log.i("Server",s);
        try{
            JSONObject reader = new JSONObject(s);
            Boolean ans = reader.getBoolean("valid");
            if(ans){

                int tipo = Integer.parseInt(reader.getString("tipo"));

                if(tipo == 2) { //json get
                    Trabajador aux;
                    JSONObject res = reader.getJSONObject("DATA");
                    Log.e("JsonDATA",res.toString());
                    if(res!=null){
                        try{
                            aux  = new Trabajador(res);
                            listaTrabajadoresConMatch.add(aux);
                            ((BaseAdapter) lvMisChatsBusco.getAdapter()).notifyDataSetChanged();
                            //actualizar list view de ser necesario.
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

                if(tipo == 3) { //json get
                    String res = reader.getJSONArray("DATA").toString();
                    Log.e("JsonDATA",res);
                    Match aux;
                    if(!res.equals("")){
                        try{
                            JSONArray anse = new JSONArray(res);
                            for(int i=0; i<anse.length();i++){
                                aux = new Match(anse.getJSONObject(i));
                                matchesList.add( aux);
                                SendPost send = new SendPost(this);
                                send.execute("/Tget","{\"ID\":\""+aux.getIdFbTrabajador().toString()+"\"}");
                            }

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
