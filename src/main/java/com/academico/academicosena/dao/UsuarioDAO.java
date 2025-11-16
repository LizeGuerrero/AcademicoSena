package com.academico.academicosena.dao;

import com.academico.academicosena.entity.Usuario;
import java.util.List;

public interface UsuarioDAO {

    // CRUD básico
    Usuario guardar(Usuario usuario);
    Usuario actualizar(Usuario usuario);
    Usuario buscarPorId(Integer id);
    List<Usuario> buscarTodos();
    boolean eliminar(Integer id);

    // Búsquedas específicas
    Usuario buscarPorEmail(String email);
    Usuario buscarPorNumeroDocumento(String numeroDocumento);

    // Inicio de sesión
    Usuario iniciarSesion(Usuario.TipoDocumento tipoDocumento,
                          String numeroDocumento,
                          String password);

    // Estados
    boolean cambiarEstado(Integer idUsuario, Usuario.EstadoUsuario nuevoEstado);
    List<Usuario> buscarPorEstado(Usuario.EstadoUsuario estado);

    // Extra
    boolean existePorEmail(String email);
    boolean existePorNumeroDocumento(String numeroDocumento);
}
