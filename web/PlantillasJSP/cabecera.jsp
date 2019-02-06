<%-- 
    Document   : cabecera
    Created on : 04-feb-2019, 17:22:24
    Author     : lmfde
--%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<header>
            <div id="banner" role="banner">
                <h2>Edupedia</h2>
                <p>Al encuentro del saber</p>
            </div>
            <form action="consultaArticulos.jsp" method="GET" id="formularioBusqueda">
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
                <c:choose>
                    <c:when test="${empty sessionScope.usuario}">
                        <a href="login.jsp"><img id="iconUsuario" src="img/UsuarioGenerico.png" alt="icono del usuario registrado"></a>
                        <a href="login.jsp">Iniciar Sesión</a>
                    </c:when>
                    <c:otherwise>
                        <!--<a href="userPanel.jsp"><img id="iconUsuario" src="imgPerfiles/${sessionScope.usuario}" alt="icono del usuario registrado"></a>-->
                        <a href="userPanel.jsp"><img id="iconUsuario" src="imgPerfiles/alumnoGenerico.jpg" alt="icono del usuario registrado"></a>

                        <form action="CerrarSesion">
                            <input type="hidden" name="usuario" value="${sessionScope.usuario}">
                            <input id="submitbutton" type="submit" value="Cerrar Sesion">
                        </form>
                    </c:otherwise>
                </c:choose>
            </section>
        </header>
