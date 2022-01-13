package org.aleks4ay.developer.model;

import javafx.scene.image.Image;

import java.time.LocalDateTime;

public class DescriptionImage implements BaseEntity<DescriptionImage> {
    private long id;
    private final String idDescription;
    private final Image image;
    private final String name;
    private final LocalDateTime time;

    public DescriptionImage(long id, String idDescription, Image image, String name, LocalDateTime time) {
        this.id = id;
        this.idDescription = idDescription;
        this.image = image;
        this.name = name;
        this.time = time;
    }

    public DescriptionImage(String idDescription, Image image, String name, LocalDateTime time) {
        this.idDescription = idDescription;
        this.image = image;
        this.name = name;
        this.time = time;
    }

    public Long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getIdDescription() {
        return idDescription;
    }

    public Image getImage() {
        return image;
    }

    public String getName() {
        return name;
    }

    public LocalDateTime getTime() {
        return time;
    }

    @Override
    public String toString() {
        return "DescriptionImage{" +
                "id=" + id +
                ", idDescription='" + idDescription + "'" +
                ", image(" + image.getWidth() + "x" + image.getHeight() +
                "), name=" + name +
                ", time=" + time +
                '}';
    }

    @Override
    public String getEntityName() {
        return "DescriptionImage";
    }
}
