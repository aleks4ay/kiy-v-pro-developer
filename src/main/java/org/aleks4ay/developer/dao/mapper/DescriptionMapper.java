package org.aleks4ay.developer.dao.mapper;

import org.aleks4ay.developer.model.Description;
import org.aleks4ay.developer.model.StatusName;
import org.aleks4ay.developer.model.TypeName;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DescriptionMapper implements ObjectMapper<Description> {
    @Override
    public Description extractFromResultSet(ResultSet rs) throws SQLException {
        Description description = new Description(
                rs.getString("id"),
                rs.getString("id_order"),
                rs.getInt("position"),
                rs.getString("id_tmc"),
                rs.getInt("quantity"),
                rs.getString("descr_second"),
                rs.getString("descr_all"),
                rs.getInt("size_a"),
                rs.getInt("size_b"),
                rs.getInt("size_c"),
                rs.getString("emb"),
                TypeName.valueOf(rs.getString("type")),
                StatusName.valueOf(rs.getString("status")),
                rs.getString("designer_name"));
        return description;
    }

    @Override
    public void insertIdToResultSet(PreparedStatement statement, Object key) throws SQLException {
        statement.setString(1, String.valueOf(key));
    }
}