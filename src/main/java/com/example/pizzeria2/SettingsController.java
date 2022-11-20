package com.example.pizzeria2;

import core.services.CooksService;
import core.services.PizzeriaService;
import core.strategy.cooksStrategy.MultiTaskStrategy;
import core.strategy.cooksStrategy.SingleTaskStrategy;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import core.models.Pizza;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.converter.IntegerStringConverter;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class SettingsController {
    @FXML
    private TableView<Pizza> menuTable;

    @FXML
    private TableColumn<Pizza, String> Name;
    @FXML
    private TableColumn<Pizza, Integer> PrepTime;
    @FXML
    private TextField cooks;
    @FXML
    private TextField queue;

    private ObservableList menu = FXCollections.observableArrayList();

    @FXML
    ToggleGroup cooksStrat;

    public void initialize(){
        Name.setCellValueFactory(new PropertyValueFactory<>("name"));
        Name.setCellFactory(TextFieldTableCell.forTableColumn());
        Name.setOnEditCommit(e->e.getTableView().getItems().get(e.getTablePosition().getRow()).setName(e.getNewValue()));
        PrepTime.setCellValueFactory(new PropertyValueFactory<>("preparationTime"));
        PrepTime.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        PrepTime.setOnEditCommit(e->e.getTableView().getItems().get(e.getTablePosition().getRow()).setPreparationTime(e.getNewValue()));
        menuTable.setRowFactory(param -> {
            TableRow<Pizza> row = new TableRow<Pizza>();
            row.setOnMouseClicked(event -> {
                if ((event.getClickCount() == 2 && row.getItem() == null)) {
                    Pizza myItem = new Pizza("Name"+menuTable.getItems().size(), 15);
                    menuTable.getItems().add(myItem);
                }
            });

            return row;
        });
        menu.add(new Pizza("Name", 15));
        menuTable.setItems(menu);
    }

    private  ArrayList<Pizza> parsePizzas() {
        try {
            JSONArray obj = (JSONArray) new JSONParser().parse(new FileReader(new File("").getAbsolutePath()+"/src/main/resources/com/example/pizzeria2/menu.json"));
            var pizzas = new ArrayList<Pizza>();
            for (int i = 0; i < obj.size(); i++) {
                var iPizza = (JSONObject) obj.get(i);
                menu.add(new Pizza((String) iPizza.get("name"), ((Long) iPizza.get("prepTime")).intValue()));
            }
            return pizzas;
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    private void submit(ActionEvent e) throws IOException {
        PizzeriaService pizzeriaService =   Main.getInjector().getInstance(PizzeriaService.class);
        pizzeriaService.init(new ArrayList<>(menuTable.getItems()), Integer.parseInt(cooks.getText()),Integer.parseInt(queue.getText()));
        CooksService cooksService =   Main.getInjector().getInstance(CooksService.class);
//        System.out.println(cooksStrat.getSelectedToggle().getUserData().toString().equals("1"));
        cooksService.setCooksStrategy(cooksStrat.getSelectedToggle().getUserData().toString().equals("1")?new SingleTaskStrategy():new MultiTaskStrategy());
        cooksService.initCooks();
        Node node=(Node) e.getSource();
        Stage stage=(Stage) node.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("main-view.fxml"));
        Scene mainScene = new Scene(fxmlLoader.load(), 779, 501);
        stage.setScene(mainScene);
        stage.show();

    }

    public void loadFromFile() {
        parsePizzas();
    }
}
