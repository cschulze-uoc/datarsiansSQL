package datarsians.DAO.MySQL;

import datarsians.DAO.interfaz.ClienteDAO;
import datarsians.excepciones.EmailDuplicado;
import datarsians.excepciones.EmailNoValido;
import datarsians.excepciones.NifDuplicado;
import datarsians.modelo.Cliente;
import datarsians.modelo.ClienteEstandar;
import datarsians.modelo.ClientePremium;
import datarsians.utils.Validations;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClienteDAOImpl implements ClienteDAO {
    private final Connection conn;

    public ClienteDAOImpl(Connection conn) {
        this.conn = conn;
    }

    @Override
    public void insertar(Cliente cliente) throws EmailDuplicado, EmailNoValido {
        String email = cliente.getEmail();
        String nif = cliente.getNif();

        if (!Validations.esEmailValido(email)) {
            throw new EmailNoValido("El email no es válido.");
        }

        try {
            if (buscarPorEmail(email) != null) {
                throw new EmailDuplicado("Error: El email ya está registrado.");
            }
            if (buscarPorNif(nif) != null) {
                throw new NifDuplicado("Error: El NIF ya está registrado.");
            }

            String sql = "INSERT INTO Cliente (email, nombre, domicilio, nif, tipo) VALUES (?, ?, ?, ?, ?)";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, cliente.getEmail());
                stmt.setString(2, cliente.getNombre());
                stmt.setString(3, cliente.getDomicilio());
                stmt.setString(4, cliente.getNif());
                stmt.setString(5, cliente instanceof ClientePremium ? "PREMIUM" : "ESTANDAR");
                stmt.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error al agregar cliente en base de datos.");
        }
    }

    @Override
    public Cliente buscarPorEmail(String email) throws SQLException {
        if (email == null || email.isBlank()) return null;
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

    // Aquí utilizamos una Transaction para actualizar un conjunto de clientes de una vez. Si falla alguno, no se aplica ningún cambio.
    @Override
    public void actualizarClientes(List<Cliente> clientes) throws SQLException {
        String sql = "UPDATE Cliente SET nombre = ?, domicilio = ?, nif = ?, tipo = ? WHERE email = ?";

        try {
            conn.setAutoCommit(false); // 🔒 Inicia la transacción

            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                for (Cliente cliente : clientes) {
                    stmt.setString(1, cliente.getNombre());
                    stmt.setString(2, cliente.getDomicilio());
                    stmt.setString(3, cliente.getNif());
                    stmt.setString(4, cliente instanceof ClientePremium ? "PREMIUM" : "ESTANDAR");
                    stmt.setString(5, cliente.getEmail());

                    stmt.executeUpdate();
                }
            }

            conn.commit(); // Confirma todos los cambios

        } catch (SQLException ex) {
            conn.rollback(); // Revierte todo si algo falla
            throw new SQLException("Error al actualizar clientes. Se ha hecho rollback.", ex);

        } finally {
            conn.setAutoCommit(true); // Vuelve a activar el autocommit
        }
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

    @Override
    public Cliente buscarPorNif(String nif) throws SQLException {
        if (nif == null || nif.isBlank()) return null;
        String sql = "SELECT * FROM Cliente WHERE nif = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, nif);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return construirClienteDesdeResultSet(rs);
                }
            }
        }
        return null;
    }

}