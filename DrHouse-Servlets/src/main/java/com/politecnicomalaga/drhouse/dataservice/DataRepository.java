package com.politecnicomalaga.drhouse.dataservice;

import com.politecnicomalaga.drhouse.model.Medicamento;
import com.politecnicomalaga.drhouse.model.Paciente;

import java.sql.SQLException;
import java.util.List;

/**
 * Contract for all data persistence operations.
 * Decouples the {@link com.politecnicomalaga.drhouse.controller.Controller}
 * from any concrete storage implementation.
 */
public interface DataRepository {
    List<Medicamento> listAllMedicamentos() throws SQLException, ClassNotFoundException;; //-> Accede a la BBDD y obtiene todos los medicamentos disponibles
    List<Medicamento> findMedicamentoXCodigo(String code) throws SQLException, ClassNotFoundException;; //-> Accede a la BBDD y busca el medicamento que coindice con codigo=code
    List<Paciente> findPacienteXDNI(String dni) throws SQLException, ClassNotFoundException;; //-> Accede a la BBDD y busca el cliente con el DNI=dni
    List<Medicamento> listMedicamentosXTratamiento(String dni, String tratamiendo_id) throws SQLException, ClassNotFoundException;; //-> Accede a la BBDD y busca todos los medicamenntos asociados en el id_tratamiento=tratamiento y para el paciente DNI=dni
    void importData(String jsonDataFromCSV) throws SQLException, ClassNotFoundException;; //-> parsea el json con los datos del CSV leído en el programa de importación y realiza los INSERT adecuados para actualizar la BBDD con la info almacenada en ese JSON
}
