package com.politecnicomalaga.clinica.view;

import com.politecnicomalaga.clinica.controller.Controller;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ListMedicamentosXTratamiento extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException {
        String dni = req.getParameter("dni");
        String tratamiendo_id = req.getParameter("tratamiendo_id");
        ServletUtils.writeJson(res, new Controller().listMedicamentosXTratamiento(dni, tratamiendo_id));
    }
}
