package com.app.app;

import com.app.app.controller.WindowController;
import com.app.app.view.Login;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class App extends Application {

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage primaryStage) throws IOException {

        WindowController windowController = WindowController.getInstance();
        windowController.setPrimaryStage(primaryStage);

        windowController.updateScene(
                new Login()
        );

        primaryStage.setTitle("Videos4All");
    }


}