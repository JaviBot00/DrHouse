package com.politecnicomalaga.importcsv.model;

public class Medicamento {

    private String idMedicamento;
    private String nombre;
    private String posologia;

    public Medicamento() {}

    public Medicamento(String idMedicamento, String nombre, String posologia) {
        this.idMedicamento = idMedicamento;
        this.nombre = nombre;
        this.posologia = posologia;
    }

    public String getIdMedicamento() { return idMedicamento; }
    public void setIdMedicamento(String idMedicamento) { this.idMedicamento = idMedicamento; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getPosologia() { return posologia; }
    public void setPosologia(String posologia) { this.posologia = posologia; }
}
