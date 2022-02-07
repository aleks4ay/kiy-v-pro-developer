package org.aleks4ay.developer.service;

import org.aleks4ay.developer.dao.ConnectionBase;
import org.aleks4ay.developer.dao.OtherDao;

public class OtherService {

    private final OtherDao dao;

    public OtherService(ConnectionBase connectionBase) {
        this.dao = new OtherDao(connectionBase);
    }

    public String selectSql(String sql) {
        return dao.selectSql(sql);
    }

    public void updateSql(String sql) {
        dao.updateSql(sql);
    }
}