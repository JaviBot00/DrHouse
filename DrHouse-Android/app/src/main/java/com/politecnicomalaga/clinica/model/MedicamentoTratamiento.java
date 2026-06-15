package com.politecnicomalaga.clinica.model;

public class MedicamentoTratamiento {

    private int idRegistro;
    private int idTratamiento;
    private String idMedicamento;

    public MedicamentoTratamiento() {}

    public MedicamentoTratamiento(int idRegistro, int idTratamiento,
                                  String idMedicamento) {
        this.idRegistro = idRegistro;
        this.idTratamiento = idTratamiento;
        this.idMedicamento = idMedicamento;
    }

    public int getIdRegistro() { return idRegistro; }
    public void setIdRegistro(int idRegistro) { this.idRegistro = idRegistro; }

    public int getIdTratamiento() { return idTratamiento; }
    public void setIdTratamiento(int idTratamiento) { this.idTratamiento = idTratamiento; }

    public String getIdMedicamento() { return idMedicamento; }
    public void setIdMedicamento(String idMedicamento) { this.idMedicamento = idMedicamento; }

}
