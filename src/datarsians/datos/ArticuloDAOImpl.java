package datarsians.datos;

import datarsians.DAO.ArticuloDAO;
import datarsians.modelo.Articulo;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ArticuloDAOImpl implements ArticuloDAO {
    private Connection conn;

    public ArticuloDAOImpl(Connection conn) {
        this.conn = conn;
    }

    @Override
    public void insertar(Articulo articulo) throws SQLException {
        String sql = "INSERT INTO Articulo VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, articulo.getCodigo());
            stmt.setString(2, articulo.getDescripcion());
            stmt.setDouble(3, articulo.getPrecioVenta());
            stmt.setDouble(4, articulo.getGastosEnvio());
            stmt.setInt(5, articulo.getTiempoPreparacion());
            stmt.executeUpdate();
        }
    }

    @Override
    public Articulo buscarPorCodigo(String codigo) throws SQLException {
        String sql = "SELECT * FROM Articulo WHERE codigo = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, codigo);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Articulo(
                            rs.getString("codigo"),
                            rs.getString("descripcion"),
                            rs.getDouble("precio_venta"),
                            rs.getDouble("gastos_envio"),
                            rs.getInt("tiempo_preparacion")
                    );
                }
            }
        }
        return null;
    }

    @Override
    public List<Articulo> obtenerTodos() throws SQLException {
        List<Articulo> articulos = new ArrayList<>();
        String sql = "SELECT * FROM Articulo";
        try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                articulos.add(new Articulo(
                        rs.getString("codigo"),
                        rs.getString("descripcion"),
                        rs.getDouble("precio_venta"),
                        rs.getDouble("gastos_envio"),
                        rs.getInt("tiempo_preparacion")
                ));
            }
        }
        return articulos;
    }
}
