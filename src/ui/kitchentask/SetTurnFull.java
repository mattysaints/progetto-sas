package ui.kitchentask;

import businesslogic.CatERing;
import businesslogic.UseCaseLogicException;
import businesslogic.kitchentask.KitchenTask;
import businesslogic.turn.Turn;
import businesslogic.turn.TurnBoard;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

public class SetTurnFull {
    private Stage stage;
    private ObservableList<KitchenTask> kitchenTasks;

    @FXML
    private CheckBox isFullCheckBox;
    @FXML
    private ListView<KitchenTask> kitchenTaskList;
    @FXML
    private ChoiceBox<Turn> turnChoiceBox;
    @FXML
    private Button salvaButton;

    public void initialize(Stage stage) {
        this.stage = stage;
        kitchenTasks = FXCollections.observableArrayList(KitchenTask.loadAllKitchenTasks());
        turnChoiceBox.setItems(FXCollections.observableList(TurnBoard.getInstance().getTurns()));

        turnChoiceBox.getSelectionModel().selectedItemProperty().addListener((obs, old, turn) -> {
            if (salvaButton.disableProperty().isBound())
                salvaButton.disableProperty().unbind();
            isFullCheckBox.setSelected(turn.isFull());
            kitchenTaskList.setItems(kitchenTasks.filtered(kt -> turn.equals(kt.getTurn())));
            salvaButton.disableProperty().bind(
                    isFullCheckBox.selectedProperty().asString().isEqualTo(
                            Boolean.toString(turnChoiceBox.getSelectionModel().selectedItemProperty().getValue().isFull())));
        });


        turnChoiceBox.getSelectionModel().select(0);
    }

    @FXML
    private void salvaButtonPressed() {
        Turn turn = turnChoiceBox.getValue();
        boolean isFull = isFullCheckBox.isSelected();
        try {
            CatERing.getInstance().getKitchenTaskManager().setTurnFull(turn, isFull);
        } catch (UseCaseLogicException e) {
            e.printStackTrace();
        }

        salvaButton.disableProperty().unbind();
        salvaButton.setDisable(true);
        salvaButton.disableProperty().bind(
                isFullCheckBox.selectedProperty().asString().isEqualTo(
                        Boolean.toString(turnChoiceBox.getSelectionModel().selectedItemProperty().getValue().isFull())));
    }

    @FXML
    private void esciButtonPressed() {
        stage.close();
    }
}
