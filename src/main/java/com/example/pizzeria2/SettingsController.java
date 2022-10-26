package com.example.pizzeria2;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import core.models.Pizza;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
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
            var films = new ArrayList<Pizza>();
            for (int i = 0; i < obj.size(); i++) {
                var iPizza = (JSONObject) obj.get(i);
                menu.add(new Pizza((String) iPizza.get("name"), ((Long) iPizza.get("prepTime")).intValue()));
            }
            return films;
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    private void submit(){
        System.out.println(cooks.getText());
        System.out.println(queue.getText());

        System.out.println(Name.getColumns());

        for (var i:menuTable.getItems()){
            System.out.println(i.getName());
        }
    }

    public void loadFromFile() {
        parsePizzas();
    }
}
