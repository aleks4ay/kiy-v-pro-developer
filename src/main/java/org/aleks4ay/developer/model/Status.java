package org.aleks4ay.developer.model;

public class Status{

    private String id;
    private TypeName typeName;
    private StatusName statusName;
    private int isTechno;
    private String designer;
    private int isParsing;
//    private List<DescriptionTime> times = new ArrayList<>();


    public Status() {
    }

    public Status(String id, String typeName, StatusName statusName, int isTechno, String designer, int isParsing) {
        this.id = id;
        this.typeName = TypeName.valueOf(typeName);
        this.statusName = statusName;
        this.isTechno = isTechno;
        this.designer = designer;
        this.isParsing = isParsing;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public int getIsTechno() {
        return isTechno;
    }

    public void setIsTechno(int isTechno) {
        this.isTechno = isTechno;
    }

    public String getDesigner() {
        return designer;
    }

    public void setDesigner(String designer) {
        this.designer = designer;
    }

    public int getIsParsing() {
        return isParsing;
    }

    public void setIsParsing(int isParsing) {
        this.isParsing = isParsing;
    }

    @Override
    public String toString() {
        return "Status{" +
                "id='" + id + '\'' +
                ", type='" + typeName + '\'' +
                ", statusName=" + statusName +
                ", isTechno=" + isTechno +
                ", designer='" + designer + '\'' +
                ", isParsing=" + isParsing +
                '}';
    }

    public enum  StatusName {
        NEW(0),
        PARSED(1),
        COMPLETE(2),
        CANCELED(3);

        private int statusIndex;

        StatusName(int statusIndex) {
            this.statusIndex = statusIndex;
        }
    }
}
