package com.example.pizzeria2;

import core.services.VisitorsService;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;

public class MainController {


    @FXML
    private Label welcomeText;

    @FXML
    private ListView visitors;

    public void initialize(){
        Platform.setImplicitExit(false);
        VisitorsService visitorsService =   Main.getInjector().getInstance(VisitorsService.class);
        visitors.setItems(visitorsService.visitors);
        visitorsService.manageCreatingPassenger();
    }
}