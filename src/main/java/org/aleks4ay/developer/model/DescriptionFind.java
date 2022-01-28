package org.aleks4ay.developer.model;

import javafx.scene.control.Button;
import javafx.scene.text.Text;
import org.aleks4ay.developer.tools.Constants;

import java.time.LocalDateTime;
import java.util.Optional;

public class DescriptionFind extends Description{

    private Button imageButton = null;

    public DescriptionFind(Description d) {
        super(d.getId(), d.getIdOrder(), d.getPosition(), d.getIdTmc(), d.getQuantity(), d.getDescrSecond(),
                d.getDescrAll(), d.getSizeA(), d.getSizeB(), d.getSizeC(), d.getEmbodiment(), d.getTypeName(),
                d.getStatusName(), d.getDesigner());
        if (d.isExistImages()) {
            setImageButton(createButton(d.getId()));
        }
        super.setTimes(d.getTimes());
    }

    public String getTypeString() {
        return getTypeName().getName();
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

    public String getTimeFactoryDone() {
        return getTimeBase(StatusName.FACTORY_DONE);
    }

    public String getTimeNotTracked() {
        return getTimeBase(StatusName.NOT_TRACKED);
    }

    public String getTimeCanceled() {
        return getTimeBase(StatusName.CANCELED);
    }

    public Object getImageButton() {
        return imageButton != null ? imageButton : "";
    }

    public void setImageButton(Button imageButton) {
        this.imageButton = imageButton;
    }

    public static Button createButton(String id) {
        Button button = new Button("V");
        button.setId(id);
        return button;
    }
}