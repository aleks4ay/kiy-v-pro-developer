package org.aleks4ay.developer.service;

import org.aleks4ay.developer.dao.BaseDao;
import org.aleks4ay.developer.dao.TmcDao;
import org.aleks4ay.developer.model.Tmc;

import java.util.List;

public class TmcService extends AbstractService<Tmc>{

    public TmcService(BaseDao<Tmc> tmcDao) {
        super(tmcDao);
    }

    public List<Tmc> findAll() {
        return ((TmcDao)getDao()).findAll();
    }
}