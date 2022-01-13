package org.aleks4ay.developer.model;

import java.util.ArrayList;
import java.util.List;

public class Description implements BaseEntity<Description> {

    private String id;
    private String idOrder;
    private int position;
    private String idTmc;
    private int quantity;
    private String descrSecond;
    private String descrAll;
    private int sizeA;
    private int sizeB;
    private int sizeC;
    private String embodiment;
    private TypeName typeName;
    private StatusName statusName;
    private String designer;
    private List<DescriptionTime> times = new ArrayList<>();
    private List<DescriptionImage> descriptionImages = new ArrayList<>();
    private boolean existImages;

    public Description(String id, String idOrder, int position, String idTmc, int quantity, String descrSecond,
                       String descrAll, int sizeA, int sizeB, int sizeC, String embodiment, TypeName typeName,
                       StatusName statusName, String designer) {
        this.id = id;
        this.idOrder = idOrder;
        this.position = position;
        this.idTmc = idTmc;
        this.quantity = quantity;
        this.descrSecond = descrSecond;
        this.descrAll = descrAll;
        this.sizeA = sizeA;
        this.sizeB = sizeB;
        this.sizeC = sizeC;
        this.embodiment = embodiment;
        this.typeName = typeName;
        this.statusName = statusName;
        this.designer = designer;
    }

    @Override
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIdOrder() {
        return idOrder;
    }

    public void setIdOrder(String idOrder) {
        this.idOrder = idOrder;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public String getIdTmc() {
        return idTmc;
    }

    public void setIdTmc(String idTmc) {
        this.idTmc = idTmc;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getDescrSecond() {
        return descrSecond;
    }

    public void setDescrSecond(String descrSecond) {
        this.descrSecond = descrSecond;
    }

    public String getDescrAll() {
        return descrAll;
    }

    public void setDescrAll(String descrAll) {
        this.descrAll = descrAll;
    }

    public int getSizeA() {
        return sizeA;
    }

    public void setSizeA(int sizeA) {
        this.sizeA = sizeA;
    }

    public int getSizeB() {
        return sizeB;
    }

    public void setSizeB(int sizeB) {
        this.sizeB = sizeB;
    }

    public int getSizeC() {
        return sizeC;
    }

    public void setSizeC(int sizeC) {
        this.sizeC = sizeC;
    }

    public String getEmbodiment() {
        return embodiment;
    }

    public void setEmbodiment(String embodiment) {
        this.embodiment = embodiment;
    }

    public TypeName getTypeName() {
        return typeName;
    }

    public void setTypeName(TypeName typeName) {
        this.typeName = typeName;
    }

    public StatusName getStatusName() {
        return statusName;
    }

    public void setStatusName(StatusName statusName) {
        this.statusName = statusName;
    }

    public String getDesigner() {
        return designer;
    }

    public void setDesigner(String designer) {
        this.designer = designer;
    }

    public List<DescriptionTime> getTimes() {
        return times;
    }

    public void setTimes(List<DescriptionTime> times) {
        this.times = times;
    }

    public List<DescriptionImage> getDescriptionImages() {
        return descriptionImages;
    }

    public void setDescriptionImages(List<DescriptionImage> descriptionImages) {
        this.descriptionImages = descriptionImages;
    }

    public boolean isExistImages() {
        return existImages;
    }

    public void setExistImages(boolean existImages) {
        this.existImages = existImages;
    }

    @Override
    public String getEntityName() {
        return "Description";
    }

    @Override
    public String toString() {
        return "Description{" +
                "id='" + id + '\'' +
                ", idOrder='" + idOrder + '\'' +
                ", position=" + position +
                ", idTmc='" + idTmc + '\'' +
                ", quantity=" + quantity +
                ", descrSecond='" + descrSecond + '\'' +
                ", descrAll='" + descrAll + '\'' +
                ", sizeA=" + sizeA +
                ", sizeB=" + sizeB +
                ", sizeC=" + sizeC +
                ", embodiment='" + embodiment + '\'' +
                ", typeName=" + typeName +
                ", statusName=" + statusName +
                ", designer='" + designer + '\'' +
                ", times=" + times +
                ", existImages=" + existImages +
                '}';
    }
}
