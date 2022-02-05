package org.aleks4ay.developer.service;

import org.aleks4ay.developer.dao.BaseDao;
import org.aleks4ay.developer.dao.DescriptionTimeDao;
import org.aleks4ay.developer.model.DescriptionTime;

import java.util.List;

public class DescriptionTimeService extends AbstractService<DescriptionTime> {

    public DescriptionTimeService(BaseDao<DescriptionTime> descriptionDao) {
        super(descriptionDao);
    }

    public boolean create(DescriptionTime time) {
        return ((DescriptionTimeDao)getDao()).create(time);
    }

    public List<DescriptionTime> findAll() {
        return ((DescriptionTimeDao)getDao()).findAll();
    }
}