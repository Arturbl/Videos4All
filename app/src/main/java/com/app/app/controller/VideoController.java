package com.app.app.controller;

import com.app.app.model.VideoResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.content.ContentBody;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import org.apache.http.entity.mime.MultipartEntityBuilder;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

public class VideoController {

    private static final WindowController windowController = WindowController.getInstance();
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static String uploadVideo(File file)  {
        String endpoint = "http://localhost:8080/video/upload";
        String accessToken = windowController.getUser().getAccessToken();

        try {
            CloseableHttpClient httpClient = HttpClientBuilder.create().build();
            HttpPost httpPost = new HttpPost(endpoint);
            MultipartEntityBuilder entityBuilder = MultipartEntityBuilder.create();
            entityBuilder.addPart("file", new FileBody(file));
            entityBuilder.addPart("username", new StringBody(
                    windowController.getUser().getUsername(),
                    ContentType.TEXT_PLAIN)
            );
            String authorizationHeader = "Bearer " + accessToken;
            httpPost.setHeader(HttpHeaders.AUTHORIZATION, authorizationHeader);
            httpPost.setEntity(entityBuilder.build());
            HttpResponse response = httpClient.execute(httpPost);
            int statusCode = response.getStatusLine().getStatusCode();
            String responseBody = EntityUtils.toString(response.getEntity());
            if (statusCode == 200) {
                System.out.println("Upload successful. Response: " + responseBody);
            } else {
                System.out.println("Upload failed. Status code: " + statusCode);
            }
            httpClient.close();
            return "Uploading " + file.getName();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return "File not found";
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
                return "Downloading " + filename;
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
            return responseMapper(response.toString());
        } else {
            throw new IOException("GET request failed with response code: " + responseCode);
        }
    }

    private static List<VideoResponse> responseMapper(String response) {
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
