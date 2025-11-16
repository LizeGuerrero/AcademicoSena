package com.academico.academicosena.service;

import com.academico.academicosena.entity.*;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import java.time.LocalDate;
import java.util.List;

@Stateless
public class DocenteService {
    
    @PersistenceContext(unitName = "academicoPU")
    private EntityManager em;
    
    /**
     * Registra un nuevo docente
     */
    public Docente registrarDocente(
            Integer idInstitucion,
            Docente.TipoDocumento tipoDocumento,
            String numeroDocumento,
            String nombres,
            String apellidos,
            String email,
            String telefono,
            String tituloProfesional,
            String especialidad) {
        
        TypedQuery<Docente> query = em.createQuery(
            "SELECT d FROM Docente d WHERE d.numeroDocumento = :numeroDocumento",
            Docente.class);
        query.setParameter("numeroDocumento", numeroDocumento);
        
        if (!query.getResultList().isEmpty()) {
            throw new IllegalArgumentException("El número de documento ya existe");
        }
        
        TypedQuery<Docente> queryEmail = em.createQuery(
            "SELECT d FROM Docente d WHERE d.email = :email",
            Docente.class);
        queryEmail.setParameter("email", email);
        
        if (!queryEmail.getResultList().isEmpty()) {
            throw new IllegalArgumentException("El email ya está registrado");
        }
        
        Institucion institucion = em.find(Institucion.class, idInstitucion);
        if (institucion == null) {
            throw new IllegalArgumentException("Institución no encontrada");
        }
        
        Docente docente = new Docente();
        docente.setInstitucion(institucion);
        docente.setTipoDocumento(tipoDocumento);
        docente.setNumeroDocumento(numeroDocumento);
        docente.setNombres(nombres);
        docente.setApellidos(apellidos);
        docente.setEmail(email);
        docente.setTelefono(telefono);
        docente.setTituloProfesional(tituloProfesional);
        docente.setEspecialidad(especialidad);
        docente.setFechaVinculacion(LocalDate.now());
        docente.setEstado(Docente.Estado.activo);
        
        em.persist(docente);
        return docente;
    }
    
    /**
     * Obtiene un docente por ID
     */
    public Docente obtenerDocente(Integer idDocente) {
        return em.find(Docente.class, idDocente);
    }
    
    /**
     * Obtiene todos los docentes activos
     */
    public List<Docente> obtenerDocentesActivos(Integer idInstitucion) {
        TypedQuery<Docente> query = em.createQuery(
            "SELECT d FROM Docente d WHERE d.institucion.id = :idInstitucion " +
            "AND d.estado = 'activo' ORDER BY d.apellidos, d.nombres",
            Docente.class);
        query.setParameter("idInstitucion", idInstitucion);
        return query.getResultList();
    }
    
    /**
     * Busca docente por email
     */
    public Docente buscarPorEmail(String email) {
        TypedQuery<Docente> query = em.createQuery(
            "SELECT d FROM Docente d WHERE d.email = :email",
            Docente.class);
        query.setParameter("email", email);
        
        List<Docente> resultados = query.getResultList();
        return resultados.isEmpty() ? null : resultados.get(0);
    }
    
    /**
     * Obtiene asignaciones del docente
     */
    public List<AsignacionDocente> obtenerAsignaciones(Integer idDocente) {
        TypedQuery<AsignacionDocente> query = em.createQuery(
            "SELECT ad FROM AsignacionDocente ad WHERE ad.docente.id = :idDocente " +
            "AND ad.activo = true",
            AsignacionDocente.class);
        query.setParameter("idDocente", idDocente);
        return query.getResultList();
    }
}