package org.aleks4ay.developer.dao;

import org.aleks4ay.developer.dao.mapper.JournalMapper;
import org.aleks4ay.developer.model.Journal;
import org.aleks4ay.developer.tools.ConstantsSql;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class JournalDao extends AbstractDao<Journal> implements BaseDao<Journal> {

    public JournalDao(ConnectionPool connectionPool) {
        super(new JournalMapper(), connectionPool);
    }

    @Override
    public Optional<Journal> findById(String id) throws SQLException {
        return findAbstractById(ConstantsSql.JOURNAL_GET_ONE, id);
    }

    @Override
    public List<Journal> findAll() {
        return findAbstractAll(ConstantsSql.JOURNAL_GET_ALL);
    }

    @Override
    public boolean update(Journal journal) {
        return updateAbstract(ConstantsSql.JOURNAL_UPDATE, journal);
    }

    @Override
    public String getEntityName() {
        return "Journal";
    }
}