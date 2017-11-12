package com.example.luisf.chimichamba.Adaptadores;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.luisf.chimichamba.Datos.Trabajador.Trabajador;
import com.example.luisf.chimichamba.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by luisf on 06/11/2017.
 */

public class CustomAdapterTrabajadoresCercanos extends ArrayAdapter {
    private TextView tvNombre;
    private ImageView ivFoto;

    public CustomAdapterTrabajadoresCercanos(@NonNull Context context, ArrayList<Trabajador> trabajador) {
        super(context, R.layout.file, trabajador);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = LayoutInflater.from(getContext());
        View vistaCustom = layoutInflater.inflate(R.layout.file, parent, false);
        Trabajador trabajador = (Trabajador) getItem(position);

        tvNombre = (TextView) vistaCustom.findViewById(R.id.tvNombreTrabajadorCercano);
        tvNombre.setText(trabajador.getNombre());

        ivFoto = (ImageView) vistaCustom.findViewById(R.id.ivFotoTrabajadorCercano);
        Picasso.with(getContext()).load(trabajador.getUrl_foto()).into(ivFoto);
        return vistaCustom;
    }
}
