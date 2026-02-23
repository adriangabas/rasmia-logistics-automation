package com.rasmia.servlet;

import com.rasmia.dao.*;
import com.rasmia.db.Database;
import com.rasmia.model.*;
import com.rasmia.service.N8nService;
import com.rasmia.util.JsonUtil;
import com.rasmia.util.PdfStorageUtil;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import javax.servlet.ServletException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Connection;
import java.util.List;
import java.util.Map;

@WebServlet("/crear-pedido")
public class CrearPedidoServlet extends HttpServlet {

    private final N8nService n8nService = new N8nService();

    private static final String N8N_ETIQUETAS_URL = "http://127.0.0.1:5678/webhook/etiquetas-pdf";
    private static final String N8N_ALBARAN_URL   = "http://127.0.0.1:5678/webhook/albaran-pdf";

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        try (Connection con = Database.getConnection()) {

            // 1) Cliente
            Cliente cliente = Cliente.fromRequest(req);
            ClienteDao clienteDao = new ClienteDao();
            int clienteId = clienteDao.insert(con, cliente);
            cliente.setId(clienteId); // IMPORTANTÍSIMO para numCliente en JSON

            // 2) Pedido
            PedidoDao pedidoDao = new PedidoDao();
            int pedidoId = pedidoDao.insert(con, clienteId);

            // 3) Lineas
            List<PedidoLinea> lineas = PedidoLinea.fromRequest(req);
            PedidoLineaDao lineaDao = new PedidoLineaDao();
            lineaDao.insertAll(con, pedidoId, lineas);

            // 4) Productos (para enriquecer líneas)
            ProductoDao productoDao = new ProductoDao();
            Map<Integer, Producto> productosById = productoDao.findByIdsMap(con, lineas);

            // 5) JSON “compatible n8n”
            String jsonPedido = JsonUtil.pedidoToJson(pedidoId, cliente, lineas, productosById);

            // 6) Generar y guardar PDFs
            generarYGuardarDocumentos(pedidoId, jsonPedido);

            resp.sendRedirect(req.getContextPath() + "/pedido-ok?pedidoId=" + pedidoId);

        } catch (Exception e) {
            throw new ServletException("Error creando pedido y generando PDFs", e);
        }
    }

    private void generarYGuardarDocumentos(int pedidoId, String jsonPedido) throws Exception {

        byte[] etiquetasPdf = n8nService.generarPdfBytes(N8N_ETIQUETAS_URL, jsonPedido);
        Path etiquetasPath = PdfStorageUtil.etiquetasPath(getServletContext(), pedidoId);
        Files.createDirectories(etiquetasPath.getParent());
        Files.write(etiquetasPath, etiquetasPdf);

        byte[] albaranPdf = n8nService.generarPdfBytes(N8N_ALBARAN_URL, jsonPedido);
        Path albaranPath = PdfStorageUtil.albaranPath(getServletContext(), pedidoId);
        Files.createDirectories(albaranPath.getParent());
        Files.write(albaranPath, albaranPdf);
    }
}
