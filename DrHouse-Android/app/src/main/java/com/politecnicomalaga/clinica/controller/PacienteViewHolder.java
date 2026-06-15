package com.politecnicomalaga.clinica.controller;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.politecnicomalaga.clinica.R;
import com.politecnicomalaga.clinica.model.Paciente;

public class PacienteViewHolder extends RecyclerView.ViewHolder {

    private final TextView tvName;
    private final TextView tvDni;
    private final TextView tvEmail;

    public PacienteViewHolder(@NonNull View itemView) {
        super(itemView);
        tvName = itemView.findViewById(R.id.tvName);
        tvDni = itemView.findViewById(R.id.tvDni);
        tvEmail = itemView.findViewById(R.id.tvEmail);
    }

    // ViewHolder — toda la lógica de presentación aquí
    public void bind(Paciente p) {
        tvName.setText(p.getNombre() + " " + p.getApellidos());
        tvDni.setText("DNI: " + p.getDni());
        tvEmail.setText(p.getEmail());
    }
}
