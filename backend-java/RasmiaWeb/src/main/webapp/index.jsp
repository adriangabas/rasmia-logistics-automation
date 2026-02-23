<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="java.util.*" %>
<%@ page import="com.rasmia.model.Producto" %>

<%
    // Si alguien entra directamente a "/" (index.jsp) sin pasar por el servlet,
    // redirigimos a /tienda para que el servlet cargue los productos y vuelva aquí con el atributo.
    if (request.getAttribute("productos") == null) {
        response.sendRedirect(request.getContextPath() + "/tienda");
        return;
    }
%>

<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Rasmia - Logística Circular</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
</head>
<body>

<div class="top-bar">
    <div class="container">
        <div class="contact-info">
            <a href="mailto:info@conrasmia.es"><i class="fas fa-envelope"></i> info@conrasmia.es</a>
            <a href="tel:976817753"><i class="fas fa-phone"></i> 976 817 753</a>
        </div>
    </div>
</div>

<header class="main-header">
    <div class="container">
        <div class="logo">
            <img src="${pageContext.request.contextPath}/assets/logo.png" alt="Rasmia Logo">
            <div class="logo-text">RASMIA <span>logística circular</span></div>
        </div>

        <div class="search-bar">
            <input type="text" placeholder="¿Qué buscas?">
            <button type="submit"><i class="fas fa-search"></i></button>
        </div>

        <div class="header-icons">
            <a class="btn" style="width:auto; text-decoration:none; padding:10px 14px;"
               href="<%=request.getContextPath()%>/checkout">
                <i class="fas fa-shopping-cart"></i> Checkout
            </a>
        </div>
    </div>
</header>

<nav class="main-nav">
    <div class="container">
        <ul>
            <li class="dropdown"><a href="#">PRODUCTOS</a></li>
            <li class="dropdown"><a href="#">SERVICIOS</a></li>
            <li class="dropdown"><a href="#">SOSTENIBILIDAD</a></li>
            <li><a href="#">ALQUILER</a></li>
            <li><a href="#">OUTLET</a></li>
            <li><a href="#">QUIÉNES SOMOS</a></li>
            <li><a href="#">BLOG</a></li>
            <li><a href="#">CONTACTO</a></li>
        </ul>
    </div>
</nav>

<div class="trust-badges">
    <div class="container">
        <ul>
            <li><i class="fas fa-check-circle"></i> Calidad garantizada</li>
            <li><i class="fas fa-credit-card"></i> Múltiples métodos de pago disponibles</li>
            <li><i class="fas fa-undo"></i> Política de devolución de 14 días</li>
            <li><i class="fas fa-truck"></i> Envío gratis a partir de 350€ sin IVA en Península</li>
        </ul>
    </div>
</div>

<main class="product-page">
    <div class="container">
        <div class="breadcrumb">
            <a href="<%=request.getContextPath()%>/tienda">Inicio</a> / <span>Tienda</span>
        </div>

        <div class="page-title">
            <h1>Tienda</h1>
            <p>Productos disponibles</p>
        </div>

        <div class="product-grid-container">
            <div class="sidebar">
                <h3>Acciones</h3>
                <a class="btn" style="text-decoration:none; display:block; text-align:center;"
                   href="<%=request.getContextPath()%>/checkout">
                    IR A CHECKOUT
                </a>
            </div>

            <div class="product-grid">
                <%
                    List<Producto> productos = (List<Producto>) request.getAttribute("productos");
                    if (productos == null || productos.isEmpty()) {
                %>
                <div style="grid-column: 1 / -1; padding: 20px; border: 1px solid #eee; border-radius: 4px;">
                    No hay productos activos en la base de datos.
                </div>
                <%
                } else {
                    for (Producto p : productos) {
                %>
                <div class="product">
                    <%
                        Map<String, String> imagenes = new HashMap<>();
                        imagenes.put("RAS-001", "caja.png");
                        imagenes.put("RAS-002", "palet.png");
                        imagenes.put("RAS-003", "bandeja_antigoteo.png");

                        String imagen = imagenes.getOrDefault(p.getCodigo(), "caja.png");
                    %>

                    <img src="<%=request.getContextPath()%>/assets/<%=imagen%>"
                         alt="<%=p.getNombre()%>">

                    <div class="product-content">
                        <h3><%=p.getNombre()%></h3>
                        <div class="product-rating">★★★★★</div>
                        <p class="product-price"><%=p.getPrecio()%> € sin IVA</p>

                        <a class="btn" style="text-decoration:none; display:block; text-align:center;"
                           href="<%=request.getContextPath()%>/checkout">
                            COMPRAR
                        </a>
                    </div>
                </div>

                <%
                        }
                    }
                %>
            </div>
        </div>
    </div>
</main>

<footer class="main-footer">
    <div class="container">
        <div class="footer-bottom">
            <p>Rasmia © 2026</p>
        </div>
    </div>
</footer>

</body>
</html>
