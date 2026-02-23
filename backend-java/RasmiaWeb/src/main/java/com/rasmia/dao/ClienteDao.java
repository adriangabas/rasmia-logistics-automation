package com.rasmia.dao;

import com.rasmia.model.Cliente;

import java.sql.*;

public class ClienteDao {

    public int insert(Connection con, Cliente c) throws Exception {

        String sql = """
            INSERT INTO clientes
            (nombre, apellidos, empresa, telefono, email, direccion, cp, ciudad, provincia, pais)
            VALUES (?,?,?,?,?,?,?,?,?,?)
        """;

        try (PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, c.getNombre());
            ps.setString(2, c.getApellidos());
            ps.setString(3, c.getEmpresa());
            ps.setString(4, c.getTelefono());
            ps.setString(5, c.getEmail());
            ps.setString(6, c.getDireccion());
            ps.setString(7, c.getCp());
            ps.setString(8, c.getCiudad());
            ps.setString(9, c.getProvincia());
            ps.setString(10, c.getPais() == null ? "España" : c.getPais());

            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                rs.next();
                return rs.getInt(1);
            }
        }
    }
}
