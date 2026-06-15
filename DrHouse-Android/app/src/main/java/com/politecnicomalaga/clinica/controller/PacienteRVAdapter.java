package com.politecnicomalaga.clinica.controller;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.politecnicomalaga.clinica.R;
import com.politecnicomalaga.clinica.model.Paciente;

import java.util.List;


public class PacienteRVAdapter extends RecyclerView.Adapter<PacienteViewHolder> {

    private final List<Paciente> data;

    public PacienteRVAdapter(List<Paciente> data) {
        this.data = data;
    }

    @NonNull
    @Override
    public PacienteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new PacienteViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_paciente,
            parent, false));
    }

    // Adapter — solo entrega el dato
    @Override
    public void onBindViewHolder(@NonNull PacienteViewHolder holder, int position) {
        holder.bind(data.get(position));
    }

    @Override
    public int getItemCount() {
        return data != null ? data.size() : 0;
    }

}
