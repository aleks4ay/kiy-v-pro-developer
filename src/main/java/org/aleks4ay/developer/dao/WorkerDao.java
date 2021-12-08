package org.aleks4ay.developer.dao;

import org.aleks4ay.developer.dao.mapper.WorkerMapper;
import org.aleks4ay.developer.model.Worker;
import org.aleks4ay.developer.tools.ConstantsSql;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class WorkerDao extends AbstractDao<Worker> implements BaseDao<Worker> {

    public WorkerDao(ConnectionPool connectionPool) {
        super(new WorkerMapper(), connectionPool);
    }

    @Override
    public Optional<Worker> findById(String id) throws SQLException {
        return findAbstractById(ConstantsSql.WORKER_GET_ONE, id);
    }

    @Override
    public List<Worker> findAll() {
        return findAbstractAll(ConstantsSql.WORKER_GET_ALL);
    }

    @Override
    public boolean update(Worker worker) {
        return updateAbstract(ConstantsSql.WORKER_UPDATE, worker);
    }

    @Override
    public String getEntityName() {
        return "Worker";
    }
}