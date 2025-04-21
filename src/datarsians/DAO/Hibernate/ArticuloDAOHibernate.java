package datarsians.DAO.Hibernate;

import datarsians.DAO.interfaz.ArticuloDAO;
import datarsians.modelo.Articulo;
import datarsians.utils.JPAUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;

import java.sql.SQLException;
import java.util.List;

public class ArticuloDAOHibernate implements ArticuloDAO {

    @Override
    public void insertar(Articulo articulo) throws SQLException {
        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();
            em.persist(articulo);
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            throw new SQLException("Error al insertar artículo", e);
        } finally {
            em.close();
        }
    }

    @Override
    public Articulo buscarPorCodigo(String codigo) throws SQLException {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            return em.find(Articulo.class, codigo);
        } catch (Exception e) {
            throw new SQLException("Error al buscar artículo", e);
        } finally {
            em.close();
        }
    }

    @Override
    public List<Articulo> obtenerTodos() throws SQLException {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            TypedQuery<Articulo> query = em.createQuery("FROM Articulo", Articulo.class);
            return query.getResultList();
        } catch (Exception e) {
            throw new SQLException("Error al obtener artículos", e);
        } finally {
            em.close();
        }
    }

    @Override
    public void actualizarArticulo(Articulo articulo) throws SQLException {
        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();
            em.merge(articulo); // Hibernate actualizará si existe
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            throw new SQLException("Error al actualizar artículo", e);
        } finally {
            em.close();
        }
    }

    @Override
    public boolean existeArticuloPorCodigo(String codigo) {
        try {
            return buscarPorCodigo(codigo) != null;
        } catch (SQLException e) {
            return false;
        }
    }
}
