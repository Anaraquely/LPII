package org.example.dao;
import org.example.db.Database;
import org.example.model.Autor;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AutorDAO extends BaseDAO<Autor> {

    public Autor create(Autor autor) throws SQLException {
        try (Connection c = Database.getConnection()) {
            // inserir pessoa
            long pessoaId;
            try (PreparedStatement ps = c.prepareStatement(
                    "INSERT INTO pessoa(nome) VALUES(?)", Statement.RETURN_GENERATED_KEYS)) {
                ps.setString(1, autor.getNome());
                ps.executeUpdate();
                ResultSet rs = ps.getGeneratedKeys();
                rs.next();
                pessoaId = rs.getLong(1);
            }

            // inserir autor, referenciando pessoa
            try (PreparedStatement ps = c.prepareStatement(
                    "INSERT INTO autor(pessoa_id, nacionalidade) VALUES(?, ?)", Statement.RETURN_GENERATED_KEYS)) {
                ps.setLong(1, pessoaId);
                ps.setString(2, autor.getNacionalidade());
                ps.executeUpdate();
                ResultSet rs = ps.getGeneratedKeys();
                rs.next();
                int autorId = rs.getInt(1);
                autor.setId(autorId);
                return autor;
            }
        }
    }

    public Autor findById(int id) throws SQLException {
        try (Connection c = Database.getConnection();
             PreparedStatement ps = c.prepareStatement(
                 "SELECT a.id as aid, p.id as pid, p.nome, a.nacionalidade " +
                 "FROM autor a JOIN pessoa p ON a.pessoa_id = p.id WHERE a.id = ?")) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Autor a = new Autor();
                a.setId(rs.getInt("aid"));
                a.setNome(rs.getString("nome"));
                a.setNacionalidade(rs.getString("nacionalidade"));
                return a;
            } else return null;
        }
    }

    public List<Autor> findAll() throws SQLException {
        try (Connection c = Database.getConnection();
             Statement s = c.createStatement();
             ResultSet rs = s.executeQuery(
                 "SELECT a.id as aid, p.nome, a.nacionalidade FROM autor a JOIN pessoa p ON a.pessoa_id = p.id")) {
            List<Autor> list = new ArrayList<>();
            while (rs.next()) {
                Autor a = new Autor();
                a.setId(rs.getInt("aid"));
                a.setNome(rs.getString("nome"));
                a.setNacionalidade(rs.getString("nacionalidade"));
                list.add(a);
            }
            return list;
        }
    }

    public boolean update(int id, Autor autor) throws SQLException {
        try (Connection c = Database.getConnection()) {
            Long pessoaId = null;
            try (PreparedStatement ps = c.prepareStatement("SELECT pessoa_id FROM autor WHERE id = ?")) {
                ps.setInt(1, id);
                ResultSet rs = ps.executeQuery();
                if (rs.next()) pessoaId = rs.getLong("pessoa_id");
            }
            if (pessoaId == null) return false;

            try (PreparedStatement ps = c.prepareStatement("UPDATE pessoa SET nome = ? WHERE id = ?")) {
                ps.setString(1, autor.getNome());
                ps.setLong(2, pessoaId);
                ps.executeUpdate();
            }
            try (PreparedStatement ps = c.prepareStatement("UPDATE autor SET nacionalidade = ? WHERE id = ?")) {
                ps.setString(1, autor.getNacionalidade());
                ps.setInt(2, id);
                int updated = ps.executeUpdate();
                return updated > 0;
            }
        }
    }

    public boolean delete(int id) throws SQLException {
        try (Connection c = Database.getConnection();
             PreparedStatement ps = c.prepareStatement("DELETE FROM autor WHERE id = ?")) {
            ps.setInt(1, id);
            int removed = ps.executeUpdate();
            return removed > 0;
        }
    }
}