package com.academico.academicosena.rest;

import com.academico.academicosena.entity.Docente;
import com.academico.academicosena.service.DocenteService;
import jakarta.ejb.EJB;
import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/api/docentes")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class DocenteResource {
    
    @EJB
    private DocenteService docenteService;
    
    /**
     * Registra un nuevo docente
     * POST /api/docentes
     */
    @POST
    public Response registrarDocente(JsonObject request) {
        try {
            Docente docente = docenteService.registrarDocente(
                request.getInt("idInstitucion"),
                Docente.TipoDocumento.valueOf(request.getString("tipoDocumento")),
                request.getString("numeroDocumento"),
                request.getString("nombres"),
                request.getString("apellidos"),
                request.getString("email"),
                request.getString("telefono"),
                request.getString("tituloProfesional"),
                request.getString("especialidad")
            );
            
            return Response.status(Response.Status.CREATED)
                .entity(Json.createObjectBuilder()
                    .add("id", docente.getId())
                    .add("nombres", docente.getNombres())
                    .add("apellidos", docente.getApellidos())
                    .add("email", docente.getEmail())
                    .add("mensaje", "Docente registrado exitosamente")
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
     * Obtiene un docente por ID
     * GET /api/docentes/{id}
     */
    @GET
    @Path("/{id}")
    public Response obtenerDocente(@PathParam("id") Integer id) {
        try {
            Docente docente = docenteService.obtenerDocente(id);
            
            if (docente == null) {
                return Response.status(Response.Status.NOT_FOUND)
                    .entity(Json.createObjectBuilder()
                        .add("error", "Docente no encontrado")
                        .build())
                    .build();
            }
            
            return Response.ok(Json.createObjectBuilder()
                .add("id", docente.getId())
                .add("nombres", docente.getNombres())
                .add("apellidos", docente.getApellidos())
                .add("email", docente.getEmail())
                .add("especialidad", docente.getEspecialidad() != null ? docente.getEspecialidad() : "")
                .add("estado", docente.getEstado().toString())
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
     * Busca docente por email
     * GET /api/docentes/buscar/{email}
     */
    @GET
    @Path("/buscar/{email}")
    public Response buscarPorEmail(@PathParam("email") String email) {
        try {
            Docente docente = docenteService.buscarPorEmail(email);
            
            if (docente == null) {
                return Response.status(Response.Status.NOT_FOUND)
                    .entity(Json.createObjectBuilder()
                        .add("error", "Docente no encontrado")
                        .build())
                    .build();
            }
            
            return Response.ok(Json.createObjectBuilder()
                .add("id", docente.getId())
                .add("nombres", docente.getNombres())
                .add("apellidos", docente.getApellidos())
                .add("email", docente.getEmail())
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