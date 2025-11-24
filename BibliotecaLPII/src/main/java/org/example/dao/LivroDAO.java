package org.example.dao;

import org.example.db.Database;
import org.example.model.Livro;
import org.example.model.LivroDigital;
import org.example.model.LivroFisico;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LivroDAO extends BaseDAO<Livro> {

    public Livro create(Livro l) throws SQLException {
        try (Connection c = Database.getConnection();
             PreparedStatement ps = c.prepareStatement(
                 "INSERT INTO livro(titulo, ano_publicacao, autor_id, tipo, tamanho_arquivo_mb, formato, numero_paginas, peso_gramas) " +
                 "VALUES(?,?,?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, l.getTitulo());
            ps.setInt(2, l.getAnoPublicacao());
            if (l.getAutorId() == 0) ps.setNull(3, Types.BIGINT); else ps.setInt(3, l.getAutorId());
            ps.setString(4, l.getTipo());

            if (l instanceof LivroDigital) {
                LivroDigital ld = (LivroDigital) l;
                ps.setDouble(5, ld.getTamanhoArquivoMB());
                ps.setString(6, ld.getFormato());
                ps.setNull(7, Types.INTEGER);
                ps.setNull(8, Types.DOUBLE);
            } else if (l instanceof LivroFisico) {
                LivroFisico lf = (LivroFisico) l;
                ps.setNull(5, Types.DOUBLE);
                ps.setNull(6, Types.VARCHAR);
                ps.setInt(7, lf.getNumeroPaginas());
                ps.setDouble(8, lf.getPesoGramas());
            } else {
                ps.setNull(5, Types.DOUBLE);
                ps.setNull(6, Types.VARCHAR);
                ps.setNull(7, Types.INTEGER);
                ps.setNull(8, Types.DOUBLE);
            }

            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            rs.next();
            l.setId(rs.getInt(1));
            return l;
        }
    }

    public Livro findById(int id) throws SQLException {
        try (Connection c = Database.getConnection();
             PreparedStatement ps = c.prepareStatement("SELECT * FROM livro WHERE id = ?")) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (!rs.next()) return null;
            return mapRow(rs);
        }
    }

    public List<Livro> findAll() throws SQLException {
        try (Connection c = Database.getConnection();
             Statement s = c.createStatement();
             ResultSet rs = s.executeQuery("SELECT * FROM livro")) {
            List<Livro> list = new ArrayList<>();
            while (rs.next()) list.add(mapRow(rs));
            return list;
        }
    }

    public boolean update(int id, Livro l) throws SQLException {
        try (Connection c = Database.getConnection();
             PreparedStatement ps = c.prepareStatement(
                 "UPDATE livro SET titulo=?, ano_publicacao=?, autor_id=?, tipo=?, tamanho_arquivo_mb=?, formato=?, numero_paginas=?, peso_gramas=? WHERE id=?")) {

            ps.setString(1, l.getTitulo());
            ps.setInt(2, l.getAnoPublicacao());
            if (l.getAutorId() == 0) ps.setNull(3, Types.BIGINT); else ps.setInt(3, l.getAutorId());
            ps.setString(4, l.getTipo());

            if (l instanceof LivroDigital) {
                LivroDigital ld = (LivroDigital) l;
                ps.setDouble(5, ld.getTamanhoArquivoMB());
                ps.setString(6, ld.getFormato());
                ps.setNull(7, Types.INTEGER);
                ps.setNull(8, Types.DOUBLE);
            } else if (l instanceof LivroFisico) {
                LivroFisico lf = (LivroFisico) l;
                ps.setNull(5, Types.DOUBLE);
                ps.setNull(6, Types.VARCHAR);
                ps.setInt(7, lf.getNumeroPaginas());
                ps.setDouble(8, lf.getPesoGramas());
            } else {
                ps.setNull(5, Types.DOUBLE);
                ps.setNull(6, Types.VARCHAR);
                ps.setNull(7, Types.INTEGER);
                ps.setNull(8, Types.DOUBLE);
            }
            ps.setInt(9, id);
            int updated = ps.executeUpdate();
            return updated > 0;
        }
    }

    public boolean delete(int id) throws SQLException {
        try (Connection c = Database.getConnection();
             PreparedStatement ps = c.prepareStatement("DELETE FROM livro WHERE id = ?")) {
            ps.setInt(1, id);
            int removed = ps.executeUpdate();
            return removed > 0;
        }
    }

    private Livro mapRow(ResultSet rs) throws SQLException {
        String tipo = rs.getString("tipo");
        if ("digital".equalsIgnoreCase(tipo)) {
            LivroDigital ld = new LivroDigital();
            ld.setId(rs.getInt("id"));
            ld.setTitulo(rs.getString("titulo"));
            ld.setAnoPublicacao(rs.getInt("ano_publicacao"));
            ld.setAutorId(rs.getInt("autor_id"));
            ld.setTamanhoArquivoMB(rs.getDouble("tamanho_arquivo_mb"));
            ld.setFormato(rs.getString("formato"));
            return ld;
        } else if ("fisico".equalsIgnoreCase(tipo)) {
            LivroFisico lf = new LivroFisico();
            lf.setId(rs.getInt("id"));
            lf.setTitulo(rs.getString("titulo"));
            lf.setAnoPublicacao(rs.getInt("ano_publicacao"));
            lf.setAutorId(rs.getInt("autor_id"));
            lf.setNumeroPaginas(rs.getInt("numero_paginas"));
            lf.setPesoGramas(rs.getDouble("peso_gramas"));
            return lf;
        } else {
            Livro l = new Livro();
            l.setId(rs.getInt("id"));
            l.setTitulo(rs.getString("titulo"));
            l.setAnoPublicacao(rs.getInt("ano_publicacao"));
            l.setAutorId(rs.getInt("autor_id"));
            l.setTipo(tipo);
            return l;
        }
    }
}