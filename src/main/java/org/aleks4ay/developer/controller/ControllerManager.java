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
import org.aleks4ay.developer.dao.ConnectionPool;
import org.aleks4ay.developer.dao.OrderDao;
import org.aleks4ay.developer.model.*;
import org.aleks4ay.developer.service.ManagerEngine;
import org.aleks4ay.developer.service.OrderService;
import org.aleks4ay.developer.tools.ConstantsSql;
import org.aleks4ay.developer.tools.FileWriter;
import org.aleks4ay.developer.tools.PropertyListener;

import java.net.URL;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

import static java.time.temporal.ChronoUnit.DAYS;

public class ControllerManager implements Initializable {
    private int numberOfStatuses;
    private Button selectedDateButton;
    private final Set<StatusName> statusNameSet = new HashSet<>();
    private final Set<TypeName> typeNameSet = new HashSet<>();
    private LocalDate dateStart;
    private final Set<Button> dateButtons = new HashSet<>();
    private final Set<CheckBox> checkBoxes = new HashSet<>();
    private final Set<CheckBox> checkBoxesType = new HashSet<>();
    private static final long positionOnPage = 100;
    private final LongProperty isNewOrderTime = PropertyListener.getOrderTimeProperty();

    private final OrderService orderService = new OrderService(new OrderDao(ConnectionPool.getInstance()));

    public static SortWay sortWay = SortWay.NUMBER;
    private int selectedRow = 0;
    private final Page page = new Page(positionOnPage);
    private String managerId = "";
    private String developerIds = "";

    private final ObservableList<Order> listOrderManager = FXCollections.observableArrayList();
    private final ObservableList<DescriptionFind> listDescriptionManager = FXCollections.observableArrayList();

    //---------------------- B U T T O N
    @FXML private Button parsing_next;
    @FXML private Button parsing_previous;
    @FXML private Button b_year;

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
    @FXML private TableView<DescriptionFind> tableManagerView2;
    @FXML private TableColumn<DescriptionFind, String> pos;
    @FXML private TableColumn<DescriptionFind, Text> description;
    @FXML private TableColumn<DescriptionFind, String> size_a;
    @FXML private TableColumn<DescriptionFind, String> size_b;
    @FXML private TableColumn<DescriptionFind, String> size_c;
    @FXML private TableColumn<DescriptionFind, String> amount;
    @FXML private TableColumn<DescriptionFind, String> type;
    @FXML private TableColumn<DescriptionFind, String> status;
    @FXML private TableColumn<DescriptionFind, String> designer;
    @FXML private TableColumn<DescriptionFind, String> time2;
    @FXML private TableColumn<DescriptionFind, String> time3;
    @FXML private TableColumn<DescriptionFind, String> time4;
    @FXML private TableColumn<DescriptionFind, String> time5;
    @FXML private TableColumn<DescriptionFind, String> time7;
    @FXML private TableColumn<DescriptionFind, String> time18;
    @FXML private TableColumn<DescriptionFind, String> time19;
    @FXML private TableColumn<DescriptionFind, String> time20;
    @FXML private TableColumn<DescriptionFind, Button> add_item;

//    @FXML private MenuButton menuType;
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

        initCheckBoxes();
        initTypes();
        initDateButtons();
        initDesigners();
//        initManagers();
        updateOrderStatusesToShow();
        updateAllView();
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
                viewSelectedDescription();
            }
        });

        info_ord.setText(String.valueOf(page.getSize()));
        info_position.setText(page.getInterval());
        if (page.isLast()) {
            parsing_next.setDisable(true);
        }
        if (page.isFirst()) {
            parsing_previous.setDisable(true);
        }
    }


    private void initCheckBoxes() {
        checkBoxes.addAll(Arrays.asList(check_new, check_kb, check_ceh, check_done, check_shipm, check_other));
        numberOfStatuses = (int) checkBoxes.stream()
                .filter(CheckBox::isSelected)
                .count();
        checkBoxesType.addAll(Arrays.asList(check_kiy_v, check_kiy_v_pro));
    }

    private void initDateButtons() {
        selectedDateButton = b_year;
        selectedDateButton.getStyleClass().add("dayClick");
        info_day.setText(String.valueOf(LocalDate.now().getDayOfYear()));
    }

    private void initTypes() {
        typeNameSet.clear();
        typeNameSet.addAll(Arrays.asList(TypeName.NEW, TypeName.KB, TypeName.FACTORY));
    }

