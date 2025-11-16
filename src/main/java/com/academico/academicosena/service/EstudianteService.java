package com.academico.academicosena.service;

import com.academico.academicosena.entity.*;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import java.time.LocalDate;
import java.util.List;

@Stateless
public class EstudianteService {
    
    @PersistenceContext(unitName = "academicoPU")
    private EntityManager em;
    
    /**
     * Registra un nuevo estudiante
     */
    public Estudiante registrarEstudiante(
            Integer idInstitucion,
            Estudiante.TipoDocumento tipoDocumento,
            String numeroDocumento,
            String nombres,
            String apellidos,
            LocalDate fechaNacimiento,
            Estudiante.Genero genero,
            String email,
            String telefono,
            String direccion,
            String nombreAcudiente,
            String telefonoAcudiente,
            String emailAcudiente) {
        
        TypedQuery<Estudiante> query = em.createQuery(
            "SELECT e FROM Estudiante e WHERE e.numeroDocumento = :numeroDocumento",
            Estudiante.class);
        query.setParameter("numeroDocumento", numeroDocumento);
        
        if (!query.getResultList().isEmpty()) {
            throw new IllegalArgumentException("El número de documento ya existe");
        }
        
        Institucion institucion = em.find(Institucion.class, idInstitucion);
        if (institucion == null) {
            throw new IllegalArgumentException("Institución no encontrada");
        }
        
        Estudiante estudiante = new Estudiante();
        estudiante.setInstitucion(institucion);
        estudiante.setTipoDocumento(tipoDocumento);
        estudiante.setNumeroDocumento(numeroDocumento);
        estudiante.setNombres(nombres);
        estudiante.setApellidos(apellidos);
        estudiante.setFechaNacimiento(fechaNacimiento);
        estudiante.setGenero(genero);
        estudiante.setEmail(email);
        estudiante.setTelefono(telefono);
        estudiante.setDireccion(direccion);
        estudiante.setNombreAcudiente(nombreAcudiente);
        estudiante.setTelefonoAcudiente(telefonoAcudiente);
        estudiante.setEmailAcudiente(emailAcudiente);
        estudiante.setFechaIngreso(LocalDate.now());
        estudiante.setEstado(Estudiante.Estado.activo);
        
        em.persist(estudiante);
        return estudiante;
    }
    
    /**
     * Obtiene un estudiante por ID
     */
    public Estudiante obtenerEstudiante(Integer idEstudiante) {
        return em.find(Estudiante.class, idEstudiante);
    }
    
    /**
     * Obtiene todos los estudiantes activos
     */
    public List<Estudiante> obtenerEstudiantesActivos(Integer idInstitucion) {
        TypedQuery<Estudiante> query = em.createQuery(
            "SELECT e FROM Estudiante e WHERE e.institucion.id = :idInstitucion " +
            "AND e.estado = 'activo' ORDER BY e.apellidos, e.nombres",
            Estudiante.class);
        query.setParameter("idInstitucion", idInstitucion);
        return query.getResultList();
    }
    
    /**
     * Busca estudiante por documento
     */
    public Estudiante buscarPorDocumento(String numeroDocumento) {
        TypedQuery<Estudiante> query = em.createQuery(
            "SELECT e FROM Estudiante e WHERE e.numeroDocumento = :numeroDocumento",
            Estudiante.class);
        query.setParameter("numeroDocumento", numeroDocumento);
        
        List<Estudiante> resultados = query.getResultList();
        return resultados.isEmpty() ? null : resultados.get(0);
    }
    
    /**
     * Actualiza un estudiante
     */
    public Estudiante actualizar(Estudiante estudiante) {
        return em.merge(estudiante);
    }
    
    /**
     * Obtiene el boletín de un estudiante
     */
    public List<CalificacionAsignatura> obtenerBoletin(Integer idMatricula) {
        TypedQuery<CalificacionAsignatura> query = em.createQuery(
            "SELECT ca FROM CalificacionAsignatura ca WHERE ca.matricula.id = :idMatricula " +
            "ORDER BY ca.asignatura.nombre",
            CalificacionAsignatura.class);
        query.setParameter("idMatricula", idMatricula);
        return query.getResultList();
    }
}
