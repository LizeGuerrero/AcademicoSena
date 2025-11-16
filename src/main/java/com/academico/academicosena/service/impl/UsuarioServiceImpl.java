package com.academico.academicosena.service.impl;

import com.academico.academicosena.dao.UsuarioDAO;
import com.academico.academicosena.dao.impl.UsuarioDaoImpl;
import com.academico.academicosena.entity.Usuario;
import com.academico.academicosena.service.UsuarioService;
import com.academico.academicosena.util.PasswordUtils;

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

        // Hashear la contraseña antes de guardar
        if (usuario.getPassword() != null && !usuario.getPassword().isEmpty()) {
            String hashed = PasswordUtils.hashPassword(usuario.getPassword());
            usuario.setPassword(hashed);
        }

        return usuarioDAO.guardar(usuario);
    }

    @Override
    public Usuario actualizar(Usuario usuario) {
        // Si se proporciona una nueva contraseña en texto plano, hashearla
        String pw = usuario.getPassword();
        if (pw != null && !pw.isEmpty() && !(pw.startsWith("$2a$") || pw.startsWith("$2b$") || pw.startsWith("$2y$"))) {
            usuario.setPassword(PasswordUtils.hashPassword(pw));
        }
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
