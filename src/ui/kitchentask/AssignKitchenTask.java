package ui.kitchentask;

import businesslogic.turn.Turn;
import businesslogic.turn.TurnBoard;
import businesslogic.user.User;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;

public class AssignKitchenTask {
    @FXML
    private TextField quantityField;
    @FXML
    private ChoiceBox<Turn> selectTurn;
    @FXML
    private ChoiceBox<User> selectCook;
    @FXML
    private TextField timeField;
    @FXML
    private CheckBox completatoCheckBox;
    @FXML
    private CheckBox daPreparareCheckBox;

    public void initialize() {
        selectTurn.setItems(FXCollections.observableList(TurnBoard.getInstance().getTurns()));
        selectCook.setItems(FXCollections.observableList(User.loadCooks()));
    }
}
