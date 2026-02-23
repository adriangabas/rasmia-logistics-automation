package com.rasmia.servlet;

import com.rasmia.dao.ProductoDao;
import com.rasmia.db.Database;
import com.rasmia.model.Producto;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.Connection;
import java.util.List;

@WebServlet("/checkout")
public class CheckoutServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try (Connection con = Database.getConnection()) {
            ProductoDao productoDao = new ProductoDao();
            List<Producto> productos = productoDao.findAllActivos(con);
            req.setAttribute("productos", productos);
            req.getRequestDispatcher("/checkout.jsp").forward(req, resp);
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }
}

