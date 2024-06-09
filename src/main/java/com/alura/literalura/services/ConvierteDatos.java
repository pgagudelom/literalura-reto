package com.alura.literalura.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ConvierteDatos implements InterfaceConvierteDatos{

    private ObjectMapper mapper = new ObjectMapper();

    @Override
    public <T> T getDatos(String json, Class<T> claseGenerica){
        try{
            return mapper.readValue(json, claseGenerica);
        } catch(JsonProcessingException e){
            throw new RuntimeException(e);
        }
    }
}
