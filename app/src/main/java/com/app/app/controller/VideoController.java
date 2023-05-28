package com.app.app.controller;

import com.app.app.model.VideoResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.*;
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

    public static String downloadVideo(String videoName) {
        String accessToken = windowController.getUser().getAccessToken();
        try {
            CloseableHttpClient httpClient = HttpClientBuilder.create().build();
            HttpGet httpGet = new HttpGet("http://localhost:8080/video/find/" + videoName);
            httpGet.setHeader("Authorization", "Bearer " + accessToken);
            CloseableHttpResponse response = httpClient.execute(httpGet);
            if (response.getStatusLine().getStatusCode() == 200) {
                String contentDispositionHeader = response.getFirstHeader("Content-Disposition").getValue();
                String filename = contentDispositionHeader.substring(contentDispositionHeader.indexOf("\"") + 1,
                        contentDispositionHeader.lastIndexOf("\""));
                String filePath = "/home/artur/Desktop/" + filename;
                try (InputStream inputStream = response.getEntity().getContent();
                     FileOutputStream outputStream = new FileOutputStream(filePath)) {
                    byte[] buffer = new byte[1024];
                    int bytesRead;
                    while ((bytesRead = inputStream.read(buffer)) != -1) {
                        outputStream.write(buffer, 0, bytesRead);
                    }
                }
                return "File at: " + filePath;
            } else {
                System.out.println("Error: " + response.getStatusLine().getStatusCode());
            }
            response.close();
            httpClient.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "File not found.";
    }

    public static List<VideoResponse> listVideos() {
        String url = "http://localhost:8080/video/findAll";
        try {
            return sendGetRequest(url);
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
        return null;
    }

    private static List<VideoResponse> sendGetRequest(String url) throws IOException {
        URL apiUrl = new URL(url);
        String accessToken = windowController.getUser().getAccessToken();
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
