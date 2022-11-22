package com.example.pizzeria2;

import core.models.*;
import core.services.CooksService;
import core.services.VisitorsService;
import core.strategy.visitorsStrategy.AverageDemandStrategy;
import core.strategy.visitorsStrategy.HighDemandStrategy;
import core.strategy.visitorsStrategy.PartyStrategy;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableStringValue;
import javafx.beans.value.ObservableValue;
import javafx.collections.ListChangeListener;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;
import javafx.util.Duration;
import ui.ActionButtonTableCell;

import java.util.ArrayList;

public class MainController {
    @FXML
    ToggleGroup strategies;

    @FXML
    private TableView visitors;
    @FXML
    private TableColumn<Visitor, String> ID;
    @FXML
    private TableColumn<Visitor, ArrayList<Pizza>> OrderedPizza;
    @FXML
    private TableColumn<Visitor, Integer> QueueSelected;
    @FXML
    private TableView cooks;

    @FXML
    private TableColumn<Cook, core.models.CookType> CookType;
    @FXML
    private TableColumn<Cook, core.models.CookState> CookState;
    @FXML
    private TableColumn<Cook, String> WorkingOn;
    @FXML
    private TableColumn<Cook, Button> StopColumn;



    public void initialize(){
        Platform.setImplicitExit(false);
        Main.getInjector().getInstance(CooksService.class).onTick();
        VisitorsService visitorsService =   Main.getInjector().getInstance(VisitorsService.class);
        ID.setCellValueFactory(new PropertyValueFactory<>("Id"));
        OrderedPizza.setCellValueFactory(new PropertyValueFactory<>("pizzas"));
        QueueSelected.setCellValueFactory(new PropertyValueFactory<>("selectedQueue"));
        visitorsService.manageCreatingVisitor();



        CookType.setCellValueFactory(new PropertyValueFactory<>("type"));
        CookState.setCellValueFactory(new PropertyValueFactory<>("state"));
        WorkingOn.setCellValueFactory(new PropertyValueFactory<>("workingOn"));
        StopColumn.setCellFactory(ActionButtonTableCell.<Cook>forTableColumn("Stop", (Cook p) -> {
            if(p.getState()== core.models.CookState.stopped){
                                            p.setState(core.models.CookState.free);
                                        }else{
                                            p.setState(core.models.CookState.stopped);
                                        }
            return p;
        }));
//
//        StopColumn.setCellValueFactory(new PropertyValueFactory<>("Stop"));
//
//        Callback<TableColumn<Cook, String>, TableCell<Cook, String>> cellFactory
//                = //
//                new Callback<TableColumn<Cook, String>, TableCell<Cook, String>>() {
//                    @Override
//                    public TableCell call(final TableColumn<Cook, String> param) {
//                        final TableCell<Cook, String> cell = new TableCell<Cook, String>() {
//
//
//                            @Override
//                            public void updateItem(String item, boolean empty) {
//                                super.updateItem(item, empty);
//                                if (empty) {
//                                    setGraphic(null);
//                                    setText(null);
//                                } else {
//                                    Cook Cook = getTableView().getItems().get(getIndex());
//                                    final Button btn = new Button(Cook.getState()== core.models.CookState.stopped?"Resume":"Stop");
//                                    btn.setOnAction(event -> {
//                                    System.out.println(Cook.getState());
//                                        if(Cook.getState()== core.models.CookState.stopped){
//                                            Cook.setState(core.models.CookState.free);
//                                        }else{
//                                            Cook.setState(core.models.CookState.stopped);
//                                        }
//                                    });
//                                    System.out.println(Cook.getState());
//
//                                    setGraphic(btn);
//                                    setText(null);
//                                }
//                            }
//                        };
//                        return cell;
//                    }
//                };
//
//        StopColumn.setCellFactory(cellFactory);
        visitors.setItems(Main.getInjector().getInstance(VisitorsService.class).visitors);

        cooks.setItems(Main.getInjector().getInstance(CooksService.class).cooks);

        Timeline fiveSecondsWonder = new Timeline(
                new KeyFrame(Duration.seconds(0.1),
                        new EventHandler<ActionEvent>() {

                            @Override
                            public void handle(ActionEvent event) {
                                Main.getInjector().getInstance(CooksService.class).onTick();

                            }
                        }));
        fiveSecondsWonder.setCycleCount(Timeline.INDEFINITE);
        fiveSecondsWonder.play();


        Timeline oneSec = new Timeline(
                new KeyFrame(Duration.seconds(1),
                        new EventHandler<ActionEvent>() {

                            @Override
                            public void handle(ActionEvent event) {
                                visitors.refresh();
                                cooks.refresh();
                            }
                        }));
        oneSec.setCycleCount(Timeline.INDEFINITE);
        oneSec.play();

        strategies.selectedToggleProperty().addListener(new ChangeListener<Toggle>(){
            public void changed(ObservableValue<? extends Toggle> ov, Toggle old_toggle, Toggle new_toggle) {

                if (strategies.getSelectedToggle() != null) {

                    switch (strategies.getSelectedToggle().getUserData().toString()){
                        case "0": visitorsService.setSelectedStrategy(new HighDemandStrategy());
                        break;
                        case "1": visitorsService.setSelectedStrategy(new AverageDemandStrategy());
                        break;
                        case "2": visitorsService.setSelectedStrategy(new PartyStrategy());
                        break;
                    }

                }

            }
        });



    }


}