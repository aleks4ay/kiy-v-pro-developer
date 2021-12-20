package org.aleks4ay.developer.model;

public class PseudoName implements BaseEntity<PseudoName> {

    private String pseudoName;
    private String name;

    public PseudoName(String pseudoName, String name) {
        this.pseudoName = pseudoName;
        this.name = name;
    }

    @Override
    public String getId() {
        return pseudoName;
    }

    @Override
    public String getEntityName() {
        return "PseudoName";
    }

    public String getPseudoName() {
        return pseudoName;
    }

    public void setPseudoName(String pseudoName) {
        this.pseudoName = pseudoName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "PseudoName{" +
                "pseudoName='" + pseudoName + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
