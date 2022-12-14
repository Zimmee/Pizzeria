package com.example.pizzeria2;

import com.google.inject.Guice;
import com.google.inject.Injector;
import core.injection.InjectionModule;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    private static Injector injector;

    public static Injector getInjector() {
        return injector;
    }

    public static void setInjector(Injector injector) {
        Main.injector = injector;
    }


    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("settings-view.fxml"));
        Scene settingsScene = new Scene(fxmlLoader.load(), 428, 501);


        stage.setTitle("Hello!");
        stage.setScene(settingsScene);
        stage.show();
    }

    public static void main(String[] args) {
        injector = Guice.createInjector(new InjectionModule());
        launch();
    }
}