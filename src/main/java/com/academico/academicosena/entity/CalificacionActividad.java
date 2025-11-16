package com.academico.academicosena.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "calificaciones_actividades", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"id_actividad", "id_matricula"})
})
public class CalificacionActividad {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_calificacion_actividad")
    private Integer id;
    
    @ManyToOne
    @JoinColumn(name = "id_actividad", nullable = false)
    private ActividadEvaluacion actividad;
    
    @ManyToOne
    @JoinColumn(name = "id_matricula", nullable = false)
    private Matricula matricula;
    
    @Column(name = "nota", nullable = false)
    private BigDecimal nota;
    
    @Column(name = "fecha_entrega")
    private LocalDateTime fechaEntrega;
    
    @Column(name = "entrega_tardia")
    private Boolean entregaTardia = false;
    
    @Column(name = "observaciones", columnDefinition = "TEXT")
    private String observaciones;
    
    @Column(name = "retroalimentacion", columnDefinition = "TEXT")
    private String retroalimentacion;
    
    @Column(name = "fecha_registro")
    private LocalDateTime fechaRegistro;
    
    @ManyToOne
    @JoinColumn(name = "registrado_por")
    private Docente registradoPor;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "estado")
    private Estado estado = Estado.pendiente;
    
    public enum Estado { pendiente, calificada, recuperada, anulada }
    
    // Getters y Setters
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    
    public ActividadEvaluacion getActividad() { return actividad; }
    public void setActividad(ActividadEvaluacion actividad) { this.actividad = actividad; }
    
    public Matricula getMatricula() { return matricula; }
    public void setMatricula(Matricula matricula) { this.matricula = matricula; }
    
    public BigDecimal getNota() { return nota; }
    public void setNota(BigDecimal nota) { this.nota = nota; }
    
    public LocalDateTime getFechaEntrega() { return fechaEntrega; }
    public void setFechaEntrega(LocalDateTime fechaEntrega) { this.fechaEntrega = fechaEntrega; }
    
    public Boolean getEntregaTardia() { return entregaTardia; }
    public void setEntregaTardia(Boolean entregaTardia) { this.entregaTardia = entregaTardia; }
    
    public String getObservaciones() { return observaciones; }
    public void setObservaciones(String observaciones) { this.observaciones = observaciones; }
    
    public String getRetroalimentacion() { return retroalimentacion; }
    public void setRetroalimentacion(String retroalimentacion) { this.retroalimentacion = retroalimentacion; }
    
    public LocalDateTime getFechaRegistro() { return fechaRegistro; }
    public void setFechaRegistro(LocalDateTime fechaRegistro) { this.fechaRegistro = fechaRegistro; }
    
    public Docente getRegistradoPor() { return registradoPor; }
    public void setRegistradoPor(Docente registradoPor) { this.registradoPor = registradoPor; }
    
    public Estado getEstado() { return estado; }
    public void setEstado(Estado estado) { this.estado = estado; }
    
    @PrePersist
    protected void onCreate() {
        fechaRegistro = LocalDateTime.now();
    }
}