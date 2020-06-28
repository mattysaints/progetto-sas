package ui.kitchentask;

import businesslogic.kitchentask.KitchenTask;
import businesslogic.turn.Turn;
import businesslogic.user.User;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;

public class AssignKitchenTask {
    @FXML
    private ChoiceBox<Turn> selectTurn;
    @FXML
    private ChoiceBox<User> selectCook;
    @FXML
    private TextField time;
    @FXML
    private TextField quantity;

    public void assign(KitchenTask kitchenTask) {
        //TODO
    }
}
