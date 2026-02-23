package com.rasmia.servlet;

import com.rasmia.util.PdfStorageUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@WebServlet("/download-pdf")
public class DownloadPdfServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String pedidoId = req.getParameter("pedidoId");
        String type = req.getParameter("type"); // "albaran" o "etiquetas"

        if (pedidoId == null || pedidoId.isBlank()) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Falta pedidoId");
            return;
        }

        if (type == null || type.isBlank()) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Falta type (albaran/etiquetas)");
            return;
        }

        Path pdfPath;
        String fileName;

        if ("albaran".equalsIgnoreCase(type)) {
            pdfPath = PdfStorageUtil.albaranPath(getServletContext(), Integer.parseInt(pedidoId));
            fileName = "albaran-" + pedidoId + ".pdf";
        } else if ("etiquetas".equalsIgnoreCase(type)) {
            pdfPath = PdfStorageUtil.etiquetasPath(getServletContext(), Integer.parseInt(pedidoId));
            fileName = "etiquetas-" + pedidoId + ".pdf";
        } else {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "type inválido: " + type);
            return;
        }

        if (pdfPath == null || !Files.exists(pdfPath)) {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND,
                    "No existe el PDF (" + type + ") para el pedido " + pedidoId);
            return;
        }

        resp.setContentType("application/pdf");
        resp.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
        resp.setContentLengthLong(Files.size(pdfPath));

        Files.copy(pdfPath, resp.getOutputStream());
        resp.getOutputStream().flush();
    }
}
