package org.aleks4ay.developer.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import org.aleks4ay.developer.model.Description;
import org.aleks4ay.developer.model.DescriptionParsing;
import org.aleks4ay.developer.model.Order;
import org.aleks4ay.developer.model.Page;
import org.aleks4ay.developer.service.ParsingEngine;

import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    private static final long positionOnPage = 10;

    private final ParsingEngine parsingEngine = new ParsingEngine();

    private static final String sortWay = "По номеру заказа";
    private int selectedRow = 0;
    private final Page page = new Page(positionOnPage);

    private final ObservableList<Order> listOrderParsing = FXCollections.observableArrayList();
    private final ObservableList<DescriptionParsing> listDescriptionParsing = FXCollections.observableArrayList();

//---------------------- B U T T O N
    @FXML private Button parsing_save;
    @FXML private Button parsing_next;
    @FXML private Button parsing_previous;

//----------------T A B    P A R S I N G  ---------------------------------
    @FXML private Label info_parsing;
//--------------- Table 1 ---------------
    @FXML private TableView<Order> tableParsingView1;
    @FXML private TableColumn<Order, String> parsing_num;
    @FXML private TableColumn<Order, String> parsing_client;
    @FXML private TableColumn<Order, String> parsing_manager;
    @FXML private TableColumn<Order, String> parsing_data_f;
    @FXML private TableColumn<Order, String> parsing_count_position;

//--------------- Table 2 ---------------
    @FXML private TableView<DescriptionParsing> tableParsingView2;
    @FXML private TableColumn<Description, String> parsing_pos;
    @FXML private TableColumn<Description, Text> parsing_description2;
    @FXML private TableColumn<Description, String> parsing_description_size;
    @FXML private TableColumn<Description, String> parsing_amount;
    @FXML private TableColumn<Description, String> parsing_type;
    @FXML private TableColumn<Description, RadioButton> parsing_factory;
    @FXML private TableColumn<Description, RadioButton> parsing_kb;
    @FXML private TableColumn<Description, RadioButton> parsing_tehn;
    @FXML private TableColumn<Description, RadioButton> parsing_other;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initParsingTabOne();
        initParsingTabTwo();

        tableParsingView1.getSelectionModel().selectedIndexProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                updateSelectedDescription();
            }
        });
    }

    private void initParsingTabOne() {
        listOrderParsing.clear();
        listOrderParsing.addAll(parsingEngine.getOrdersWithDescriptions(page));

        parsing_num.setCellValueFactory(new PropertyValueFactory<>("docNumber"));
        parsing_client.setCellValueFactory(new PropertyValueFactory<>("client"));
        parsing_manager.setCellValueFactory(new PropertyValueFactory<>("manager"));
        parsing_data_f.setCellValueFactory(new PropertyValueFactory<>("dateToFactoryString"));
        parsing_count_position.setCellValueFactory(new PropertyValueFactory<>("numberOfPosition"));

        tableParsingView1.setItems(listOrderParsing);
        tableParsingView1.getSelectionModel().select(selectedRow);
        info_parsing.setText("Заказов: " + page.getSize());
    }

    private void initParsingTabTwo() {
        updateSelectedDescription();

        parsing_pos.setCellValueFactory(new PropertyValueFactory<>("position"));
        parsing_description2.setCellValueFactory(new PropertyValueFactory<>("descriptionText"));
        parsing_description_size.setCellValueFactory(new PropertyValueFactory<>("size"));
        parsing_amount.setCellValueFactory(new PropertyValueFactory<>("amount"));
        parsing_type.setCellValueFactory(new PropertyValueFactory<>("type"));
        parsing_factory.setCellValueFactory(new PropertyValueFactory<>("buttonFactory"));
        parsing_kb.setCellValueFactory(new PropertyValueFactory<>("buttonKB"));
        parsing_tehn.setCellValueFactory(new PropertyValueFactory<>("buttonTeh"));
        parsing_other.setCellValueFactory(new PropertyValueFactory<>("buttonOther"));

        tableParsingView2.setItems(listDescriptionParsing);
    }

    private void updateSelectedDescription() {
        listDescriptionParsing.clear();
        if (tableParsingView1.getSelectionModel().getSelectedItem() != null) {
            Order selectedOrder = tableParsingView1.getSelectionModel().getSelectedItem();
            for (Description d : selectedOrder.getDescriptions()) {
                listDescriptionParsing.add(new DescriptionParsing(
                        d.getId(),
                        d.getPosition(),
                        d.getDescrSecond(),
                        (d.getSizeA() + "×" + d.getSizeB() + "×" + d.getSizeC()),
                        d.getQuantity(),
                        d.getStatus().getType(),
                        d.getStatus().getType()
                ));
            }
        }
    }

    public void applyParsing(ActionEvent actionEvent) {
        //        thisAppChangeData = true;
//        Setter.setType (listDescriptionParsing);
//        selektedRow = tableParsingView1.getSelectionModel().getSelectedIndex();

    }

    public void applyNext() {
        if (page.next()) {
            if (page.isLast()) {
                parsing_next.setDisable(true);
            }
            if (!page.isFirst()) {
                parsing_previous.setDisable(false);
            }
            initParsingTabOne();
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
            initParsingTabOne();
        }
    }
}