package com.example.luisf.chimichamba.Views.Trabajador;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.luisf.chimichamba.Chat;
import com.example.luisf.chimichamba.R;

import java.util.ArrayList;

public class MisChats extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mis_chats);
        ListView lvMisChats = (ListView) findViewById(R.id.lvMisChats);
        ArrayList<String> chatsList = new ArrayList<String>();
        chatsList.add("Chat 1");
        chatsList.add("Chat 2");
        chatsList.add("Chat 3");
        chatsList.add("Chat 4");

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_list_item_1, chatsList
        );
        lvMisChats.setAdapter(adapter);

        lvMisChats.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MisChats.this,Chat.class);
                startActivity(intent);
            }
        });
    }
}
