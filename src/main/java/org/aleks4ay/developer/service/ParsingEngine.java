package org.aleks4ay.developer.service;

import org.aleks4ay.developer.dao.*;
import org.aleks4ay.developer.model.*;
import org.aleks4ay.developer.tools.FileWriter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ParsingEngine {

    ConnectionPool connectionPool = ConnectionPool.getInstance();
    OrderService orderService = new OrderService(new OrderDao(connectionPool));
    OrderTimeService orderTimeService = new OrderTimeService(new OrderTimeDao(connectionPool));
    DescriptionService descriptionService = new DescriptionService(new DescriptionDao(connectionPool));
    DescriptionTimeService descriptionTimeService = new DescriptionTimeService(new DescriptionTimeDao(connectionPool));


    public int setType (List<DescriptionParsing> listParsing, boolean isSecondParsing) {
        List<DescriptionTime> times = new ArrayList<>();
        int newType = 0;

        for (DescriptionParsing descr : listParsing) {
            TypeName typeName = null;
            if (descr.getButtonKB().isSelected() && descr.getType() != TypeName.KB) {
                typeName = TypeName.KB;
                descr.setStatus(StatusName.KB_NEW.toString());
            } else if (descr.getButtonFactory().isSelected() && descr.getType() != TypeName.FACTORY) {
                typeName = TypeName.FACTORY;
                descr.setStatus(StatusName.FACTORY.toString());
            } else if (descr.getButtonTeh().isSelected() &&
                    (descr.getType() != TypeName.TECHNO)  ||  descr.getStatus().equals(StatusName.NEW.toString())) {
                typeName = TypeName.TECHNO;
                descr.setStatus(StatusName.NOT_TRACKED.toString());
            } else if (descr.getButtonOther().isSelected() && descr.getType() != TypeName.OTHER) {
                typeName = TypeName.OTHER;
                descr.setStatus(StatusName.NOT_TRACKED.toString());
            }
            boolean resultSaving;
            if (typeName != null) {
                DescriptionTime time = new DescriptionTime(descr.getId(), descr.getStatus(), LocalDateTime.now());
                times.add(time);
                resultSaving = saveDescriptionType(descr, typeName, isSecondParsing);
                if (resultSaving) {
                    newType ++;
                }
            }

        }
        if (newType == listParsing.size()) {
            String orderId = listParsing.get(0).getId().split("-")[0];
            DescriptionTime minDescriptionTime = getMinimumDescriptionTime(times);
            OrderTime minOrderTime = new OrderTime(minDescriptionTime);

            orderService.updateStatus(orderId, minOrderTime.getStatusName());
            orderTimeService.create(minOrderTime);
        }
        if (newType > 0) {
            FileWriter.writeTimeChange("par");
        }
        return newType;
    }



    private boolean saveDescriptionType(DescriptionParsing descr, TypeName typeName, boolean isSecondParsing) {
        LocalDateTime statusTime = LocalDateTime.now();
        DescriptionTime time = new DescriptionTime(descr.getId(), descr.getStatus(), statusTime);
        if (isSecondParsing) {
            descriptionTimeService.delete(descr.getId());
        }
        boolean b1 = descriptionTimeService.create(time);
        boolean b2 = descriptionService.updateStatusName(descr.getId(), descr.getStatus());
        boolean b3 = descriptionService.updateTypeName(descr.getId(), typeName.toString());
        return b1 & b2 & b3;
    }

    private DescriptionTime getMinimumDescriptionTime(List<DescriptionTime> list) {
        return list.stream()
                .min(DescriptionTime::compareTo)
                .get();
    }
}
