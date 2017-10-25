package com.example.luisf.chimichamba.Views.Busco_Trabajador;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.luisf.chimichamba.Chat;
import com.example.luisf.chimichamba.R;

public class PerfilTrabajador extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil_trabajador);
    }

    public void aceptoTrabajador(View view) {
        switch (view.getId()) {
            case R.id.ibAceptoTrabajador:
                Intent intent = new Intent(PerfilTrabajador.this, Chat.class);
                startActivity(intent);
                break;
        }
    }

    public void noAceptoTrabajador(View view) {
        switch (view.getId()) {
            case R.id.ibNoAcpetoTrabajador:
                Intent intent = new Intent(PerfilTrabajador.this, BuscoTrabajador.class);
                startActivity(intent);
                break;
        }
    }
}
