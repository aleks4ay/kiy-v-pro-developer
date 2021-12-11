package org.aleks4ay.developer.model;

import org.aleks4ay.developer.tools.Constants;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Order implements BaseEntity<Order>{

    private String idDoc;
    private String docNumber;
    private String client;
    private String manager;
    private int durationTime;
    private Timestamp dateCreate;
    private Timestamp dateToFactory;
    private double price;
    private List<Description> descriptions = new ArrayList<>();

    public Order(String idDoc, String docNumber, String client, String manager, int durationTime, Timestamp dateCreate, Timestamp dateToFactory) {
        this.idDoc = idDoc;
        this.docNumber = docNumber;
        this.client = client;
        this.manager = manager;
        this.durationTime = durationTime;
        this.dateCreate = dateCreate;
        this.dateToFactory = dateToFactory;
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

    public String getDocNumber() {
        return docNumber;
    }

    public void setDocNumber(String docNumber) {
        this.docNumber = docNumber;
    }

    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }

    public String getManager() {
        return manager;
    }

    public void setManager(String manager) {
        this.manager = manager;
    }

    public int getDurationTime() {
        return durationTime;
    }

    public void setDurationTime(int durationTime) {
        this.durationTime = durationTime;
    }

    public Timestamp getDateCreate() {
        return dateCreate;
    }

    public void setDateCreate(Timestamp dateCreate) {
        this.dateCreate = dateCreate;
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

    public List<Description> getDescriptions() {
        return descriptions;
    }

    public void setDescriptions(List<Description> descriptions) {
        this.descriptions = descriptions;
    }

    public String getNumberOfPosition() {
        return String.valueOf(descriptions.size());
    }

    public String getDateToFactoryString() {
        return dateToFactory == null ? "-" : dateToFactory.toLocalDateTime().format(Constants.dayTimeFormatter);
    }

    public String getDateCreateString() {
        return dateCreate.toLocalDateTime().format(Constants.dayTimeFormatter);
    }


    @Override
    public String getEntityName() {
        return "Order";
    }

    @Override
    public String toString() {
        return "Order{" +
                "idDoc='" + idDoc + '\'' +
                ", docNumber='" + docNumber + '\'' +
                ", client='" + client + '\'' +
                ", manager='" + manager + '\'' +
                ", durationTime=" + durationTime +
                ", dateCreate=" + dateCreate +
                ", dateToFactory=" + dateToFactory +
                ", price=" + price +
                ", descriptions=" + descriptions +
                '}';
    }

    enum  Status {
        NEW(0),
        PARSED(1),
        COMPLETE(2),
        CANCELED(3);

        private int statusIndex;

        Status(int statusIndex) {
            this.statusIndex = statusIndex;
        }

        @Override
        public String toString() {
            return "Order." + super.toString();
        }
    }
}
