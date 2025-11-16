package com.academico.academicosena.dao.impl;

import com.academico.academicosena.dao.UsuarioDAO;
import com.academico.academicosena.entity.Usuario;
import com.academico.academicosena.entity.Usuario.EstadoUsuario;
import com.academico.academicosena.config.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public class UsuarioDaoImpl implements UsuarioDAO {

    @Override
    public Usuario guardar(Usuario usuario) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            session.persist(usuario);
            tx.commit();
            return usuario;
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            throw e;
        }
    }

    @Override
    public Usuario actualizar(Usuario usuario) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            session.merge(usuario);
            tx.commit();
            return usuario;
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            throw e;
        }
    }

    @Override
    public Usuario buscarPorId(Integer id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.find(Usuario.class, id);
        }
    }

    @Override
    public List<Usuario> buscarTodos() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("FROM Usuario", Usuario.class).list();
        }
    }

    @Override
    public boolean eliminar(Integer id) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Usuario usuario = session.find(Usuario.class, id);
            if (usuario == null) {
                return false;
            }
            tx = session.beginTransaction();
            session.remove(usuario);
            tx.commit();
            return true;
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            return false;
        }
    }

    @Override
    public Usuario buscarPorEmail(String email) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Usuario> q = session.createQuery(
                "FROM Usuario WHERE email = :email", Usuario.class);
            q.setParameter("email", email);
            return q.uniqueResult();
        }
    }

    @Override
    public Usuario buscarPorNumeroDocumento(String numeroDocumento) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Usuario> q = session.createQuery(
                "FROM Usuario WHERE numeroDocumento = :num", Usuario.class);
            q.setParameter("num", numeroDocumento);
            return q.uniqueResult();
        }
    }

    @Override
    public Usuario iniciarSesion(Usuario.TipoDocumento tipoDocumento,
                                 String numeroDocumento,
                                 String password) {

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {

            Query<Usuario> q = session.createQuery(
                "FROM Usuario u WHERE u.tipoDocumento = :td " +
                "AND u.numeroDocumento = :nd " +
                "AND u.password = :pw " +
                "AND u.estado = :estado",  // mejor usar parámetro para estado
                Usuario.class
            );
            q.setParameter("td", tipoDocumento);
            q.setParameter("nd", numeroDocumento);
            q.setParameter("pw", password);
            q.setParameter("estado", EstadoUsuario.ACTIVO);

            Usuario u = q.uniqueResult();

            if (u != null) {
                Transaction tx = session.beginTransaction();
                u.setUltimoAcceso(java.time.LocalDateTime.now());
                session.merge(u);
                tx.commit();
            }

            return u;
        }
    }

    @Override
    public boolean cambiarEstado(Integer idUsuario, EstadoUsuario nuevoEstado) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Usuario u = session.find(Usuario.class, idUsuario);
            if (u == null) return false;

            tx = session.beginTransaction();
            u.setEstado(nuevoEstado);
            session.merge(u);
            tx.commit();
            return true;
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            return false;
        }
    }

    @Override
    public List<Usuario> buscarPorEstado(EstadoUsuario estado) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Usuario> q = session.createQuery(
                "FROM Usuario WHERE estado = :e", Usuario.class);
            q.setParameter("e", estado);
            return q.list();
        }
    }

    @Override
    public boolean existePorEmail(String email) {
        return buscarPorEmail(email) != null;
    }

    @Override
    public boolean existePorNumeroDocumento(String numeroDocumento) {
        return buscarPorNumeroDocumento(numeroDocumento) != null;
    }
}
