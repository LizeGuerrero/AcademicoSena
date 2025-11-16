package com.academico.academicosena.controller;

import com.academico.academicosena.entity.Usuario;
import com.academico.academicosena.service.UsuarioService;
import com.academico.academicosena.service.impl.UsuarioServiceImpl;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/auth/login")
public class LoginServlet extends HttpServlet {

    private UsuarioService usuarioService = new UsuarioServiceImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.getRequestDispatcher("/auth/login.jsp").forward(request, response);

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            // Leer parámetros
            String tipoDoc = request.getParameter("tipoDocumento");
            String numDoc = request.getParameter("numeroDocumento");
            String password = request.getParameter("password");

            // Validación simple
            if (numDoc == null || password == null || tipoDoc == null ||
                numDoc.isEmpty() || password.isEmpty() || tipoDoc.isEmpty()) {

                request.setAttribute("error", "Todos los campos son obligatorios");
                request.getRequestDispatcher("/auth/login.jsp").forward(request, response);

                return;
            }

            // Llamar al servicio
            Usuario usuario = usuarioService.iniciarSesion(
                    Usuario.TipoDocumento.valueOf(tipoDoc),
                    numDoc,
                    password
            );

            if (usuario == null) {
                request.setAttribute("error", "Credenciales inválidas");
                request.getRequestDispatcher("/auth/login.jsp").forward(request, response);
                return;
            }

            // Guardar en sesión
            HttpSession session = request.getSession();
            session.setAttribute("usuario", usuario);

            // Redirigir al panel principal
            response.sendRedirect(request.getContextPath() + "/views/home.jsp");

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Error al iniciar sesión: " + e.getMessage());
            request.getRequestDispatcher("/auth/login.jsp").forward(request, response);
        }
    }
}
