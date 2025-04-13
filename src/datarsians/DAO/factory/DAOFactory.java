package datarsians.DAO.factory;

import datarsians.DAO.interfaz.ArticuloDAO;
import datarsians.DAO.interfaz.ClienteDAO;
import datarsians.DAO.interfaz.PedidoDAO;

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

