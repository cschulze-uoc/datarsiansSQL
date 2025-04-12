package datarsians.factory;

import datarsians.datos.ArticuloDAOImpl;
import datarsians.datos.BBDD_Util;
import datarsians.datos.ClienteDAOImpl;
import datarsians.DAO.ArticuloDAO;
import datarsians.DAO.ClienteDAO;
import datarsians.DAO.PedidoDAO;
import datarsians.datos.PedidoDAOImpl;


import java.sql.Connection;

public class MySQLDAOFactory extends DAOFactory {
    private final Connection conn;

    public MySQLDAOFactory() {
        this.conn = BBDD_Util.getConnection();
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
}
