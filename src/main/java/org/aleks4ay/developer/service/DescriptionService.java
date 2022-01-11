package org.aleks4ay.developer.service;

import org.aleks4ay.developer.dao.*;
import org.aleks4ay.developer.model.Description;
import org.aleks4ay.developer.model.DescriptionTime;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class DescriptionService extends AbstractService<Description> {

    public DescriptionService(BaseDao<Description> descriptionDao) {
        super(descriptionDao);
    }

    public static void main(String[] args) {
        String fileName = "J:\\__Temp___\\Ава.jpg";
        String idDescr = "  EYSJ-1";
        new DescriptionService(new DescriptionDao(ConnectionPool.getInstance())).createImage(fileName, idDescr);

        List<byte[]> imagesByte = new DescriptionService(new DescriptionDao(ConnectionPool.getInstance())).getImagesByte(idDescr);
        System.out.println("size = " + imagesByte.size());
    }

    public boolean updateStatusName(String id, String statusName) {
        return ((DescriptionDao)getDao()).updateStatusName(id, statusName);
    }

    public boolean updateTypeName(String id, String typeName) {
        return ((DescriptionDao)getDao()).updateTypeName(id, typeName);
    }

    public void updateDesignerName(String id, String designerName) {
        ((DescriptionDao)getDao()).updateDesignerName(id, designerName);
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
        return descriptionMap.values()
                .stream()
                .sorted(Comparator.comparing(Description::getPosition))
                .collect(Collectors.toList());
    }

    public List<Description> findAllNew() {
        Map<String, Description> descriptionMap = ((DescriptionDao)getDao()).findAllNew()
                .stream()
                .collect(Collectors.toMap(Description::getId, d -> d));

        DescriptionTimeService timeService = new DescriptionTimeService(new DescriptionTimeDao(ConnectionPool.getInstance()));

        for (DescriptionTime time : timeService.findAll()) {
            Description description = descriptionMap.get(time.getIdDescription());
            if (description != null) {
                description.getTimes().add(time);
            }
        }
        return descriptionMap.values()
                .stream()
                .sorted(Comparator.comparing(Description::getPosition))
                .collect(Collectors.toList());
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
        return descriptionMap.values()
                .stream()
                .sorted(Comparator.comparing(Description::getPosition))
                .collect(Collectors.toList());
    }

    public Map<String, String> getDesignerPseudoNames() {
        return ((DescriptionDao)getDao()).getDesignerPseudoNames();
    }

    public void createPseudoName(String pseudoName, String name) {
        ((DescriptionDao)getDao()).createPseudoName(pseudoName, name);
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

    public boolean createImage(String fileName, String id) {
        return ((DescriptionDao)getDao()).createImage(fileName, id);
    }

    public List<byte[]> getImagesByte(String id) {
        return ((DescriptionDao)getDao()).findImages(id);
    }
}