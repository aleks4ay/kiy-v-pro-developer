package org.aleks4ay.developer.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import org.aleks4ay.developer.dao.ConnectionPool;
import org.aleks4ay.developer.dao.OrderDao;
import org.aleks4ay.developer.model.Description;
import org.aleks4ay.developer.model.Order;
import org.aleks4ay.developer.model.Page;
import org.aleks4ay.developer.service.OrderService;
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
    private final ObservableList<Description> listDescriptionParsing = FXCollections.observableArrayList();

//---------------------- B U T T O N
    @FXML private Button parsing_save;
    @FXML private Button parsing_next;
    @FXML private Button parsing_previous;

//----------------T A B    P A R S I N G  ---------------------------------
//    @FXML private Label info_parsing;
//--------------- Table 1 ---------------
    @FXML private TableView<Order> tableParsingView1;
    @FXML private TableColumn<Order, String> parsing_num;
    @FXML private TableColumn<Order, String> parsing_client;
    @FXML private TableColumn<Order, String> parsing_manager;
    @FXML private TableColumn<Order, String> parsing_data_f;
    @FXML private TableColumn<Order, String> parsing_count_position;

//--------------- Table 2 ---------------
    @FXML private TableView<Description> tableParsingView2;
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
//        System.out.println(info1.getText().toUpperCase());
//        info1.setText(sortWay + " test");
//        info1.setText("test 2");
    }

    private void initParsingTabOne() {
        listOrderParsing.clear();
        listOrderParsing.addAll(parsingEngine.findAllParsing(page));

        parsing_num.setCellValueFactory(new PropertyValueFactory<Order, String>("clientId"));
        parsing_client.setCellValueFactory(new PropertyValueFactory<Order, String>("clientId"));
        parsing_manager.setCellValueFactory(new PropertyValueFactory<Order, String>("managerId"));
        parsing_data_f.setCellValueFactory(new PropertyValueFactory<Order, String>("dateToFactoryString"));
        parsing_count_position.setCellValueFactory(new PropertyValueFactory<Order, String>("dateToFactoryString"));


        tableParsingView1.setItems(listOrderParsing);
        tableParsingView1.getSelectionModel().select(selectedRow);
//        info_parsing.setText(String.valueOf(listOrderParsing.size()));
    }

    /*private void initParsingTabTwo() {
        listDescriptionParsing.clear();
        if (tableParsingView1.getSelectionModel().getSelectedItem() != null) {
            Order selectedOrder = tableParsingView1.getSelectionModel().getSelectedItem();
            listDescriptionParsing.addAll(selectedOrder.getDescriptions());
        }

        parsing_pos.setCellValueFactory(new PropertyValueFactory<Description, String>("position"));
        parsing_description2.setCellValueFactory(new PropertyValueFactory<Description, Text>("descriptionText"));
        parsing_description_size.setCellValueFactory(new PropertyValueFactory<Description, String>("size"));
        parsing_amount.setCellValueFactory(new PropertyValueFactory<Description, String>("amount"));
        parsing_type.setCellValueFactory(new PropertyValueFactory<Description, String>("type"));
        parsing_factory.setCellValueFactory(new PropertyValueFactory<Description, RadioButton>("buttonFactory"));
        parsing_kb.setCellValueFactory(new PropertyValueFactory<Description, RadioButton>("buttonKB"));
        parsing_tehn.setCellValueFactory(new PropertyValueFactory<Description, RadioButton>("buttonTeh"));
        parsing_other.setCellValueFactory(new PropertyValueFactory<Description, RadioButton>("buttonOther"));

        tableParsingView2.setItems(listDescriptionParsing);
    }*/

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