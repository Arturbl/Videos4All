package com.app.app.controller;

import com.app.app.model.User;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class WindowController {

    private static WindowController INSTANCE;
    private Stage primaryStage;
    private User user;


    private WindowController() {}


    public void updateScene(Parent nextParent) {
        Scene scene = new Scene(nextParent, 400, 450);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public static  WindowController getInstance() {
        if(INSTANCE == null) {
            return new WindowController();
        }
        return INSTANCE;
    }

}
