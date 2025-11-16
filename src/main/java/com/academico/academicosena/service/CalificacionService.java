package com.academico.academicosena.service;

import com.academico.academicosena.entity.*;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Stateless
public class CalificacionService {
    
    @PersistenceContext(unitName = "academicoPU")
    private EntityManager em;
    
    /**
     * Registra una calificación de actividad
     */
    public CalificacionActividad registrarCalificacion(
            Integer idActividad, 
            Integer idMatricula, 
            BigDecimal nota,
            Integer registradoPor) {
        
        ActividadEvaluacion actividad = em.find(ActividadEvaluacion.class, idActividad);
        if (actividad == null) {
            throw new IllegalArgumentException("Actividad no encontrada");
        }
        
        Matricula matricula = em.find(Matricula.class, idMatricula);
        if (matricula == null) {
            throw new IllegalArgumentException("Matrícula no encontrada");
        }
        
        if (nota.compareTo(BigDecimal.ZERO) < 0 || nota.compareTo(actividad.getNotaMaxima()) > 0) {
            throw new IllegalArgumentException("Nota fuera de rango permitido");
        }
        
        CalificacionActividad calificacion = new CalificacionActividad();
        calificacion.setActividad(actividad);
        calificacion.setMatricula(matricula);
        calificacion.setNota(nota);
        calificacion.setRegistradoPor(em.find(Docente.class, registradoPor));
        calificacion.setEstado(CalificacionActividad.Estado.calificada);
        calificacion.setFechaRegistro(LocalDateTime.now());
        
        em.persist(calificacion);
        
        recalcularNotaPeriodo(idMatricula, actividad.getAsignatura().getId(), actividad.getPeriodo().getId());
        
        return calificacion;
    }
    
    /**
     * Recalcula la nota del período
     */
    public void recalcularNotaPeriodo(Integer idMatricula, Integer idAsignatura, Integer idPeriodo) {
        
        TypedQuery<ActividadEvaluacion> queryActividades = em.createQuery(
            "SELECT ae FROM ActividadEvaluacion ae WHERE ae.asignatura.id = :idAsignatura " +
            "AND ae.periodo.id = :idPeriodo AND ae.activo = true",
            ActividadEvaluacion.class);
        queryActividades.setParameter("idAsignatura", idAsignatura);
        queryActividades.setParameter("idPeriodo", idPeriodo);
        
        List<ActividadEvaluacion> actividades = queryActividades.getResultList();
        BigDecimal notaCalculada = BigDecimal.ZERO;
        
        for (ActividadEvaluacion actividad : actividades) {
            TypedQuery<CalificacionActividad> queryCalif = em.createQuery(
                "SELECT ca FROM CalificacionActividad ca WHERE ca.actividad.id = :idActividad " +
                "AND ca.matricula.id = :idMatricula AND ca.estado IN ('calificada', 'recuperada')",
                CalificacionActividad.class);
            queryCalif.setParameter("idActividad", actividad.getId());
            queryCalif.setParameter("idMatricula", idMatricula);
            
            List<CalificacionActividad> calificaciones = queryCalif.getResultList();
            if (!calificaciones.isEmpty()) {
                BigDecimal nota = calificaciones.get(0).getNota();
                BigDecimal porcentaje = actividad.getPorcentajeNota();
                
                notaCalculada = notaCalculada.add(
                    nota.multiply(porcentaje).divide(new BigDecimal("100"), 2, java.math.RoundingMode.HALF_UP));
            }
        }
        
        notaCalculada = notaCalculada.setScale(2, java.math.RoundingMode.HALF_UP);
        
        TypedQuery<CalificacionPeriodo> queryPeriodo = em.createQuery(
            "SELECT cp FROM CalificacionPeriodo cp WHERE cp.matricula.id = :idMatricula " +
            "AND cp.asignatura.id = :idAsignatura AND cp.periodo.id = :idPeriodo",
            CalificacionPeriodo.class);
        queryPeriodo.setParameter("idMatricula", idMatricula);
        queryPeriodo.setParameter("idAsignatura", idAsignatura);
        queryPeriodo.setParameter("idPeriodo", idPeriodo);
        
        List<CalificacionPeriodo> resultados = queryPeriodo.getResultList();
        CalificacionPeriodo calificacionPeriodo;
        
        if (resultados.isEmpty()) {
            calificacionPeriodo = new CalificacionPeriodo();
            calificacionPeriodo.setMatricula(em.find(Matricula.class, idMatricula));
            calificacionPeriodo.setAsignatura(em.find(Asignatura.class, idAsignatura));
            calificacionPeriodo.setPeriodo(em.find(PeriodoAcademico.class, idPeriodo));
            em.persist(calificacionPeriodo);
        } else {
            calificacionPeriodo = resultados.get(0);
        }
        
        calificacionPeriodo.setNotaCalculada(notaCalculada);
        calificacionPeriodo.setNotaFinal(notaCalculada);
        calificacionPeriodo.setEstado(CalificacionPeriodo.Estado.abierto);
        
        em.merge(calificacionPeriodo);
    }
    
