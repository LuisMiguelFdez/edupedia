<%-- 
    Document   : index
    Created on : 21-ene-2019, 17:34:38
    Author     : lmfde
--%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">  
        <link rel="stylesheet" href="css/reset meyer.css">
        <link rel="stylesheet" href="css/default.css">
        <link rel="stylesheet" href="css/estiloRecientes.css">
        <title>Página Principal Edupedia</title>
    </head>
    <body>
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
        <script>window.jQuery || document.write('<script src="js/331_jquery.min.js"><\/script>')</script>
        <script src="js/ajaxAsignaturas.js"></script>
        <jsp:include page="cargaCombos"/>
        <jsp:include page="PlantillasJSP/cabecera.jsp"/>
        <div class="flex-box">
            <jsp:include page="PlantillasJSP/asideNavegacion.jsp"/>
            <main>
                <jsp:include page="cargaAleatorioyRecientes"/>
                <c:choose>
                    <c:when test="${!empty requestScope.ultimos}">
                        <c:forEach var="articulo" items="${requestScope.ultimos}">
                            <div class="novedad" onclick="location.href='${articulo.url}'">
                                <h2>${articulo.titulo}</h2>
                                <img src="img/${articulo.imagen}" alt="${articulo.tituloImagen}">
                            </div>
                        </c:forEach>
                        <div class="aleatorio">
                            ${requestScope.articulo}
                        </div>
                    </c:when>
                    <c:otherwise>
                        <h1>No tenemos ningún articulo disponible en estos momentos, se el primero en aportar. </h1>
                    </c:otherwise>
                </c:choose>
            </main>
        </div>
    </body>
</html>
