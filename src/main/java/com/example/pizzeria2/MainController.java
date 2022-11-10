package com.example.pizzeria2;

import core.services.VisitorsService;
import core.strategy.AverageDemandStrategy;
import core.strategy.HighDemandStrategy;
import core.strategy.PartyStrategy;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;

public class MainController {
    @FXML
    ToggleGroup strategies;

    @FXML
    private ListView visitors;

    public void initialize(){
        Platform.setImplicitExit(false);
        VisitorsService visitorsService =   Main.getInjector().getInstance(VisitorsService.class);
        visitors.setItems(visitorsService.visitors);
        visitorsService.manageCreatingVisitor();

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