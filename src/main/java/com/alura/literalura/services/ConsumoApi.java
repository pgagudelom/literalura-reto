package com.alura.literalura.services;

import ch.qos.logback.core.net.server.Client;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class ConsumoApi {

    public String obtieneDatos(String url) {
        HttpClient cliente = HttpClient.newHttpClient();
        HttpRequest solicitud = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .build();

        HttpResponse<String> respuesta = null;
        try{
            respuesta = cliente.send(solicitud, HttpResponse.BodyHandlers.ofString());
        } catch(IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }

        String json = respuesta.body();
        return json;
    }
}