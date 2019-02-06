<%-- 
    Document   : asideNavegacion
    Created on : 04-feb-2019, 17:23:56
    Author     : lmfde
--%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<aside id="lateral">
    <nav role="navegator">
        <ul>
            <li><a href="index.jsp">Inicio</a></li>
            <c:choose>
                <c:when test="${!empty sessionScope.usuario}">
                    <li><a href="userPanel.jsp">Mi sitio</a></li>
                </c:when>
            </c:choose>
            <li>
                Cursos
                <ul>
                    <c:forEach var="curso" items="${requestScope.cursos}">
                        <li><a href="consultaArticulos.jsp?filtroCurso=${curso.codCursos}">${curso.nombreCurso}</a></li>
                        </c:forEach>  
                </ul>
            </li>
            <li>
                Asignaturas
                <ul>
                    <c:forEach var="asig" items="${requestScope.asignaturas}">
                        <li><a href="consultaArticulos.jsp?filtroAsig=${asig.codAsignatura}">${asig.nombreAsignatura}</a></li>
                        </c:forEach>  
                </ul>
            </li>
            <li>
                Idiomas
                <ul>
                    <li><a href="#">Frances</a></li>  
                    <li><a href="#">Español</a></li>  
                    <li><a href="#">Inglés</a></li>  
                </ul>
            </li>
            <li><a href="contactos.jsp">Contactanos</a></li>
            <li><a href="descargaProyecto.jsp">Descargue el proyecto</a></li>
        </ul>
    </nav>
</aside>

