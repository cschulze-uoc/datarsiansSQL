package datarsians.datos;

import datarsians.DAO.ClienteDAO;
import datarsians.modelo.Cliente;
import datarsians.modelo.ClienteEstandar;
import datarsians.modelo.ClientePremium;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClienteDAOImpl implements ClienteDAO {
    private final Connection conn;

    public ClienteDAOImpl(Connection conn) {
        this.conn = conn;
    }

    @Override
    public void insertar(Cliente cliente) throws SQLException {
        String sql = "INSERT INTO Cliente (email, nombre, domicilio, nif, tipo) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, cliente.getEmail());
            stmt.setString(2, cliente.getNombre());
            stmt.setString(3, cliente.getDomicilio());
            stmt.setString(4, cliente.getNif());
            stmt.setString(5, cliente instanceof ClientePremium ? "PREMIUM" : "ESTANDAR");
            stmt.executeUpdate();
        }
    }

    @Override
    public Cliente buscarPorEmail(String email) throws SQLException {
        String sql = "SELECT * FROM Cliente WHERE email = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, email);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return construirClienteDesdeResultSet(rs);
                }
            }
        }
        return null;
    }

    @Override
    public List<Cliente> obtenerTodos() throws SQLException {
        List<Cliente> clientes = new ArrayList<>();
        String sql = "SELECT * FROM Cliente";
        try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                clientes.add(construirClienteDesdeResultSet(rs));
            }
        }
        return clientes;
    }

    private Cliente construirClienteDesdeResultSet(ResultSet rs) throws SQLException {
        String tipo = rs.getString("tipo");
        String nombre = rs.getString("nombre");
        String domicilio = rs.getString("domicilio");
        String nif = rs.getString("nif");
        String email = rs.getString("email");

        return "PREMIUM".equals(tipo)
                ? new ClientePremium(nombre, domicilio, nif, email)
                : new ClienteEstandar(nombre, domicilio, nif, email);
    }
}