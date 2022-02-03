package org.aleks4ay.developer.controller;

import javafx.beans.property.LongProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import javafx.util.Callback;
import org.aleks4ay.developer.dao.ConnectionPool;
import org.aleks4ay.developer.dao.OrderDao;
import org.aleks4ay.developer.model.*;
import org.aleks4ay.developer.service.KbEngine;
import org.aleks4ay.developer.service.OrderService;
import org.aleks4ay.developer.tools.FileWriter;
import org.aleks4ay.developer.tools.PropertyListener;

import java.net.URL;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

public class ControllerKb implements Initializable {
    private static boolean rowOdd;
    private static final long positionOnPage = 30;
    private final LongProperty isNewOrderTime = PropertyListener.getOrderTimeProperty();

    private final OrderService orderService = new OrderService(new OrderDao(ConnectionPool.getInstance()));

    public static SortWay sortWayKb = SortWay.DATE_KB;
    private int selectedRow = 0;
    private final Page page = new Page(positionOnPage);

    private final ObservableList<Order> listOrderKb = FXCollections.observableArrayList();
    private final ObservableList<DescriptionKb> listDescriptionKb = FXCollections.observableArrayList();

//---------------------- B U T T O N
    @FXML private Button parsing_next;
    @FXML private Button parsing_previous;

    @FXML private Text timeUpdate;
    @FXML private Label info_ord;
    @FXML private Label info_ord_now;
    @FXML private Label info_descr;
    @FXML private Label info_docnum;
    @FXML private Label info_client;
    @FXML private Label info_data_f;
    @FXML private Label info_manag;
    @FXML private Label info_position;

//----------------T A B    K B   1 ---------------------------------
    @FXML private TableView<Order> tableKbView1;
    @FXML private TableColumn<Order, String> num;
    @FXML private TableColumn<Order, String> client;
    @FXML private TableColumn<Order, String> manager;
    @FXML private TableColumn<Order, String> data_f;
    @FXML private TableColumn<Order, String> count_position;

//----------------T A B    K B   2 ---------------------------------
    @FXML private TableView<DescriptionKb> tableKbView2;
    @FXML private TableColumn<DescriptionKb, String> pos;
    @FXML private TableColumn<DescriptionKb, String> description;
    @FXML private TableColumn<DescriptionKb, String> size_a;
    @FXML private TableColumn<DescriptionKb, String> size_b;
    @FXML private TableColumn<DescriptionKb, String> size_c;
    @FXML private TableColumn<DescriptionKb, String> amount;
    @FXML private TableColumn<DescriptionKb, String> designer;
    @FXML private TableColumn<DescriptionKb, String> time2;
    @FXML private TableColumn<DescriptionKb, Object> time3;
    @FXML private TableColumn<DescriptionKb, Object> time4;
    @FXML private TableColumn<DescriptionKb, Object> time5;
    @FXML private TableColumn<DescriptionKb, Object> time6;
    @FXML private TableColumn<DescriptionKb, String> time7;
    @FXML private TableColumn<DescriptionKb, Button> add_item;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        initKbTabOne();
        initKbTabTwo();

        isNewOrderTime.addListener((observable, oldValue, newValue) -> {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            updateAllView();
        });

