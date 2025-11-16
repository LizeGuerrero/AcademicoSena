package com.academico.academicosena.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "instituciones")
public class Institucion {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_institucion")
    private Integer id;
    
    @Column(name = "nit", unique = true, nullable = false, length = 20)
    private String nit;
    
    @Column(name = "nombre", nullable = false, length = 200)
    private String nombre;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_institucion", nullable = false)
    private TipoInstitucion tipoInstitucion;
    
    @Column(name = "direccion", length = 255)
    private String direccion;
    
    @Column(name = "telefono", length = 20)
    private String telefono;
    
    @Column(name = "email", length = 100)
    private String email;
    
    @Column(name = "activo")
    private Boolean activo = true;
    
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    
    @OneToMany(mappedBy = "institucion", cascade = CascadeType.ALL)
    private List<ProgramaAcademico> programas;
    
    // Getters y Setters
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    
    public String getNit() { return nit; }
    public void setNit(String nit) { this.nit = nit; }
    
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    
    public TipoInstitucion getTipoInstitucion() { return tipoInstitucion; }
    public void setTipoInstitucion(TipoInstitucion tipoInstitucion) { this.tipoInstitucion = tipoInstitucion; }
    
    public String getDireccion() { return direccion; }
    public void setDireccion(String direccion) { this.direccion = direccion; }
    
    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }
    
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    
    public Boolean getActivo() { return activo; }
    public void setActivo(Boolean activo) { this.activo = activo; }
    
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
    
    public List<ProgramaAcademico> getProgramas() { return programas; }
    public void setProgramas(List<ProgramaAcademico> programas) { this.programas = programas; }
    
    public enum TipoInstitucion {
        colegio, instituto_tecnico, universidad
    }
    
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