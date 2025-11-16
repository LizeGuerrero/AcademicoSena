<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.academico.academicosena.entity.Usuario" %>
<%
    Usuario usuario = (Usuario) session.getAttribute("usuario");
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Home</title>
</head>
<body>
   <h1>Hola, <%= usuario != null ? usuario.getNombres() + " " + usuario.getApellidos() : "Invitado" %>!</h1>

    <p>¡Sesión iniciada correctamente!</p>
</body>
</html>
