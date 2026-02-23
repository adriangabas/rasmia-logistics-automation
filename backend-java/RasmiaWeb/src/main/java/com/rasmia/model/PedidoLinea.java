package com.rasmia.model;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

public class PedidoLinea {

    private int productoId;
    private int cantidad;

    public int getProductoId() {
        return productoId;
    }

    public void setProductoId(int productoId) {
        this.productoId = productoId;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    // ✅ Este método DEBE devolver List<PedidoLinea>
    public static List<PedidoLinea> fromRequest(HttpServletRequest req) {

        List<PedidoLinea> lineas = new ArrayList<>();

        Enumeration<String> names = req.getParameterNames();
        while (names.hasMoreElements()) {
            String name = names.nextElement();

            // inputs tipo qty_3, qty_7, etc
            if (!name.startsWith("qty_")) continue;

            try {
                int productoId = Integer.parseInt(name.substring(4));
                int qty = Integer.parseInt(req.getParameter(name));

                if (qty <= 0) continue;

                PedidoLinea l = new PedidoLinea();
                l.setProductoId(productoId);
                l.setCantidad(qty);

                lineas.add(l);

            } catch (Exception ignored) {}
        }

        return lineas;
    }
}
