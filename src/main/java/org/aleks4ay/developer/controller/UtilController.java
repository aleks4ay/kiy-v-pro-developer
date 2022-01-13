package org.aleks4ay.developer.controller;

import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.TabPane;
import javafx.scene.layout.AnchorPane;
import org.aleks4ay.developer.model.Description;
import org.aleks4ay.developer.model.DescriptionKb;
import org.aleks4ay.developer.model.Order;

import java.util.Set;

public final class UtilController {
    private static final String SEPARATOR = ",  ";
    private static final String SEPARATOR_2 = " × ";
    private TabPane rootTabPane;
    private AnchorPane rootImagePane;
    private Label labelId;
    private Label labelDescription;
    private DescriptionKb descriptionKb;
    private Order order;

    private static final UtilController instance = new UtilController();

    private UtilController() {
    }

    public static UtilController getInstance() {
        return instance;
    }


    public void setImagePaneVisible(Parent root) {
        if (this.rootImagePane == null || this.rootTabPane == null) {
            initNodeVariable(root);
        }
        setLabelDescription();
        rootTabPane.setDisable(true);
        rootTabPane.setVisible(false);
        rootImagePane.setVisible(true);
    }

    public void setImagePaneInvisible(Parent root) {
        if (this.rootImagePane == null || this.rootTabPane == null) {
            initNodeVariable(root);
        }
        emptyLabelDescription();
        rootTabPane.setDisable(false);
        rootTabPane.setVisible(true);
        rootImagePane.setVisible(false);
    }

    private void initNodeVariable(Parent root) {
        ObservableList<Node> nodes = root.getChildrenUnmodifiable();
        for (Node n : nodes) {
            if (n.getClass().getSimpleName().equalsIgnoreCase("TabPane")) {
                this.rootTabPane = (TabPane)n;
            }
            if (n.getClass().getSimpleName().equalsIgnoreCase("AnchorPane")) {
                this.rootImagePane = (AnchorPane)n;
            }
        }
        Set<Node> labels = rootImagePane.lookupAll("Label");
        for (Node l : labels) {
            if (l.getId().equalsIgnoreCase("description")) {
                this.labelDescription = (Label)l;
            }
            if (l.getId().equalsIgnoreCase("description_id")) {
                this.labelId = (Label)l;
            }
        }
    }

    public void emptyLabelDescription() {
        labelDescription.setText("");
        labelId.setText("");
    }

    public void setLabelDescription() {
        if (order != null && descriptionKb != null) {
            labelDescription.setText(getSelectedDescriptionText(order, descriptionKb.getId()));
            labelId.setText(descriptionKb.getId());
        }
    }

    public DescriptionKb getDescription() {
        return descriptionKb;
    }

    public void setDescriptionKb(DescriptionKb descriptionKb) {
        this.descriptionKb = descriptionKb;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public static String getSelectedDescriptionText(Order order, String descriptionId) {
        StringBuilder sb = new StringBuilder();
        if (order != null) {
            sb.append(order.getDocNumber()).append(SEPARATOR);
            for (Description d : order.getDescriptions()) {
                if (d.getId().equals(descriptionId)) {
                    sb.append(d.getDescrAll()).append(" ").append(d.getDescrSecond())
                            .append(SEPARATOR).append(d.getSizeA())
                            .append(SEPARATOR_2).append(d.getSizeB())
                            .append(SEPARATOR_2).append(d.getSizeC());
                    break;
                }
            }
        }
        return sb.toString();
    }
}
