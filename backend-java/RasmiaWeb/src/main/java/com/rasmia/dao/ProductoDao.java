package com.rasmia.dao;

import com.rasmia.model.PedidoLinea;
import com.rasmia.model.Producto;

import java.sql.*;
import java.util.*;
import java.util.stream.Collectors;

public class ProductoDao {

    public List<Producto> findAllActivos(Connection con) throws SQLException {
        List<Producto> lista = new ArrayList<>();

        String sql = """
            SELECT id, codigo, nombre, descripcion, precio, activo
            FROM productos
            WHERE activo = 1
        """;

        try (PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Producto p = new Producto();
                p.setId(rs.getInt("id"));
                p.setCodigo(rs.getString("codigo"));
                p.setNombre(rs.getString("nombre"));
                p.setDescripcion(rs.getString("descripcion"));
                p.setPrecio(rs.getBigDecimal("precio"));
                p.setActivo(rs.getBoolean("activo"));
                lista.add(p);
            }
        }

        return lista;
    }

    public Producto findById(Connection con, int id) throws SQLException {
        String sql = """
            SELECT id, codigo, nombre, descripcion, precio, activo
            FROM productos
            WHERE id = ?
        """;

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) return null;

                Producto p = new Producto();
                p.setId(rs.getInt("id"));
                p.setCodigo(rs.getString("codigo"));
                p.setNombre(rs.getString("nombre"));
                p.setDescripcion(rs.getString("descripcion"));
                p.setPrecio(rs.getBigDecimal("precio"));
                p.setActivo(rs.getBoolean("activo"));
                return p;
            }
        }
    }

    /**
     * Devuelve Map<idProducto, Producto> leyendo los ids desde PedidoLinea.productoId
     * para poder rellenar codigo/descripcion en el JSON a n8n.
     */
    public Map<Integer, Producto> findByIdsMap(Connection con, List<PedidoLinea> lineas) throws SQLException {

        if (lineas == null || lineas.isEmpty()) return Collections.emptyMap();

        Set<Integer> ids = lineas.stream()
                .map(PedidoLinea::getProductoId)
                .filter(id -> id > 0)
                .collect(Collectors.toCollection(LinkedHashSet::new));

        if (ids.isEmpty()) return Collections.emptyMap();

        String placeholders = ids.stream().map(x -> "?").collect(Collectors.joining(","));

        String sql = "SELECT id, codigo, nombre, descripcion, precio, activo " +
                "FROM productos " +
                "WHERE id IN (" + placeholders + ")";


        Map<Integer, Producto> map = new HashMap<>();

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            int i = 1;
            for (Integer id : ids) ps.setInt(i++, id);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Producto p = new Producto();
                    p.setId(rs.getInt("id"));
                    p.setCodigo(rs.getString("codigo"));
                    p.setNombre(rs.getString("nombre"));
                    p.setDescripcion(rs.getString("descripcion"));
                    p.setPrecio(rs.getBigDecimal("precio"));
                    p.setActivo(rs.getBoolean("activo"));
                    map.put(p.getId(), p);
                }
            }
        }

        return map;
    }
}
