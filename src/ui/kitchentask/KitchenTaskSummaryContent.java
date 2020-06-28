package ui.kitchentask;

import businesslogic.CatERing;
import businesslogic.UseCaseLogicException;
import businesslogic.kitchentask.KitchenTask;
import businesslogic.kitchentask.KitchenTaskManager;
import businesslogic.kitchentask.KitchenTaskSummary;
import businesslogic.recipe.KitchenItem;
import businesslogic.recipe.Recipe;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.ListView;

import java.util.Optional;

public class KitchenTaskSummaryContent {
    @FXML
    public ListView<KitchenTask> kitchenTaskList;
    @FXML
    public Button deleteSectionButton;
    @FXML
    public ListView<String> itemsList;

    private KitchenTaskManagement kitchenTaskManagement;

    public void initialize() {

        KitchenTaskSummary summary = CatERing.getInstance().getKitchenTaskManager().getCurrentKitchenTaskSummary();
        if(summary!=null) {kitchenTaskList.getItems().addAll(summary.getKitchenTasks());}
        kitchenTaskList.getSelectionModel().selectedItemProperty().addListener((obs, oldV, newV) -> {
            if (newV == null)
                return;

            deleteSectionButton.setDisable(false);
            itemsList.getItems().setAll(newV.toString());
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
        if (result.isPresent())
            CatERing.getInstance().getKitchenTaskManager().addKitchenTask(result.get());
        kitchenTaskList.refresh();
    }

    @FXML
    private void deleteKitchenTaskPressed() throws UseCaseLogicException {
        KitchenTaskManager kitchenTaskManager= CatERing.getInstance().getKitchenTaskManager();
        kitchenTaskManager.removeKitchenTask(kitchenTaskList.getSelectionModel().getSelectedItem());
        kitchenTaskList.refresh();
        //kitchenTaskList.getItems().remove(kitchenTaskList.getSelectionModel().getSelectedItem());
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
    private void modificaItemPressed() throws UseCaseLogicException{
        KitchenTaskManager kitchenTaskManager = CatERing.getInstance().getKitchenTaskManager();
        KitchenTask kitchenTask = kitchenTaskList.getSelectionModel().getSelectedItem();
        //kitchenTaskManager.assignKitchenTask(kitchenTask, , , , ,);
        kitchenTaskList.refresh();
        itemsList.refresh();
    }

    public void setMenuManagementController(KitchenTaskManagement kitchenTaskManagement) {
        this.kitchenTaskManagement = kitchenTaskManagement;
    }
}
