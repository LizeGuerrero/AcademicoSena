package com.academico.academicosena.service;

import com.academico.academicosena.entity.Usuario;

import java.util.List;

public interface UsuarioService {

    // CRUD
    Usuario registrar(Usuario usuario);
    Usuario actualizar(Usuario usuario);
    Usuario buscarPorId(Integer id);
    List<Usuario> listarTodos();
    boolean eliminar(Integer id);

    // Login
    Usuario iniciarSesion(Usuario.TipoDocumento tipoDocumento,
                          String numeroDocumento,
                          String password);

    // Validaciones
    boolean emailOcupado(String email);
    boolean documentoOcupado(String documento);
}
