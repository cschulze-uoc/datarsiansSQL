package datarsians.DAO.Hibernate;

import datarsians.DAO.interfaz.ClienteDAO;
import datarsians.excepciones.EmailDuplicado;
import datarsians.excepciones.EmailNoValido;
import datarsians.excepciones.NifDuplicado;
import datarsians.excepciones.NifNoValido;
import datarsians.modelo.Cliente;
import datarsians.utils.JPAUtil;
import datarsians.utils.Validations;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;

import java.sql.SQLException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ClienteDAOHibernate implements ClienteDAO {
    @Override
    public void insertar(Cliente cliente) throws EmailDuplicado, EmailNoValido {
        if (!Validations.esEmailValido(cliente.getEmail())) {
            throw new EmailNoValido("El email no es válido.");
        }

        if (existeClientePorEmail(cliente.getEmail())) {
            throw new EmailDuplicado("El email ya está registrado.");
        }

        if (!Validations.esNifValido(cliente.getNif())) {
            throw new NifNoValido("El NIF no es válido.");
        }

        if (existeClientePorNif(cliente.getNif())) {
            throw new NifDuplicado("El NIF ya está registrado.");
        }


        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();
            em.persist(cliente);
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            throw new RuntimeException("Error al insertar cliente", e);
        } finally {
            em.close();
        }
    }

    @Override
    public Cliente buscarPorEmail(String email) throws SQLException {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            return em.find(Cliente.class, email);
        } catch (Exception e) {
            throw new SQLException("Error al buscar cliente por email", e);
        } finally {
            em.close();
        }
    }

    @Override
    public Cliente buscarPorNif(String nif) throws SQLException {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            TypedQuery<Cliente> query = em.createQuery(
                    "SELECT c FROM Cliente c WHERE c.nif = :nif", Cliente.class);
            query.setParameter("nif", nif);

            List<Cliente> resultados = query.getResultList();
            if (resultados.isEmpty()) {
                return null;
            }
            return resultados.get(0);
        } catch (Exception e) {
            throw new SQLException("Error al buscar cliente por Nif", e);
        } finally {
            em.close();
        }
    }

    @Override
    public List<Cliente> obtenerTodos() throws SQLException {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            TypedQuery<Cliente> query = em.createQuery("FROM Cliente", Cliente.class);
            return query.getResultList();
        } catch (Exception e) {
            throw new SQLException("Error al listar clientes", e);
        } finally {
            em.close();
        }
    }

    @Override
    public void actualizarClientes(List<Cliente> clientes) throws SQLException {
        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();

            // Validación previa antes de hacer commit
            Set<String> emails = new HashSet<>();
            for (Cliente cliente : clientes) {
                if (!Validations.esEmailValido(cliente.getEmail())) {
                    throw new EmailNoValido("Email no válido: " + cliente.getEmail());
                }
                if (!emails.add(cliente.getEmail())) {
                    throw new EmailDuplicado("Email duplicado en la lista: " + cliente.getEmail());
                }
            }

            for (Cliente cliente : clientes) {
                em.merge(cliente);
            }

            tx.commit();
        } catch (EmailDuplicado | EmailNoValido e) {
            if (tx.isActive()) tx.rollback();
            throw new SQLException("Validación fallida: " + e.getMessage(), e);
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            throw new SQLException("Error al actualizar clientes", e);
        } finally {
            em.close();
        }
    }

    private boolean existeClientePorEmail(String email) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            Cliente cliente = em.find(Cliente.class, email);
            return cliente != null;
        } finally {
            em.close();
        }
    }

    private boolean existeClientePorNif(String nif) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            TypedQuery<Cliente> query = em.createQuery(
                    "SELECT c FROM Cliente c WHERE c.nif = :nif", Cliente.class
            );
            query.setParameter("nif", nif);
            List<Cliente> resultados = query.getResultList();
            return !resultados.isEmpty();

        } finally {
            em.close();
        }
    }
}
