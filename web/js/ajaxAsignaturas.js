$("#filtroCurso").on("change",function(){
    $.ajax({
        data:"curso="+$("#filtroCurso").val(),
        success: function (data, textStatus, jqXHR) {
            $("#filtroAsig").empty();
            $("#filtroAsig").append($("<option>").attr("value",0).text("Todas las asignaturas"));
            $.each(data,function(indice,valor){
                $("#filtroAsig").append($("<option>").attr("value",indice).text(valor));
            });
        },
        error: function (jqXHR, textStatus, errorThrown) {
            alert("Error");
        },
        url:"cambiaAsignaturas",
        async: true,
        statusCode: {
            404:function(){alert("error en ajax");},
            500:function(){alert("error del servidor");},
        }
    });
})

