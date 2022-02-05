package org.aleks4ay.developer.service;

import org.aleks4ay.developer.dao.*;
import org.aleks4ay.developer.model.*;
import org.aleks4ay.developer.tools.FileReader;

import java.io.File;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class DescriptionService extends AbstractService<Description> {
    private final DescriptionDao descriptionDao = (DescriptionDao)getDao();
    private final DescriptionImageDao descriptionImageDao = new DescriptionImageDao(ConnectionPool.getInstance());
    private final DescriptionTimeService timeService = new DescriptionTimeService(new DescriptionTimeDao(ConnectionPool.getInstance()));

    public DescriptionService(BaseDao<Description> descriptionDao) {
        super(descriptionDao);
    }

    public List<Description> findAll(String sqlDescription) {

        Map<String, Description> descriptionMap = descriptionDao.findAll(sqlDescription)
                .stream()
                .collect(Collectors.toMap(Description::getId, d -> d));

        fillDescriptionWithTimes(descriptionMap);
        fillDescriptionImageIds(descriptionMap);

        return descriptionMap.values().stream()
                .sorted(Comparator.comparing(Description::getPosition))
                .collect(Collectors.toList());
    }

    private void fillDescriptionWithTimes(Map<String, Description> descriptionMap) {
        for (DescriptionTime time : timeService.findAll()) {
            Description description = descriptionMap.get(time.getIdDescription());
            if (description != null) {
                description.getTimes().add(time);
            }
        }
    }

    private void fillDescriptionImageIds(Map<String, Description> descriptionMap) {
        List<String> descriptionIdWithImages = descriptionImageDao.getDescriptionIdWithImages();
        for (String idWithImage : descriptionIdWithImages) {
            if (descriptionMap.containsKey(idWithImage)) {
                descriptionMap.get(idWithImage).setExistImages(true);
            }
        }
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

    public Map<String, String> getDesignerPseudoNames() {
        return ((DescriptionDao)getDao()).getDesignerPseudoNames();
    }

    public void createPseudoName(String pseudoName, String name) {
        ((DescriptionDao)getDao()).createPseudoName(pseudoName, name);
    }


    public void createImage(String fileName, String idDescription) {
        byte[] imageBytes = new FileReader().file2byteArray(fileName);
        descriptionImageDao.createImage(imageBytes, idDescription, new File(fileName).getName());
    }

    public List<DescriptionImage> findImagesByDescriptionId(String id) {
        return descriptionImageDao.findImagesByDescriptionId(id);
    }
}