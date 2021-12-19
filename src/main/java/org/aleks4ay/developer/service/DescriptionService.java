package org.aleks4ay.developer.service;

import org.aleks4ay.developer.dao.*;
import org.aleks4ay.developer.model.Description;
import org.aleks4ay.developer.model.DescriptionTime;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class DescriptionService extends AbstractService<Description> {


    public DescriptionService(BaseDao<Description> descriptionDao) {
        super(descriptionDao);
    }

    public boolean updateStatusName(String id, String statusName) {
        return ((DescriptionDao)getDao()).updateStatusName(id, statusName);
    }

    public boolean updateTypeName(String id, String typeName) {
        return ((DescriptionDao)getDao()).updateTypeName(id, typeName);
    }

    public boolean updateDesignerName(String id, String designerName) {
        return ((DescriptionDao)getDao()).updateTypeName(id, designerName);
    }

    public List<Description> findAllByOrderId(String orderId) {
        Map<String, Description> descriptionMap = ((DescriptionDao) getDao()).findByOrderId(orderId)
                .stream()
                .collect(Collectors.toMap(Description::getId, d -> d));

        DescriptionTimeService timeService = new DescriptionTimeService(new DescriptionTimeDao(ConnectionPool.getInstance()));

        for (DescriptionTime time : timeService.findAllByOrderId(orderId)) {
            Description description = descriptionMap.get(time.getIdDescription());
            if (description != null) {
                description.getTimes().add(time);
            }
        }
        return new ArrayList<>(descriptionMap.values());
    }

    @Override
    public List<Description> findAll() {
        Map<String, Description> descriptionMap = getDao().findAll()
                .stream()
                .collect(Collectors.toMap(Description::getId, d -> d));

        DescriptionTimeService timeService = new DescriptionTimeService(new DescriptionTimeDao(ConnectionPool.getInstance()));

        for (DescriptionTime time : timeService.findAll()) {
            Description description = descriptionMap.get(time.getIdDescription());
            if (description != null) {
                description.getTimes().add(time);
            }
        }
        return new ArrayList<>(descriptionMap.values());
    }

    public List<Description> findAllKb() {
        Map<String, Description> descriptionMap = ((DescriptionDao)getDao()).findAllKb()
                .stream()
                .collect(Collectors.toMap(Description::getId, d -> d));

        DescriptionTimeService timeService = new DescriptionTimeService(new DescriptionTimeDao(ConnectionPool.getInstance()));

        for (DescriptionTime time : timeService.findAll()) {
            Description description = descriptionMap.get(time.getIdDescription());
            if (description != null) {
                description.getTimes().add(time);
            }
        }
        return new ArrayList<>(descriptionMap.values());
    }
}