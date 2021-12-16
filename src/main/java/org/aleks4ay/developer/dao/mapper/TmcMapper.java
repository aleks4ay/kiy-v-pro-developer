package org.aleks4ay.developer.dao.mapper;

import org.aleks4ay.developer.model.Tmc;

import java.sql.*;

public class TmcMapper implements ObjectMapper<Tmc> {
    @Override
    public Tmc extractFromResultSet(ResultSet rs) throws SQLException {
        Tmc tmc = new Tmc(rs.getString("id"));
        tmc.setIdParent(rs.getString("id_parent"));
        tmc.setCode(rs.getString("code"));
        tmc.setDescr(rs.getString("descr"));
        tmc.setSizeA(rs.getInt("size_a"));
        tmc.setSizeB(rs.getInt("size_b"));
        tmc.setSizeC(rs.getInt("size_c"));
        tmc.setIsFolder(rs.getInt("is_folder"));
        tmc.setDescrAll(rs.getString("descr_all"));
        tmc.setType(rs.getString("type"));
        return tmc;
    }

    @Override
    public void insertIdToResultSet(PreparedStatement statement, Object key) throws SQLException {
        statement.setString(1, String.valueOf(key));
    }
}