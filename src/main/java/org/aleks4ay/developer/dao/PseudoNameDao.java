package org.aleks4ay.developer.dao;

import org.aleks4ay.developer.dao.mapper.PseudoNameMapper;
import org.aleks4ay.developer.model.PseudoName;
import org.aleks4ay.developer.tools.ConstantsSql;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class PseudoNameDao extends AbstractDao<PseudoName> implements BaseDao<PseudoName> {
    private static final Logger log = LoggerFactory.getLogger(PseudoNameDao.class);

    public PseudoNameDao(ConnectionBase connectionBase) {
        super(new PseudoNameMapper(), connectionBase);
    }

    public boolean create(String pseudoName, String name) {
        return updateAbstract(ConstantsSql.PSEUDO_NAME_CREATE, new PseudoName(pseudoName, name));
    }

    @Override
    public List<PseudoName> findAll() {
        return findAbstractAll(ConstantsSql.PSEUDO_NAME_FIND_ALL);
    }

    @Override
    public String getEntityName() {
        return "PseudoName";
    }

    public void saveStatement(String sql) {
        Connection connection = getConnection();
        try (Statement st = connection.createStatement()) {
            st.executeUpdate(sql);
            log.debug("Will be execute sql {}", sql);
        } catch (SQLException e) {
            log.warn("Exception during execute sql. SQL = {}.", sql, e);
        }
        closeConnection(connection);
    }
}
