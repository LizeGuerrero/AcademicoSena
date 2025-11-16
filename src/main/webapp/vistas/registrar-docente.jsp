<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Registrar Docente</title>
    <style>
        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
        }
        
        body {
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            min-height: 100vh;
            padding: 20px;
        }
        
        .container {
            max-width: 600px;
            margin: 0 auto;
            background: white;
            padding: 30px;
            border-radius: 10px;
            box-shadow: 0 10px 40px rgba(0, 0, 0, 0.2);
        }
        
        h1 {
            color: #333;
            margin-bottom: 30px;
            border-bottom: 3px solid #667eea;
            padding-bottom: 10px;
        }
        
        .form-group {
            margin-bottom: 20px;
        }
        
        label {
            display: block;
            margin-bottom: 8px;
            color: #333;
            font-weight: 500;
            font-size: 14px;
        }
        
        input, select {
            width: 100%;
            padding: 12px;
            border: 1px solid #ddd;
            border-radius: 5px;
            font-size: 14px;
            font-family: inherit;
        }
        
        input:focus, select:focus {
            outline: none;
            border-color: #667eea;
            box-shadow: 0 0 5px rgba(102, 126, 234, 0.3);
        }
        
        .form-row {
            display: grid;
            grid-template-columns: 1fr 1fr;
            gap: 15px;
        }
        
        button {
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            color: white;
            padding: 12px 30px;
            border: none;
            border-radius: 5px;
            cursor: pointer;
            font-size: 16px;
            font-weight: 600;
        }
        
        button:hover {
            transform: translateY(-2px);
            box-shadow: 0 5px 20px rgba(102, 126, 234, 0.4);
        }
        
        .btn-group {
            display: flex;
            gap: 10px;
            margin-top: 30px;
        }
        
        .btn-group button {
            flex: 1;
        }
        
        .btn-back {
            background: #6c757d;
        }
        
        .alert {
            padding: 15px;
            margin-bottom: 20px;
            border-radius: 5px;
            display: none;
        }
        
        .alert-success {
            background: #d4edda;
            color: #155724;
            border: 1px solid #c3e6cb;
        }
        
        .alert-error {
            background: #f8d7da;
            color: #721c24;
            border: 1px solid #f5c6cb;
        }
    </style>
</head>
<body>
    <div class="container">
        <h1>👨‍🏫 Registrar Nuevo Docente</h1>
        
        <div id="alertSuccess" class="alert alert-success"></div>
        <div id="alertError" class="alert alert-error"></div>
        
        <form id="formDocente">
            <div class="form-row">
                <div class="form-group">
                    <label for="tipoDocumento">Tipo de Documento *</label>
                    <select id="tipoDocumento" name="tipoDocumento" required>
                        <option value="">Selecciona...</option>
                        <option value="CC">CC - Cédula de Ciudadanía</option>
                        <option value="CE">CE - Cédula de Extranjería</option>
                        <option value="PEP">PEP - Pasaporte</option>
                    </select>
                </div>
                
                <div class="form-group">
                    <label for="numeroDocumento">Número de Documento *</label>
                    <input type="text" id="numeroDocumento" name="numeroDocumento" required>
                </div>
            </div>
            
            <div class="form-row">
                <div class="form-group">
                    <label for="nombres">Nombres *</label>
                    <input type="text" id="nombres" name="nombres" required>
                </div>
                
                <div class="form-group">
                    <label for="apellidos">Apellidos *</label>
                    <input type="text" id="apellidos" name="apellidos" required>
                </div>
            </div>
            
            <div class="form-group">
                <label for="email">Email Institucional *</label>
                <input type="email" id="email" name="email" required>
            </div>
            
            <div class="form-group">
                <label for="telefono">Teléfono</label>
                <input type="tel" id="telefono" name="telefono">
            </div>
            
            <div class="form-group">
                <label for="tituloProfesional">Título Profesional</label>
                <input type="text" id="tituloProfesional" name="tituloProfesional" placeholder="Ej: Ingeniero en Sistemas">
            </div>
            
            <div class="form-group">
                <label for="especialidad">Especialidad</label>
                <input type="text" id="especialidad" name="especialidad" placeholder="Ej: Bases de Datos">
            </div>
            
            <div class="btn-group">
                <button type="submit" onclick="registrarDocente()">Registrar Docente</button>
                <button type="button" class="btn-back" onclick="window.location.href='../index.jsp'">Volver</button>
            </div>
        </form>
    </div>
    
    <script>
        function registrarDocente() {
            event.preventDefault();
            
            const datos = {
                idInstitucion: 1,
                tipoDocumento: document.getElementById('tipoDocumento').value,
                numeroDocumento: document.getElementById('numeroDocumento').value,
                nombres: document.getElementById('nombres').value,
                apellidos: document.getElementById('apellidos').value,
                email: document.getElementById('email').value,
                telefono: document.getElementById('telefono').value,
                tituloProfesional: document.getElementById('tituloProfesional').value,
                especialidad: document.getElementById('especialidad').value
            };
            
            fetch('/AcademicoSena/api/docentes', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(datos)
            })
            .then(response => response.json())
            .then(data => {
                if (data.mensaje) {
                    showAlert('success', '✓ ' + data.mensaje);
                    document.getElementById('formDocente').reset();
                } else {
                    showAlert('error', '✗ Error: ' + data.error);
                }
            })
            .catch(error => {
                showAlert('error', '✗ Error de conexión: ' + error.message);
            });
        }
        
        function showAlert(type, message) {
            const successAlert = document.getElementById('alertSuccess');
            const errorAlert = document.getElementById('alertError');
            
            if (type === 'success') {
                successAlert.textContent = message;
                successAlert.style.display = 'block';
                errorAlert.style.display = 'none';
                setTimeout(() => successAlert.style.display = 'none', 5000);
            } else {
                errorAlert.textContent = message;
                errorAlert.style.display = 'block';
                successAlert.style.display = 'none';
                setTimeout(() => errorAlert.style.display = 'none', 5000);
            }
        }
    </script>
</body>
</html>