package org.aleks4ay.developer.model;

import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.text.Text;

public class DescriptionParsing {

    private RadioButton buttonFactory;
    private RadioButton buttonKB;
    private RadioButton buttonTeh;
    private final RadioButton buttonOther;

    private String id;
    private int position;
    private String descr;
    private String size;
    private int amount;
    private String type;
    private String status;

    public DescriptionParsing(String id, int position, String descr, String size, int amount, String type, String status) {
        this.id = id;
        this.position = position;
        this.descr = descr;
        this.size = size;
        this.amount = amount;
        this.type = type;
        this.status = status;

        this.buttonFactory = new RadioButton();
        this.buttonKB = new RadioButton();
        this.buttonTeh = new RadioButton();
        this.buttonOther = new RadioButton();
        ToggleGroup parsingCheckGroup = new ToggleGroup();

        buttonFactory.setToggleGroup(parsingCheckGroup);
        buttonKB.setToggleGroup(parsingCheckGroup);
        buttonTeh.setToggleGroup(parsingCheckGroup);
        buttonOther.setToggleGroup(parsingCheckGroup);


        if (type.equalsIgnoreCase("Техн.")) {
            buttonTeh.setSelected(true);
        }
/*        else if (isParsing == 1) {
            if (type.equalsIgnoreCase("ЦЕХ")) {
                buttonFactory.setSelected(true);
            }
            else if (type.equalsIgnoreCase("КБ")) {
                buttonKB.setSelected(true);
            }
            else if (type.equalsIgnoreCase("Прочее")) {
                buttonOther.setSelected(true);
            }
            buttonFactory.setDisable(true);
            buttonKB.setDisable(true);
            buttonOther.setDisable(true);
            buttonTeh.setDisable(true);
        }*/
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

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStatus() {
        return status;
    }

    public RadioButton getButtonFactory() {
        return buttonFactory;
    }

    public void setButtonFactory(RadioButton buttonFactory) {
        this.buttonFactory = buttonFactory;
    }

    public RadioButton getButtonKB() {
        return buttonKB;
    }

    public void setButtonKB(RadioButton buttonKB) {
        this.buttonKB = buttonKB;
    }

    public Text getDescriptionText() {
        Text result = new Text(descr);
        result.setWrappingWidth(490.0);
        return result;
    }

    public RadioButton getButtonTeh() {
        return buttonTeh;
    }

    public RadioButton getButtonOther() {
        return buttonOther;
    }

    public void setButtonTeh(RadioButton buttonTeh) {
        this.buttonTeh = buttonTeh;
    }
}
