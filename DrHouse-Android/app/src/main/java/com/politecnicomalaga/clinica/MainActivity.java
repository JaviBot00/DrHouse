package com.politecnicomalaga.clinica;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.politecnicomalaga.clinica.controller.MedicamentoRVAdapter;
import com.politecnicomalaga.clinica.controller.PacienteRVAdapter;
import com.politecnicomalaga.clinica.model.Medicamento;
import com.politecnicomalaga.clinica.model.Paciente;

import java.io.IOException;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class MainActivity extends AppCompatActivity {

    private static final String BASE_URL = "http://192.168.1.139:8888/clinicaServlets";
    private static final String URL_PACIENTE = BASE_URL + "/find-paciente";
    private static final String URL_MEDICAMENTOS = BASE_URL + "/list-medicamentos";
    private final OkHttpClient httpClient = new OkHttpClient();
    private final Gson gson = new Gson();
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextInputLayout txtInputDni = findViewById(R.id.txtInputDni);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        findViewById(R.id.btnPaciente).setOnClickListener(v -> cargarPaciente(txtInputDni.getEditText().getText().toString()));
        findViewById(R.id.btnMedicamento).setOnClickListener(v -> cargarMedicamentos());

    }

    // --- Clientes ---

    private void cargarPaciente(String dni) {
        new Thread(() -> {
            try {
                Request request = new Request.Builder().url(URL_PACIENTE + "?dni=" + dni).get().build();
                Response response = httpClient.newCall(request).execute();
                String json = response.body().string();
                Log.d("RESPUESTA", json); // ver en Logcat qué devuelve el servidor

                List<Paciente> paciente = gson.fromJson(json, new TypeToken<List<Paciente>>() {
                }.getType());

                runOnUiThread(() -> {
                    recyclerView.setAdapter(new PacienteRVAdapter(paciente));
                });

            } catch (IOException e) {
                runOnUiThread(() ->
                    Toast.makeText(this, "Error al cargar paciente", Toast.LENGTH_SHORT).show()
                );
            }
        }).start();
    }

    // --- Medicamentos ---

    private void cargarMedicamentos() {
        new Thread(() -> {
            try {
                Request request = new Request.Builder().url(URL_MEDICAMENTOS).get().build();
                Response response = httpClient.newCall(request).execute();
                String json = response.body().string();

                List<Medicamento> medicamentos = gson.fromJson(json, new TypeToken<List<Medicamento>>() {
                }.getType());

                runOnUiThread(() -> {
                    recyclerView.setAdapter(new MedicamentoRVAdapter(medicamentos));
                });

            } catch (IOException e) {
                runOnUiThread(() ->
                    Toast.makeText(this, "Error al cargar medicamentos", Toast.LENGTH_SHORT).show()
                );
            }
        }).start();
    }
}
