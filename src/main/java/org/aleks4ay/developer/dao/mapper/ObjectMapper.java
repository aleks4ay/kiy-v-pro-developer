package org.aleks4ay.developer.dao.mapper;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public interface ObjectMapper<T> {

    T extractFromResultSet(ResultSet rs) throws SQLException;
    void insertToResultSet(PreparedStatement statement, T entity) throws SQLException;
    void insertIdToResultSet(PreparedStatement statement, T entity) throws SQLException;
}
