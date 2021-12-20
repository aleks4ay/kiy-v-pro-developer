package org.aleks4ay.developer.dao.mapper;

import org.aleks4ay.developer.model.PseudoName;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PseudoNameMapper implements ObjectMapperWithCreate<PseudoName> {

    @Override
    public PseudoName extractFromResultSet(ResultSet rs) throws SQLException {
        return new PseudoName(rs.getString("pseudo_name"), rs.getString("name"));
    }

    @Override
    public void insertToResultSet(PreparedStatement statement, PseudoName pseudoName) throws SQLException {
        statement.setString(1, pseudoName.getPseudoName());
        statement.setString(2, pseudoName.getName());
    }

    @Override
    public void insertIdToResultSet(PreparedStatement statement, Object key) throws SQLException {
        statement.setString(1, String.valueOf(key));
    }
}