package org.example.http;

import org.example.dao.LivroDAO;
import org.example.model.Livro;
import org.example.service.JsonUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.*;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.util.List;

public class LivroHandler implements HttpHandler {
    private final LivroDAO dao = new LivroDAO();
    private final ObjectMapper mapper = JsonUtil.MAPPER;

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String method = exchange.getRequestMethod();
        String path = exchange.getRequestURI().getPath();
        try {
            if ("GET".equalsIgnoreCase(method) && path.matches("^/livros/?$")) {
                List<Livro> list = dao.findAll();
                sendJson(exchange, 200, mapper.writeValueAsString(list));
                return;
            }
            if ("POST".equalsIgnoreCase(method) && path.matches("^/livros/?$")) {
                String body = new String(exchange.getRequestBody().readAllBytes(), StandardCharsets.UTF_8);
                Livro l = JsonUtil.readLivroFromJson(body);
                Livro created = dao.create(l);
                sendJson(exchange, 201, mapper.writeValueAsString(created));
                return;
            }
            if ("GET".equalsIgnoreCase(method) && path.matches("^/livros/\\d+$")) {
                int id = Integer.parseInt(path.substring(path.lastIndexOf('/') + 1));
                Livro l = dao.findById(id);
                if (l == null) { sendText(exchange,404,"Not found"); return; }
                sendJson(exchange,200,mapper.writeValueAsString(l));
                return;
            }
            if ("PUT".equalsIgnoreCase(method) && path.matches("^/livros/\\d+$")) {
                int id = Integer.parseInt(path.substring(path.lastIndexOf('/') + 1));
                String body = new String(exchange.getRequestBody().readAllBytes(), StandardCharsets.UTF_8);
                Livro l = JsonUtil.readLivroFromJson(body);
                boolean ok = dao.update(id, l);
                sendText(exchange, ok?200:404, ok? "Updated":"Not found");
                return;
            }
            if ("DELETE".equalsIgnoreCase(method) && path.matches("^/livros/\\d+$")) {
                int id = Integer.parseInt(path.substring(path.lastIndexOf('/') + 1));
                boolean ok = dao.delete(id);
                sendText(exchange, ok?200:404, ok? "Deleted":"Not found");
                return;
            }

            sendText(exchange, 404, "Not found");
        } catch (SQLException e) {
            e.printStackTrace();
            sendText(exchange, 500, "DB error: " + e.getMessage());
        } catch (Exception ex) {
            ex.printStackTrace();
            sendText(exchange, 400, "Bad request: " + ex.getMessage());
        }
    }

    private void sendJson(HttpExchange ex, int status, String json) throws IOException {
        byte[] bytes = json.getBytes(StandardCharsets.UTF_8);
        ex.getResponseHeaders().set("Content-Type", "application/json; charset=utf-8");
        ex.sendResponseHeaders(status, bytes.length);
        try (OutputStream os = ex.getResponseBody()) { os.write(bytes); }
    }

    private void sendText(HttpExchange ex, int status, String msg) throws IOException {
        byte[] b = msg.getBytes(StandardCharsets.UTF_8);
        ex.getResponseHeaders().set("Content-Type", "text/plain; charset=utf-8");
        ex.sendResponseHeaders(status, b.length);
        try (OutputStream os = ex.getResponseBody()) { os.write(b); }
    }
}