    /**
     * Calcula la nota final de una asignatura
     */
    public void calcularNotaAsignatura(Integer idMatricula, Integer idAsignatura) {
        
        TypedQuery<CalificacionPeriodo> query = em.createQuery(
            "SELECT cp FROM CalificacionPeriodo cp WHERE cp.matricula.id = :idMatricula " +
            "AND cp.asignatura.id = :idAsignatura AND cp.estado = 'cerrado'",
            CalificacionPeriodo.class);
        query.setParameter("idMatricula", idMatricula);
        query.setParameter("idAsignatura", idAsignatura);
        
        List<CalificacionPeriodo> periodos = query.getResultList();
        
        if (periodos.isEmpty()) {
            throw new IllegalArgumentException("No hay períodos cerrados para calcular");
        }
        
        BigDecimal notaFinal = BigDecimal.ZERO;
        
        for (CalificacionPeriodo periodo : periodos) {
            BigDecimal porcentaje = periodo.getPeriodo().getPorcentajeNota();
            BigDecimal nota = periodo.getNotaFinal();
            
            notaFinal = notaFinal.add(
                nota.multiply(porcentaje).divide(new BigDecimal("100"), 2, java.math.RoundingMode.HALF_UP));
        }
        
        notaFinal = notaFinal.setScale(2, java.math.RoundingMode.HALF_UP);
        
        Matricula matricula = em.find(Matricula.class, idMatricula);
        TypedQuery<EscalaCalificacion> queryEscala = em.createQuery(
            "SELECT e FROM EscalaCalificacion e WHERE e.institucion.id = :idInstitucion AND e.activo = true",
            EscalaCalificacion.class);
        queryEscala.setParameter("idInstitucion", matricula.getEstudiante().getInstitucion().getId());
        
        List<EscalaCalificacion> escalas = queryEscala.getResultList();
        if (escalas.isEmpty()) {
            throw new IllegalArgumentException("No existe escala de calificación");
        }
        
        EscalaCalificacion escala = escalas.get(0);
        CalificacionAsignatura.EstadoAsignatura estado = 
            notaFinal.compareTo(escala.getNotaAprobatoria()) >= 0 ?
            CalificacionAsignatura.EstadoAsignatura.aprobada :
            CalificacionAsignatura.EstadoAsignatura.reprobada;
        
        TypedQuery<CalificacionAsignatura> queryAsignatura = em.createQuery(
            "SELECT ca FROM CalificacionAsignatura ca WHERE ca.matricula.id = :idMatricula " +
            "AND ca.asignatura.id = :idAsignatura",
            CalificacionAsignatura.class);
        queryAsignatura.setParameter("idMatricula", idMatricula);
        queryAsignatura.setParameter("idAsignatura", idAsignatura);
        
        List<CalificacionAsignatura> resultados = queryAsignatura.getResultList();
        CalificacionAsignatura calificacionAsignatura;
        
        if (resultados.isEmpty()) {
            calificacionAsignatura = new CalificacionAsignatura();
            calificacionAsignatura.setMatricula(matricula);
            calificacionAsignatura.setAsignatura(em.find(Asignatura.class, idAsignatura));
            em.persist(calificacionAsignatura);
        } else {
            calificacionAsignatura = resultados.get(0);
        }
        
        calificacionAsignatura.setNotaFinal(notaFinal);
        calificacionAsignatura.setEstadoAsignatura(estado);
        calificacionAsignatura.setRequiereRecuperacion(estado == CalificacionAsignatura.EstadoAsignatura.reprobada);
        
        em.merge(calificacionAsignatura);
    }
}