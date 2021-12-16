package org.aleks4ay.developer.model;

import java.time.LocalDateTime;

public class DescriptionTime implements BaseEntity<DescriptionTime>, Comparable<DescriptionTime> {
    private long id;
    private final String idDescription;
    private final StatusName statusName;
    private final LocalDateTime time;

    public DescriptionTime(long id, String idDescription, StatusName statusName, LocalDateTime time) {
        this.id = id;
        this.idDescription = idDescription;
        this.statusName = statusName;
        this.time = time;
    }

    public DescriptionTime(String idDescription, String statusName, LocalDateTime time) {
        this.idDescription = idDescription;
        this.statusName = StatusName.valueOf(statusName);
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

    public StatusName getStatusName() {
        return statusName;
    }

    public LocalDateTime getTime() {
        return time;
    }

    @Override
    public String toString() {
        return "DescriptionTime{" +
                "id=" + id +
                ", idDescription='" + idDescription + '\'' +
                ", status='" + statusName + '\'' +
                ", time=" + time +
                '}';
    }

    @Override
    public String getEntityName() {
        return "DescriptionTime";
    }

    @Override
    public int compareTo(DescriptionTime o) {
        return statusName.getStatusIndex() - o.getStatusName().getStatusIndex();
    }
}
