package ui.kitchentask;

import businesslogic.CatERing;
import businesslogic.StaffException;
import businesslogic.TurnException;
import businesslogic.UseCaseLogicException;
import businesslogic.kitchentask.KitchenTask;
import businesslogic.kitchentask.KitchenTaskManager;
import businesslogic.kitchentask.KitchenTaskSummary;
import businesslogic.recipe.KitchenItem;
import businesslogic.recipe.Recipe;
import businesslogic.turn.Turn;
import businesslogic.turn.TurnBoard;
import businesslogic.user.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;

import java.util.Optional;

public class KitchenTaskSummaryContent {
    @FXML
    private ListView<KitchenTask> kitchenTaskList;
    @FXML
    private Button deleteKitchenTaskButton;
    @FXML
    private BorderPane assignKitchenTaskPane;

    private KitchenTaskManagement kitchenTaskManagement;

    private ObservableList<KitchenTask> kitchenTasks;
    @FXML
    private ChoiceBox<Turn> selectTurn;
    @FXML
    private ChoiceBox<User> selectCook;
    @FXML
    private Spinner<Integer> timeField;
    @FXML
    private CheckBox completatoCheckBox;
    @FXML
    private CheckBox daPreparareCheckBox;
    @FXML
    private TextField quantityField;

    public void initialize() {
        selectTurn.setItems(FXCollections.observableList(TurnBoard.getInstance().getTurns()));
        selectCook.setItems(FXCollections.observableList(User.loadCooks()));

        KitchenTaskSummary summary = CatERing.getInstance().getKitchenTaskManager().getCurrentKitchenTaskSummary();
        kitchenTasks = FXCollections.observableArrayList();
        if (summary != null) {
            kitchenTasks.addAll(summary.getKitchenTasks());
            kitchenTaskList.setItems(kitchenTasks);
        }
        kitchenTaskList.getSelectionModel().selectedItemProperty().addListener((obs, old, kt) -> {
            if (kt == null) {
                selectTurn.getSelectionModel().select(null);
                selectCook.getSelectionModel().select(null);
                completatoCheckBox.setSelected(false);
                daPreparareCheckBox.setSelected(false);
                quantityField.setText("");
                timeField.getValueFactory().setValue(0);
                assignKitchenTaskPane.setVisible(false);
                return;
            }

            selectTurn.getSelectionModel().select(kt.getTurn());
            selectCook.getSelectionModel().select(kt.getCook());
            completatoCheckBox.setSelected(kt.isCompleted());
            daPreparareCheckBox.setSelected(kt.getToPrepare());
            quantityField.setText(kt.getProductQuantity());
            timeField.getValueFactory().setValue(kt.getPreparationTime());

            assignKitchenTaskPane.setVisible(true);
            deleteKitchenTaskButton.setDisable(false);
        });
    }

    @FXML
    private void exitButtonPressed() {
        kitchenTaskManagement.showEventList(CatERing.getInstance().getKitchenTaskManager().getCurrentKitchenTaskSummary());
    }

    @FXML
    private void addKitchenTaskPressed() throws UseCaseLogicException {
        ObservableList<Recipe> choices = CatERing.getInstance().getRecipeManager().getRecipes();
        ChoiceDialog<KitchenItem> dialog = new ChoiceDialog<>();
        for (Recipe r : choices)
            dialog.getItems().add(r);

        dialog.setTitle("Aggiungi compito");
        dialog.setHeaderText("Scegli compito da aggiungere");

        Optional<KitchenItem> result = dialog.showAndWait();
        if (result.isPresent()) {
            KitchenTask kitchenTask = CatERing.getInstance().getKitchenTaskManager().addKitchenTask(result.get());
            kitchenTasks.add(kitchenTask);
        }
    }

    @FXML
    private void deleteKitchenTaskPressed() throws UseCaseLogicException {
        KitchenTask kitchenTask = kitchenTaskList.getSelectionModel().getSelectedItem();
        CatERing.getInstance().getKitchenTaskManager().removeKitchenTask(kitchenTask);
        kitchenTasks.remove(kitchenTask);
    }

    @FXML
    private void ordinaDifficoltaPressed() throws UseCaseLogicException {
        KitchenTaskManager kitchenTaskManager = CatERing.getInstance().getKitchenTaskManager();
        kitchenTaskManager.sortKitchenTasks(KitchenTask.DIFFICULT_FIRST);
        kitchenTaskList.refresh();
    }

    @FXML
    private void ordinaTempiPressed() throws UseCaseLogicException {
        KitchenTaskManager kitchenTaskManager = CatERing.getInstance().getKitchenTaskManager();
        kitchenTaskManager.sortKitchenTasks(KitchenTask.LONG_FIRST);
        kitchenTaskList.refresh();
    }

    @FXML
    private void assegnaButtonPressed() throws UseCaseLogicException, TurnException, StaffException {
        KitchenTask kitchenTask = kitchenTaskList.getSelectionModel().getSelectedItem();
        Turn turn = selectTurn.getValue();
        User cook = selectCook.getValue();
        boolean toPrepare = daPreparareCheckBox.isSelected();
        boolean isCompleted = completatoCheckBox.isSelected();
        int preparationTime = timeField.getValue();
        String productQuantity = quantityField.getText();
        CatERing.getInstance().getKitchenTaskManager().assignKitchenTask(kitchenTask, turn, cook, preparationTime, productQuantity, toPrepare, isCompleted);
        kitchenTaskList.refresh();
    }

    public void setMenuManagementController(KitchenTaskManagement kitchenTaskManagement) {
        this.kitchenTaskManagement = kitchenTaskManagement;
    }
}
