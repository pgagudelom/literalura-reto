package com.alura.literalura.services;

public interface InterfaceConvierteDatos {
    <T> T getDatos(String json, Class<T> claseGenerica);
}
