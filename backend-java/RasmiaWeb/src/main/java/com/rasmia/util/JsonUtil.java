package com.rasmia.util;

import com.google.gson.Gson;
import com.rasmia.model.Cliente;
import com.rasmia.model.PedidoLinea;
import com.rasmia.model.Producto;

import java.util.*;

public class JsonUtil {

    private static final Gson gson = new Gson();

    public static String pedidoToJson(
            int pedidoId,
            Cliente cliente,
            List<PedidoLinea> lineas,
            Map<Integer, Producto> productosById
    ) {
        Map<String, Object> root = new LinkedHashMap<>();

        // Campos “arriba” (por si tu plantilla los usa)
        root.put("pedidoId", "PED-" + pedidoId);
        root.put("numAlbaran", "ALB-" + pedidoId);
        root.put("numCliente", (cliente != null && cliente.getId() > 0) ? String.valueOf(cliente.getId()) : "SIN_CLIENTE");
        root.put("contacto", "Webshop España");
        root.put("fechaEnvio", "");
        root.put("incoterms", "");
        root.put("referencia", "");
        root.put("comentarios", "");

        // Cliente con el nombre que suele usar n8n (nombreEmpresa)
        Map<String, Object> cli = new LinkedHashMap<>();
        cli.put("nombreEmpresa", safe(cliente != null ? cliente.getEmpresa() : null, "SIN EMPRESA"));
        cli.put("direccion", safe(cliente != null ? cliente.getDireccion() : null, "SIN DIRECCIÓN"));
        cli.put("cp", safe(cliente != null ? cliente.getCp() : null, ""));
        cli.put("ciudad", safe(cliente != null ? cliente.getCiudad() : null, ""));
        cli.put("provincia", safe(cliente != null ? cliente.getProvincia() : null, ""));
        cli.put("pais", safe(cliente != null ? cliente.getPais() : null, "España"));
        root.put("cliente", cli);

        // Lineas enriquecidas para etiquetas/albarán
        List<Map<String, Object>> out = new ArrayList<>();
        if (lineas != null) {
            for (PedidoLinea l : lineas) {
                Producto p = (productosById != null) ? productosById.get(l.getProductoId()) : null;

                Map<String, Object> row = new LinkedHashMap<>();
                row.put("codigo", (p != null && !isBlank(p.getCodigo())) ? p.getCodigo() : ("PROD-" + l.getProductoId()));
                row.put("descripcion", (p != null)
                        ? safe(p.getNombre(), "Producto") + (isBlank(p.getDescripcion()) ? "" : (" - " + p.getDescripcion()))
                        : "Producto");
                row.put("cantidad", l.getCantidad());      // clave: cantidad
                row.put("unidad", "ud");
                row.put("pendiente", "");
                out.add(row);
            }
        }
        root.put("lineas", out);

        return gson.toJson(root);
    }

    private static String safe(String v, String def) {
        return isBlank(v) ? def : v;
    }

    private static boolean isBlank(String v) {
        return v == null || v.trim().isEmpty();
    }
}
