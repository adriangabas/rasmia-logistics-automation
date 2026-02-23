package com.rasmia.dao;

import com.rasmia.model.PedidoLinea;
import com.rasmia.model.Producto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.List;

public class PedidoLineaDao {

    private static final String SQL_INSERT = """
        INSERT INTO pedido_lineas
        (pedido_id, producto_id, codigo, descripcion, cantidad, precio, unidad)
        VALUES (?, ?, ?, ?, ?, ?, ?)
    """;

    public void insertAll(Connection con, int pedidoId, List<PedidoLinea> lineas) throws Exception {

        ProductoDao productoDao = new ProductoDao();
        PreparedStatement ps = con.prepareStatement(SQL_INSERT);

        for (PedidoLinea l : lineas) {

            Producto p = productoDao.findById(con, l.getProductoId());
            if (p == null || !p.isActivo()) continue;

            ps.setInt(1, pedidoId);
            ps.setInt(2, p.getId());
            ps.setString(3, p.getCodigo());
            ps.setString(4, p.getNombre());
            ps.setInt(5, l.getCantidad());
            ps.setBigDecimal(6, p.getPrecio());
            ps.setString(7, "Pcs.");

            ps.executeUpdate();
        }

        ps.close();
    }
}
