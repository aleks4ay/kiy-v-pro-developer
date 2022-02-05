package org.aleks4ay.developer.service;

import org.aleks4ay.developer.dao.BaseDao;
import org.aleks4ay.developer.model.BaseEntity;

import java.util.List;

public abstract class AbstractService<T extends BaseEntity<T>> {

    private final BaseDao<T> dao;

    public AbstractService(BaseDao<T> dao) {
        this.dao = dao;
    }

    public BaseDao<T> getDao() {
        return dao;
    }

//    public List<T> findAll() {
//        return dao.findAll();
//    }
}