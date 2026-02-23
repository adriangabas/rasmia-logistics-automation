package com.rasmia.service;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

public class N8nService {

    private final HttpClient client = HttpClient.newBuilder()
            .connectTimeout(Duration.ofSeconds(5))
            .version(HttpClient.Version.HTTP_1_1) // evita problemas con chunked/transfer-encoding
            .build();

    public byte[] generarPdfBytes(String webhookUrl, String json) throws Exception {

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(webhookUrl))
                .timeout(Duration.ofSeconds(180))
                .header("Content-Type", "application/json")
                .header("Accept", "application/pdf")
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .build();

        HttpResponse<InputStream> response =
                client.send(request, HttpResponse.BodyHandlers.ofInputStream());

        int status = response.statusCode();
        String contentType = response.headers().firstValue("content-type").orElse("");

        if (status < 200 || status >= 300) {
            String err = readAllToString(response.body());
            throw new RuntimeException("n8n respondió HTTP " + status + ". Body=" + err);
        }

        if (!contentType.toLowerCase().contains("application/pdf")) {
            String err = readAllToString(response.body());
            throw new RuntimeException("Respuesta no es PDF. Content-Type=" + contentType + ". Body=" + err);
        }

        return readAllBytes(response.body());
    }

    private byte[] readAllBytes(InputStream in) throws Exception {
        try (in; ByteArrayOutputStream bos = new ByteArrayOutputStream()) {
            byte[] buffer = new byte[8192];
            int n;
            while ((n = in.read(buffer)) != -1) {
                bos.write(buffer, 0, n);
            }
            return bos.toByteArray();
        }
    }

    private String readAllToString(InputStream in) throws Exception {
        return new String(readAllBytes(in));
    }
}
