package com.app.app.view;

import com.app.app.controller.Api;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;


public class Login extends VBox {
    private TextField usernameField;
    private PasswordField passwordField;
    private Text headerText;

    public Login() {
        setSpacing(10);
        setPadding(new Insets(25));
        setAlignment(Pos.CENTER);

        headerText = new Text("Login");
        headerText.setFont(Font.font("Verdana", FontWeight.BOLD, 20));
        getChildren().add(headerText);

        Label usernameLabel = new Label("Username:");
        getChildren().add(usernameLabel);

        usernameField = new TextField();
        getChildren().add(usernameField);

        Label passwordLabel = new Label("Password:");
        getChildren().add(passwordLabel);

        passwordField = new PasswordField();
        getChildren().add(passwordField);

        Button loginButton = new Button("Login");
        getChildren().add(loginButton);
        VBox.setMargin(loginButton, new Insets(10, 0, 0, 0));

        loginButton.setOnAction(e -> handleLogin());

        setOnKeyPressed(e -> {
            if (e.getCode().toString().equals("ENTER")) {
                handleLogin();
            }
        });
    }

    private void handleLogin() {
        String username = usernameField.getText();
        String password = passwordField.getText();

        if(!username.isEmpty() && !password.isEmpty()) {
            try {
                Api.login(username, password);
            } catch (Exception e) {
                headerText.setText("Invalid data");
            }
        }

    }
}
