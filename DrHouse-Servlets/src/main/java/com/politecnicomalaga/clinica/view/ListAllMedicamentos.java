package com.politecnicomalaga.clinica.view;

import com.politecnicomalaga.clinica.controller.Controller;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ListAllMedicamentos extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException {
        ServletUtils.writeJson(res, new Controller().listAllMedicamentos());
    }
}
