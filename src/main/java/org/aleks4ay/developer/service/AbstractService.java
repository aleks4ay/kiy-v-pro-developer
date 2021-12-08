package org.aleks4ay.developer.service;

import org.aleks4ay.developer.dao.BaseDao;
import org.aleks4ay.developer.exception.NotFoundException;
import org.aleks4ay.developer.model.BaseEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;
import java.util.*;

public abstract class AbstractService<T extends BaseEntity<T>> {
    static final Logger log = LoggerFactory.getLogger(AbstractService.class);

    private final BaseDao<T> dao;

    public AbstractService(BaseDao<T> dao) {
        this.dao = dao;
    }

    public BaseDao<T> getDao() {
        return dao;
    }

    public T findById(String id) {
        T entity = null;
        try {
            return dao.findById(id).orElseThrow(
                    () -> {
                        log.warn(dao.getEntityName() + " with id='" + id + "' not found");
//                        return null;
                        return new NotFoundException(dao.getEntityName() + " with id='" + id + "' not found");
                    });
        } catch (NotFoundException | SQLException e) {
            log.warn(dao.getEntityName() + " with id='" + id + "' not found");
            e.printStackTrace();
        }
        return entity;
    }

    public List<T> findAll() {
        return dao.findAll();
    }

    public boolean update(T t) {
        return dao.update(t);
    }
}