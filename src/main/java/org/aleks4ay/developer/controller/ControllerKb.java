package org.aleks4ay.developer.controller;

import javafx.application.Platform;
import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import org.aleks4ay.developer.model.Description;
import org.aleks4ay.developer.model.DescriptionKb;
import org.aleks4ay.developer.model.Order;
import org.aleks4ay.developer.model.Page;
import org.aleks4ay.developer.service.KbEngine;
import org.aleks4ay.developer.tools.Constants;
import org.aleks4ay.developer.tools.FileWriter;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class ControllerKb implements Initializable {
    private static final long positionOnPage = 10;
    private static final File file1 = new File(Constants.FILE_CHANGES);
    private final LongProperty isNewOrderTime = new SimpleLongProperty(new File(Constants.FILE_CHANGES).lastModified());

    private final KbEngine kbEngine = new KbEngine();

    public static String sortWayKb = "по № заказа";
    private int selectedRow = 0;
    private final Page page = new Page(positionOnPage);

    private final ObservableList<Order> listOrderKb = FXCollections.observableArrayList();
    private final ObservableList<DescriptionKb> listDescriptionKb = FXCollections.observableArrayList();

//---------------------- B U T T O N
    @FXML private Button kb_save;
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

//----------------T A B    P A R S I N G   1 ---------------------------------
    @FXML private TableView<Order> tableKbView1;
    @FXML private TableColumn<Order, String> num;
    @FXML private TableColumn<Order, String> client;
    @FXML private TableColumn<Order, String> manager;
    @FXML private TableColumn<Order, String> data_f;
    @FXML private TableColumn<Order, String> count_position;

//----------------T A B    P A R S I N G   2 ---------------------------------
    @FXML private TableView<DescriptionKb> tableKbView2;
    @FXML private TableColumn<Description, String> pos;
    @FXML private TableColumn<Description, Text> description;
    @FXML private TableColumn<Description, String> size_a;
    @FXML private TableColumn<Description, String> size_b;
    @FXML private TableColumn<Description, String> size_c;
    @FXML private TableColumn<Description, String> amount;
    @FXML private TableColumn<Description, String> designer;
    @FXML private TableColumn<Description, String> time2;
    @FXML private TableColumn<Description, Object> time3;
    @FXML private TableColumn<Description, Object> time4;
    @FXML private TableColumn<Description, Object> time5;
    @FXML private TableColumn<Description, Object> time6;
    @FXML private TableColumn<Description, String> time7;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Thread main = Thread.currentThread();
        new Thread(() -> {
            while (main.isAlive()) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (isNewOrderTime.get() != file1.lastModified()) {
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    Platform.runLater( () -> isNewOrderTime.setValue(file1.lastModified()));
                }
            }
        }).start();


        initKbTabOne();
        initKbTabTwo();

        isNewOrderTime.addListener((observable, oldValue, newValue) -> {
            try {
                System.out.println("Sleep 0.1 c");
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
        listOrderKb.addAll(kbEngine.getOrdersWithDescriptionsKb(page, sortWayKb));
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
        time6.setCellValueFactory(new PropertyValueFactory<>("timeKbEnd"));
        time7.setCellValueFactory(new PropertyValueFactory<>("endDayString"));

        tableKbView2.setItems(listDescriptionKb);
    }

    private void updateSelectedDescription() {
        listDescriptionKb.clear();
        if (tableKbView1.getSelectionModel().getSelectedItem() != null) {
            selectedRow = tableKbView1.getSelectionModel().getSelectedIndex();
            Order selectedOrder = tableKbView1.getSelectionModel().getSelectedItem();
            listDescriptionKb.addAll(DescriptionKb.makeFromOrderDescription(selectedOrder));
            info_docnum.setText(selectedOrder.getDocNumber());
            info_client.setText(selectedOrder.getClient());
            info_data_f.setText(selectedOrder.getDateToFactoryString());
            info_manag.setText(selectedOrder.getManager());
        }
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
//        System.out.println("sortWayKb = " + sortWayKb);
        initKbTabOne();
    }

    public void applyKb() {
        kbEngine.setStatus(listDescriptionKb);
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
}