package org.aleks4ay.developer.model;

import java.time.LocalDateTime;

public class OrderTime implements BaseEntity<OrderTime> {
    private long id;
    private final String idOrder;
    private final StatusName statusName;
    private final LocalDateTime time;

    public OrderTime(long id, String idOrder, StatusName statusName, LocalDateTime time) {
        this.id = id;
        this.idOrder = idOrder;
        this.statusName = statusName;
        this.time = time;
    }

    public OrderTime(DescriptionTime descriptionTime) {
        this.idOrder = descriptionTime.getIdDescription().split("-")[0];
        this.statusName = descriptionTime.getStatusName();
        this.time = descriptionTime.getTime();
    }


    public Long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getIdOrder() {
        return idOrder;
    }

    public StatusName getStatusName() {
        return statusName;
    }

    public LocalDateTime getTime() {
        return time;
    }

    @Override
    public String toString() {
        return "DescriptionTime{" +
                "id=" + id +
                ", idOrder='" + idOrder + '\'' +
                ", status='" + statusName + '\'' +
                ", time=" + time +
                '}';
    }

    @Override
    public String getEntityName() {
        return "OrderTime";
    }
}
