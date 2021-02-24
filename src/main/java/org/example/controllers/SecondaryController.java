package org.example.controllers;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.example.App;

public class SecondaryController {

    private String weaponChoice;
    private String gameDifficulty;
    private int goldAmount;

    @FXML
    private TextField nameID;

    @FXML
    private Button startButton;

    @FXML
    private Label gameConditions;

    @FXML
    private Label gameConditions2;

    @FXML
    private Label gameConditions3;

    @FXML
    private void switchToPrimary() throws IOException {
        App.setRoot("primary");
    }

    @FXML
    public void setEasyMode(ActionEvent event) {
        gameDifficulty = "Easy";
    }

    @FXML
    public void setMediumMode(ActionEvent event) {
        gameDifficulty = "Medium";
    }

    @FXML
    public void setHardMode(ActionEvent event) {
        gameDifficulty = "Hard";
    }

    //Placeholder names currently change them as needed
    @FXML
    public void weapon1(ActionEvent event) {
        weaponChoice = "weaponA";
    }

    @FXML
    public void weapon2(ActionEvent event) {
        weaponChoice = "weaponB";
    }

    @FXML
    public void weapon3(ActionEvent event) {
        weaponChoice = "weaponC";
    }

    @FXML
    public void switchToGameScreen(ActionEvent event) throws IOException {
        //This is the logic for the text field inputs
        boolean nameCheck = false;
        boolean difficultyCheck = false;
        boolean weaponCheck = false;
        if (nameID.getText().isEmpty() || nameID.getText() == null || nameID.getText().trim().equals("")) {
            gameConditions.setText("Please enter a valid username!");
        } else {
            String userName = nameID.getText();
            gameConditions.setText("Your name is " + nameID.getText());
            nameCheck = true;
        }
        //This is the logic for the game difficulty
        if (gameDifficulty == null) {
            gameConditions2.setText("Please select a difficulty!");
        } else {
            if (gameDifficulty.equals("Easy")) {
                gameConditions2.setText("Your chosen difficulty is " + gameDifficulty + ", you're a baby");
                difficultyCheck = true;
                goldAmount = 1000;
            }
            if (gameDifficulty.equals("Medium")) {
                gameConditions2.setText("Your chosen difficulty is " + gameDifficulty);
                difficultyCheck = true;
                goldAmount = 500;
            }
            if (gameDifficulty.equals("Hard")) {
                gameConditions2.setText("Your chosen difficulty is " + gameDifficulty + ", I'm proud of you");
                difficultyCheck = true;
                goldAmount = 100;
            }
        }
        //This is the logic for the chosen weapons, currently are placeholders
        if (weaponChoice == null) {
            gameConditions3.setText("Please choose a weapon!");
        } else {
            gameConditions3.setText("Your selected weapon " + weaponChoice);
            weaponCheck = true;
        }
        if (nameCheck && difficultyCheck && weaponCheck) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("gameScreen.fxml"));
            GameScreenController gameScreenController = loader.getController();
            gameScreenController.displayGold(goldAmount);
            App.setRoot("gameScreen");
        }
    }

    @FXML
    private javafx.scene.control.Button closeButton;

    @FXML
    private void closeButtonAction(){
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }
}