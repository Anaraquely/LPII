package org.example.dao;

import java.sql.SQLException;
import java.util.List;

public abstract class BaseDAO<T> {
    public abstract T create(T t) throws SQLException;
    public abstract T findById(int id) throws SQLException;
    public abstract List<T> findAll() throws SQLException;
    public abstract boolean update(int id, T t) throws SQLException;
    public abstract boolean delete(int id) throws SQLException;
}