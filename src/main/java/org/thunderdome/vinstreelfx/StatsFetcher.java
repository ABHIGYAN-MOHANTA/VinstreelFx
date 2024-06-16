package org.thunderdome.vinstreelfx;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.BufferedReader;
import java.io.InputStreamReader;



public class StatsFetcher {

    public static String fetchLeetcodeStats(String leetcodeUsername) {
        String urlString = "https://leetcode-stats-api.herokuapp.com/" + leetcodeUsername;
        try {
            URL url = new URL(urlString);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/vnd.github.v3+json");

            if (conn.getResponseCode() != 200) {
                throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
            }

            BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));

            String output;
            StringBuilder response = new StringBuilder();
            while ((output = br.readLine()) != null) {
                response.append(output);
            }

            conn.disconnect();

            JsonParser parser = new JsonParser();
            JsonObject leetcodeStats = parser.parse(response.toString()).getAsJsonObject();
            int totalSolved = leetcodeStats.get("totalSolved").getAsInt();
            System.out.println("Total Solved: " + totalSolved);
            return ""+totalSolved;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String fetchGithubStats(String githubUsername) {
        String urlString = "https://github-contributions-api.jogruber.de/v4/" + githubUsername;
        try {
            URL url = new URL(urlString);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/vnd.github.v3+json");

            if (conn.getResponseCode() != 200) {
                throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
            }

            BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));

            String output;
            StringBuilder response = new StringBuilder();
            while ((output = br.readLine()) != null) {
                response.append(output);
            }

            conn.disconnect();

            JsonParser parser = new JsonParser();
            JsonObject githubStats = parser.parse(response.toString()).getAsJsonObject();
            JsonObject totalContributionsObject = githubStats.getAsJsonObject("total");

            int totalContributions = totalContributionsObject.get("2024").getAsInt(); // Adjust the year as needed
            System.out.println("Total contributions: " + totalContributions);
            return ""+totalContributions;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}

