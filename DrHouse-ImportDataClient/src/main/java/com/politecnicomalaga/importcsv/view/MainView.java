package com.politecnicomalaga.importcsv.view;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.politecnicomalaga.importcsv.controller.ImportController;
import com.politecnicomalaga.importcsv.dataservice.CSVParser;
import com.politecnicomalaga.importcsv.model.Paciente;

import java.util.List;
import java.util.Scanner;

public class MainView {

    private final ImportController controller = new ImportController();
    private final Scanner scanner = new Scanner(System.in);

    public void mostrar() {
        System.out.println("=== Cliente importación de datos ===");
//        System.out.print("Ruta del fichero CSV: ");
//        String ruta = scanner.nextLine().trim();

        System.out.println("Importando datos...");
//        controller.importar(ruta);
        controller.importar("../data/data.csv");
        System.out.println("Proceso finalizado.");
    }
}
