package datarsians.DAO.factory;

import datarsians.DAO.MySQL.ArticuloDAOImpl;
import datarsians.utils.BBDD_Util;
import datarsians.DAO.MySQL.ClienteDAOImpl;
import datarsians.DAO.interfaz.ArticuloDAO;
import datarsians.DAO.interfaz.ClienteDAO;
import datarsians.DAO.interfaz.PedidoDAO;
import datarsians.DAO.MySQL.PedidoDAOImpl;


import java.sql.Connection;
import java.sql.SQLException;

public class MySQLDAOFactory extends DAOFactory {
    private final Connection conn;

    public MySQLDAOFactory() {
        try {
            this.conn = BBDD_Util.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error al obtener la conexión con la base de datos", e);
        }
    }

    @Override
    public ClienteDAO getClienteDAO() {
        return new ClienteDAOImpl(conn);
    }

    @Override
    public ArticuloDAO getArticuloDAO() {
        return new ArticuloDAOImpl(conn);
    }

    @Override
    public PedidoDAO getPedidoDAO() {
        ClienteDAO clienteDAO = getClienteDAO();
        ArticuloDAO articuloDAO = getArticuloDAO();
        return new PedidoDAOImpl(conn, clienteDAO, articuloDAO);
    }
    @Override
    public Connection getConexion(){
        return conn;
    }
}
