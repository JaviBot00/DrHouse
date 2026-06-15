package com.politecnicomalaga.clinica.controller;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.politecnicomalaga.clinica.model.Medicamento;
import com.politecnicomalaga.clinica.R;

import java.util.List;


public class MedicamentoRVAdapter extends RecyclerView.Adapter<MedicamentoViewHolder> {

    private final List<Medicamento> data;

    public MedicamentoRVAdapter(List<Medicamento> data) {
        this.data = data;
    }

    @NonNull
    @Override
    public MedicamentoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MedicamentoViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_medicamento,
            parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MedicamentoViewHolder holder, int position) {
        holder.bind(data.get(position));
    }

    @Override
    public int getItemCount() {
        return data != null ? data.size() : 0;
    }

}
