package ui.kitchentask;

import businesslogic.CatERing;
import businesslogic.StaffException;
import businesslogic.TurnException;
import businesslogic.UseCaseLogicException;
import businesslogic.event.ServiceInfo;
import businesslogic.kitchentask.KitchenTask;
import businesslogic.kitchentask.KitchenTaskSummary;
import businesslogic.recipe.KitchenItem;
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
    private KitchenTaskManagement kitchenTaskManagement;
    private ObservableList<KitchenTask> kitchenTasks;

    @FXML
    private ListView<KitchenTask> kitchenTaskList;
    @FXML
    private Button deleteKitchenTaskButton;
    @FXML
    private BorderPane assignKitchenTaskPane;
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
    @FXML
    private Label dettaglioServizioLabel;
    @FXML
    private Label dettaglioCompitoLabel;
    @FXML
    private Button assegnaButton;

    public void initialize(ServiceInfo service) {
        dettaglioServizioLabel.setText(service.toString());
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
                dettaglioCompitoLabel.setText("");
                selectTurn.getSelectionModel().select(null);
                selectCook.getSelectionModel().select(null);
                completatoCheckBox.setSelected(false);
                daPreparareCheckBox.setSelected(false);
                quantityField.setText("");
                timeField.getValueFactory().setValue(0);
                assignKitchenTaskPane.setVisible(false);
                assegnaButton.disableProperty().unbind();
                return;
            }

            dettaglioCompitoLabel.setText(kt.getKitchenItem().toString());
            selectTurn.getSelectionModel().select(kt.getTurn());
            selectCook.getSelectionModel().select(kt.getCook());
            completatoCheckBox.setSelected(kt.isCompleted());
            daPreparareCheckBox.setSelected(kt.getToPrepare());
            quantityField.setText(kt.getProductQuantity());
            timeField.getValueFactory().setValue(kt.getPreparationTime());

            assignKitchenTaskPane.setVisible(true);
            deleteKitchenTaskButton.setDisable(false);
            bindAssegnaButton();
        });

        assegnaButton.setDisable(true);
    }

    @FXML
    private void exitButtonPressed() {
        kitchenTaskManagement.showEventList();
    }

    @FXML
    private void addKitchenTaskPressed() throws UseCaseLogicException {
        ObservableList<KitchenItem> choices = FXCollections.observableArrayList(KitchenItem.loadAllKitchenItems());
        ChoiceDialog<KitchenItem> dialog = new ChoiceDialog<>();
        for (KitchenItem r : choices)
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
        // Non implementata la difficoltà
       /* KitchenTaskManager kitchenTaskManager = CatERing.getInstance().getKitchenTaskManager();
        kitchenTaskManager.sortKitchenTasks(KitchenTask.DIFFICULT_FIRST);
        kitchenTaskList.getItems().sort(KitchenTask.DIFFICULT_FIRST);*/
    }

    @FXML
    private void ordinaTempiPressed() throws UseCaseLogicException {
        CatERing.getInstance().getKitchenTaskManager().sortKitchenTasks(KitchenTask.LONG_FIRST);
        kitchenTaskList.getItems().sort(KitchenTask.LONG_FIRST);
    }

    @FXML
    private void ordinaNonCompletatoDaPrepararePressed() throws UseCaseLogicException {
        CatERing.getInstance().getKitchenTaskManager().sortKitchenTasks(KitchenTask.TO_PREPARE_NOT_COMPLETED_FIRST);
        kitchenTaskList.getItems().sort(KitchenTask.TO_PREPARE_NOT_COMPLETED_FIRST);
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
        assegnaButton.disableProperty().unbind();
        assegnaButton.setDisable(true);
        bindAssegnaButton();
    }

    private void bindAssegnaButton() {
        assegnaButton.disableProperty().bind(selectTurn.getSelectionModel().selectedItemProperty().isEqualTo(kitchenTaskList.getSelectionModel().selectedItemProperty().getValue().getTurn())
                .and(selectCook.getSelectionModel().selectedItemProperty().isEqualTo(kitchenTaskList.getSelectionModel().selectedItemProperty().getValue().getCook()))
                .and(completatoCheckBox.selectedProperty().asString().isEqualTo(Boolean.toString(kitchenTaskList.getSelectionModel().selectedItemProperty().getValue().isCompleted())))
                .and(daPreparareCheckBox.selectedProperty().asString().isEqualTo(Boolean.toString(kitchenTaskList.getSelectionModel().selectedItemProperty().getValue().getToPrepare())))
                .and(quantityField.textProperty().isEqualTo(kitchenTaskList.getSelectionModel().selectedItemProperty().getValue().getProductQuantity()))
                .and(timeField.valueProperty().isEqualTo(kitchenTaskList.getSelectionModel().selectedItemProperty().getValue().getPreparationTime())));
    }

    public void setMenuManagementController(KitchenTaskManagement kitchenTaskManagement) {
        this.kitchenTaskManagement = kitchenTaskManagement;
    }
}
