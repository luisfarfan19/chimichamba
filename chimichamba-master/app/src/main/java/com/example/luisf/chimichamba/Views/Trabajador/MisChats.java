package com.example.luisf.chimichamba.Views.Trabajador;

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

import com.example.luisf.chimichamba.Chat;
import com.example.luisf.chimichamba.Datos.BuscoTrabajador.BuscoTrabajador;
import com.example.luisf.chimichamba.Datos.BuscoTrabajador.BuscoTrabajadorCRUD;
import com.example.luisf.chimichamba.Datos.Match.Match;
import com.example.luisf.chimichamba.Datos.Match.MatchCRUD;
import com.example.luisf.chimichamba.Datos.Trabajador.Trabajador;
import com.example.luisf.chimichamba.HTTP.SendPost;
import com.example.luisf.chimichamba.HTTP.Sender;
import com.example.luisf.chimichamba.R;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MisChats extends AppCompatActivity implements Sender {
    String idFbTrabajador = "";
    private MatchCRUD matchCRUD;
    private Match match;
    private BuscoTrabajador buscoTrabajador;
    private BuscoTrabajadorCRUD buscoTrabajadorCRUD;
    private ArrayList<Match> matchesList;
    private ArrayList<String> listaBuscoTrabajadoresConMatch;
    private  ListView lvMisChatsTrabajador;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mis_chats);
         lvMisChatsTrabajador = (ListView) findViewById(R.id.lvMisChats);
        Bundle bundle = getIntent().getExtras();

        idFbTrabajador = bundle.getString("trabajadoridfb");
        matchCRUD = new MatchCRUD(this);
        buscoTrabajadorCRUD = new BuscoTrabajadorCRUD(this);

        matchesList = new ArrayList<Match>();
        listaBuscoTrabajadoresConMatch = new ArrayList<String>();

        SendPost send = new SendPost(this);
        send.execute("/Mall2","{\"ID\":\""+idFbTrabajador+"\"}");




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
    public  void answerFromServer(String s) {

        Log.i("Server",s);
        try{
            JSONObject reader = new JSONObject(s);
            Boolean ans = reader.getBoolean("valid");
            if(ans){

                int tipo = Integer.parseInt(reader.getString("tipo"));

                if(tipo == 2) { //json get
                    BuscoTrabajador aux;
                    JSONObject res = reader.getJSONObject("DATA");
                    Log.e("JsonDATA",res.toString());
                    if(res!=null){
                        try{
                            aux  = new BuscoTrabajador(res);
                            listaBuscoTrabajadoresConMatch.add(aux.getNombre().toString());
                            ((BaseAdapter) lvMisChatsTrabajador.getAdapter()).notifyDataSetChanged();
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
                                send.execute("/BTget","{\"ID\":\""+aux.getIdFbBusco().toString()+"\"}");
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
