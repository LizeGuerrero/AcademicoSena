package com.academico.academicosena.entity;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "matriculas")
public class Matricula {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_matricula")
    private Integer id;
    
    @ManyToOne
    @JoinColumn(name = "id_estudiante", nullable = false)
    private Estudiante estudiante;
    
    @ManyToOne
    @JoinColumn(name = "id_programa", nullable = false)
    private ProgramaAcademico programa;
    
    @ManyToOne
    @JoinColumn(name = "id_nivel", nullable = false)
    private Nivel nivel;
    
    @Column(name = "codigo_estudiante", unique = true, nullable = false, length = 20)
    private String codigoEstudiante;
    
    @Column(name = "fecha_matricula", nullable = false)
    private LocalDate fechaMatricula;
    
    @Column(name = "fecha_fin")
    private LocalDate fechaFin;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "estado")
    private Estado estado = Estado.activo;
    
    @Column(name = "observaciones", columnDefinition = "TEXT")
    private String observaciones;
    
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    
    @OneToMany(mappedBy = "matricula", cascade = CascadeType.ALL)
    private List<CalificacionActividad> calificacionesActividades;
    
    @OneToMany(mappedBy = "matricula", cascade = CascadeType.ALL)
    private List<CalificacionPeriodo> calificacionesPeriodos;
    
    @OneToMany(mappedBy = "matricula", cascade = CascadeType.ALL)
    private List<CalificacionAsignatura> calificacionesAsignaturas;
    
    public enum Estado { activo, retirado, graduado, aplazado }
    
    // Getters y Setters
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    
    public Estudiante getEstudiante() { return estudiante; }
    public void setEstudiante(Estudiante estudiante) { this.estudiante = estudiante; }
    
    public ProgramaAcademico getPrograma() { return programa; }
    public void setPrograma(ProgramaAcademico programa) { this.programa = programa; }
    
    public Nivel getNivel() { return nivel; }
    public void setNivel(Nivel nivel) { this.nivel = nivel; }
    
    public String getCodigoEstudiante() { return codigoEstudiante; }
    public void setCodigoEstudiante(String codigoEstudiante) { this.codigoEstudiante = codigoEstudiante; }
    
    public LocalDate getFechaMatricula() { return fechaMatricula; }
    public void setFechaMatricula(LocalDate fechaMatricula) { this.fechaMatricula = fechaMatricula; }
    
    public LocalDate getFechaFin() { return fechaFin; }
    public void setFechaFin(LocalDate fechaFin) { this.fechaFin = fechaFin; }
    
    public Estado getEstado() { return estado; }
    public void setEstado(Estado estado) { this.estado = estado; }
    
    public String getObservaciones() { return observaciones; }
    public void setObservaciones(String observaciones) { this.observaciones = observaciones; }
    
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }
}
