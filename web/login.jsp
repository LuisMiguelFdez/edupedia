<%-- 
    Document   : index
    Created on : 28-ene-2019, 11:58:46
    Author     : Alejandro
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Inicio de Sesión</title>
        <link rel="stylesheet" href="css/reset meyer.css">
        <link rel="stylesheet" href="css/default.css">
        <link rel="stylesheet" href="css/estiloLogin.css">
    </head>
    <body>
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
        <script>window.jQuery || document.write('<script src="js/331_jquery.min.js"><\/script>')</script>
        <script src="js/ajaxAsignaturas.js"></script>
        <jsp:include page="cargaCombos"/>
        <jsp:include page="PlantillasJSP/cabecera.jsp"/>
        <div class="flex-box">
            <jsp:include page="PlantillasJSP/asideNavegacion.jsp"/>
            <main class="login">
                <form action="login" method="POST">
                    <h2>Inicio de sesión</h2>
                    <label for="email">Email: </label>
                    <input id="email" type="text" name="email">
                    <label for="contra">Contraseña: </label>
                    <input id="contra" type="password" name="pass">
                    <input type="submit" value="Acceder">
                    <p class="mensaje">${requestScope.mensaje}</p>
                </form>
            </main>
        </div>
    </body>
</html>
