package com.politecnicomalaga.drhouse.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.politecnicomalaga.drhouse.dataservice.CSVParser;
import com.politecnicomalaga.drhouse.model.Medicamento;
import com.politecnicomalaga.drhouse.model.Paciente;
import com.politecnicomalaga.drhouse.model.Tratamiento;
import okhttp3.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ImportController {

    private static final String BASE_URL   = "http://127.0.0.1:8888/clinicaServlets";
    private static final String ENDPOINT   = BASE_URL + "/import-data";
    private final OkHttpClient  httpClient = new OkHttpClient();
    private static final MediaType JSON    = MediaType.parse("application/json");

    private final CSVParser parser = new CSVParser();
    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public void importar(String rutaCSV) {
        try {
            List<Paciente> pacientes        = parser.parsear(rutaCSV);
            Map<String, Medicamento> medicamentos = parser.getMedicamentos();
            Map<Integer, Tratamiento> tratamientos = parser.getTratamientos();

            Map<String, Object> payload = new HashMap<>();
            payload.put("pacientes",  pacientes);
            payload.put("medicamentos", medicamentos.values());
            payload.put("tratamientos", tratamientos.values());

            String json = gson.toJson(payload);
            System.out.println("JSON generado:\n" + json);

            enviar(json);

        } catch (IOException e) {
            System.err.println("Error al importar: " + e.getMessage());
        }
    }

    private void enviar(String json) throws IOException {
        RequestBody body = RequestBody.create(json, JSON);

        Request request = new Request.Builder()
            .url(ENDPOINT)
            .post(body)
            .build();

        Response response = httpClient.newCall(request).execute();
        System.out.println("Respuesta del servidor: " + response.code());
        response.close();
    }
}
