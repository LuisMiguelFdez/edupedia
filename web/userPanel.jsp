<%-- 
    Document   : userPanel
    Created on : 04-feb-2019, 13:37:49
    Author     : Alejandro
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Panel de usuario</title>
        <jsp:include page="userPanel.VerAportaciones"/>
    </head>
    <body>
        
        
        <form action="" method="POST">
            Nombre:<input type="text" name="nombre" value="${sessionScope.usuario.nombre}">
            Apellidos:<input type="text" name="apellidos" value="${sessionScope.usuario.apellidos}"><br>
            Contraseña actual:<input type="password" name="oldPass">
            Nueva contraseña<input type="password" name="newPass" disabled><br>
            Tipo de cuenta:<input type="text" value="${sessionScope.usuario.rol}" disabled><br>
        </form>
        
       
   
        <c:forEach var="art" items="${requestScope.aportaciones}">
            ${art} <br>
        </c:forEach>
        
        
    </body>
</html>
