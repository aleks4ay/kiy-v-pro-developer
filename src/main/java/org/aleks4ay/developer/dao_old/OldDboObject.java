package org.aleks4ay.developer.dao_old;

import org.aleks4ay.developer.model.DescriptionTime;
import org.aleks4ay.developer.model.StatusName;
import org.aleks4ay.developer.model.TypeName;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class OldDboObject {
    private String id;
    private TypeName typeName;
    private StatusName statusName;
    private String designer;
    private List<DescriptionTime> times = new ArrayList<>();

    public OldDboObject(String id, int typeIndex, int statusIndexFromOldDB, String designer) {
        this.id = id;
        this.typeName = TypeName.values()[typeIndex];
        String statusName = convertStatusNameByOldStatusIndex(statusIndexFromOldDB);
        this.statusName = StatusName.valueOf(statusName);
        this.designer = designer;
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

    public void addTime(int statusIndex, LocalDateTime time) {
        String statusName = convertStatusNameByOldStatusIndex(statusIndex);
        times.add(new DescriptionTime(id, statusName, time));
    }

    private String convertStatusNameByOldStatusIndex(int statusIndex) {
        if (statusIndex <= 7) {
            return StatusName.values()[statusIndex].toString();
        }
        if (statusIndex == 22) {
            return StatusName.values()[8].toString();
        }
        if (statusIndex == 24) {
            return StatusName.values()[9].toString();
        }
        if (statusIndex == 26) {
            return StatusName.values()[10].toString();
        }
        if (statusIndex == 23) {
            return StatusName.values()[11].toString();
        }
        return "";
    }

    @Override
    public String toString() {
        return "DboBigObject{" +
                "id='" + id + '\'' +
                ", typeName=" + typeName +
                ", statusName=" + statusName +
                ", designer='" + designer + '\'' +
                ", times=" + times +
                '}';
    }
}
