package com.app.app.controller;

import com.app.app.model.VideoResponse;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

public class VideoController {

    private static final WindowController windowController = WindowController.getInstance();
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static void uploadVideo(File file) {
        // TODO: implement request to save video into database
        System.out.println(file.getName());
    }
    
    public static void downloadVideo(String name) {
        // TODO: implement request to download video into database
    }

    public static List<VideoResponse> listVideos() {
        String url = "http://localhost:8080/video/findAll";
        String accessToken = windowController.getUser().getAccessToken();
        try {
            return sendGetRequest(url, accessToken);
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
        return null;
    }

    private static List<VideoResponse> sendGetRequest(String url, String accessToken) throws IOException {
        URL apiUrl = new URL(url);
        HttpURLConnection connection = (HttpURLConnection) apiUrl.openConnection();
        connection.setRequestMethod("GET");
        connection.setRequestProperty("Authorization", "Bearer " + accessToken);

        int responseCode = connection.getResponseCode();

        if (responseCode == HttpURLConnection.HTTP_OK) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();

            return mapResponse(response.toString());
        } else {
            throw new IOException("GET request failed with response code: " + responseCode);
        }
    }


    private static List<VideoResponse> mapResponse(String response) {
        try {
            VideoResponse[] videoResponses = objectMapper.readValue(response, VideoResponse[].class);
            if (videoResponses.length > 0) {
                return List.of(videoResponses);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
