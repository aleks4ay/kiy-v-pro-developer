package org.aleks4ay.developer.service;

import org.aleks4ay.developer.dao.*;
import org.aleks4ay.developer.dao.mapper.DescriptionTimeMapper;
import org.aleks4ay.developer.dao_old.BaseDao;
import org.aleks4ay.developer.dao_old.DaoOldDB;
import org.aleks4ay.developer.dao_old.OldDboObject;
import org.aleks4ay.developer.dao_old.mapper.OldDboObjectMapper;
import org.aleks4ay.developer.model.DescriptionTime;
import org.aleks4ay.developer.model.StatusName;
import org.aleks4ay.developer.tools.ConstantsSql;

import java.util.List;
import java.util.stream.Collectors;

public class CopyOldDataService {
    private final BaseDao baseDao;
    private final DaoOldDB daoOldDB;

    public CopyOldDataService() {
        this.daoOldDB = new DaoOldDB(org.aleks4ay.developer.dao_old.ConnectionPoolOldData.getInstance());
        this.baseDao = new BaseDao(ConnectionPool.getInstance());
    }

    public static void main(String[] args) {
        CopyOldDataService copyOldDataService = new CopyOldDataService();
        List<OldDboObject> all = copyOldDataService.findAll();

        copyOldDataService.createTime(all);
        copyOldDataService.updateDesignerName(all);
        copyOldDataService.updateStatusName(all);
        copyOldDataService.updateTypeName(all);
    }


    private void updateStatusName(List<OldDboObject> oldDboObjectList) {
        baseDao.saveOrUpdateAbstractAll(ConstantsSql.DESCRIPTION_UPDATE_STATUS_FROM_OLD, oldDboObjectList,
                new OldDboObjectMapper(OldDboObjectMapper.SaveType.valueOf("STATUS")), "update");
    }

    private void updateTypeName(List<OldDboObject> oldDboObjectList) {
        baseDao.saveOrUpdateAbstractAll(ConstantsSql.DESCRIPTION_UPDATE_TYPE_FROM_OLD, oldDboObjectList,
                new OldDboObjectMapper(OldDboObjectMapper.SaveType.valueOf("TYPE")), "update");
    }

    private void updateDesignerName(List<OldDboObject> oldDboObjectList) {
        baseDao.saveOrUpdateAbstractAll(ConstantsSql.DESCRIPTION_UPDATE_DESIGNER_FROM_OLD, oldDboObjectList,
                new OldDboObjectMapper(OldDboObjectMapper.SaveType.valueOf("DESIGNER")), "update");
    }

    private void createTime(List<OldDboObject> oldDboObjectList) {
        List<DescriptionTime> times = oldDboObjectList.stream()
                .flatMap(i -> i.getTimes().stream())
                .filter(time -> time.getStatusName() != StatusName.NEW)
                .collect(Collectors.toList());

        baseDao.saveAbstractAll(ConstantsSql.DESCRIPTION_TIME_CREATE_FROM_OLD, times, new DescriptionTimeMapper());
    }

    private List<OldDboObject> findAll() {
        return daoOldDB.findAll();
    }
}