package com.academico.academicosena.service;

import com.academico.academicosena.entity.*;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import java.time.LocalDate;
import java.util.List;

@Stateless
public class MatriculaService {
    
    @PersistenceContext(unitName = "academicoPU")
    private EntityManager em;
    
    /**
     * Matricula un estudiante en un nivel
     */
    public Matricula matricularEstudiante(Integer idEstudiante, Integer idPrograma, Integer idNivel) {
        
        TypedQuery<Matricula> queryActiva = em.createQuery(
            "SELECT m FROM Matricula m WHERE m.estudiante.id = :idEstudiante " +
            "AND m.programa.id = :idPrograma AND m.estado = 'activo'",
            Matricula.class);
        queryActiva.setParameter("idEstudiante", idEstudiante);
        queryActiva.setParameter("idPrograma", idPrograma);
        
        if (!queryActiva.getResultList().isEmpty()) {
            throw new IllegalArgumentException("Matrícula activa ya existe en este programa");
        }
        
        Estudiante estudiante = em.find(Estudiante.class, idEstudiante);
        if (estudiante == null) throw new IllegalArgumentException("Estudiante no encontrado");
        
        ProgramaAcademico programa = em.find(ProgramaAcademico.class, idPrograma);
        if (programa == null) throw new IllegalArgumentException("Programa no encontrado");
        
        Nivel nivel = em.find(Nivel.class, idNivel);
        if (nivel == null) throw new IllegalArgumentException("Nivel no encontrado");
        
        String codigoEstudiante = generarCodigoEstudiante(idPrograma);
        
        Matricula matricula = new Matricula();
        matricula.setEstudiante(estudiante);
        matricula.setPrograma(programa);
        matricula.setNivel(nivel);
        matricula.setCodigoEstudiante(codigoEstudiante);
        matricula.setFechaMatricula(LocalDate.now());
        matricula.setEstado(Matricula.Estado.activo);
        
        em.persist(matricula);
        return matricula;
    }
    
    /**
     * Genera código único para el estudiante
     */
    private String generarCodigoEstudiante(Integer idPrograma) {
        int año = LocalDate.now().getYear();
        
        TypedQuery<Long> query = em.createQuery(
            "SELECT COUNT(m) FROM Matricula m WHERE m.programa.id = :idPrograma",
            Long.class);
        query.setParameter("idPrograma", idPrograma);
        
        long consecutivo = query.getSingleResult() + 1;
        return String.format("%d-%d-%04d", año, idPrograma, consecutivo);
    }
    
    /**
     * Obtiene matrícula activa
     */
    public Matricula obtenerMatriculaActiva(Integer idEstudiante, Integer idPrograma) {
        TypedQuery<Matricula> query = em.createQuery(
            "SELECT m FROM Matricula m WHERE m.estudiante.id = :idEstudiante " +
            "AND m.programa.id = :idPrograma AND m.estado = 'activo'",
            Matricula.class);
        query.setParameter("idEstudiante", idEstudiante);
        query.setParameter("idPrograma", idPrograma);
        
        List<Matricula> resultados = query.getResultList();
        return resultados.isEmpty() ? null : resultados.get(0);
    }
    
    /**
     * Obtiene matrículas de un nivel
     */
    public List<Matricula> obtenerMatriculasNivel(Integer idNivel) {
        TypedQuery<Matricula> query = em.createQuery(
            "SELECT m FROM Matricula m WHERE m.nivel.id = :idNivel " +
            "AND m.estado = 'activo' ORDER BY m.estudiante.apellidos",
            Matricula.class);
        query.setParameter("idNivel", idNivel);
        return query.getResultList();
    }
    
    /**
     * Cierra una matrícula
     */
    public Matricula cerrarMatricula(Integer idMatricula) {
        Matricula matricula = em.find(Matricula.class, idMatricula);
        if (matricula == null) {
            throw new IllegalArgumentException("Matrícula no encontrada");
        }
        
        matricula.setFechaFin(LocalDate.now());
        matricula.setEstado(Matricula.Estado.graduado);
        
        return em.merge(matricula);
    }
}
