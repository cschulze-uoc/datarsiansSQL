package datarsians.factory;

import datarsians.DAO.ArticuloDAO;
import datarsians.DAO.ClienteDAO;
import datarsians.DAO.PedidoDAO;

public abstract class DAOFactory {
    public abstract ClienteDAO getClienteDAO();
    public abstract ArticuloDAO getArticuloDAO();
    public abstract PedidoDAO getPedidoDAO();

    public static DAOFactory getDAOFactory() {
        return new MySQLDAOFactory();
    }
}

