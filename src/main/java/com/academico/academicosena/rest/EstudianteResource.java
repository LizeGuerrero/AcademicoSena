package com.academico.academicosena.rest;

import com.academico.academicosena.entity.Estudiante;
import com.academico.academicosena.service.EstudianteService;
import jakarta.ejb.EJB;
import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.time.LocalDate;
import java.util.List;

@Path("/api/estudiantes")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class EstudianteResource {
    
    @EJB
    private EstudianteService estudianteService;
    
    /**
     * Registra un nuevo estudiante
     * POST /api/estudiantes
     */
    @POST
    public Response registrarEstudiante(JsonObject request) {
        try {
            Estudiante estudiante = estudianteService.registrarEstudiante(
                request.getInt("idInstitucion"),
                Estudiante.TipoDocumento.valueOf(request.getString("tipoDocumento")),
                request.getString("numeroDocumento"),
                request.getString("nombres"),
                request.getString("apellidos"),
                LocalDate.parse(request.getString("fechaNacimiento")),
                Estudiante.Genero.valueOf(request.getString("genero")),
                request.getString("email"),
                request.getString("telefono"),
                request.getString("direccion"),
                request.getString("nombreAcudiente"),
                request.getString("telefonoAcudiente"),
                request.getString("emailAcudiente")
            );
            
            return Response.status(Response.Status.CREATED)
                .entity(Json.createObjectBuilder()
                    .add("id", estudiante.getId())
                    .add("nombres", estudiante.getNombres())
                    .add("apellidos", estudiante.getApellidos())
                    .add("numeroDocumento", estudiante.getNumeroDocumento())
                    .add("mensaje", "Estudiante registrado exitosamente")
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
     * Obtiene un estudiante por ID
     * GET /api/estudiantes/{id}
     */
    @GET
    @Path("/{id}")
    public Response obtenerEstudiante(@PathParam("id") Integer id) {
        try {
            Estudiante estudiante = estudianteService.obtenerEstudiante(id);
            
            if (estudiante == null) {
                return Response.status(Response.Status.NOT_FOUND)
                    .entity(Json.createObjectBuilder()
                        .add("error", "Estudiante no encontrado")
                        .build())
                    .build();
            }
            
            return Response.ok(Json.createObjectBuilder()
                .add("id", estudiante.getId())
                .add("nombres", estudiante.getNombres())
                .add("apellidos", estudiante.getApellidos())
                .add("numeroDocumento", estudiante.getNumeroDocumento())
                .add("email", estudiante.getEmail() != null ? estudiante.getEmail() : "")
                .add("estado", estudiante.getEstado().toString())
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
     * Busca estudiante por documento
     * GET /api/estudiantes/buscar/{documento}
     */
    @GET
    @Path("/buscar/{documento}")
    public Response buscarPorDocumento(@PathParam("documento") String documento) {
        try {
            Estudiante estudiante = estudianteService.buscarPorDocumento(documento);
            
            if (estudiante == null) {
                return Response.status(Response.Status.NOT_FOUND)
                    .entity(Json.createObjectBuilder()
                        .add("error", "Estudiante no encontrado")
                        .build())
                    .build();
            }
            
            return Response.ok(Json.createObjectBuilder()
                .add("id", estudiante.getId())
                .add("nombres", estudiante.getNombres())
                .add("apellidos", estudiante.getApellidos())
                .add("numeroDocumento", estudiante.getNumeroDocumento())
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
