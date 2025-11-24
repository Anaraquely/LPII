package org.example.db;

import java.sql.*;

public class Database {
    private static final String URL = "jdbc:h2:./data/librarydb;AUTO_SERVER=TRUE";
    private static final String USER = "sa";
    private static final String PASS = "";

    static {
        try {
            init();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASS);
    }

    private static void init() throws SQLException {
        try (Connection c = getConnection();
             Statement s = c.createStatement()) {
            s.execute("""
                CREATE TABLE IF NOT EXISTS pessoa (
                    id IDENTITY PRIMARY KEY,
                    nome VARCHAR(255) NOT NULL
                );
                """);

            s.execute("""
                CREATE TABLE IF NOT EXISTS autor (
                    id IDENTITY PRIMARY KEY,
                    pessoa_id BIGINT UNIQUE,
                    nacionalidade VARCHAR(100),
                    FOREIGN KEY (pessoa_id) REFERENCES pessoa(id) ON DELETE CASCADE
                );
                """);

            s.execute("""
                CREATE TABLE IF NOT EXISTS livro (
                    id IDENTITY PRIMARY KEY,
                    titulo VARCHAR(255) NOT NULL,
                    ano_publicacao INT,
                    autor_id BIGINT,
                    tipo VARCHAR(20),
                    tamanho_arquivo_mb DOUBLE,
                    formato VARCHAR(50),
                    numero_paginas INT,
                    peso_gramas DOUBLE,
                    FOREIGN KEY (autor_id) REFERENCES autor(id) ON DELETE SET NULL
                );
                """);
        }
    }
}