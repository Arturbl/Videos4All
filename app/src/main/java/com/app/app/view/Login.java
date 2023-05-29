package com.app.app.view;

import com.app.app.controller.AuthController;
import com.app.app.controller.WindowController;
import com.app.app.model.User;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
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

        headerText = new Text("Videos4All");
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

        HBox hBox = new HBox();
        hBox.setAlignment(Pos.BASELINE_CENTER);

        Button loginButton = new Button("Login");
        getChildren().add(loginButton);
        VBox.setMargin(loginButton, new Insets(10, 0, 0, 0));

        Button registerButton = new Button("Create Account");
        getChildren().add(registerButton);
        VBox.setMargin(registerButton, new Insets(10, 0, 0, 0));

        hBox.getChildren().addAll(loginButton, registerButton);

        getChildren().add(hBox);

        loginButton.setOnAction(e -> login());
        registerButton.setOnAction(e -> register());
    }

    private void login() {
        String username = usernameField.getText();
        String password = passwordField.getText();
        if(!username.isEmpty() && !password.isEmpty()) {
            try {
                User user = AuthController.login(username, password);
                System.out.println("User from login: " + user.getUsername());
                WindowController windowController = WindowController.getInstance();
                windowController.setUser(user);
                windowController.updateScene(
                        new Home()
                );
            } catch (Exception e) {
                headerText.setText("Invalid data.");
                System.out.println(e.getMessage());
            }
        }
    }

    private void register() {
        String username = usernameField.getText();
        String password = passwordField.getText();
        if(!username.isEmpty() && !password.isEmpty()) {
            try {
                User user = AuthController.register(username, password);
                headerText.setText("Account created for: " + user.getUsername());
            } catch (Exception e) {
                headerText.setText("Username already in use.");
                System.out.println(e.getMessage());
            }
        }
    }
}
