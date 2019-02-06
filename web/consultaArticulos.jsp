<%-- 
    Document   : index
    Created on : 21-ene-2019, 17:34:38
    Author     : lmfde
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">  
        <!--<link rel="stylesheet" href="css/smartphones.css">
        <link rel="stylesheet" href="css/tablets.css">
        <link rel="stylesheet" href="css/ordenadores.css.css">
        <link rel="stylesheet" href="css/widescreen.css">-->
        <link rel="stylesheet" href="css/reset meyer.css">
        <link rel="stylesheet" href="css/default.css">
        <title>Resultado de búsquedas</title>
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
                <jsp:include page="buscaArticulos"/>
                <c:choose>
                    <c:when test="${!empty requestScope.articulosEncontrados && requestScope.articulosEncontrados!=null}">
                        <h1>${requestScope.mensaje}</h1>
                        <c:forEach var="articulo" items="${requestScope.articulosEncontrados}" varStatus="estado">
                            <div class="resultadoConsulta" onclick="location.href='${articulo.url}'">
                                <h2>${articulo.titulo}</h2>
                                <h3>${articulo.descripcion}</h3>
                                <img src="img/${articulo.imagen}" alt="${articulo.tituloImagen}">
                            </div>
                        </c:forEach>
                    </c:when>
                    <c:otherwise>
                        <h2>No hemos encontrado ningún artículo</h2>
                        <h3>¡Se el primero en aportar algo!</h3>
                    </c:otherwise>
                </c:choose>
            </main>
        </div>
    </body>
</html>
