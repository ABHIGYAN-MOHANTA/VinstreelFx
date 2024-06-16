package org.thunderdome.vinstreelfx;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class AddFriendView extends Application {
    private MainController mainController;

    public AddFriendView(MainController mainController) {
        this.mainController = mainController;
    }

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("add-friend-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 280);
        stage.setTitle("VinstreelFx");
        stage.setScene(scene);
        stage.show();
        stage.alwaysOnTopProperty();
        stage.setOnHidden(e -> mainController.onRefreshButtonClick());

    }

    public static void main(String[] args) {
        launch();
    }
}