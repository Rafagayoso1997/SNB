package controller;

import com.jfoenix.controls.JFXListView;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import logic.Controller;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class TeamsController implements Initializable {

    @FXML
    private JFXListView<String> teamsListView;

    @FXML
    private Pane imagePane;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        List<String> teams = Controller.getSingletonController().getTeams();
        teamsListView.setItems(FXCollections.observableArrayList(teams));
        teamsListView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                Image     image     = new Image(getClass().getResourceAsStream("/resources/teams/" + newValue + ".png"));
                ImageView teamImage = new ImageView(image);
                teamImage.setFitHeight(imagePane.getHeight());
                teamImage.setFitWidth(imagePane.getWidth());
                teamImage.setPreserveRatio(true);
                teamImage.setSmooth(true);
                imagePane.getChildren().clear();
                imagePane.getChildren().add(teamImage);
                System.out.println(newValue);
            }
        });
    }
}
