package datarsians.DAO.Hibernate;

import datarsians.DAO.interfaz.PedidoDAO;
import datarsians.modelo.Pedido;
import datarsians.utils.JPAUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;

import java.sql.SQLException;
import java.util.List;

public class PedidoDAOHibernate implements PedidoDAO {
    @Override
    public void insertar(Pedido pedido) throws SQLException {
        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();
            em.persist(pedido);
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            throw new SQLException("Error al insertar pedido", e);
        } finally {
            em.close();
        }
    }

    @Override
    public Pedido buscarPorNumero(int numero) throws SQLException {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            return em.find(Pedido.class, numero);
        } catch (Exception e) {
            throw new SQLException("Error al buscar pedido", e);
        } finally {
            em.close();
        }
    }

    @Override
    public List<Pedido> obtenerTodos() throws SQLException {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            TypedQuery<Pedido> query = em.createQuery("FROM Pedido", Pedido.class);
            return query.getResultList();
        } catch (Exception e) {
            throw new SQLException("Error al listar pedidos", e);
        } finally {
            em.close();
        }
    }

    @Override
    public void eliminar(int numero) throws SQLException {
        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();

        try {
            Pedido pedido = em.find(Pedido.class, numero);
            if (pedido == null) {
                throw new SQLException("No existe pedido con número " + numero);
            }

            tx.begin();
            em.remove(pedido);
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            throw new SQLException("Error al eliminar pedido", e);
        } finally {
            em.close();
        }
    }
}
