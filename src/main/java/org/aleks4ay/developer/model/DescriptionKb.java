package org.aleks4ay.developer.model;

import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.text.Text;
import org.aleks4ay.developer.tools.Constants;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class DescriptionKb {

    private CheckBox checkBoxKbStart;
    private CheckBox checkBoxKbQuestion;
    private CheckBox checkBoxKbContinued;
    private CheckBox checkBoxKbEnd;

    private String id;
    private int position;
    private String descr;
    private int sizeA;
    private int sizeB;
    private int sizeC;
    private int amount;
    private String status;
    private LocalDateTime endDay;
    private String designer;
    private List<DescriptionTime> times;
    private Button imageButton = null;


    public DescriptionKb(String id, int position, String descr, int sizeA, int sizeB, int sizeC, int amount,
                         String status, String designer, List<DescriptionTime> times) {
        this.id = id;
        this.position = position;
        this.descr = descr;
        this.sizeA = sizeA;
        this.sizeB = sizeB;
        this.sizeC = sizeC;
        this.amount = amount;
        this.status = status;
        this.designer = designer;
        this.checkBoxKbStart = new CheckBox();
        this.checkBoxKbQuestion = new CheckBox();
        this.checkBoxKbContinued = new CheckBox();
        this.checkBoxKbEnd = new CheckBox();
        this.times = times;
    }


    public static List<DescriptionKb> makeFromOrderDescription(Order order) {
        List<DescriptionKb> result = new ArrayList<>();
        for (Description d : order.getDescriptions()) {
            DescriptionKb newDescription = new DescriptionKb(
                    d.getId(),
                    d.getPosition(),
                    (d.getDescrAll() + " " + d.getDescrSecond()),
                    d.getSizeA(),
                    d.getSizeB(),
                    d.getSizeC(),
                    d.getQuantity(),
                    d.getStatusName().toString(),
                    d.getDesigner(),
                    d.getTimes()
            );
            newDescription.setEndDay();
            if (d.isExistImages()) {
                newDescription.setImageButton(createButton(newDescription.getId()));
            }
            result.add(newDescription);
        }
        return result;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public String getDescr() {
        return descr;
    }

    public void setDescr(String descr) {
        this.descr = descr;
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

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public String getDesigner() {
        return designer;
    }

    public void setDesigner(String designer) {
        this.designer = designer;
    }

    public CheckBox getCheckBoxKbStart() {
        return checkBoxKbStart;
    }

    public void setCheckBoxKbStart(CheckBox checkBoxKbStart) {
        this.checkBoxKbStart = checkBoxKbStart;
    }

    public CheckBox getCheckBoxKbQuestion() {
        return checkBoxKbQuestion;
    }

    public void setCheckBoxKbQuestion(CheckBox checkBoxKbQuestion) {
        this.checkBoxKbQuestion = checkBoxKbQuestion;
    }

    public CheckBox getCheckBoxKbContinued() {
        return checkBoxKbContinued;
    }

    public void setCheckBoxKbContinued(CheckBox checkBoxKbContinued) {
        this.checkBoxKbContinued = checkBoxKbContinued;
    }

    public CheckBox getCheckBoxKbEnd() {
        return checkBoxKbEnd;
    }

    public void setCheckBoxKbEnd(CheckBox checkBoxKbEnd) {
        this.checkBoxKbEnd = checkBoxKbEnd;
    }

    public Text getDescriptionText() {
        Text result = new Text(descr);
        result.setWrappingWidth(290.0);
        return result;
    }

    public List<DescriptionTime> getTimes() {
        return times;
    }

    public void setTimes(List<DescriptionTime> times) {
        this.times = times;
    }

    public void setEndDay() {
        for (DescriptionTime t : times) {
            if (t.getStatusName().equals(StatusName.KB_NEW)) {
                this.endDay = t.getTime().plusDays(3);
                break;
            }
        }
        if (endDay == null) {
            this.endDay = LocalDateTime.now().plusDays(3);
        }
        if (endDay.getDayOfWeek().equals(DayOfWeek.SATURDAY) | endDay.getDayOfWeek().equals(DayOfWeek.SUNDAY)) {
            this.endDay = endDay.plusDays(2);
        }
    }

    public LocalDateTime getEndDay() {
        return endDay;
    }

    public String getEndDayString() {
        return endDay == null ? "-" : endDay.format(Constants.dayTimeFormatter).replace("  ", System.lineSeparator());
    }

    private Object getTimeBase(StatusName statusName, CheckBox checkBox) {
        Optional<LocalDateTime> time = times.stream()
                .filter(t -> t.getStatusName().equals(statusName))
                .map(DescriptionTime::getTime)
                .findFirst();
        return time.isPresent()
                ? time.get().format(Constants.tableCellTimeFormatter)
                : checkBox;
    }

    public Object getTimeKb() {
        return getTimeBase(StatusName.KB_NEW, null);
    }

    public Object getTimeKbStart() {
        return getTimeBase(StatusName.KB_START, checkBoxKbStart);
    }

    public Object getTimeKbQuestion() {
        return getTimeBase(StatusName.KB_QUESTION, checkBoxKbQuestion);
    }

    public Object getTimeKbContinued() {
        return getTimeBase(StatusName.KB_CONTINUED, checkBoxKbContinued);
    }

    public Object getTimeKbEnd() {
        return getTimeBase(StatusName.KB_END, checkBoxKbEnd);
    }

    public boolean isNewStatusBigger(StatusName newStatusName) {
        return newStatusName.getStatusIndex() > StatusName.valueOf(status).getStatusIndex();
    }

    public boolean isNewStatusLess(StatusName newStatusName) {
        return newStatusName.getStatusIndex() < StatusName.valueOf(status).getStatusIndex();
    }

    public void setEndDay(LocalDateTime endDay) {
        this.endDay = endDay;
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
