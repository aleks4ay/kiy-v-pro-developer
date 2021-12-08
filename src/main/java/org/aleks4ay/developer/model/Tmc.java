package org.aleks4ay.developer.model;

public class Tmc implements BaseEntity<Tmc> {

    private String id;
    private String idParent;
    private String code;
    private int sizeA;
    private int sizeB;
    private int sizeC;
    private String descr;
    private int isFolder;
    private String descrAll;
    private String type;

    public Tmc(String id) {
        this.id = id;
    }

    public Tmc(String id, String idParent, String code, int sizeA, int sizeB, int sizeC, String descr, int isFolder, String descrAll, String type) {
        this.id = id;
        this.idParent = idParent;
        this.code = code;
        this.sizeA = sizeA;
        this.sizeB = sizeB;
        this.sizeC = sizeC;
        this.descr = descr;
        this.isFolder = isFolder;
        this.descrAll = descrAll;
        this.type = type;
    }

    @Override
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIdParent() {
        return idParent;
    }

    public void setIdParent(String idParent) {
        this.idParent = idParent;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
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

    public String getDescr() {
        return descr;
    }

    public void setDescr(String descr) {
        this.descr = descr;
    }

    public int getIsFolder() {
        return isFolder;
    }

    public void setIsFolder(int isFolder) {
        this.isFolder = isFolder;
    }

    public String getDescrAll() {
        return descrAll;
    }

    public void setDescrAll(String descrAll) {
        this.descrAll = descrAll;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }


    @Override
    public String getEntityName() {
        return "Tmc";
    }

    @Override
    public String toString() {
        return "Tmc{" +
                "id='" + id + '\'' +
                ", idParent='" + idParent + '\'' +
                ", code='" + code + '\'' +
                ", sizeA=" + sizeA +
                ", sizeB=" + sizeB +
                ", sizeC=" + sizeC +
                ", descr='" + descr + '\'' +
                ", isFolder=" + isFolder +
                ", descrAll='" + descrAll + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}
