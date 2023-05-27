package com.app.app;

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
    public void start(Stage stage) throws IOException {

        Login login = new Login();

        Scene scene = new Scene(login, 320, 240);
        stage.setTitle("Videos4All");
        stage.setScene(scene);
        stage.show();
    }


}