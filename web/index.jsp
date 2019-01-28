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
        <title>Página Principal Edupedia</title>
    </head>
    <body>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    <script>window.jQuery || document.write('<script src="js/331_jquery.min.js"><\/script>')</script>
        <jsp:include page="cargaCombos"/>
        <header>
            <div id="banner" role="banner">
                <h2>Edupedia</h2>
                <p>Al encuentro del saber</p>
            </div>
            <form action="#" method="GET" id="formularioBusqueda">
                <input type="search" name="busqueda" placeholder="Búsqueda" role="search">
                <select name="filtroCurso" id="filtroCurso">
                    <option value="0">Todos los cursos</option>
                    <c:forEach var="curso" items="${requestScope.cursos}">
                         <option value="${curso.codCursos}">${curso.nombreCurso}</option>
                    </c:forEach>
                </select>
                <select name="filtroAsig" id="filtroAsig">
                    <option value="0">Todas las asignaturas</option>
                    <c:forEach var="asig" items="${requestScope.asignaturas}">
                         <option value="${asig.codAsignatura}">${asig.nombreAsignatura}</option>
                    </c:forEach>
                </select>
            </form>
            
            <section>
                <img id="iconUsuario" src="img/UsuarioGenerico.png" alt="icono del usuario registrado">
                <a href="#">Iniciar Sesión</a>
            </section>
        </header>
        
        <aside id="lateral">
            <nav role="navegator">
                <ul>
                    <li><a href="#">Inicio</a></li>
                    <li><a href="#">Asignaturas</a></li>
                    <li><a href="#">Cursos</a></li>
                    <li><a href="#">Idiomas--</a></li>
                    <li><a href="#">Español</a></li>
                </ul>
            </nav>
        </aside>
        
        <main>
            <section>
                <article>
                    <img src="img/imagenGenerica.jpg" alt="imagen generica">
                    <p>Lorem ipsum dolor sit amet.....</p>
                </article>
                <article>
                    <img src="img/imagenGenerica.jpg" alt="imagen generica">
                    <p>Lorem ipsum dolor sit amet.....</p>
                </article>
                <article>
                    <img src="img/imagenGenerica.jpg" alt="imagen generica">
                    <p>Lorem ipsum dolor sit amet.....</p>
                </article>
            </section>
            <article id="cargaHTML">
                .......................................
            </article>
        </main>
        <script src="js/ajaxAsignaturas.js"></script>
    </body>
</html>
