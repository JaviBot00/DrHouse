package com.politecnicomalaga.importcsv.model;

import java.util.ArrayList;
import java.util.List;

public class Tratamiento {

    private int idTratamiento;
    private String dniPaciente;
    private String fechaInicio;
    private int diasTratamiento;
    private String diagnostico;
    private List<Medicamento> medicamentos;

    public Tratamiento() {
        medicamentos = new ArrayList<>();
    }

    public Tratamiento(int idTratamiento, String dniPaciente, String fechaInicio,
                       int diasTratamiento, String diagnostico) {
        this.idTratamiento = idTratamiento;
        this.dniPaciente = dniPaciente;
        this.fechaInicio = fechaInicio;
        this.diasTratamiento = diasTratamiento;
        this.diagnostico = diagnostico;
        medicamentos = new ArrayList<>();
    }

    public int getIdTratamiento() { return idTratamiento; }
    public void setIdTratamiento(int idTratamiento) { this.idTratamiento = idTratamiento; }

    public String getDniPaciente() { return dniPaciente; }
    public void setDniPaciente(String dniPaciente) { this.dniPaciente = dniPaciente; }

    public String getFechaInicio() { return fechaInicio; }
    public void setFechaInicio(String fechaInicio) { this.fechaInicio = fechaInicio; }

    public int getDiasTratamiento() { return diasTratamiento; }
    public void setDiasTratamiento(int diasTratamiento) { this.diasTratamiento = diasTratamiento; }

    public String getDiagnostico() { return diagnostico; }
    public void setDiagnostico(String diagnostico) { this.diagnostico = diagnostico; }

    public List<Medicamento> getMedicamentos() { return medicamentos; }
    public void setMedicamentos(List<Medicamento> medicamentos) { this.medicamentos = medicamentos; }

    public void addMedicamento(Medicamento m) { medicamentos.add(m); }
}
