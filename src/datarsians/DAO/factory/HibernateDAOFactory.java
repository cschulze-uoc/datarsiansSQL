package datarsians.DAO.factory;

import datarsians.DAO.Hibernate.ArticuloDAOHibernate;
import datarsians.DAO.Hibernate.ClienteDAOHibernate;
import datarsians.DAO.Hibernate.PedidoDAOHibernate;
import datarsians.DAO.interfaz.ArticuloDAO;
import datarsians.DAO.interfaz.ClienteDAO;
import datarsians.DAO.interfaz.PedidoDAO;

import java.sql.Connection;

public class HibernateDAOFactory extends DAOFactory {
    @Override
    public ClienteDAO getClienteDAO() {
        return new ClienteDAOHibernate();
    }

    @Override
    public ArticuloDAO getArticuloDAO() {
        return new ArticuloDAOHibernate();
    }

    @Override
    public PedidoDAO getPedidoDAO() {
        return new PedidoDAOHibernate();
    }

    @Override
    public Connection getConexion() {
        return null; // Hibernate no usa conexión directa
    }
}
