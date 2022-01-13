package org.aleks4ay.developer.dao.mapper;

import javafx.scene.image.Image;
import org.aleks4ay.developer.model.DescriptionImage;

import java.io.ByteArrayInputStream;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DescriptionImageMapper {

    public DescriptionImage extractFromResultSet(ResultSet rs) throws SQLException {
        byte[] bFile = rs.getBytes("image");
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bFile);
        Image image = new Image(byteArrayInputStream, 800, 650, true, true);

        return new DescriptionImage(
                rs.getLong("id"),
                rs.getString("id_description"),
                image,
                rs.getString("name"),
                rs.getTimestamp("time").toLocalDateTime()
        );
    }
}