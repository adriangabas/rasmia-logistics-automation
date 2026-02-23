package com.rasmia.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class PedidoDao {

    private static final String SQL_INSERT = """
        INSERT INTO pedidos (cliente_id, estado, total)
        VALUES (?, 'pagado', 0)
    """;

    private static final String SQL_UPDATE_RUTAS = """
        UPDATE pedidos
        SET ruta_albaran = ?, ruta_etiquetas = ?
        WHERE id = ?
    """;

    public int insert(Connection con, int clienteId) throws Exception {
        PreparedStatement ps = con.prepareStatement(
                SQL_INSERT,
                Statement.RETURN_GENERATED_KEYS
        );

        ps.setInt(1, clienteId);
        ps.executeUpdate();

        ResultSet rs = ps.getGeneratedKeys();
        if (!rs.next()) {
            throw new RuntimeException("No se pudo obtener el ID del pedido");
        }

        int pedidoId = rs.getInt(1);
        rs.close();
        ps.close();

        return pedidoId;
    }

    public void updateRutas(Connection con, int pedidoId, String rutaAlbaran, String rutaEtiquetas)
            throws Exception {

        PreparedStatement ps = con.prepareStatement(SQL_UPDATE_RUTAS);
        ps.setString(1, rutaAlbaran);
        ps.setString(2, rutaEtiquetas);
        ps.setInt(3, pedidoId);
        ps.executeUpdate();
        ps.close();
    }
    public String getRutaPdf(Connection con, int pedidoId, String type) throws Exception {

        String col = "albaran".equals(type) ? "ruta_albaran" : "ruta_etiquetas";
        String sql = "SELECT " + col + " FROM pedidos WHERE id = ?";

        PreparedStatement ps = con.prepareStatement(sql);
        ps.setInt(1, pedidoId);
        ResultSet rs = ps.executeQuery();

        if (!rs.next()) {
            rs.close();
            ps.close();
            return null;
        }

        String ruta = rs.getString(1);
        rs.close();
        ps.close();
        return ruta;
    }

}
