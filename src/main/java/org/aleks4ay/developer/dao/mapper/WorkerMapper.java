package org.aleks4ay.developer.dao.mapper;

import org.aleks4ay.developer.model.Worker;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class WorkerMapper implements ObjectMapper<Worker> {
    @Override
    public Worker extractFromResultSet(ResultSet rs) throws SQLException {
        return new Worker(
                rs.getString("id"),
                rs.getString("name")
                );
    }

    @Override
    public void insertToResultSet(PreparedStatement statement, Worker worker) throws SQLException {
        statement.setString(1, worker.getName());
        statement.setString(2, worker.getId());
    }

    @Override
    public void insertIdToResultSet(PreparedStatement statement, Worker worker) throws SQLException {
        statement.setString(1, worker.getId());
    }
}