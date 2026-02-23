<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="java.util.*" %>
<%@ page import="com.rasmia.model.Producto" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Rasmia - Checkout</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">




    <style>
        .card { border:1px solid #eee; border-radius:6px; padding:20px; background:#fff; }
        .row2 { display:grid; grid-template-columns: 1fr 1fr; gap: 25px; }
        label { font-size:14px; display:block; margin-bottom:6px; }
        input { width:100%; padding:10px 12px; border:1px solid #ccc; border-radius:4px; }
        table { width:100%; border-collapse:collapse; }
        th, td { padding:12px; border-bottom:1px solid #eee; font-size:14px; }
        th:last-child, td:last-child { text-align:right; }
    </style>
</head>
<body>

<main class="product-page">
    <div class="container">
        <div class="page-title">
            <h1>Checkout</h1>
            <p>Completa los datos y confirma el pedido</p>
        </div>

        <form method="post" action="<%=request.getContextPath()%>/crear-pedido">
            <div class="row2">
                <div class="card">
                    <h3 style="margin-bottom:12px;">Cliente</h3>

                    <label>Nombre *</label>
                    <input name="nombre" required>

                    <label style="margin-top:10px;">Apellidos</label>
                    <input name="apellidos">

                    <label style="margin-top:10px;">Empresa</label>
                    <input name="empresa">

                    <label style="margin-top:10px;">Teléfono</label>
                    <input name="telefono">

                    <label style="margin-top:10px;">Email</label>
                    <input name="email" type="email">

                    <h3 style="margin:20px 0 12px;">Dirección</h3>

                    <label>Dirección *</label>
                    <input name="direccion" required>

                    <label style="margin-top:10px;">CP *</label>
                    <input name="cp" required>

                    <label style="margin-top:10px;">Ciudad *</label>
                    <input name="ciudad" required>

                    <label style="margin-top:10px;">Provincia *</label>
                    <input name="provincia" required>

                    <label style="margin-top:10px;">País</label>
                    <input name="pais" value="España">

                    <div style="margin-top:18px;">
                        <button class="btn" type="submit">CONFIRMAR PEDIDO</button>
                    </div>
                </div>

                <div class="card">
                    <h3 style="margin-bottom:12px;">Productos</h3>
                    <p style="color:#777; margin-bottom:12px;">Pon cantidad (0 = no se añade)</p>

                    <table>
                        <thead>
                        <tr>
                            <th>Producto</th>
                            <th>Precio</th>
                            <th>Cant.</th>
                        </tr>
                        </thead>
                        <tbody>
                        <%
                            List<Producto> productos = (List<Producto>) request.getAttribute("productos");
                            if (productos != null) {
                                for (Producto p : productos) {
                        %>
                        <tr>
                            <td>
                                <div style="font-weight:600;"><%=p.getNombre()%></div>
                                <div style="color:#777;"><%=p.getCodigo()%></div>
                            </td>
                            <td><%=p.getPrecio()%> €</td>
                            <td>
                                <input type="number" min="0" value="0" name="qty_<%=p.getId()%>" style="text-align:right;">
                            </td>
                        </tr>
                        <%
                                }
                            }
                        %>
                        </tbody>
                    </table>
                </div>
            </div>
        </form>

        <div style="margin-top:20px;">
            <a class="btn" style="width:auto; text-decoration:none;" href="<%=request.getContextPath()%>/tienda">
                Volver a tienda
            </a>
        </div>
    </div>
</main>

</body>
</html>
