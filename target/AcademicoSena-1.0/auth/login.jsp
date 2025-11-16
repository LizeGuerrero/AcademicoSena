<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.academico.academicosena.entity.Usuario" %>
<%
    String error = (String) request.getAttribute("error");
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Login</title>
</head>
<body>
    <h2>Iniciar Sesión</h2>
    <% if (error != null) { %>
        <p style="color:red;"><%= error %></p>
    <% } %>
    <form action="<%= request.getContextPath() %>/login" method="post">
        <label for="tipoDocumento">Tipo Documento:</label>
        <select name="tipoDocumento" id="tipoDocumento" required>
            <option value="CC">CC</option>
            <option value="CE">CE</option>
            <option value="PEP">PEP</option>
        </select><br><br>

        <label for="numeroDocumento">Número Documento:</label>
        <input type="text" id="numeroDocumento" name="numeroDocumento" required><br><br>

        <label for="password">Contraseña:</label>
        <input type="password" id="password" name="password" required><br><br>

        <button type="submit">Ingresar</button>
    </form>
</body>
</html>
