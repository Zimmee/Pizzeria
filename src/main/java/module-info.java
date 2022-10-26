module com.example.pizzeria2 {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;
    requires com.almasb.fxgl.all;
    requires com.google.guice;
    requires lombok;
    requires json.simple;

    opens com.example.pizzeria2 to javafx.fxml;
    opens core.models to javafx.base;

    exports core.services;
    exports com.example.pizzeria2;
}