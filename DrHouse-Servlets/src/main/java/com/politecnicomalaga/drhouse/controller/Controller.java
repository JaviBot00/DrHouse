package com.politecnicomalaga.drhouse.controller;

import com.google.gson.Gson;
import com.politecnicomalaga.drhouse.dataservice.DataRepository;
import com.politecnicomalaga.drhouse.dataservice.DatabaseAccess;

import java.sql.SQLException;
import java.util.Collections;

public class Controller implements ControllerContract {

    private static final Gson GSON = new Gson();

    private final DataRepository repository;

    /** Production constructor — uses the shared {@link DatabaseAccess} singleton. */
    public Controller() {
        this(DatabaseAccess.getInstance());
    }

    /**
     * Injectable constructor for testing or alternative data sources.
     *
     * @param repository data access implementation to use
     */
    public Controller(DataRepository repository) {
        this.repository = repository;
    }

    @Override
    public String listAllMedicamentos() {
        try {
            return GSON.toJson(repository.listAllMedicamentos());
        } catch (SQLException | ClassNotFoundException e) {
            return errorJson("listAllMedicamentos", e);
        }
    }

    @Override
    public String findMedicamentoXCodigo(String code) {
        try {
            return GSON.toJson(repository.findMedicamentoXCodigo(code));
        } catch (SQLException | ClassNotFoundException e) {
            return errorJson("findMedicamentoXCodigo", e);
        }
    }

    @Override
    public String findPacienteXDNI(String dni) {
        try {
            return GSON.toJson(repository.findPacienteXDNI(dni));
        } catch (SQLException | ClassNotFoundException e) {
            return errorJson("findPacienteXDNI", e);
        }
    }

    @Override
    public String listMedicamentosXTratamiento(String dni, String tratamiendo_id) {
        try {
            return GSON.toJson(repository.listMedicamentosXTratamiento(dni, tratamiendo_id));
        } catch (SQLException | ClassNotFoundException e) {
            return errorJson("listMedicamentosXTratamiento", e);
        }
    }

    @Override
    public String importData(String jsonDataFromCSV) {
        try {
            repository.importData(jsonDataFromCSV);
            return okJson();
        } catch (SQLException | ClassNotFoundException e) {
            return errorJson("importData", e);
        }
    }

    // ── Private helpers ──────────────────────────────────────────────────────

    private String okJson() {
        return GSON.toJson(Collections.singletonMap("status", "OK"));
    }

    private String errorJson(String operation, Exception e) {
        return GSON.toJson(Collections.singletonMap("error", operation + ": " + e.getMessage()));
    }
}
