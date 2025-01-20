package com.simulador.credito.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.experimental.UtilityClass;

import java.nio.file.Files;
import java.nio.file.Paths;

@UtilityClass
public class EmprestimoUtils {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    public <T> T getObjectFromJson(String json, Class<T> targetClass) {
        try {
            objectMapper.registerModule(new JavaTimeModule());
            return objectMapper.readValue(json, targetClass);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao converter json para objeto", e);
        }
    }

    public String getJsonFromPath(String filePath) {
        try {
            return new String(Files.readAllBytes(Paths.get(filePath)));
        } catch (Exception e) {
            throw new RuntimeException("Erro ao ler json", e);
        }
    }
}
