package org.aleks4ay.developer.dao.mapper;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public interface ObjectMapperTime<T> extends ObjectMapper<T> {

    void insertToResultSet(PreparedStatement statement, T entity) throws SQLException;
}
