package com.example.pizzeria2;

import core.models.Cook;
import core.models.CookState;
import core.models.CookType;
import core.models.Pizza;
import core.services.CooksService;
import core.services.VisitorsService;
import core.strategy.visitorsStrategy.AverageDemandStrategy;
import core.strategy.visitorsStrategy.HighDemandStrategy;
import core.strategy.visitorsStrategy.PartyStrategy;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.Duration;
import javafx.util.converter.IntegerStringConverter;

public class MainController {
    @FXML
    ToggleGroup strategies;

    @FXML
    private ListView visitors;

    @FXML
    private TableView cooks;

    @FXML
    private TableColumn<Cook, core.models.CookType> CookType;
    @FXML
    private TableColumn<Cook, core.models.CookState> CookState;

    public void initialize(){
        Platform.setImplicitExit(false);
        Main.getInjector().getInstance(CooksService.class).init();
        VisitorsService visitorsService =   Main.getInjector().getInstance(VisitorsService.class);
        visitors.setItems(visitorsService.pizzas);
        visitorsService.manageCreatingVisitor();

        CookType.setCellValueFactory(new PropertyValueFactory<>("type"));
        CookState.setCellValueFactory(new PropertyValueFactory<>("state"));

        cooks.setItems(Main.getInjector().getInstance(CooksService.class).cooks);



        Timeline fiveSecondsWonder = new Timeline(
                new KeyFrame(Duration.seconds(0.1),
                        new EventHandler<ActionEvent>() {

                            @Override
                            public void handle(ActionEvent event) {
                                Main.getInjector().getInstance(CooksService.class).init();
                                visitors.refresh();
                                cooks.refresh();
                            }
                        }));
        fiveSecondsWonder.setCycleCount(Timeline.INDEFINITE);
        fiveSecondsWonder.play();

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