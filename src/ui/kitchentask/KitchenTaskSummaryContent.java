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
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;

import java.util.ArrayList;
import java.util.Optional;

public class KitchenTaskSummaryContent {
    private KitchenTaskManagement kitchenTaskManagement;
    private ObservableList<KitchenTask> kitchenTasks;
    private ArrayList<KitchenItem> serviceMenuKitchenItems;
    private ServiceInfo service;
    private SimpleIntegerProperty preparationTime;

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
    private Spinner<Integer> minutiField;
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
    @FXML
    private Spinner<Integer> oreField;

    public void initialize(ServiceInfo service) {
        this.service = service;
        serviceMenuKitchenItems = service.getMenuKitchenItems();
        dettaglioServizioLabel.setText(service.toString());
        selectTurn.setItems(FXCollections.observableList(TurnBoard.getInstance().getTurns()));
        ObservableList<User> cooks = FXCollections.observableList(User.loadCooks());

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
                selectCook.setItems(cooks);
                completatoCheckBox.setSelected(false);
                daPreparareCheckBox.setSelected(false);
                quantityField.setText("");
                oreField.getValueFactory().setValue(0);
                minutiField.getValueFactory().setValue(0);
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
            oreField.getValueFactory().setValue(kt.getPreparationTime() / 60);
            minutiField.getValueFactory().setValue(kt.getPreparationTime() % 60);

            assignKitchenTaskPane.setVisible(true);
            deleteKitchenTaskButton.setDisable(false);
            bindAssegnaButton();
        });


        selectTurn.getSelectionModel().selectedItemProperty().addListener((obs, old, turn) -> {
            if (turn == null)
                selectCook.setItems(FXCollections.emptyObservableList());
            else
                selectCook.setItems(cooks.filtered(user -> user.isAvailableIn(turn) || user.isChef() || user.isManager()));
        });

        completatoCheckBox.setDisable(true);
        completatoCheckBox.disableProperty().bind(daPreparareCheckBox.selectedProperty().not()
                .or(selectCook.getSelectionModel().selectedItemProperty().isNull()));

        assegnaButton.setDisable(true);

        preparationTime = new SimpleIntegerProperty();
        oreField.valueProperty().addListener((obs, old, ore) -> preparationTime.setValue(ore * 60 + minutiField.getValue()));
        minutiField.valueProperty().addListener((obs, old, minuti) -> preparationTime.setValue(oreField.getValue() * 60 + minuti));
    }

    @FXML
    private void exitButtonPressed() {
        kitchenTaskManagement.showEventList();
    }

    @FXML
    private void addKitchenTaskPressed() throws UseCaseLogicException {
        ObservableList<KitchenItem> choices = FXCollections.observableArrayList(serviceMenuKitchenItems);
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

        dettaglioServizioLabel.setText(service.toString());
    }

    @FXML
    private void deleteKitchenTaskPressed() throws UseCaseLogicException {
        KitchenTask kitchenTask = kitchenTaskList.getSelectionModel().getSelectedItem();
        CatERing.getInstance().getKitchenTaskManager().removeKitchenTask(kitchenTask);
        kitchenTasks.remove(kitchenTask);

        dettaglioServizioLabel.setText(service.toString());
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
        int preparationTime = oreField.getValue() * 60 + minutiField.getValue();
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
                .and(preparationTime.isEqualTo(kitchenTaskList.getSelectionModel().getSelectedItem().getPreparationTime())));
    }

    public void setMenuManagementController(KitchenTaskManagement kitchenTaskManagement) {
        this.kitchenTaskManagement = kitchenTaskManagement;
    }
}
