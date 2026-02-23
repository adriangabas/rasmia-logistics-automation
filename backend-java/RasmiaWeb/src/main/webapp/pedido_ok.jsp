<%@ page contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Pedido creado</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css">
</head>
<body>

<div class="container" style="padding:40px 20px;">
    <h1>✅ Pedido creado</h1>

    <%
        Object pedidoIdObj = request.getAttribute("pedidoId");
        String pedidoId = (pedidoIdObj != null) ? pedidoIdObj.toString() : request.getParameter("pedidoId");
        if (pedidoId == null) pedidoId = "";
    %>

    <p>Pedido ID: <b><%= pedidoId %></b></p>

    <div style="margin-top:20px; display:flex; gap:12px; flex-wrap:wrap;">

        <!-- ✅ DESCARGA DEL PDF YA GUARDADO (BUENO) -->
        <a class="btn" style="width:auto; text-decoration:none;"
           href="<%=request.getContextPath()%>/download-pdf?pedidoId=<%=pedidoId%>&type=albaran">
            Descargar albarán
        </a>

        <!-- ✅ LO MISMO PARA ETIQUETAS -->
        <a class="btn" style="width:auto; text-decoration:none;"
           href="<%=request.getContextPath()%>/download-pdf?pedidoId=<%=pedidoId%>&type=etiquetas">
            Descargar etiquetas
        </a>

        <a class="btn" style="width:auto; text-decoration:none;"
           href="<%=request.getContextPath()%>/tienda">
            Volver a tienda
        </a>

    </div>
</div>

</body>
</html>
