module com.app.app {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;
    requires com.google.gson;
    requires com.fasterxml.jackson.databind;

    opens com.app.app.model to com.fasterxml.jackson.databind;

    exports com.app.app;
    opens com.app.app to javafx.fxml;
}
