package org.aleks4ay.developer.model;

import java.sql.Timestamp;

public class Journal implements BaseEntity<Journal> {

    private String idDoc;
    private String docNumber;
    private Timestamp dateCreate;

    public Journal(String idDoc, String docNumber, Timestamp dateCreate) {
        this.idDoc = idDoc;
        this.docNumber = docNumber;
        this.dateCreate = dateCreate;
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

    public Timestamp getDateCreate() {
        return dateCreate;
    }

    public void setDateCreate(Timestamp dateCreate) {
        this.dateCreate = dateCreate;
    }

    @Override
    public String getEntityName() {
        return "Journal";
    }

    @Override
    public String toString() {
        return "Journal{" +
                "idDoc='" + idDoc + '\'' +
                ", docNumber='" + docNumber + '\'' +
                ", dateCreate=" + dateCreate +
                '}';
    }
}
