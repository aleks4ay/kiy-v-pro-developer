package org.aleks4ay.developer.model;

import java.sql.Timestamp;

public class Order implements BaseEntity<Order>{

    private String idDoc;
    private String clientId;
    private String managerId;
    private int durationTime;
    private Timestamp dateToFactory;
    private double price;

    public Order(String idDoc, String clientId, String managerId, int durationTime, Timestamp dateToFactory, double price) {
        this.idDoc = idDoc;
        this.clientId = clientId;
        this.managerId = managerId;
        this.durationTime = durationTime;
        this.dateToFactory = dateToFactory;
        this.price = price;
    }

    @Override
    public String getId() {
        return idDoc;
    }

    public String getIdDoc() {
        return idDoc;
    }

    public void setIdDoc(String idDoc) {
        this.idDoc = idDoc;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getManagerId() {
        return managerId;
    }

    public void setManagerId(String managerId) {
        this.managerId = managerId;
    }

    public int getDurationTime() {
        return durationTime;
    }

    public void setDurationTime(int durationTime) {
        this.durationTime = durationTime;
    }

    public Timestamp getDateToFactory() {
        return dateToFactory;
    }

    public void setDateToFactory(Timestamp dateToFactory) {
        this.dateToFactory = dateToFactory;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public String getEntityName() {
        return "Order";
    }

    @Override
    public String toString() {
        return "Order{" +
                "idDoc='" + idDoc + '\'' +
                ", clientId='" + clientId + '\'' +
                ", managerId='" + managerId + '\'' +
                ", durationTime=" + durationTime +
                ", dateToFactory=" + dateToFactory +
                ", price=" + price +
                '}';
    }
}