//    private void initManagers() {
//        Map<String, String> managers = new ManagerEngine().detManagers(ConnectionPool.getInstance());
//        for (Map.Entry<String, String> entry : managers.entrySet()) {
//            MenuItem menuItem = new MenuItem();
//            menuItem.setText(entry.getValue() + "_2");
//            menuItem.setId("m4_" + entry.getKey());
//            menuItem.setOnAction(this::menuManager);
//            menuManager.getItems().add(menuItem);
//        }
//    }

    private void initDesigners() {
        Map<String, String>  designers = new ManagerEngine().detDesigners(ConnectionPool.getInstance());
        for (Map.Entry<String, String> d : designers.entrySet()) {
            MenuItem menuItem = new MenuItem();
            menuItem.setText(d.getKey());
            menuItem.setId("m5_" + d.getValue());
            menuItem.setOnAction(this::menuDeveloper);
            menuDeveloper.getItems().add(menuItem);
        }
    }

    private void updateAllView() {
        try {
            Thread.sleep(50);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        updateOrderStatusesToShow();
        initKbTabOne();
        timeUpdate.setText(FileWriter.readTimeChange());
    }


    private void initKbTabOne() {
        listOrderManager.clear();
        parsing_next.setDisable(false);
        String numbersOrderLike = getNumbersOrderLike();
        List<Order> orderList = orderService.getOrdersWithDescriptionsManager(page, sortWay, statusNameSet, typeNameSet,
                dateStart, numbersOrderLike, developerIds, managerId);

        listOrderManager.addAll(orderList);
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

    private String getNumbersOrderLike() {
        return check_kiy_v.isSelected() && check_kiy_v_pro.isSelected() ? ""
                : check_kiy_v.isSelected() ? ConstantsSql.KI_ORDERS
                : check_kiy_v_pro.isSelected() ? ConstantsSql.KP_ORDERS
                : "0-0-0-0";
    }


    private void initKbTabTwo() {
        viewSelectedDescription();

        pos.setCellValueFactory(new PropertyValueFactory<>("position"));
        description.setCellValueFactory(new PropertyValueFactory<>("descriptionText"));
        size_a.setCellValueFactory(new PropertyValueFactory<>("sizeA"));
        size_b.setCellValueFactory(new PropertyValueFactory<>("sizeB"));
        size_c.setCellValueFactory(new PropertyValueFactory<>("sizeC"));
        amount.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        type.setCellValueFactory(new PropertyValueFactory<>("typeString"));
        status.setCellValueFactory(new PropertyValueFactory<>("statusString"));
        designer.setCellValueFactory(new PropertyValueFactory<>("designer"));
        time2.setCellValueFactory(new PropertyValueFactory<>("timeKb"));
        time3.setCellValueFactory(new PropertyValueFactory<>("timeKbStart"));
        time4.setCellValueFactory(new PropertyValueFactory<>("timeKbQuestion"));
        time5.setCellValueFactory(new PropertyValueFactory<>("timeKbContinued"));
        time7.setCellValueFactory(new PropertyValueFactory<>("timeFactory"));
        time18.setCellValueFactory(new PropertyValueFactory<>(""));
        time19.setCellValueFactory(new PropertyValueFactory<>(""));
        time20.setCellValueFactory(new PropertyValueFactory<>(""));
        add_item.setCellValueFactory(new PropertyValueFactory<>("imageButton"));

        tableManagerView2.setItems(listDescriptionManager);
    }

    private void updateOrderStatusesToShow(){
        statusNameSet.clear();
        for (CheckBox ch : checkBoxes) {
            if (ch.isSelected()) {
                statusNameSet.addAll(StatusName.addStatusFromCheckBox(ch));
            }
        }
    }

    private void viewSelectedDescription() {
        listDescriptionManager.clear();

        if (tableManagerView1.getSelectionModel().getSelectedItem() != null) {
            Order selectedOrder = tableManagerView1.getSelectionModel().getSelectedItem();

            listDescriptionManager.addAll(selectedOrder.getDescriptions().stream()
                    .map(DescriptionFind::new)
                    .collect(Collectors.toList()));

            listDescriptionManager.stream()
                    .filter(d -> d.getImageButton() instanceof Button)
                    .forEach(d -> ((Button)d.getImageButton()).setOnAction(
                            event -> UtilController.getInstance().viewImages(((Button)event.getSource()).getId())));
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

    public void addImage() {
        if (tableManagerView2.getSelectionModel().getSelectedItem() != null) {
            UtilController.getInstance().addImage(tableManagerView1, tableManagerView2.getSelectionModel().getSelectedItem().getId());
        }
    }

    public void menuSorting(ActionEvent event) {
        String numberMenuItem = ((MenuItem)event.getSource()).getId().split("_")[1];
        sortWay = SortWay.values()[Integer.parseInt(numberMenuItem)];
        infoSorting.setText(sortWay.toStringRus());
        initKbTabOne();
        initKbTabTwo();
    }

    public void menuType(ActionEvent event) {
        String[] partOfId = ((MenuItem)event.getSource()).getId().split("_");
        List<String> types = Arrays.stream(partOfId).skip(1).collect(Collectors.toList());
        typeNameSet.clear();
        if (!types.contains("ALL")) {
            Arrays.stream(TypeName.values())
                    .filter(type -> types.contains(type.toString()))
                    .forEach(typeNameSet::add);
        }
        infoType.setText(((MenuItem)event.getSource()).getText());
        initKbTabOne();
        initKbTabTwo();
    }

    public void menuManager(ActionEvent event) {
        MenuItem item = (MenuItem) event.getSource();
        String rawId = item.getId().split("_")[1];
        managerId = rawId.length() == 1 ? "" : rawId.replaceAll("@", " ");
        String managerName = item.getText().equalsIgnoreCase("Выбрать всех") ? "Все" : item.getText();
        infoManager.setText(managerName);
        initKbTabOne();
        initKbTabTwo();
    }

    public void menuDeveloper(ActionEvent event) {
        MenuItem item = (MenuItem) event.getSource();
        developerIds = item.getId().equalsIgnoreCase("m5_1") ? "" : item.getId();
        String developerName = item.getText().equalsIgnoreCase("Выбрать всех") ? "Все" : item.getText();
        infoDeveloper.setText(developerName);
        initKbTabOne();
        initKbTabTwo();
    }

    @FXML
    private void checkOrder(ActionEvent event) {
        CheckBox checkBox = (CheckBox) event.getSource();
        if (checkBox.getId().equalsIgnoreCase("check_all")) {
            checkBoxes.forEach(h -> h.setSelected(checkBox.isSelected()));
            numberOfStatuses = check_all.isSelected() ? 6 : 0;
        }
        else {
            numberOfStatuses = checkBox.isSelected() ? numberOfStatuses + 1 : numberOfStatuses - 1;
            check_all.setSelected(numberOfStatuses == 6);
        }
        updateAllView();
//        initKbTabOne();
//        initKbTabTwo();
    }

    @FXML
    private void checkOrderNumber() {
        initKbTabOne();
        initKbTabTwo();
    }

    private void setDate(ActionEvent event) {
        if (selectedDateButton != null) {
            selectedDateButton.getStyleClass().remove("dayClick");
        }
        selectedDateButton = ((Button)event.getSource());
        selectedDateButton.getStyleClass().add("dayClick");
        long period = DAYS.between(dateStart, LocalDate.now()) + 1;

        info_day.setText(String.valueOf(period));
//        updateAllView();
        initKbTabOne();
        initKbTabTwo();
    }

    public void forAll(ActionEvent event) {
        dateStart = LocalDate.of(2021, 5, 1);
        setDate(event);
    }

    public void forYear(ActionEvent event) {
        dateStart = LocalDate.of(LocalDate.now().getYear(), 1, 1);
        setDate(event);
    }

    public void forThirty(ActionEvent event) {
        dateStart = LocalDate.now().minusDays(29);
        setDate(event);
    }

    public void forMonth(ActionEvent event) {
        dateStart = LocalDate.of(LocalDate.now().getYear(), LocalDate.now().getMonth(), 1);
        setDate(event);
    }

    public void forWeek(ActionEvent event) {
        int dayOfWeek = LocalDate.now().getDayOfWeek().getValue();
        dateStart = LocalDate.now().minusDays(dayOfWeek - 1);
        setDate(event);
    }

    public void forDay(ActionEvent event) {
        dateStart = LocalDate.now();
        setDate(event);
    }
}