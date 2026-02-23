package com.rasmia.util;

import javax.servlet.ServletContext;
import java.nio.file.Path;

public class PdfStorageUtil {

    // Carpeta dentro del proyecto desplegado (Tomcat)
    private static final String BASE_DIR = "/pdf";

    public static Path albaranPath(ServletContext ctx, int pedidoId) {
        String realBase = ctx.getRealPath(BASE_DIR);
        return Path.of(realBase, "albaran-" + pedidoId + ".pdf");
    }

    public static Path etiquetasPath(ServletContext ctx, int pedidoId) {
        String realBase = ctx.getRealPath(BASE_DIR);
        return Path.of(realBase, "etiquetas-" + pedidoId + ".pdf");
    }
}
