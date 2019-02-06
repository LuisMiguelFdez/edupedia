<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8">
        <title>A Simple Page with CKEditor</title>

        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">  
        <link rel="stylesheet" href="css/reset meyer.css">
        <link rel="stylesheet" href="css/default.css">
        <script src="ckeditor/ckeditor.js"></script>
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
        <script>window.jQuery || document.write('<script src="js/331_jquery.min.js"><\/script>')</script>
        <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
        <%@page contentType="text/html" pageEncoding="UTF-8"%>

        <jsp:include page="cargaCombos"/>

        <script>
            $(document).ready(function () {
                // Replace the <textarea id="editor1"> with a CKEditor
                // instance, using default configuration.
                CKEDITOR.replace('editor1');

                $("form").submit(function () {
                    var data = CKEDITOR.instances.editor1.getData();
                    $("#contenido").val(data);

                });

                $("#cargar").click(function () {
                    alert("The paragraph was clicked.");
                });
            });


        </script>

    </head>
    <body>

        <form action="creaArticulo" method="POST" enctype="multipart/form-data">  

            Titulo <input type="text" name="titulo"><br>
            Titulo de imagen <input type="text" name="tituloImagen"><br>
            Imagen <input type="file" name="imagen"><br>
            Descripcion:<br>
            <textarea name="descripcion" cols="40" rows="5">
                
            </textarea>
            <br>
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

            <textarea name="editor1" id="editor1" rows="10" cols="80">
                This is my textarea to be replaced with CKEditor.
            </textarea>

            <textarea id="contenido" name="contenidoEditor" style="display: none">
                
            </textarea>
            <input type="submit" value="Crear articulo">
        </form>

        
        
        <script src="js/ajaxAsignaturas.js"></script>
    </body>
</html>