package com.academico.academicosena.entity;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "asignaciones_docentes", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"id_docente", "id_asignatura", "id_periodo"})
})
public class AsignacionDocente {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_asignacion")
    private Integer id;
    
    @ManyToOne
    @JoinColumn(name = "id_docente", nullable = false)
    private Docente docente;
    
    @ManyToOne
    @JoinColumn(name = "id_asignatura", nullable = false)
    private Asignatura asignatura;
    
    @ManyToOne
    @JoinColumn(name = "id_periodo", nullable = false)
    private PeriodoAcademico periodo;
    
    @Column(name = "fecha_asignacion", nullable = false)
    private LocalDate fechaAsignacion;
    
    @Column(name = "activo")
    private Boolean activo = true;
    
    // Getters y Setters
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    
    public Docente getDocente() { return docente; }
    public void setDocente(Docente docente) { this.docente = docente; }
    
    public Asignatura getAsignatura() { return asignatura; }
    public void setAsignatura(Asignatura asignatura) { this.asignatura = asignatura; }
    
    public PeriodoAcademico getPeriodo() { return periodo; }
    public void setPeriodo(PeriodoAcademico periodo) { this.periodo = periodo; }
    
    public LocalDate getFechaAsignacion() { return fechaAsignacion; }
    public void setFechaAsignacion(LocalDate fechaAsignacion) { this.fechaAsignacion = fechaAsignacion; }
    
    public Boolean getActivo() { return activo; }
    public void setActivo(Boolean activo) { this.activo = activo; }
}