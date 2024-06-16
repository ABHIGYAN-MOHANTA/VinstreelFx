package org.thunderdome.vinstreelfx;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import org.thunderdome.vinstreelfx.AddFriendView;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class MainController {
    private static final String FILE_PATH = "friends.json";
    @FXML
    private Label welcomeText;

    @FXML
    private TableView<Friend> leaderboardTable;

    @FXML
    private TableColumn<Friend, Integer> rankColumn;

    @FXML
    private TableColumn<Friend, String> nameColumn;

    @FXML
    private TableColumn<Friend, String> leetcodeStatsColumn;

    @FXML
    private TableColumn<Friend, String> githubStatsColumn;

    private List<Friend> friends;
    private ExecutorService executorService;

    @FXML
    private void initialize() {
        rankColumn.setCellValueFactory(new PropertyValueFactory<>("rank"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("leetcodeUsername"));
        leetcodeStatsColumn.setCellValueFactory(new PropertyValueFactory<>("leetcodeStats"));
        githubStatsColumn.setCellValueFactory(new PropertyValueFactory<>("githubStats"));
        loadFriends();
        executorService = Executors.newCachedThreadPool();
        updateLeaderboardAsync();
    }

    private void loadFriends() {
        File file = new File(FILE_PATH);
        if (file.exists() && !file.isDirectory()) {
            Gson gson = new Gson();
            try (FileReader reader = new FileReader(file)) {
                Type listType = new TypeToken<ArrayList<Friend>>() {}.getType();
                friends = gson.fromJson(reader, listType);
                if (friends == null) {
                    friends = new ArrayList<>();
                }
            } catch (IOException e) {
                e.printStackTrace();
                friends = new ArrayList<>();
            }
        } else {
            friends = new ArrayList<>();
        }
    }

    private void updateLeaderboardAsync() {
        Task<Void> task = new Task<>() {
            @Override
            protected Void call() throws Exception {
                for (Friend friend : friends) {
                    Future<String> leetcodeStatsFuture = executorService.submit(() -> StatsFetcher.fetchLeetcodeStats(friend.getLeetcodeUsername()));
                    Future<String> githubStatsFuture = executorService.submit(() -> StatsFetcher.fetchGithubStats(friend.getGithubUsername()));

                    friend.setLeetcodeStats(leetcodeStatsFuture.get());
                    friend.setGithubStats(githubStatsFuture.get());
                }
                return null;
            }

            @Override
            protected void succeeded() {
                super.succeeded();
                Platform.runLater(() -> {
                    leaderboardTable.getItems().setAll(friends);
                });
            }
        };

        executorService.submit(task);
    }

    @FXML
    protected void onRefreshButtonClick() {
        loadFriends();
        updateLeaderboardAsync();
        welcomeText.setText("Welcome to JavaFX Application! LeaderBoard Updated!");
    }

    public void onAddFriendButtonClick() throws IOException {
        AddFriendView a = new AddFriendView(this);
        a.start(new Stage());
    }

    @FXML
    public void onDestroy() {
        executorService.shutdown();
    }
}
