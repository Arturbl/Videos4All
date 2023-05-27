package com.app.app.view;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

public class Home extends VBox {

    public Home() {
        // Set spacing and padding
        setSpacing(10);
        setPadding(new Insets(10));

        // Create Download button
        Button downloadButton = new Button("Download");
        downloadButton.setOnAction(e -> {
            // Perform download action
            System.out.println("Download button clicked");
        });

        // Create Upload button
        Button uploadButton = new Button("Upload");
        uploadButton.setOnAction(e -> {
            // Perform upload action
            System.out.println("Upload button clicked");
        });

        // Add buttons to the VBox
        getChildren().addAll(downloadButton, uploadButton);
    }

}
