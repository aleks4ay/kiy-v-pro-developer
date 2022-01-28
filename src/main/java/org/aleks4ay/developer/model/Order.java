package org.aleks4ay.developer.model;

import org.aleks4ay.developer.tools.Constants;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Order implements BaseEntity<Order>{

    private String id;
    private String docNumber;
    private String client;
    private String manager;
    private int durationTime;
    private Timestamp dateCreate;
    private Timestamp dateToFactory;
    private double price;
    private StatusName status;
    private List<Description> descriptions = new ArrayList<>();
    private List<OrderTime> times = new ArrayList<>();

    public Order(String id, String docNumber, String client, String manager, int durationTime, Timestamp dateCreate,
                 Timestamp dateToFactory, StatusName status) {
        this.id = id;
        this.docNumber = docNumber;
        this.client = client;
        this.manager = manager;
        this.durationTime = durationTime;
        this.dateCreate = dateCreate;
        this.dateToFactory = dateToFactory;
        this.status = status;
    }

    @Override
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public List<OrderTime> getTimes() {
        return times;
    }

    public void setTimes(List<OrderTime> times) {
        this.times = times;
    }

    public StatusName getStatus() {
        return status;
    }

    public void setStatus(StatusName status) {
        this.status = status;
    }

    public int getNumberOfPosition() {
        return descriptions.size();
    }

    public String getDateToFactoryString() {
        return dateToFactory == null ? "-" : dateToFactory.toLocalDateTime().format(Constants.dayFormatter);
    }

    public String getDateCreateString() {
        return dateCreate.toLocalDateTime().format(Constants.dayTimeFormatter);
    }

    public static Comparator<Order> getComparator (SortWay sortWay) {
        if (sortWay == SortWay.NUMBER) {
            return Comparator.comparing(Order::getDocNumber);
        } else if (sortWay == SortWay.DATE_CREATE) {
            return Comparator.comparing(Order::getDateCreate);
        } else if (sortWay == SortWay.DATE_FACTORY) {
            return Comparator.comparing(Order::getDateToFactory);
        } else if (sortWay == SortWay.DATE_SHIPMENT) {
            return Comparator.comparing(Order::getDateToShipment);
        } else if (sortWay == SortWay.DATE_SHIPMENT_REAL) {
            return Comparator.comparing(Order::getDateToShipmentReal);
        } else if (sortWay == SortWay.CLIENT) {
            return Comparator.comparing(Order::getClient);
        } else if (sortWay == SortWay.MANAGER) {
            return Comparator.comparing(Order::getManager);
        } else if (sortWay == SortWay.DATE_KB) {
            return Comparator.comparing(o -> o.getDateKbNew().getTime());
        }
        return Comparator.comparing(Order::getDocNumber);
    }

    public DescriptionTime getDateKbNew () {
        return this.getDescriptions().stream()
                .filter(description -> description.getTypeName() == TypeName.KB)
                .findFirst()
                .map(Description::getTimes)
                .get().stream()
                .filter(times -> times.getStatusName() == StatusName.KB_NEW)
                .findFirst().orElse(null);
    }

    public LocalDateTime getDateToShipment () {
        return dateCreate.toLocalDateTime().plusDays(durationTime);
    }

    public LocalDateTime getDateToShipmentReal () {
        return times.stream()
                .filter(t -> t.getStatusName() == StatusName.SHIPMENT_REAL)
                .map(OrderTime::getTime)
                .findFirst().orElse(getDateToShipment());
    }

    @Override
    public String getEntityName() {
        return "Order";
    }

    @Override
    public String toString() {
        return "Order{" +
                "id='" + id + '\'' +
                ", docNumber='" + docNumber + '\'' +
                ", client='" + client + '\'' +
                ", manager='" + manager + '\'' +
                ", durationTime=" + durationTime +
                ", dateCreate=" + dateCreate +
                ", dateToFactory=" + dateToFactory +
                ", price=" + price +
                ", status=" + status +
                ", descriptions=" + descriptions +
                ", times=" + times +
                '}';
    }
}
