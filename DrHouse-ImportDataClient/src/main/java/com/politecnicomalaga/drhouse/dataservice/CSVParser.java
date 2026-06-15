package com.politecnicomalaga.drhouse.dataservice;

import com.politecnicomalaga.drhouse.model.Medicamento;
import com.politecnicomalaga.drhouse.model.Paciente;
import com.politecnicomalaga.drhouse.model.Tratamiento;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

// ============================================================
//  ESTRUCTURA DEL CSV (separador: ";")
// ============================================================
//
//  Tipo 1 — Paciente (una línea por paciente)
//  Paciente;DNI;Nombre;Apellidos;Email;Telefono;Direccion
//
//  Tipo 2 — Tratamiento (vinculado al paciente por DNI)
//  Tratamiento;idTratamiento;DNI;Fecha;DuracionDias;Diagnostico
//
//  Tipo 3 — Medicamento (catálogo independiente)
//  Medicamento;idMedicamento;Nombre;Posologia
//
//  Tipo 4 — MedicamentoXTratamiento (relación N:M)
//  MedicamentoXTratamiento;idTratamiento;idMedicamento
//
//  Las secciones están separadas por líneas en blanco,
//  pero la lógica se basa en campos[0], no en posición.
// ============================================================

public class CSVParser {

    // ── Almacenamiento principal ──────────────────────────────────────────────
    private final Map<String, Paciente>     pacientes     = new LinkedHashMap<>();
    private final Map<Integer, Tratamiento> tratamientos  = new LinkedHashMap<>();
    private final Map<String, Medicamento>  medicamentos  = new LinkedHashMap<>();

    public List<Paciente> parsear(String rutaFichero) throws IOException {

        BufferedReader br = new BufferedReader(
            new InputStreamReader(new FileInputStream(rutaFichero), StandardCharsets.UTF_8)
        );

        String linea;

        while ((linea = br.readLine()) != null) {

            // Limpiar \r residual de ficheros Windows (CRLF)
            linea = linea.trim();

            // Ignorar líneas vacías (separadores de sección)
            if (linea.isEmpty()) continue;

            String[] campos = linea.split(";");

            switch (campos[0].trim()) {

                // ── TIPO 1: PACIENTE ──────────────────────────────────────
                case "Paciente": {
                    String dni       = campos[1].trim();
                    String nombre    = campos[2].trim();
                    String apellidos = campos[3].trim();
                    String email     = campos[4].trim();
                    String telefono  = campos[5].trim();
                    String direccion = campos[6].trim();

                    // computeIfAbsent: si ya existe el DNI, no lo sobreescribe
                    pacientes.computeIfAbsent(
                        dni, d -> new Paciente(d, nombre, apellidos, email, telefono, direccion)
                    );
                    break;
                }

                // ── TIPO 2: TRATAMIENTO ───────────────────────────────────
                case "Tratamiento": {
                    int    idTratamiento  = Integer.parseInt(campos[1].trim());
                    String dniPaciente    = campos[2].trim();
                    String fecha          = campos[3].trim();
                    int    duracionDias   = Integer.parseInt(campos[4].trim());
                    String diagnostico    = campos[5].trim();

                    Tratamiento t = new Tratamiento(idTratamiento, dniPaciente, fecha, duracionDias, diagnostico);
                    tratamientos.put(idTratamiento, t);

                    // Vincular al paciente correspondiente
                    Paciente p = pacientes.get(dniPaciente);
                    if (p != null) p.addTratamiento(t);
                    break;
                }

                // ── TIPO 3: MEDICAMENTO (catálogo) ────────────────────────
                case "Medicamento": {
                    String idMedicamento = campos[1].trim();
                    String nombre        = campos[2].trim();
                    String posologia     = campos[3].trim();

                    medicamentos.computeIfAbsent(
                        idMedicamento, id -> new Medicamento(id, nombre, posologia)
                    );
                    break;
                }

                // ── TIPO 4: RELACIÓN N:M — MedicamentoXTratamiento ────────
                case "MedicamentoXTratamiento": {
                    int    idTratamiento = Integer.parseInt(campos[1].trim());
                    String idMedicamento = campos[2].trim();

                    Tratamiento t = tratamientos.get(idTratamiento);
                    Medicamento m = medicamentos.get(idMedicamento);

                    // Solo añadimos si ambos extremos de la relación existen
                    if (t != null && m != null) {
                        t.addMedicamento(m);
                    }
                    break;
                }

                default:
                    // Línea desconocida: ignorar o loggear
                    System.err.println("Línea no reconocida: " + linea);
                    break;
            }
        }

        br.close();
        return new ArrayList<>(pacientes.values());
    }

    // ── Getters auxiliares ────────────────────────────────────────────────────
    public Map<String, Medicamento>  getMedicamentos()  { return medicamentos; }

}
