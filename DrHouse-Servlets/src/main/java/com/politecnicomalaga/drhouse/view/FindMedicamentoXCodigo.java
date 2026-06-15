package com.politecnicomalaga.drhouse.view;

import com.politecnicomalaga.drhouse.controller.Controller;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class FindMedicamentoXCodigo extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException {
        String code = req.getParameter("code");
        ServletUtils.writeJson(res, (new Controller()).findMedicamentoXCodigo(code));
    }
}
