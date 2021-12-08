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
    public void insertToResultSet(PreparedStatement statement, Tmc tmc) throws SQLException {
        statement.setString(10, tmc.getId());
        statement.setString(1, tmc.getIdParent());
        statement.setString(2, tmc.getCode());
        statement.setString(3, tmc.getDescr());
        statement.setInt(4, tmc.getSizeA());
        statement.setInt(5, tmc.getSizeB());
        statement.setInt(6, tmc.getSizeC());
        statement.setInt(7, tmc.getIsFolder());
        statement.setString(8, tmc.getDescrAll());
        statement.setString(9, tmc.getType());
    }

    @Override
    public void insertIdToResultSet(PreparedStatement statement, Tmc tmc) throws SQLException {
        statement.setString(1, tmc.getId());
    }
}