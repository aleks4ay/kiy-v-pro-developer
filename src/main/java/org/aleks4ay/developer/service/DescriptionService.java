package org.aleks4ay.developer.service;

import org.aleks4ay.developer.dao.*;
import org.aleks4ay.developer.model.Description;
import org.aleks4ay.developer.model.DescriptionImage;
import org.aleks4ay.developer.model.DescriptionTime;
import org.aleks4ay.developer.tools.FileReader;

import java.io.File;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class DescriptionService extends AbstractService<Description> {
    private final DescriptionImageDao descriptionImageDao = new DescriptionImageDao(ConnectionPool.getInstance());

    public DescriptionService(BaseDao<Description> descriptionDao) {
        super(descriptionDao);
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
        return findAllBase("ALL");
    }

    public List<Description> findAllNew() {
        return findAllBase("NEW");
    }

    public List<Description> findAllKb() {
        return findAllBase("KB");
    }

    public List<Description> findAllBase(String type) {
        DescriptionDao dao = ((DescriptionDao) getDao());
        List<Description> descriptions;
        descriptions = type.equalsIgnoreCase("NEW")
                ? dao.findAllNew()
                : type.equalsIgnoreCase("ALL")
                    ? dao.findAll()
                    : dao.findAllKb();

        Map<String, Description> descriptionMap = descriptions
                .stream()
                .collect(Collectors.toMap(Description::getId, d -> d));

        DescriptionTimeService timeService = new DescriptionTimeService(new DescriptionTimeDao(ConnectionPool.getInstance()));

        for (DescriptionTime time : timeService.findAll()) {
            Description description = descriptionMap.get(time.getIdDescription());
            if (description != null) {
                description.getTimes().add(time);
            }
        }

        List<String> descriptionIdWithImages = descriptionImageDao.getDescriptionIdWithImages();
        for (String idWithImage : descriptionIdWithImages) {
            if (descriptionMap.containsKey(idWithImage)) {
                descriptionMap.get(idWithImage).setExistImages(true);
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

    public void createImage(String fileName, String idDescription) {
        byte[] imageBytes = new FileReader().file2byteArray(fileName);
        descriptionImageDao.createImage(imageBytes, idDescription, new File(fileName).getName());
    }

    public List<DescriptionImage> findImagesByDescriptionId(String id) {
        return descriptionImageDao.findImagesByDescriptionId(id);
    }

    public void updateImageDescriptionDDL() {
        descriptionImageDao.updateImageDescriptionDDL();
    }

    public void emptyMainTables() {
        descriptionImageDao.emptyMainTables();
    }
}