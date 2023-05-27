package com.app.app.controller;


import com.app.app.model.User;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import com.google.gson.Gson;



public class Api {

    public static User login(String username, String password) throws IOException {
        String url = "http://localhost:8080/addUser/" + username + "/" + password;
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("POST");
        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream(), StandardCharsets.UTF_8));
        String inputLine;
        StringBuilder response = new StringBuilder();
        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();
        return mapResponseToUser(response);
    }

    private static User mapResponseToUser(StringBuilder jsonResponse) {
        Gson gson = new Gson();
        return gson.fromJson(String.valueOf(jsonResponse), User.class);
    }

}
