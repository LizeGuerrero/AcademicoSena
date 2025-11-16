package com.academico.academicosena.rest;

import com.academico.academicosena.entity.Matricula;
import com.academico.academicosena.service.MatriculaService;
import jakarta.ejb.EJB;
import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/api/matriculas")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class MatriculaResource {
    
    @EJB
    private MatriculaService matriculaService;
    
    /**
     * Matricula un estudiante en un nivel
     * POST /api/matriculas
     */
    @POST
    public Response matricularEstudiante(JsonObject request) {
        try {
            Matricula matricula = matriculaService.matricularEstudiante(
                request.getInt("idEstudiante"),
                request.getInt("idPrograma"),
                request.getInt("idNivel")
            );
            
            return Response.status(Response.Status.CREATED)
                .entity(Json.createObjectBuilder()
                    .add("id", matricula.getId())
                    .add("codigoEstudiante", matricula.getCodigoEstudiante())
                    .add("estado", matricula.getEstado().toString())
                    .add("mensaje", "Matrícula registrada exitosamente")
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
     * Obtiene matrículas activas de un nivel
     * GET /api/matriculas/nivel/{idNivel}
     */
    @GET
    @Path("/nivel/{idNivel}")
    public Response obtenerMatriculasNivel(@PathParam("idNivel") Integer idNivel) {
        try {
            return Response.ok(Json.createObjectBuilder()
                .add("total", matriculaService.obtenerMatriculasNivel(idNivel).size())
                .add("mensaje", "Matrículas obtenidas exitosamente")
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
     * Cierra una matrícula
     * PUT /api/matriculas/{id}/cerrar
     */
    @PUT
    @Path("/{id}/cerrar")
    public Response cerrarMatricula(@PathParam("id") Integer id) {
        try {
            Matricula matricula = matriculaService.cerrarMatricula(id);
            
            return Response.ok(Json.createObjectBuilder()
                .add("id", matricula.getId())
                .add("estado", matricula.getEstado().toString())
                .add("mensaje", "Matrícula cerrada exitosamente")
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