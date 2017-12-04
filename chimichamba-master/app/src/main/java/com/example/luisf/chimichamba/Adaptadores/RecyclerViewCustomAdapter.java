package com.example.luisf.chimichamba.Adaptadores;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.luisf.chimichamba.Datos.Trabajador.Trabajador;
import com.example.luisf.chimichamba.Image.CircleTransform;
import com.example.luisf.chimichamba.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Avotlasej on 03/12/2017.
 */

public class RecyclerViewCustomAdapter extends RecyclerView.Adapter<RecyclerViewCustomAdapter.CustomViewHolder>{
    private ArrayList<Trabajador> trabajador;
    private RecyclerViewClickListener listener;

    public RecyclerViewCustomAdapter(ArrayList<Trabajador> trabajador, RecyclerViewClickListener listener){
        this.trabajador= trabajador;
        this.listener = listener;
    }

    public static class CustomViewHolder extends RecyclerView.ViewHolder{
        private TextView tvNombre;
        private ImageView ivFoto;

        CustomViewHolder(View vista){
            super(vista);

            tvNombre = (TextView) vista.findViewById(R.id.tvNombreTrabajadorCercano);
            ivFoto = (ImageView) vista.findViewById(R.id.ivFotoTrabajadorCercano);
        }
    }
    @Override
    public int getItemCount() {
        return trabajador.size();
    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View vista = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.file, parent, false);
        CustomViewHolder customViewHolder = new CustomViewHolder(vista);

        //CustomViewHolder customViewHolder = new CustomViewHolder(vista);
        //return customViewHolder;

        // TODO: 13.- Agregamos un objeto RowViewHolder y eliminamos las dos líneas anteriores
        return new RowViewHolder(vista, listener);
    }

    @Override
    public void onBindViewHolder(CustomViewHolder holder, int position) {
        Picasso.with(holder.itemView.getContext()).load(trabajador.get(position).getUrl_foto()).transform(new CircleTransform()).into(holder.ivFoto);
        holder.tvNombre.setText(trabajador.get(position).getNombre());
    }
}
