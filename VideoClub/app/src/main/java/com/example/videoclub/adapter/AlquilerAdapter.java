package com.example.videoclub.adapter;


import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.videoclub.Modelos.Alquiler;
import com.example.videoclub.R;

import java.util.ArrayList;

public class AlquilerAdapter extends RecyclerView.Adapter<AlquilerAdapter.AlquilerHolder> {

    ArrayList<Alquiler> listAlquiler;

    public AlquilerAdapter(ArrayList<Alquiler> listAlquiler) {
        this.listAlquiler = listAlquiler;
    }

    @NonNull
    @Override
    public AlquilerHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_list,viewGroup,false);
        RecyclerView.LayoutParams layoutParams = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        view.setLayoutParams(layoutParams);
        return new AlquilerHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AlquilerHolder alquilerHolder, int i) {
        alquilerHolder.fechaAlquiler.setText(listAlquiler.get(i).getFechaDesde());
        alquilerHolder.fechaHasta.setText(listAlquiler.get(i).getFechaHasta());
        alquilerHolder.pelicula.setText(listAlquiler.get(i).getPelicula().getNombre());
        alquilerHolder.genero.setText(listAlquiler.get(i).getPelicula().getGenero());
        alquilerHolder.formato.setText(listAlquiler.get(i).getPelicula().getFormato());

    }

    @Override
    public int getItemCount() {
        return listAlquiler.size();
    }

    public class AlquilerHolder extends RecyclerView.ViewHolder {
        TextView fechaAlquiler,fechaHasta,pelicula,genero,formato;
        public AlquilerHolder(@NonNull View itemView) {

            super(itemView);
            fechaAlquiler=itemView.findViewById(R.id.et_fecha_alquiler);
            fechaHasta=itemView.findViewById(R.id.et_fecha_hasta);
            pelicula=itemView.findViewById(R.id.et_pelicula);
            genero=itemView.findViewById(R.id.et_genero);
            formato=itemView.findViewById(R.id.et_formato);


        }
    }
}
