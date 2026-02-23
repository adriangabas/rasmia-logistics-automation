package com.rasmia.servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/generar-pdf")
public class GenerarPdfServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.sendError(HttpServletResponse.SC_GONE,
                "Este endpoint ya no se usa. Usa /download-pdf (los PDFs se generan al crear el pedido).");
    }
}
