package org.aleks4ay.developer.model;

import javafx.scene.text.Text;
import org.aleks4ay.developer.tools.Constants;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public class DescriptionFind extends Description{

    public DescriptionFind(String id, String idOrder, int position, String idTmc, int quantity, String descrSecond,
                           String descrAll, int sizeA, int sizeB, int sizeC, String embodiment, TypeName typeName,
                           StatusName statusName, String designer, List<DescriptionTime> times) {
        super(id, idOrder, position, idTmc, quantity, descrSecond, descrAll, sizeA, sizeB, sizeC, embodiment, typeName,
                statusName, designer);
        super.setTimes(times);
    }

    public DescriptionFind(Description d) {
        super(d.getId(), d.getIdOrder(), d.getPosition(), d.getIdTmc(), d.getQuantity(), d.getDescrSecond(),
                d.getDescrAll(), d.getSizeA(), d.getSizeB(), d.getSizeC(), d.getEmbodiment(), d.getTypeName(),
                d.getStatusName(), d.getDesigner());
        super.setTimes(d.getTimes());
    }

    public String getTypeString() {
        return getTypeName().toString();
    }

    public String getStatusString() {
        return getStatusName().toStringRusToCell();
    }

    public Text getDescriptionText() {
        Text result = new Text(getDescrAll() + " " + getDescrSecond());
        result.setWrappingWidth(290.0);
        return result;
    }

    private String getTimeBase(StatusName statusName) {

        Optional<LocalDateTime> time = getTimes().stream()
                .filter(t -> t.getStatusName().equals(statusName))
                .map(DescriptionTime::getTime)
                .findFirst();
        return time.isPresent()
                ? time.get().format(Constants.tableCellTimeFormatter)
                : "-";
    }

    public String getTimeKb() {
        return getTimeBase(StatusName.KB_NEW);
    }

    public String getTimeKbStart() {
        return getTimeBase(StatusName.KB_START);
    }

    public String getTimeKbQuestion() {
        return getTimeBase(StatusName.KB_QUESTION);
    }

    public String getTimeKbContinued() {
        return getTimeBase(StatusName.KB_CONTINUED);
    }

    public String getTimeKbEnd() {
        return getTimeBase(StatusName.KB_END);
    }

    public String getTimeFactory() {
        return getTimeBase(StatusName.FACTORY);
    }

    public String getTimeShipment() {
        return getTimeBase(StatusName.SHIPMENT);
    }

    public String getTimeComplete() {
        return getTimeBase(StatusName.COMPLETE);
    }

    public String getTimeNotTracked() {
        return getTimeBase(StatusName.NOT_TRACKED);
    }

    public String getTimeCanceled() {
        return getTimeBase(StatusName.CANCELED);
    }
}