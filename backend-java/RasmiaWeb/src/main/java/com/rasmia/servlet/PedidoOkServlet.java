package com.rasmia.servlet;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/pedido-ok")
public class PedidoOkServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String pedidoId = req.getParameter("pedidoId");
        req.setAttribute("pedidoId", pedidoId);

        req.getRequestDispatcher("/pedido_ok.jsp").forward(req, resp);
    }
}
