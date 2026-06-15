package com.politecnicomalaga.clinica.dataservice;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.politecnicomalaga.clinica.model.Medicamento;
import com.politecnicomalaga.clinica.model.MedicamentoTratamiento;
import com.politecnicomalaga.clinica.model.Paciente;
import com.politecnicomalaga.clinica.model.Tratamiento;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DatabaseAccess implements DataRepository {

    //Aquí los métodos necesarios para CRUD de datos en la BBDD
    private static final String TABLE_PACIENTES = "pacientes";
    private static final String TABLE_MEDICAMENTO = "medicamentos";
    private static final String TABLE_TRATAMIENTO = "tratamientos";
    private static final String TABLE_MEDICAMENTO_TRATAMIENTO = "medicamentos_tratamientos";

    private static DatabaseAccess instance;

    public DatabaseAccess() {}

    public static synchronized DatabaseAccess getInstance() {
        if (instance == null) instance = new DatabaseAccess();
        return instance;
    }

    @Override
    public List<Medicamento> listAllMedicamentos() throws SQLException, ClassNotFoundException {
        List<Medicamento> results = new ArrayList<>();
        String sql = "SELECT codigo, nombre, posologia FROM " + TABLE_MEDICAMENTO;
        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) results.add(mapRowMedicamento(rs));
            }
        }
        return results;
    }

    @Override
    public List<Medicamento> findMedicamentoXCodigo(String code) throws SQLException, ClassNotFoundException {
        List<Medicamento> results = new ArrayList<>();
        String sql = "SELECT codigo, nombre, posologia FROM " + TABLE_MEDICAMENTO + " WHERE codigo = ?";
        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, code);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) results.add(mapRowMedicamento(rs));
            }
        }
        return results;
    }

    @Override
    public List<Paciente> findPacienteXDNI(String dni) throws SQLException, ClassNotFoundException {
        List<Paciente> results = new ArrayList<>();
        String sql = "SELECT dni, nombre, apellidos, email, telefono, direccion FROM " + TABLE_PACIENTES + " WHERE dni = ?";
        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, dni);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) results.add(mapRowPaciente(rs));
            }
        }
        return results;
    }

    @Override
    public List<Medicamento> listMedicamentosXTratamiento(String dni, String id_tratamiento) throws SQLException, ClassNotFoundException {
        List<Medicamento> results = new ArrayList<>();
        String sql = "SELECT id_tratamiento, dni_paciente, fecha_inicio, dias_tratamiento, diagnostico FROM " + TABLE_TRATAMIENTO
            + " WHERE dni_paciente = ? AND id_tratamiento = ?";
        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, dni);
            ps.setString(2, id_tratamiento);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) results.add(mapRowMedicamento(rs));
            }
        }
        return results;
    }

    @Override
    public void importData(String jsonDataFromCSV) throws SQLException, ClassNotFoundException {
        Gson gson = new Gson();
        JsonObject root = JsonParser.parseString(jsonDataFromCSV).getAsJsonObject();

        // Parsear listas del JSON
        List<Paciente>  pacientes  = gson.fromJson(root.get("pacientes"),  new TypeToken<List<Paciente>>(){}.getType());
        List<Tratamiento> tratamientos = gson.fromJson(root.get("tratamientos"), new TypeToken<List<Tratamiento>>(){}.getType());
        List<Medicamento>  medicamentos  = gson.fromJson(root.get("medicamentos"),  new TypeToken<List<Medicamento>>(){}.getType());

        try (Connection conn = DatabaseConnection.getConnection()) {
//            conn.setAutoCommit(false); // transacción: o todo o nada
            try {
                insertarMedicamento(conn, medicamentos);
                for (Paciente p : pacientes) {
                    insertarPaciente(conn, p);
                }
                for (Tratamiento t : tratamientos) {
                    insertarTratamiento(conn, t);
                    for (Medicamento m : t.getMedicamentos()) {
                        insertarMedicamentoTratamiento(conn, t, m);
                    }
                }
                conn.commit();
            } catch (SQLException e) {
                conn.rollback();
            }
        }
    }

    // --- Métodos auxiliares INSERT---

    private void insertarMedicamento(Connection conn, List<Medicamento> medicamentos) throws SQLException {
        String sql = "INSERT IGNORE INTO " + TABLE_MEDICAMENTO + " (codigo, nombre, posologia) VALUES (?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            for (Medicamento m : medicamentos) {
                ps.setString(1, m.getIdMedicamento());
                ps.setString(2, m.getNombre());
                ps.setString(3, m.getPosologia());
                ps.executeUpdate();
            }
        }
    }

    private void insertarPaciente(Connection conn, Paciente p) throws SQLException {
        String sql = "INSERT IGNORE INTO " + TABLE_PACIENTES + " (dni, nombre, apellidos, email, telefono, direccion) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, p.getDni());
            ps.setString(2, p.getNombre());
            ps.setString(3, p.getApellidos());
            ps.setString(4, p.getEmail());
            ps.setString(5, p.getTelefono());
            ps.setString(6, p.getDireccion());
            ps.executeUpdate();
        }
    }

    private void insertarTratamiento(Connection conn, Tratamiento t) throws SQLException {
        String sql = "INSERT IGNORE INTO " + TABLE_TRATAMIENTO + " (id_tratamiento, dni_paciente, fecha_inicio, dias_tratamiento, diagnostico) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, t.getIdTratamiento());
            ps.setString(2, t.getDniPaciente());
            ps.setString(3, t.getFechaInicio());
            ps.setInt(4, t.getDiasTratamiento());
            ps.setString(5, t.getDiagnostico());
            ps.executeUpdate();
        }
    }

    private void insertarMedicamentoTratamiento(Connection conn, Tratamiento t, Medicamento m) throws SQLException {
        String sql = "INSERT INTO " + TABLE_MEDICAMENTO_TRATAMIENTO + " (id_registro, id_tratamiento, codigo_medicamento) VALUES (?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
//            ps.setInt(1, mt.getIdPedido());
            ps.setInt(2, t.getIdTratamiento());
            ps.setString(3, m.getIdMedicamento());
            ps.executeUpdate();
        }
    }

    // --- Métodos auxiliares PARSER---

    private Medicamento mapRowMedicamento(ResultSet rs) throws SQLException {
        return new Medicamento(rs.getString("codigo"),
            rs.getString("nombre"),
            rs.getString("posologia"));
    }

    private Paciente mapRowPaciente(ResultSet rs) throws SQLException {
        return new Paciente(rs.getString("dni"),
            rs.getString("nombre"),
            rs.getString("apellidos"),
            rs.getString("email"),
            rs.getString("telefono"),
            rs.getString("direccion"));
    }

}