        tableKbView1.getSelectionModel().selectedIndexProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                updateSelectedDescription();
            }
        });
    }

    private void updateAllView(){
        try {
            Thread.sleep(50);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        initKbTabOne();
        timeUpdate.setText(FileWriter.readTimeChange());
    }


    private void initKbTabOne() {
        listOrderKb.clear();
        listOrderKb.addAll(orderService.getOrdersWithDescriptionsKb(page, sortWayKb));
        if (listOrderKb.size() == 0 && page.getPosition() > 0) {
            applyPrevious();
            return;
        }
        num.setCellValueFactory(new PropertyValueFactory<>("docNumber"));
        client.setCellValueFactory(new PropertyValueFactory<>("client"));
        manager.setCellValueFactory(new PropertyValueFactory<>("manager"));
        data_f.setCellValueFactory(new PropertyValueFactory<>("dateToFactoryString"));
        count_position.setCellValueFactory(new PropertyValueFactory<>("numberOfPosition"));

        tableKbView1.setItems(listOrderKb);

        tableKbView1.setRowFactory(param -> new TableRow<Order>() {
            @Override
            public void updateItem(Order item, boolean empty) {
                super.updateItem(item, empty);
                this.getStyleClass().removeIf(s -> s.contains("red"));

                if (item!=null) {
                    String style = applyRowStyleTableView(item.getEndDevelopingDay(), true);
                    if (!style.isEmpty()) {
                        this.getStyleClass().add(style);
                    }
                }
            }
        });

        tableKbView1.getSelectionModel().select(selectedRow);
        info_ord.setText(String.valueOf(page.getSize()));
        info_position.setText(page.getInterval());
        if (page.isLast()) {
            parsing_next.setDisable(true);
        }
        if (page.isFirst()) {
            parsing_previous.setDisable(true);
        }

        info_descr.setText(getNumberOfDescriptionAsString());
        info_ord_now.setText(getNumberOfWorkedOrderAsString());
    }

    private void initKbTabTwo() {
        updateSelectedDescription();

        pos.setCellValueFactory(new PropertyValueFactory<>("position"));
        description.setCellValueFactory(new PropertyValueFactory<>("descr"));
        description.setCellFactory(param -> {
            TableCell<DescriptionKb, String> cell = new TableCell<>();
            Text text = new Text();
            cell.setGraphic(text);
            cell.setPrefHeight(Control.USE_COMPUTED_SIZE);
            text.wrappingWidthProperty().bind(cell.widthProperty());
            text.textProperty().bind(cell.itemProperty());
            text.setStyle("-fx-text-fill: green;");
            cell.setStyle("-fx-text-fill: green;");
            return cell ;
        });
        size_a.setCellValueFactory(new PropertyValueFactory<>("sizeA"));
        size_b.setCellValueFactory(new PropertyValueFactory<>("sizeB"));
        size_c.setCellValueFactory(new PropertyValueFactory<>("sizeC"));
        amount.setCellValueFactory(new PropertyValueFactory<>("amount"));
        designer.setCellValueFactory(new PropertyValueFactory<>("designer"));
        time2.setCellValueFactory(new PropertyValueFactory<>("timeKb"));
        time3.setCellValueFactory(new PropertyValueFactory<>("timeKbStart"));
        time4.setCellValueFactory(new PropertyValueFactory<>("timeKbQuestion"));
        time5.setCellValueFactory(new PropertyValueFactory<>("timeKbContinued"));
        time6.setCellValueFactory(new PropertyValueFactory<>("timeKbEnd"));
        time7.setCellValueFactory(new PropertyValueFactory<>("endDayString"));
        add_item.setCellValueFactory(new PropertyValueFactory<>("imageButton"));

        tableKbView2.setItems(listDescriptionKb);

        tableKbView2.setRowFactory(param -> new TableRow<DescriptionKb>() {
            @Override
            public void updateItem(DescriptionKb item, boolean empty) {

                super.updateItem(item, empty);
                this.getStyleClass().removeIf(s -> s.contains("red"));
                if (item!=null) {
                    String style = applyRowStyleTableView(item.getEndDay(), false);
                    if (!style.isEmpty()) {
                        this.getStyleClass().add(style);
                    }
                }
            }
        });
    }

    static String applyRowStyleTableView(LocalDate dayEnd, boolean needRed) {
        if (dayEnd == null) {
            return "red0";
        }

        if (needRed) {
            LocalDate today = LocalDate.now();

            if (today.plusDays(1).equals(dayEnd)) {
                return "red1";
            }
            if (today.equals(dayEnd)) {
                return "red2";
            }
            if (today.isAfter(dayEnd) && dayEnd.plusDays(4).isAfter(today)) {
                return "red3";
            }
            if (dayEnd.plusDays(3).isBefore(today)) {
                return "red4";
            }
        }
        rowOdd = !rowOdd;
        if (rowOdd) {
            return "red0";
        } else {
            return "red0_2";
        }
    }

    private void updateSelectedDescription() {
        listDescriptionKb.clear();
        Order selectedOrder;
        if (tableKbView1.getSelectionModel().getSelectedItem() != null) {
            selectedRow = tableKbView1.getSelectionModel().getSelectedIndex();
            selectedOrder = tableKbView1.getSelectionModel().getSelectedItem();
            List<DescriptionKb> descriptionKbList = DescriptionKb.makeFromOrderDescription(selectedOrder);
            addButtonListener(descriptionKbList);
            listDescriptionKb.addAll(descriptionKbList);

            info_docnum.setText(selectedOrder.getDocNumber());
            info_client.setText(selectedOrder.getClient());
            info_data_f.setText(selectedOrder.getDateToFactoryString());
            info_manag.setText(selectedOrder.getManager());
        }
    }

    private void addButtonListener(List<DescriptionKb> descriptionKbList) {
        descriptionKbList.stream()
                .filter(d -> d.getImageButton() instanceof Button)
                .forEach(d -> ((Button)d.getImageButton()).setOnAction(
                        event -> UtilController.getInstance().viewImages(((Button)event.getSource()).getId())));
    }

    public void applyNext() {
        if (page.next()) {
            if (page.isLast()) {
                parsing_next.setDisable(true);
            }
            if (!page.isFirst()) {
                parsing_previous.setDisable(false);
            }
            initKbTabOne();
        }
    }

    public void applyPrevious() {
        if (page.previous()) {
            if (page.isFirst()) {
                parsing_previous.setDisable(true);
            }
            if (!page.isLast()) {
                parsing_next.setDisable(false);
            }
            initKbTabOne();
        }
    }

    public void sortingKb(ActionEvent actionEvent) {
        String text = ((RadioButton) actionEvent.getSource()).getText();
        sortWayKb = Arrays.stream(SortWay.values())
                .filter(sortWay -> sortWay.toStringRus().equalsIgnoreCase(text))
                .findFirst().orElse(SortWay.NUMBER);
        initKbTabOne();
    }

    public void applyKb() {
        new KbEngine().setStatus(listDescriptionKb);
        updateAllView();
    }

    private String getNumberOfDescriptionAsString() {
        int sumDescription = listOrderKb.stream()
                .mapToInt(Order::getNumberOfPosition)
                .sum();
        return String.valueOf(sumDescription);
    }

    private String getNumberOfWorkedOrderAsString() {
        int result = 0;
        for (Order o : listOrderKb) {
            for (Description d : o.getDescriptions()) {
                if (d.getDesigner() != null) {
                    result ++;
                    break;
                }
            }
        }
        return String.valueOf(result);
    }


    public void addImage() {
        if (tableKbView2.getSelectionModel().getSelectedItem() != null) {
            UtilController.getInstance().addImage(tableKbView1, tableKbView2.getSelectionModel().getSelectedItem().getId());
        }
    }
}