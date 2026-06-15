package com.politecnicomalaga.clinica.controller;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.politecnicomalaga.clinica.R;
import com.politecnicomalaga.clinica.model.Medicamento;

public class MedicamentoViewHolder extends RecyclerView.ViewHolder {

    private final TextView tvNombre;
    private final TextView tvPosologia;

    public MedicamentoViewHolder(@NonNull View itemView) {
        super(itemView);
        tvNombre = itemView.findViewById(R.id.tvNombre);
        tvPosologia = itemView.findViewById(R.id.tvPosologia);
    }

    // ViewHolder — toda la lógica de presentación aquí
    public void bind(Medicamento m) {
        tvNombre.setText(m.getNombre());
        tvPosologia.setText(m.getPosologia());
    }
}
