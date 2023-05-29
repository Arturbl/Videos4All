package com.app.app.controller;

import com.app.app.model.User;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class WindowController {

    private static WindowController INSTANCE;
    private static Stage primaryStage;
    private static User user;


    private WindowController() {}


    public void updateScene(Parent nextParent) {
        Scene scene = new Scene(nextParent, 400, 450);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public void setPrimaryStage(Stage primaryStage) {
        WindowController.primaryStage = primaryStage;
    }

    public void setUser(User user) {
        WindowController.user = user;
    }

    public User getUser() {
        return user;
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    public static WindowController getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new WindowController();
        }
        return INSTANCE;
    }


}
