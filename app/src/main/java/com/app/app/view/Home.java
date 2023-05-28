package com.app.app.view;

import com.app.app.controller.VideoController;
import com.app.app.controller.WindowController;
import com.app.app.model.VideoResponse;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.util.List;
import java.util.Objects;

public class Home extends VBox {

    private final WindowController windowController = WindowController.getInstance();
    private final TextField textField;
    private final Button downloadButton;
    private final Button selectFileButton;
    private final Button saveToDbButton;
    private File selectedFile;
    private final ListView<VideoResponse> listView = new ListView<>();

    public Home() {

        System.out.println("User: " + windowController.getUser().getUsername() + "Access token: " + windowController.getUser().getAccessToken());

        // Create the text
        Text titleText = new Text("Welcome " + windowController.getUser().getUsername() + "!");
        titleText.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");

        // Create the text field
        textField = new TextField();
        textField.setPromptText("Enter video name");

        // Create the download button
        downloadButton = new Button("Download");

        // Create the upload button
        selectFileButton = new Button("SelectFile");
        selectFileButton.setOnAction(event -> {
            Stage stage = (Stage) getScene().getWindow();
            FileChooser fileChooser = new FileChooser();
            selectedFile = fileChooser.showOpenDialog(stage);
            if (selectedFile != null) {
                selectFileButton.setText(selectedFile.getName());
            }
        });

        // Create the button container
        HBox buttonContainer = new HBox(10);
        buttonContainer.setAlignment(Pos.CENTER_LEFT);
        buttonContainer.setPadding(new Insets(20));
        buttonContainer.getChildren().addAll(textField, downloadButton);


        saveToDbButton = new Button("Save to database");
        HBox uploadContainer = new HBox(10);
        uploadContainer.setAlignment(Pos.CENTER);
        uploadContainer.setAlignment(Pos.CENTER_LEFT);
        uploadContainer.setPadding(new Insets(20));
        uploadContainer.getChildren().addAll(selectFileButton, saveToDbButton);


        // Create the main container
        VBox mainContainer = new VBox(20);
        mainContainer.setAlignment(Pos.CENTER);
        mainContainer.setPadding(new Insets(40));


        if(Objects.equals(windowController.getUser().getUsername(), "admin")) {
            mainContainer.getChildren().addAll(titleText, uploadContainer);
        } else {
            List<VideoResponse> videos = VideoController.listVideos();
            ObservableList<VideoResponse> observableVideos = FXCollections.observableArrayList(videos);
            listView.setItems(observableVideos);
            mainContainer.getChildren().addAll(titleText, listView, buttonContainer);
        }


        getChildren().add(mainContainer);

        downloadButton.setOnAction(e -> VideoController.downloadVideo(textField.getText()));
        saveToDbButton.setOnAction(e -> VideoController.uploadVideo(selectedFile));

    }
}

