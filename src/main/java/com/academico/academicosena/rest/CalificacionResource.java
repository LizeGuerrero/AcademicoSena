package com.academico.academicosena.rest;

import com.academico.academicosena.entity.CalificacionActividad;
import com.academico.academicosena.service.CalificacionService;
import jakarta.ejb.EJB;
import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.math.BigDecimal;

@Path("/api/calificaciones")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CalificacionResource {
    
    @EJB
    private CalificacionService calificacionService;
    
    /**
     * Registra una calificación de actividad
     * POST /api/calificaciones/actividad
     */
    @POST
    @Path("/actividad")
    public Response registrarCalificacion(JsonObject request) {
        try {
            Integer idActividad = request.getInt("idActividad");
            Integer idMatricula = request.getInt("idMatricula");
            BigDecimal nota = new BigDecimal(request.getString("nota"));
            Integer registradoPor = request.getInt("registradoPor");
            
            CalificacionActividad calificacion = calificacionService.registrarCalificacion(
                idActividad, idMatricula, nota, registradoPor);
            
            return Response.status(Response.Status.CREATED)
                .entity(Json.createObjectBuilder()
                    .add("id", calificacion.getId())
                    .add("nota", calificacion.getNota().toPlainString())
                    .add("estado", calificacion.getEstado().toString())
                    .add("mensaje", "Calificación registrada exitosamente")
                    .build())
                .build();
                
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                .entity(Json.createObjectBuilder()
                    .add("error", e.getMessage())
                    .build())
                .build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity(Json.createObjectBuilder()
                    .add("error", "Error: " + e.getMessage())
                    .build())
                .build();
        }
    }
    
    /**
     * Recalcula la nota de un período
     * POST /api/calificaciones/periodo/recalcular
     */
    @POST
    @Path("/periodo/recalcular")
    public Response recalcularNotaPeriodo(JsonObject request) {
        try {
            Integer idMatricula = request.getInt("idMatricula");
            Integer idAsignatura = request.getInt("idAsignatura");
            Integer idPeriodo = request.getInt("idPeriodo");
            
            calificacionService.recalcularNotaPeriodo(idMatricula, idAsignatura, idPeriodo);
            
            return Response.ok(Json.createObjectBuilder()
                .add("mensaje", "Nota del período recalculada exitosamente")
                .build())
                .build();
                
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity(Json.createObjectBuilder()
                    .add("error", e.getMessage())
                    .build())
                .build();
        }
    }
    
    /**
     * Calcula la nota final de una asignatura
     * POST /api/calificaciones/asignatura/calcular
     */
    @POST
    @Path("/asignatura/calcular")
    public Response calcularNotaAsignatura(JsonObject request) {
        try {
            Integer idMatricula = request.getInt("idMatricula");
            Integer idAsignatura = request.getInt("idAsignatura");
            
            calificacionService.calcularNotaAsignatura(idMatricula, idAsignatura);
            
            return Response.ok(Json.createObjectBuilder()
                .add("mensaje", "Nota de asignatura calculada exitosamente")
                .build())
                .build();
                
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity(Json.createObjectBuilder()
                    .add("error", e.getMessage())
                    .build())
                .build();
        }
    }
}