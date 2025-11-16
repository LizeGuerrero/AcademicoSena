<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
    // Redirige automáticamente al login
    // usar el context path para que la ruta funcione desde cualquier contexto
    response.sendRedirect(request.getContextPath() + "/auth/login.jsp");
%>
