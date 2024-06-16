package org.thunderdome.vinstreelfx;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class AddFriendController {
    @FXML
    private Label addFriendText;

    @FXML
    private TextArea leetcodeTextArea;

    @FXML
    private TextArea githubTextArea;

    private List<Friend> friends = new ArrayList<>();
    private static final String FILE_PATH = "friends.json";

    @FXML
    private void initialize() {
        System.out.println("Initializing AddFriendController...");
        loadFriends();
    }

    @FXML
    private void onAddFriendButtonClick() {
        String leetcodeUsername = leetcodeTextArea.getText();
        String githubUsername = githubTextArea.getText();

        if (!leetcodeUsername.isEmpty() && !githubUsername.isEmpty()) {
            Friend friend = new Friend(leetcodeUsername, githubUsername);
            friends.add(friend);
            System.out.println("Adding friend: " + friend);
            saveFriends();
            addFriendText.setText("Friend Added: " + leetcodeUsername + ", " + githubUsername);
        } else {
            addFriendText.setText("Please enter both usernames.");
        }
    }

    public void saveFriends() {
        Gson gson = new Gson();
        try (FileWriter writer = new FileWriter(FILE_PATH)) {
            gson.toJson(friends, writer);
            System.out.println("Friends saved to " + FILE_PATH);
        } catch (IOException e) {
            e.printStackTrace();
            addFriendText.setText("Error saving friends.");
        }
    }

    private void loadFriends() {
        File file = new File(FILE_PATH);
        if (file.exists() && !file.isDirectory()) {
            Gson gson = new Gson();
            try (FileReader reader = new FileReader(FILE_PATH)) {
                Type listType = new TypeToken<ArrayList<Friend>>() {}.getType();
                friends = gson.fromJson(reader, listType);
                if (friends == null) {
                    friends = new ArrayList<>();
                }
                System.out.println("Friends loaded from " + FILE_PATH);
            } catch (IOException e) {
                e.printStackTrace();
                friends = new ArrayList<>();
                addFriendText.setText("Error loading friends.");
            }
        } else {
            friends = new ArrayList<>();
            System.out.println("No existing friends file found, starting with an empty list.");
        }
    }
}
