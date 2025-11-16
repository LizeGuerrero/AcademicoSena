package com.academico.academicosena.service.impl;

import com.academico.academicosena.dao.UsuarioDAO;
import com.academico.academicosena.dao.impl.UsuarioDaoImpl;
import com.academico.academicosena.entity.Usuario;
import com.academico.academicosena.service.UsuarioService;

import java.util.List;

public class UsuarioServiceImpl implements UsuarioService {

    private final UsuarioDAO usuarioDAO = new UsuarioDaoImpl();

    @Override
    public Usuario registrar(Usuario usuario) {
        // Validaciones simples
        if (usuarioDAO.existePorEmail(usuario.getEmail())) {
            throw new RuntimeException("El email ya está registrado");
        }

        if (usuarioDAO.existePorNumeroDocumento(usuario.getNumeroDocumento())) {
            throw new RuntimeException("El número de documento ya está registrado");
        }

        return usuarioDAO.guardar(usuario);
    }

    @Override
    public Usuario actualizar(Usuario usuario) {
        return usuarioDAO.actualizar(usuario);
    }

    @Override
    public Usuario buscarPorId(Integer id) {
        return usuarioDAO.buscarPorId(id);
    }

    @Override
    public List<Usuario> listarTodos() {
        return usuarioDAO.buscarTodos();
    }

    @Override
    public boolean eliminar(Integer id) {
        return usuarioDAO.eliminar(id);
    }

    @Override
    public Usuario iniciarSesion(Usuario.TipoDocumento tipoDocumento,
                                 String numeroDocumento,
                                 String password) {
        return usuarioDAO.iniciarSesion(tipoDocumento, numeroDocumento, password);
    }

    @Override
    public boolean emailOcupado(String email) {
        return usuarioDAO.existePorEmail(email);
    }

    @Override
    public boolean documentoOcupado(String documento) {
        return usuarioDAO.existePorNumeroDocumento(documento);
    }
}
