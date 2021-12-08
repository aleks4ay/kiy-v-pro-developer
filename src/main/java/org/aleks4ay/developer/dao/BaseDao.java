package org.aleks4ay.developer.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface BaseDao<T> {
    Optional<T> findById(String key) throws SQLException;
    List<T> findAll();
    boolean update(T t);
    String getEntityName();
}
