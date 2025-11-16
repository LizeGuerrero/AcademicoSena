package com.academico.academicosena.entity;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "docentes")
public class Docente {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_docente")
    private Integer id;
    
    @ManyToOne
    @JoinColumn(name = "id_institucion", nullable = false)
    private Institucion institucion;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_documento", nullable = false)
    private TipoDocumento tipoDocumento;
    
    @Column(name = "numero_documento", unique = true, nullable = false, length = 20)
    private String numeroDocumento;
    
    @Column(name = "nombres", nullable = false, length = 100)
    private String nombres;
    
    @Column(name = "apellidos", nullable = false, length = 100)
    private String apellidos;
    
    @Column(name = "email", unique = true, nullable = false, length = 100)
    private String email;
    
    @Column(name = "telefono", length = 20)
    private String telefono;
    
    @Column(name = "titulo_profesional", length = 200)
    private String tituloProfesional;
    
    @Column(name = "especialidad", length = 200)
    private String especialidad;
    
    @Column(name = "fecha_vinculacion", nullable = false)
    private LocalDate fechaVinculacion;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "estado")
    private Estado estado = Estado.activo;
    
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    
    @OneToMany(mappedBy = "docente", cascade = CascadeType.ALL)
    private List<AsignacionDocente> asignaciones;
    
    @OneToMany(mappedBy = "registradoPor", cascade = CascadeType.ALL)
    private List<CalificacionActividad> calificacionesRegistradas;
    
    @OneToMany(mappedBy = "cerradoPor", cascade = CascadeType.ALL)
    private List<CalificacionPeriodo> periodosCerrados;
    
    // Enums
    public enum TipoDocumento { CC, CE, PEP }
    public enum Estado { activo, inactivo }
    
    // Getters y Setters
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    
    public Institucion getInstitucion() { return institucion; }
    public void setInstitucion(Institucion institucion) { this.institucion = institucion; }
    
    public TipoDocumento getTipoDocumento() { return tipoDocumento; }
    public void setTipoDocumento(TipoDocumento tipoDocumento) { this.tipoDocumento = tipoDocumento; }
    
    public String getNumeroDocumento() { return numeroDocumento; }
    public void setNumeroDocumento(String numeroDocumento) { this.numeroDocumento = numeroDocumento; }
    
    public String getNombres() { return nombres; }
    public void setNombres(String nombres) { this.nombres = nombres; }
    
    public String getApellidos() { return apellidos; }
    public void setApellidos(String apellidos) { this.apellidos = apellidos; }
    
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    
    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }
    
    public String getTituloProfesional() { return tituloProfesional; }
    public void setTituloProfesional(String tituloProfesional) { this.tituloProfesional = tituloProfesional; }
    
    public String getEspecialidad() { return especialidad; }
    public void setEspecialidad(String especialidad) { this.especialidad = especialidad; }
    
    public LocalDate getFechaVinculacion() { return fechaVinculacion; }
    public void setFechaVinculacion(LocalDate fechaVinculacion) { this.fechaVinculacion = fechaVinculacion; }
    
    public Estado getEstado() { return estado; }
    public void setEstado(Estado estado) { this.estado = estado; }
    
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
    
    public List<AsignacionDocente> getAsignaciones() { return asignaciones; }
    public void setAsignaciones(List<AsignacionDocente> asignaciones) { this.asignaciones = asignaciones; }
    
    public List<CalificacionActividad> getCalificacionesRegistradas() { return calificacionesRegistradas; }
    public void setCalificacionesRegistradas(List<CalificacionActividad> calificacionesRegistradas) { this.calificacionesRegistradas = calificacionesRegistradas; }
    
    public List<CalificacionPeriodo> getPeriodosCerrados() { return periodosCerrados; }
    public void setPeriodosCerrados(List<CalificacionPeriodo> periodosCerrados) { this.periodosCerrados = periodosCerrados; }
    
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }
    
    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}