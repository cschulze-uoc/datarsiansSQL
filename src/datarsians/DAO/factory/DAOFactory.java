package datarsians.DAO.factory;

import datarsians.DAO.ArticuloDAO;
import datarsians.DAO.ClienteDAO;
import datarsians.DAO.PedidoDAO;

import java.sql.Connection;

public abstract class DAOFactory {
    public abstract ClienteDAO getClienteDAO();
    public abstract ArticuloDAO getArticuloDAO();
    public abstract PedidoDAO getPedidoDAO();
    public abstract Connection getConexion();

    public static DAOFactory getDAOFactory() {
        return new MySQLDAOFactory();
    }
}

