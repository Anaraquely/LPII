package org.example.service;

import org.example.model.Livro;
import org.example.model.LivroDigital;
import org.example.model.LivroFisico;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class JsonUtil {
    public static final ObjectMapper MAPPER = new ObjectMapper();
    public static Livro readLivroFromJson(String json) throws IOException {
        JsonNode node = MAPPER.readTree(json);
        String tipo = node.has("tipo") ? node.get("tipo").asText() : null;
        if ("digital".equalsIgnoreCase(tipo)) {
            return MAPPER.treeToValue(node, LivroDigital.class);
        } else if ("fisico".equalsIgnoreCase(tipo)) {
            return MAPPER.treeToValue(node, LivroFisico.class);
        } else {
            return MAPPER.treeToValue(node, Livro.class);
        }
    }
}
