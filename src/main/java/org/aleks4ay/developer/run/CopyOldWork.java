package org.aleks4ay.developer.run;

import org.aleks4ay.developer.dao.*;
import org.aleks4ay.developer.dao.mapper.DescriptionTimeMapper;
import org.aleks4ay.developer.dao.mapper.OrderTimeMapper;
import org.aleks4ay.developer.dao_old.*;
import org.aleks4ay.developer.dao_old.mapper.OldDboObjectMapper;
import org.aleks4ay.developer.model.*;
import org.aleks4ay.developer.service.*;
import org.aleks4ay.developer.tools.ConstantsSql;

import java.util.*;

public class CopyOldWork {
    private final DaoForCopyOldValues<DescriptionTime> daoForCopyOldDescription = new DaoForCopyOldValues<>(ConnectionPool.getInstance());
    private final DaoForCopyOldValues<OrderTime> daoForCopyOldOrder = new DaoForCopyOldValues<>(ConnectionPool.getInstance());
    private final DaoOldDB daoOldDB = new DaoOldDB(ConnectionPoolOldData.getInstance());


    public static void main(String[] args) {
        new CopyOldWork().run();
    }

    public void run() {
        System.out.println("-----Start copy old data----------");
        List<OldDboObject> all = findAll();

        createTime(all);
        updateDesignerName(all);
        updateStatusName(all);
        updateTypeName(all);

        System.out.println("-----End copy old data--------");
    }

    public void createTime(List<OldDboObject> oldDboObjectList) {
        Map<String, OrderTime> orderTimeMap = new HashMap<>();
        List<DescriptionTime> descriptionTimeList = new ArrayList<>();
        for (OldDboObject oldObject : oldDboObjectList) {
            descriptionTimeList.addAll(oldObject.getTimes());

            String idOrder = oldObject.getId().split("-")[0];
            DescriptionTime actualDescriptionTime = getActualDescriptionTime(oldObject);
            if (orderTimeMap.containsKey(idOrder)) {
                OrderTime minOrderTime = orderTimeMap.get(idOrder);
                if (actualDescriptionTime.getStatusName().getStatusIndex() < minOrderTime.getStatusName().getStatusIndex()) {
                    orderTimeMap.put(idOrder, new OrderTime(actualDescriptionTime));
                }
            } else {
                orderTimeMap.put(idOrder, new OrderTime(actualDescriptionTime));
            }
        }

        daoForCopyOldDescription.saveAbstractParametersAll(
                ConstantsSql.DESCRIPTION_TIME_CREATE_FROM_OLD, descriptionTimeList, new DescriptionTimeMapper());
        daoForCopyOldOrder.saveAbstractParametersAll(
                ConstantsSql.ORDER_TIME_CREATE_FROM_OLD, new ArrayList<>(orderTimeMap.values()), new OrderTimeMapper());

        OrderService orderService = new OrderService(new OrderDao(ConnectionPool.getInstance()));
        for (OrderTime orderTime : orderTimeMap.values()) {
            orderService.updateStatus(orderTime.getIdOrder(), orderTime.getStatusName());
        }
    }

    private DescriptionTime getActualDescriptionTime(OldDboObject oldDboObject) {
        return oldDboObject.getTimes().stream()
                .max(Comparator.comparing(DescriptionTime::getStatusName))
                .get();
    }

    public void updateStatusName(List<OldDboObject> oldDboObjectList) {
        daoForCopyOldDescription.saveOrUpdateAbstractAll(ConstantsSql.DESCRIPTION_UPDATE_STATUS_FROM_OLD, oldDboObjectList,
                new OldDboObjectMapper(OldDboObjectMapper.SaveType.valueOf("STATUS")), "update");
    }

    public void updateTypeName(List<OldDboObject> oldDboObjectList) {
        daoForCopyOldDescription.saveOrUpdateAbstractAll(ConstantsSql.DESCRIPTION_UPDATE_TYPE_FROM_OLD, oldDboObjectList,
                new OldDboObjectMapper(OldDboObjectMapper.SaveType.valueOf("TYPE")), "update");
    }

    public void updateDesignerName(List<OldDboObject> oldDboObjectList) {
        daoForCopyOldDescription.saveOrUpdateAbstractAll(ConstantsSql.DESCRIPTION_UPDATE_DESIGNER_FROM_OLD, oldDboObjectList,
                new OldDboObjectMapper(OldDboObjectMapper.SaveType.valueOf("DESIGNER")), "update");
    }

    public List<OldDboObject> findAll() {
        return daoOldDB.findAll();
    }
}
