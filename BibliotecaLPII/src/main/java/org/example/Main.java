package org.example;

import com.sun.net.httpserver.HttpServer;
import java.net.InetSocketAddress;
import org.example.http.AutorHandler;
import org.example.http.LivroHandler;

public class Main {
    public static void main(String[] args) throws Exception {
        int port = 8080;
        HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);

        server.createContext("/autores", new AutorHandler());
        server.createContext("/autores/", new AutorHandler()); 
        server.createContext("/livros", new LivroHandler());
        server.createContext("/livros/", new LivroHandler());

        server.setExecutor(java.util.concurrent.Executors.newFixedThreadPool(8));
        server.start();
        System.out.println("Server started on http://localhost:" + port);
    }
}