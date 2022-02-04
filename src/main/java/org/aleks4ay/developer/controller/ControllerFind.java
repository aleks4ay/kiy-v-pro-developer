package org.aleks4ay.developer.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Text;
import org.aleks4ay.developer.dao.ConnectionPool;
import org.aleks4ay.developer.dao.OrderDao;
import org.aleks4ay.developer.model.DescriptionFind;
import org.aleks4ay.developer.model.Order;
import org.aleks4ay.developer.model.Page;
import org.aleks4ay.developer.model.SortWay;
import org.aleks4ay.developer.service.OrderService;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class ControllerFind implements Initializable {
    private final OrderService orderService = new OrderService(new OrderDao(ConnectionPool.getInstance()));
    private final ObservableList<Order> listOrderFind = FXCollections.observableArrayList();
    private final ObservableList<DescriptionFind> listDescriptionFind = FXCollections.observableArrayList();
    private boolean isSearching = false;

    private static final long positionOnPage = 1000;
    private final Page page = new Page(positionOnPage);

    @FXML private TextField find_year;
    @FXML private TextField find_number;

    //----------------T A B    F I N D   1 ---------------------------------
    @FXML
    private TableView<Order> tableFind1;
    @FXML private TableColumn<Order, String> f_num;
    @FXML private TableColumn<Order, String> f_client;
    @FXML private TableColumn<Order, String> f_manager;
    @FXML private TableColumn<Order, String> f_data_cr;
    @FXML private TableColumn<Order, String> f_data_f;
    @FXML private TableColumn<Order, String> f_count_position;

    //----------------T A B    F I N D   2 ---------------------------------
    @FXML private TableView<DescriptionFind> tableFind2;
    @FXML private TableColumn<DescriptionFind, String> f_pos;
    @FXML private TableColumn<DescriptionFind, Text> f_description;
    @FXML private TableColumn<DescriptionFind, String> f_size_a;
    @FXML private TableColumn<DescriptionFind, String> f_size_b;
    @FXML private TableColumn<DescriptionFind, String> f_size_c;
    @FXML private TableColumn<DescriptionFind, String> f_amount;
    @FXML private TableColumn<DescriptionFind, String> f_type;
    @FXML private TableColumn<DescriptionFind, String> f_status;
    @FXML private TableColumn<DescriptionFind, String> f_designer;
    @FXML private TableColumn<DescriptionFind, String> f_time2;
    @FXML private TableColumn<DescriptionFind, String> f_time3;
    @FXML private TableColumn<DescriptionFind, String> f_time4;
    @FXML private TableColumn<DescriptionFind, String> f_time5;
    @FXML private TableColumn<DescriptionFind, String> f_time7;
    @FXML private TableColumn<DescriptionFind, String> f_time18;
    @FXML private TableColumn<DescriptionFind, String> f_time19;
    @FXML private TableColumn<DescriptionFind, String> f_time24;
    @FXML private TableColumn<DescriptionFind, String> f_time30;
    @FXML private TableColumn<DescriptionFind, Button> add_item;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        tableFind1.getSelectionModel().selectedIndexProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                viewSelectedDescription();
            }
        });
    }

    public void initFindTabOne() {
        listOrderFind.clear();
        listOrderFind.addAll(orderService.getOrdersWithDescriptionsFind(page, SortWay.NUMBER, find_year.getText(), find_number.getText()));

        if (isSearching && listOrderFind.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("INFO");
            alert.setHeaderText("Заказа с таким номером нет в базе...");
            alert.showAndWait();
            isSearching = false;
            return;
        }
        f_num.setCellValueFactory(new PropertyValueFactory<>("docNumber"));
        f_client.setCellValueFactory(new PropertyValueFactory<>("client"));
        f_manager.setCellValueFactory(new PropertyValueFactory<>("manager"));
        f_data_cr.setCellValueFactory(new PropertyValueFactory<>("dateCreateString"));
        f_data_f.setCellValueFactory(new PropertyValueFactory<>("dateToFactoryString"));
        f_count_position.setCellValueFactory(new PropertyValueFactory<>("numberOfPosition"));

        tableFind1.setItems(listOrderFind);
        tableFind1.getSelectionModel().select(0);
    }

    public void initFindTabTwo() {
        viewSelectedDescription();

        f_pos.setCellValueFactory(new PropertyValueFactory<>("position"));
        f_description.setCellValueFactory(new PropertyValueFactory<>("descriptionText"));
        f_size_a.setCellValueFactory(new PropertyValueFactory<>("sizeA"));
        f_size_b.setCellValueFactory(new PropertyValueFactory<>("sizeB"));
        f_size_c.setCellValueFactory(new PropertyValueFactory<>("sizeC"));
        f_amount.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        f_type.setCellValueFactory(new PropertyValueFactory<>("typeString"));
        f_status.setCellValueFactory(new PropertyValueFactory<>("statusString"));
        f_designer.setCellValueFactory(new PropertyValueFactory<>("designer"));
        f_time2.setCellValueFactory(new PropertyValueFactory<>("timeKb"));
        f_time3.setCellValueFactory(new PropertyValueFactory<>("timeKbStart"));
        f_time4.setCellValueFactory(new PropertyValueFactory<>("timeKbQuestion"));
        f_time5.setCellValueFactory(new PropertyValueFactory<>("timeKbContinued"));
        f_time7.setCellValueFactory(new PropertyValueFactory<>("timeFactory"));
        f_time18.setCellValueFactory(new PropertyValueFactory<>("timeFactoryDone"));
        f_time19.setCellValueFactory(new PropertyValueFactory<>("timeShipment"));
        f_time24.setCellValueFactory(new PropertyValueFactory<>("timeNotTracked"));
        f_time30.setCellValueFactory(new PropertyValueFactory<>("timeCanceled"));
        add_item.setCellValueFactory(new PropertyValueFactory<>("imageButton"));

        tableFind2.setItems(listDescriptionFind);
    }

    private void viewSelectedDescription() {
        listDescriptionFind.clear();

        if (tableFind1.getSelectionModel().getSelectedItem() != null) {
            Order selectedOrder = tableFind1.getSelectionModel().getSelectedItem();

            listDescriptionFind.addAll(selectedOrder.getDescriptions().stream()
                    .map(DescriptionFind::new)
                    .collect(Collectors.toList()));

            listDescriptionFind.stream()
                    .filter(d -> d.getImageButton() instanceof Button)
                    .forEach(d -> ((Button)d.getImageButton()).setOnAction(
                            event -> UtilController.getInstance().viewImages(((Button)event.getSource()).getId())));
        }
    }

    public void showFoundedOrder() {
        isSearching = true;
        if (find_number.getText() == null || find_number.getText().equals("")) {

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("INFO");
            alert.setHeaderText("Номер заказа должен содержать минимум один символ.");
            alert.showAndWait();
            return;
        }
        initFindTabOne();
        initFindTabTwo();
    }

    public void searchOrderByEnter(KeyEvent keyEvent) {
        KeyCode keyCode = keyEvent.getCode();
        if (keyCode.getName().equalsIgnoreCase("Enter")) {
            showFoundedOrder();
        }
    }

    public void addImage() {
        if (tableFind2.getSelectionModel().getSelectedItem() != null) {
            UtilController.getInstance().addImage(tableFind1, tableFind2.getSelectionModel().getSelectedItem().getId());
        }
    }
}
