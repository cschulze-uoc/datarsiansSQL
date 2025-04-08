package datarsians.datos;

import datarsians.DAO.ArticuloDAO;
import datarsians.DAO.ClienteDAO;
import datarsians.DAO.PedidoDAO;
import datarsians.modelo.Articulo;
import datarsians.modelo.Cliente;
import datarsians.modelo.Pedido;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class PedidoDAOImpl implements PedidoDAO {
    private final Connection conn;
    private final ClienteDAO clienteDAO;
    private final ArticuloDAO articuloDAO;

    public PedidoDAOImpl(Connection conn, ClienteDAO clienteDAO, ArticuloDAO articuloDAO) {
        this.conn = conn;
        this.clienteDAO = clienteDAO;
        this.articuloDAO = articuloDAO;
    }

    @Override
    public void insertar(Pedido pedido) throws SQLException {
        String sql = "{CALL sp_insertar_pedido(?, ?, ?)}";

        try (CallableStatement cs = conn.prepareCall(sql)) {
            cs.setString(1, pedido.getCliente().getEmail());
            cs.setString(2, pedido.getArticulo().getCodigo());
            cs.setInt(3, pedido.getCantidad());

            boolean hasResult = cs.execute();

            if (hasResult) {
                try (ResultSet rs = cs.getResultSet()) {
                    if (rs.next()) {
                        int numeroPedido = rs.getInt("numero_pedido");
                        pedido.setNumeroPedido(numeroPedido);
                    }
                }
            }

            pedido.setFechaHoraPedido(LocalDateTime.now());
        }
    }



    @Override
    public Pedido buscarPorNumero(int numero) throws SQLException {
        String sql = "SELECT * FROM Pedido WHERE numero_pedido = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, numero);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Cliente cliente = clienteDAO.buscarPorEmail(rs.getString("email_cliente"));
                    Articulo articulo = articuloDAO.buscarPorCodigo(rs.getString("codigo_articulo"));
                    Pedido pedido = new Pedido(cliente, articulo, rs.getInt("cantidad"));
                    pedido.setNumeroPedido(rs.getInt("numero_pedido"));
                    pedido.setFechaHoraPedido(rs.getTimestamp("fecha_pedido").toLocalDateTime());
                    return pedido;
                }
            }
        }
        return null;
    }

    @Override
    public List<Pedido> obtenerTodos() throws SQLException {
        List<Pedido> pedidos = new ArrayList<>();
        String sql = "SELECT * FROM Pedido";
        try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Cliente cliente = clienteDAO.buscarPorEmail(rs.getString("email_cliente"));
                Articulo articulo = articuloDAO.buscarPorCodigo(rs.getString("codigo_articulo"));
                Pedido pedido = new Pedido(cliente, articulo, rs.getInt("cantidad"));
                pedido.setNumeroPedido(rs.getInt("numero_pedido"));
                pedido.setFechaHoraPedido(rs.getTimestamp("fecha_pedido").toLocalDateTime());
                pedidos.add(pedido);
            }
        }
        return pedidos;
    }

    @Override
    public void eliminar(int numero) throws SQLException {
        String sql = "{CALL sp_eliminar_pedido(?)}";

        try (CallableStatement cs = conn.prepareCall(sql)) {
            cs.setInt(1, numero);
            cs.execute();
        } catch (SQLException e) {
            // Si el procedimiento lanza una excepción con SIGNAL, se captura aquí
            throw new SQLException("No se pudo eliminar el pedido: " + e.getMessage(), e);
        }
    }
}