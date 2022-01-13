package org.aleks4ay.developer.dao;

import javafx.scene.image.Image;
import org.aleks4ay.developer.dao.mapper.DescriptionMapper;
import org.aleks4ay.developer.model.Description;
import org.aleks4ay.developer.model.PseudoName;
import org.aleks4ay.developer.tools.ConstantsSql;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class DescriptionDao extends AbstractDao<Description> implements BaseDao<Description> {
    private static final Logger log = LoggerFactory.getLogger(DescriptionDao.class);

    private final PseudoNameDao pseudoNameDao = new PseudoNameDao(ConnectionPool.getInstance());

    public DescriptionDao(ConnectionPool connectionPool) {
        super(new DescriptionMapper(), connectionPool);
    }

    @Override
    public List<Description> findAll() {
        return findAbstractAll(ConstantsSql.DESCRIPTION_GET_ALL);
    }

    public List<Description> findAllNew() {
        return findAbstractAll(ConstantsSql.DESCRIPTION_GET_ALL_NEW);
    }

    public List<Description> findAllKb() {
        return findAbstractAll(ConstantsSql.DESCRIPTION_GET_ALL_KB);
    }

    @Override
    public String getEntityName() {
        return "Description";
    }

    public List<Description> findByOrderId(String orderId) {
        return findAbstractAllById(ConstantsSql.DESCRIPTION_GET_BY_ORDER_ID, orderId);
    }

    public boolean updateStatusName(String id, String statusName) {
        return updateStringAbstract(ConstantsSql.DESCRIPTION_UPDATE_STATUS, id, statusName);
    }

    public boolean updateTypeName(String id, String typeName) {
        return updateStringAbstract(ConstantsSql.DESCRIPTION_UPDATE_TYPE, id, typeName);
    }

    public boolean updateDesignerName(String id, String designer) {
        return updateStringAbstract(ConstantsSql.DESCRIPTION_UPDATE_DESIGNER, id, designer);
    }

    public Map<String, String> getDesignerPseudoNames() {
        return pseudoNameDao.findAll().stream()
                .collect(Collectors.toMap(PseudoName::getPseudoName, PseudoName::getName));
    }

    public void createPseudoName(String pseudoName, String name) {
        pseudoNameDao.create(pseudoName, name);
    }


/*    public boolean createImage(String fileName, String id) {

        Connection connection = getConnection();
        File file = new File(fileName);
        byte[] bFile = new byte[(int) file.length()];
        try (PreparedStatement prepStatement = connection.prepareStatement(ConstantsSql.CREATE_IMAGE)) {
            FileInputStream fileInputStream = new FileInputStream(file);
            fileInputStream.read(bFile);
            fileInputStream.close();
            prepStatement.setString(1, id);
            prepStatement.setBytes(2, bFile);
            prepStatement.setString(3, fileName);
            boolean result = 1 == prepStatement.executeUpdate();
            if (result) {
                log.info("Was create an Image for Description with id '{}'.", id);
            } else {
                log.info("En Image for Description with id '{}' was not created.", id);
            }
            return result;
        } catch (SQLException | IOException e) {
            log.warn("Exception during creating Description.", e);
        } finally {
            closeConnection(connection);
        }
        return false;
    }


    public List<byte[]> findImages(String idDescription) {
        String idOrder = idDescription.split("-")[0];
        Connection conn = getConnection();

        try (PreparedStatement prepStatement = conn.prepareStatement(ConstantsSql.FIND_IMAGE)){
            List<byte[]> entities = new ArrayList<>();
            prepStatement.setString(1, idOrder + "-%");
            ResultSet rs = prepStatement.executeQuery();
//            int x = 0;
            while (rs.next()) {
                byte[] bFile = rs.getBytes("image");
                entities.add(bFile);
*//*                try (FileOutputStream fos = new FileOutputStream(new File("J:\\test" + ++x + ".jpg"))){
                    fos.write(bFile);
                } catch (IOException e) {
                    e.printStackTrace();
                }*//*
            }
            return entities;

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnection(conn);
        }
        return null;
    }

    public Map<String, Image> findMapImages(String id) {
        Connection conn = getConnection();

        try (PreparedStatement prepStatement = conn.prepareStatement(ConstantsSql.FIND_IMAGE)){
            List<byte[]> entities = new ArrayList<>();
            prepStatement.setString(1, id);
            ResultSet rs = prepStatement.executeQuery();
//            int x = 0;
            while (rs.next()) {
                byte[] bFile = rs.getBytes("image");
                entities.add(bFile);
*//*                try (FileOutputStream fos = new FileOutputStream(new File("J:\\test" + ++x + ".jpg"))){
                    fos.write(bFile);
                } catch (IOException e) {
                    e.printStackTrace();
                }*//*
            }
            return entities;

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnection(conn);
        }
        return null;
    }*/
}