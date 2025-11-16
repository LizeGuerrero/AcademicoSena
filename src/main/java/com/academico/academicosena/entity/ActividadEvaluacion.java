package com.academico.academicosena.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "actividades_evaluacion", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"id_asignatura", "id_periodo", "codigo"})
})
public class ActividadEvaluacion {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_actividad")
    private Integer id;
    
    @ManyToOne
    @JoinColumn(name = "id_asignatura", nullable = false)
    private Asignatura asignatura;
    
    @ManyToOne
    @JoinColumn(name = "id_periodo", nullable = false)
    private PeriodoAcademico periodo;
    
    @Column(name = "codigo", nullable = false, length = 20)
    private String codigo;
    
    @Column(name = "nombre", nullable = false, length = 200)
    private String nombre;
    
    @Column(name = "descripcion", columnDefinition = "TEXT")
    private String descripcion;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_actividad", nullable = false)
    private TipoActividad tipoActividad;
    
    @Column(name = "porcentaje_nota", nullable = false)
    private BigDecimal porcentajeNota;
    
    @Column(name = "nota_maxima")
    private BigDecimal notaMaxima = new BigDecimal("5.00");
    
    @Column(name = "fecha_asignacion")
    private LocalDate fechaAsignacion;
    
    @Column(name = "fecha_entrega")
    private LocalDate fechaEntrega;
    
    @Column(name = "permite_recuperacion")
    private Boolean permiteRecuperacion = false;
    
    @Column(name = "es_obligatoria")
    private Boolean esObligatoria = true;
    
    @Column(name = "activo")
    private Boolean activo = true;
    
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    
    @OneToMany(mappedBy = "actividad", cascade = CascadeType.ALL)
    private List<CalificacionActividad> calificaciones;
    
    public enum TipoActividad {
        quiz, examen, taller, proyecto, exposicion, trabajo, participacion, otro
    }
    
    // Getters y Setters
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    
    public Asignatura getAsignatura() { return asignatura; }
    public void setAsignatura(Asignatura asignatura) { this.asignatura = asignatura; }
    
    public PeriodoAcademico getPeriodo() { return periodo; }
    public void setPeriodo(PeriodoAcademico periodo) { this.periodo = periodo; }
    
    public String getCodigo() { return codigo; }
    public void setCodigo(String codigo) { this.codigo = codigo; }
    
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    
    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
    
    public TipoActividad getTipoActividad() { return tipoActividad; }
    public void setTipoActividad(TipoActividad tipoActividad) { this.tipoActividad = tipoActividad; }
    
    public BigDecimal getPorcentajeNota() { return porcentajeNota; }
    public void setPorcentajeNota(BigDecimal porcentajeNota) { this.porcentajeNota = porcentajeNota; }
    
    public BigDecimal getNotaMaxima() { return notaMaxima; }
    public void setNotaMaxima(BigDecimal notaMaxima) { this.notaMaxima = notaMaxima; }
    
    public LocalDate getFechaAsignacion() { return fechaAsignacion; }
    public void setFechaAsignacion(LocalDate fechaAsignacion) { this.fechaAsignacion = fechaAsignacion; }
    
    public LocalDate getFechaEntrega() { return fechaEntrega; }
    public void setFechaEntrega(LocalDate fechaEntrega) { this.fechaEntrega = fechaEntrega; }
    
    public Boolean getPermiteRecuperacion() { return permiteRecuperacion; }
    public void setPermiteRecuperacion(Boolean permiteRecuperacion) { this.permiteRecuperacion = permiteRecuperacion; }
    
    public Boolean getEsObligatoria() { return esObligatoria; }
    public void setEsObligatoria(Boolean esObligatoria) { this.esObligatoria = esObligatoria; }
    
    public Boolean getActivo() { return activo; }
    public void setActivo(Boolean activo) { this.activo = activo; }
    
    public List<CalificacionActividad> getCalificaciones() { return calificaciones; }
    public void setCalificaciones(List<CalificacionActividad> calificaciones) { this.calificaciones = calificaciones; }
    
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }
}