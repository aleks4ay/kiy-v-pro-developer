package org.aleks4ay.developer.dao;

import org.aleks4ay.developer.dao.mapper.DescriptionImageMapper;
import org.aleks4ay.developer.model.DescriptionImage;
import org.aleks4ay.developer.tools.ConstantsSql;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class DescriptionImageDao {

    private final DescriptionImageMapper descriptionImageMapper = new DescriptionImageMapper();
    private static final Logger log = LoggerFactory.getLogger(AbstractDao.class);
    private final ConnectionBase connectionBase;

    public DescriptionImageDao(ConnectionBase connectionBase) {
        this.connectionBase = connectionBase;
    }

    public List<String> getDescriptionIdWithImages() {
        Connection connection = connectionBase.getConnection();
        Set<String> result = new HashSet<>();
        try (Statement statement = connection.createStatement()) {
            ResultSet rs = statement.executeQuery(ConstantsSql.DESCRIPTION_GET_ALL_ID_WITH_IMAGE);
            while (rs.next()) {
                result.add(rs.getString("id_description"));
            }
            if (!result.isEmpty()) {
                log.info("Was read {} 'description id' with Image.", result.size());
            } else {
                log.info("Description with images was not readed.");
            }
        } catch (SQLException e) {
            log.warn("Exception during creating Description.", e);
        } finally {
            connectionBase.closeConnection(connection);
        }
        System.out.println("result.size() = " + result.size());
        return new ArrayList<>(result);
    }

    public void createImage(byte[] bFile, String idDescription, String fileName) {
        Connection connection = connectionBase.getConnection();
        try (PreparedStatement prepStatement = connection.prepareStatement(ConstantsSql.CREATE_IMAGE)) {
            prepStatement.setString(1, idDescription);
            prepStatement.setBytes(2, bFile);
            prepStatement.setString(3, fileName);
            boolean result = 1 == prepStatement.executeUpdate();
            if (result) {
                log.info("Was create an Image for Description with id '{}'.", idDescription);
            } else {
                log.info("En Image for Description with id '{}' was not created.", idDescription);
            }
        } catch (SQLException e) {
            log.warn("Exception during creating Description.", e);
        } finally {
            connectionBase.closeConnection(connection);
        }
    }

    public List<DescriptionImage> findImagesByDescriptionId(String id) {
        Connection conn = connectionBase.getConnection();
        List<DescriptionImage> entities = new ArrayList<>();

        try (PreparedStatement prepStatement = conn.prepareStatement(ConstantsSql.FIND_IMAGE)){
            prepStatement.setString(1, id);
            ResultSet rs = prepStatement.executeQuery();
            while (rs.next()) {
                DescriptionImage descriptionImage = descriptionImageMapper.extractFromResultSet(rs);
                entities.add(descriptionImage);
            }
        } catch (SQLException e) {
            log.warn("Exception during reading Image for Description with id='{}'.", id, e);
        } finally {
            connectionBase.closeConnection(conn);
        }
        return entities;
    }
}
