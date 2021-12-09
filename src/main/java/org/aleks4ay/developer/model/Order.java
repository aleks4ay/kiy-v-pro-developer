package org.aleks4ay.developer.model;

import java.sql.Timestamp;
import java.util.List;

public class Order implements BaseEntity<Order>{

    private String idDoc;
    private String clientId;
    private String managerId;
    private int durationTime;
    private Timestamp dateToFactory;
    private double price;
    private List<Description> descriptions;

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

    public String getDateToFactoryString() {
        return dateToFactory == null ? "-" : dateToFactory.toString();
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

    public List<Description> getDescriptions() {
        return descriptions;
    }

    public void setDescriptions(List<Description> descriptions) {
        this.descriptions = descriptions;
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
