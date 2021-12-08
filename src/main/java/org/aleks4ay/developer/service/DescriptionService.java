package org.aleks4ay.developer.service;

import org.aleks4ay.developer.dao.BaseDao;
import org.aleks4ay.developer.dao.ConnectionPool;
import org.aleks4ay.developer.dao.DescriptionDao;
import org.aleks4ay.developer.model.Description;

import java.util.List;

public class DescriptionService extends AbstractService<Description> {

    public DescriptionService(BaseDao<Description> descriptionDao) {
        super(descriptionDao);
    }

    public static void main(String[] args) {
        DescriptionService descriptionService = new DescriptionService(new DescriptionDao(ConnectionPool.getInstance()));
        List<Description> allByOrderId = descriptionService.findAllByOrderId("  G8IM");
        System.out.println(allByOrderId);
    }

    public List<Description> findAllByOrderId(String orderId) {
        return ((DescriptionDao)getDao()).findByOrderId(orderId);
    }
}