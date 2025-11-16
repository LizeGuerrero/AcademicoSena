package com.academico.academicosena.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "configuracion_periodos")
public class ConfiguracionPeriodo {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_configuracion")
    private Integer id;
    
    @ManyToOne
    @JoinColumn(name = "id_programa", nullable = false)
    private ProgramaAcademico programa;
    
    @Column(name = "nombre_periodo", nullable = false, length = 50)
    private String nombrePeriodo;
    
    @Column(name = "cantidad_periodos", nullable = false)
    private Integer cantidadPeriodos;
    
    @Column(name = "es_periodo_principal")
    private Boolean esPeriodoPrincipal = false;
    
    @Column(name = "descripcion", columnDefinition = "TEXT")
    private String descripcion;
    
    // Getters y Setters
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    
    public ProgramaAcademico getPrograma() { return programa; }
    public void setPrograma(ProgramaAcademico programa) { this.programa = programa; }
    
    public String getNombrePeriodo() { return nombrePeriodo; }
    public void setNombrePeriodo(String nombrePeriodo) { this.nombrePeriodo = nombrePeriodo; }
    
    public Integer getCantidadPeriodos() { return cantidadPeriodos; }
    public void setCantidadPeriodos(Integer cantidadPeriodos) { this.cantidadPeriodos = cantidadPeriodos; }
    
    public Boolean getEsPeriodoPrincipal() { return esPeriodoPrincipal; }
    public void setEsPeriodoPrincipal(Boolean esPeriodoPrincipal) { this.esPeriodoPrincipal = esPeriodoPrincipal; }
    
    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
}