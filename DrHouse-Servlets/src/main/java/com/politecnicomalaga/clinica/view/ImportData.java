package com.politecnicomalaga.clinica.view;

import com.politecnicomalaga.clinica.controller.Controller;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;

public class ImportData extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException {
        BufferedReader br = req.getReader();

        String linea;
        StringBuilder sb = new StringBuilder();

        while((linea = br.readLine()) != null){
            sb.append(linea);
        }

        String json = sb.toString();
        try {
            ServletUtils.writeJson(res, new Controller().importData(json));
        } catch (IllegalArgumentException e) {
            res.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            ServletUtils.writeJson(res, "{\"error\":\"" + e.getMessage() + "\"}");
        }
    }
}
