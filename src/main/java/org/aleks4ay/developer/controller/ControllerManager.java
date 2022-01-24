package org.aleks4ay.developer.controller;

import javafx.beans.property.LongProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.aleks4ay.developer.dao.ConnectionPool;
import org.aleks4ay.developer.dao.DescriptionDao;
import org.aleks4ay.developer.model.*;
import org.aleks4ay.developer.service.DescriptionService;
import org.aleks4ay.developer.service.KbEngine;
import org.aleks4ay.developer.tools.FileWriter;
import org.aleks4ay.developer.tools.PropertyListener;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class ControllerManager implements Initializable {
    private static final long positionOnPage = 100;
    private LongProperty isNewOrderTime = PropertyListener.getOrderTimeProperty();

    private final KbEngine kbEngine = new KbEngine();
    private final DescriptionService descriptionService = new DescriptionService(new DescriptionDao(ConnectionPool.getInstance()));

    public static String sortWayKb = "по № заказа";
    private int selectedRow = 0;
    private final Page page = new Page(positionOnPage);
    private Order selectedOrder = null;

    private final ObservableList<Order> listOrderManager = FXCollections.observableArrayList();
    private final ObservableList<DescriptionKb> listDescriptionManager = FXCollections.observableArrayList();

    //---------------------- B U T T O N
    @FXML private Button parsing_next;
    @FXML private Button parsing_previous;
    @FXML private Button b_year;
    @FXML private Button b_month;
    @FXML private Button b_30_day;
    @FXML private Button b_week;
    @FXML private Button b_day;


    @FXML private Label info_position;
    @FXML private Text timeUpdate;
    @FXML private Label infoSorting;
    @FXML private Label infoType;
    @FXML private Label infoManager;
    @FXML private Label infoDeveloper;
    @FXML private Label info_day;
    @FXML private Label info_ord;
    @FXML private Label info_shipment;
    @FXML private Label info_shipment_today;

    //----------------T A B    P A R S I N G   1 ---------------------------------
    @FXML private TableView<Order> tableManagerView1;
    @FXML private TableColumn<Order, String> num;
    @FXML private TableColumn<Order, String> client;
    @FXML private TableColumn<Order, String> manager;
    @FXML private TableColumn<Order, String> data_f;
    @FXML private TableColumn<Order, String> count_position;
    @FXML private TableColumn<Order, String> time_1s;
    @FXML private TableColumn<Order, String> time_18;
    @FXML private TableColumn<Order, String> time_19;
    @FXML private TableColumn<Order, String> time_20;

    //----------------T A B    P A R S I N G   2 ---------------------------------
    @FXML private TableView<DescriptionKb> tableManagerView2;
    @FXML private TableColumn<DescriptionKb, String> pos;
    @FXML private TableColumn<DescriptionKb, Text> description;
    @FXML private TableColumn<DescriptionKb, String> size_a;
    @FXML private TableColumn<DescriptionKb, String> size_b;
    @FXML private TableColumn<DescriptionKb, String> size_c;
    @FXML private TableColumn<DescriptionKb, String> amount;
    @FXML private TableColumn<DescriptionKb, String> designer;
    @FXML private TableColumn<DescriptionKb, String> time2;
    @FXML private TableColumn<DescriptionKb, Object> time3;
    @FXML private TableColumn<DescriptionKb, Object> time4;
    @FXML private TableColumn<DescriptionKb, Object> time5;
    @FXML private TableColumn<DescriptionKb, String> time7;
    @FXML private TableColumn<DescriptionKb, Object> time18;
    @FXML private TableColumn<DescriptionKb, Object> time19;
    @FXML private TableColumn<DescriptionKb, Object> time20;
    @FXML private TableColumn<DescriptionKb, Button> add_item;

    @FXML private MenuButton menuSorting;
    @FXML private MenuButton menuType;
    @FXML private MenuButton menuManager;
    @FXML private MenuButton menuDeveloper;

    @FXML private CheckBox check_new;
    @FXML private CheckBox check_kb;
    @FXML private CheckBox check_ceh;
    @FXML private CheckBox check_done;
    @FXML private CheckBox check_shipm;
    @FXML private CheckBox check_other;
    @FXML private CheckBox check_all;
    @FXML private CheckBox check_kiy_v;
    @FXML private CheckBox check_kiy_v_pro;


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

        tableManagerView1.getSelectionModel().selectedIndexProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                updateSelectedDescription();
            }
        });
    }

    private void updateAllView() {
        try {
            Thread.sleep(50);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        initKbTabOne();
        timeUpdate.setText(FileWriter.readTimeChange());
    }


    private void initKbTabOne() {
        listOrderManager.clear();
        listOrderManager.addAll(kbEngine.getOrdersWithDescriptionsKb(page, sortWayKb));
        if (listOrderManager.size() == 0 && page.getPosition() > 0) {
            applyPrevious();
            return;
        }
        num.setCellValueFactory(new PropertyValueFactory<>("docNumber"));
        client.setCellValueFactory(new PropertyValueFactory<>("client"));
        manager.setCellValueFactory(new PropertyValueFactory<>("manager"));
        data_f.setCellValueFactory(new PropertyValueFactory<>("dateToFactoryString"));
        count_position.setCellValueFactory(new PropertyValueFactory<>("numberOfPosition"));

        tableManagerView1.setItems(listOrderManager);
        tableManagerView1.getSelectionModel().select(selectedRow);
        info_ord.setText(String.valueOf(page.getSize()));
        info_position.setText(page.getInterval());
        if (page.isLast()) {
            parsing_next.setDisable(true);
        }
        if (page.isFirst()) {
            parsing_previous.setDisable(true);
        }
    }

    private void initKbTabTwo() {
        updateSelectedDescription();

        pos.setCellValueFactory(new PropertyValueFactory<>("position"));
        description.setCellValueFactory(new PropertyValueFactory<>("descriptionText"));
        size_a.setCellValueFactory(new PropertyValueFactory<>("sizeA"));
        size_b.setCellValueFactory(new PropertyValueFactory<>("sizeB"));
        size_c.setCellValueFactory(new PropertyValueFactory<>("sizeC"));
        amount.setCellValueFactory(new PropertyValueFactory<>("amount"));
        designer.setCellValueFactory(new PropertyValueFactory<>("designer"));
        time2.setCellValueFactory(new PropertyValueFactory<>("timeKb"));
        time3.setCellValueFactory(new PropertyValueFactory<>("timeKbStart"));
        time4.setCellValueFactory(new PropertyValueFactory<>("timeKbQuestion"));
        time5.setCellValueFactory(new PropertyValueFactory<>("timeKbContinued"));
        time7.setCellValueFactory(new PropertyValueFactory<>("timeKbEnd"));
        time18.setCellValueFactory(new PropertyValueFactory<>(""));
        time19.setCellValueFactory(new PropertyValueFactory<>(""));
        time20.setCellValueFactory(new PropertyValueFactory<>(""));
        add_item.setCellValueFactory(new PropertyValueFactory<>("imageButton"));

        tableManagerView2.setItems(listDescriptionManager);
    }

    private void updateSelectedDescription() {
        listDescriptionManager.clear();
        if (tableManagerView1.getSelectionModel().getSelectedItem() != null) {
            selectedRow = tableManagerView1.getSelectionModel().getSelectedIndex();
            selectedOrder = tableManagerView1.getSelectionModel().getSelectedItem();
            List<DescriptionKb> descriptionKbList = DescriptionKb.makeFromOrderDescription(selectedOrder);
            addButtonListener(descriptionKbList);
            listDescriptionManager.addAll(descriptionKbList);
        } else {
            selectedOrder = null;
        }
    }

    private void addButtonListener(List<DescriptionKb> descriptionKbList) {
        for (DescriptionKb d : descriptionKbList) {
            if (d.getImageButton() instanceof Button) {
                Button button = (Button) d.getImageButton();
                button.setOnAction(event -> viewImages(((Button) event.getSource()).getId()));
            }
        }
    }

    private void viewImages(String id) {
        Parent root = null;
        URL location = getClass().getResource("/fxml/imagePaneView.fxml");
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(location);
        fxmlLoader.setBuilderFactory(new JavaFXBuilderFactory());
        try {
            root = fxmlLoader.load(location.openStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        ControllerImageView controller = fxmlLoader.getController();
        List<DescriptionImage> images = descriptionService.findImagesByDescriptionId(id);
        controller.setImages(images);
        controller.updateValues();

        Stage newWindow = new Stage();
        newWindow.setTitle("Просмотр рисунков");
        newWindow.setScene(new Scene(root));

        newWindow.show();
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
        sortWayKb = ((RadioButton) actionEvent.getSource()).getText();
        initKbTabOne();
    }

    public void applyKb() {
        kbEngine.setStatus(listDescriptionManager);
        updateAllView();
    }

    private String getNumberOfDescriptionAsString() {
        int sumDescription = listOrderManager.stream()
                .mapToInt(Order::getNumberOfPosition)
                .sum();
        return String.valueOf(sumDescription);
    }

    private String getNumberOfWorkedOrderAsString() {
        int result = 0;
        for (Order o : listOrderManager) {
            for (Description d : o.getDescriptions()) {
                if (d.getDesigner() != null) {
                    result++;
                    break;
                }
            }
        }
        return String.valueOf(result);
    }


    public void addImage() {
        if (tableManagerView2.getSelectionModel().getSelectedItem() != null) {
            Order selectedOrder = tableManagerView1.getSelectionModel().getSelectedItem();
            DescriptionKb descriptionKb = tableManagerView2.getSelectionModel().getSelectedItem();

            UtilController utilController = UtilController.getInstance();
            Parent rootNode = parsing_next.getScene().getRoot();
            utilController.setOrder(selectedOrder);
            utilController.setDescriptionKb(descriptionKb);
            utilController.setImagePaneVisible(rootNode);
        }
    }

    public void menuSorting(ActionEvent event) {

    }

    public void menuType(ActionEvent event) {

    }

    public void menuManager(ActionEvent event) {

    }

    public void menuDeveloper(ActionEvent event) {

    }
}