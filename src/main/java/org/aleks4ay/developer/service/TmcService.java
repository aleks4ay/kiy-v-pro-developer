package org.aleks4ay.developer.service;

import org.aleks4ay.developer.dao.BaseDao;
import org.aleks4ay.developer.model.Tmc;

public class TmcService extends AbstractService<Tmc>{

    public TmcService(BaseDao<Tmc> tmcDao) {
        super(tmcDao);
    }
}