package org.aleks4ay.developer.dao_old.mapper;

import org.aleks4ay.developer.dao_old.OldDboObject;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class OldDboObjectMapper {
    public OldDboObjectMapper(SaveType saveType) {
        this.saveType = saveType;
    }

    private final SaveType saveType;

    public enum SaveType {
        DESIGNER("designer") {
            @Override
            public void insertToResultSet(PreparedStatement statement, Object object) throws SQLException {
                OldDboObject dboObject = (OldDboObject)object;
                statement.setString(1, dboObject.getDesigner());
                statement.setString(2, dboObject.getId());
            }
        },
        STATUS("status") {
            @Override
            public void insertToResultSet(PreparedStatement statement, Object object) throws SQLException {
                OldDboObject dboObject = (OldDboObject)object;
                statement.setString(1, dboObject.getStatusName().toString());
                statement.setString(2, dboObject.getId());
                statement.setString(3, dboObject.getStatusName().toString());
            }
        },
        TYPE("type") {
            @Override
            public void insertToResultSet(PreparedStatement statement, Object object) throws SQLException {
                OldDboObject dboObject = (OldDboObject)object;
                statement.setString(1, dboObject.getTypeName().toString());
                statement.setString(2, dboObject.getId());
                statement.setString(3, dboObject.getTypeName().toString());
            }
        };

        SaveType(String typeOfEntity) {
            this.typeOfEntity = typeOfEntity;
        }

        final String typeOfEntity;
        abstract void insertToResultSet(PreparedStatement statement, Object object) throws SQLException;
    }

    public void insertToResultSet(PreparedStatement statement, Object dboObject) throws SQLException {
        saveType.insertToResultSet(statement, dboObject);
    }
}