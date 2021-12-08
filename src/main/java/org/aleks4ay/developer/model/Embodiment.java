package org.aleks4ay.developer.model;

public class Embodiment implements BaseEntity<Embodiment>{

    private String id;
    private String description;

    public Embodiment(String id, String description) {
        this.id = id;
        this.description = description;
    }

    @Override
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String getEntityName() {
        return "Embodiment";
    }

    @Override
    public String toString() {
        return "Embodiment{" +
                "id='" + id + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}