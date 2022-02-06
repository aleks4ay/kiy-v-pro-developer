package org.aleks4ay.developer.model;

import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.text.Text;
import org.aleks4ay.developer.service.TmcServiceTechno;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DescriptionParsing{

    private RadioButton buttonFactory;
    private RadioButton buttonKB;
    private RadioButton buttonTeh;
    private final RadioButton buttonOther;

    private String id;
    private int position;
    private String descr;
    private String size;
    private int amount;
    private TypeName type;
    private String status;

    public DescriptionParsing(String id, int position, String descr, String size, int amount, String type, String status) {
        this.id = id;
        this.position = position;
        this.descr = descr;
        this.size = size;
        this.amount = amount;
        this.type = TypeName.valueOf(type);
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

        if (type.equalsIgnoreCase(TypeName.TECHNO.toString())) {
            buttonTeh.setSelected(true);
        }
        if (type.equalsIgnoreCase(TypeName.KB.toString())) {
            buttonKB.setSelected(true);
        }
        if (type.equalsIgnoreCase(TypeName.FACTORY.toString())) {
            buttonFactory.setSelected(true);
        }
        if (type.equalsIgnoreCase(TypeName.OTHER.toString())) {
            buttonOther.setSelected(true);
        }
    }

    public static List<DescriptionParsing> makeFromOrderDescription(Order order) {
        Map<String, String> technoIdAllMap = TmcServiceTechno.getTechnoIdAll();
        List<DescriptionParsing> result = new ArrayList<>();
        for (Description d : order.getDescriptions()) {
            if (d.getTypeName() == TypeName.NEW && technoIdAllMap.containsKey(d.getIdTmc())) {
                d.setTypeName(TypeName.TECHNO);
            }

            result.add(new DescriptionParsing(
                    d.getId(),
                    d.getPosition(),
                    (d.getDescrAll() + " " + d.getDescrSecond()),
                    (d.getSizeA() + "×" + d.getSizeB() + "×" + d.getSizeC()),
                    d.getQuantity(),
                    d.getTypeName().toString(),
                    d.getStatusName().toString()
            ));
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

    public TypeName getType() {
        return type;
    }

    public void setType(TypeName type) {
        this.type = type;
    }

    public void setStatus(String status) {
        this.status = status;
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
        result.setWrappingWidth(370.0);
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
