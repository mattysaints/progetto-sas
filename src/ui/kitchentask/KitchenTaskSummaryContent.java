package ui.kitchentask;

import businesslogic.CatERing;
import javafx.fxml.FXML;

public class KitchenTaskSummaryContent {
    private KitchenTaskManagement kitchenTaskManagement;

    public void initialize() {

    }

    @FXML
    private void exitButtonPressed() {
        kitchenTaskManagement.showEventList(CatERing.getInstance().getKitchenTaskManager().getCurrentKitchenTaskSummary());
    }

    @FXML
    private void addKitchenTaskPressed() {
    }

    @FXML
    private void deleteKitchenTaskPressed() {
    }

    @FXML
    private void editKitchenTaskPressed() {
    }

    @FXML
    private void ordinaDifficoltaPressed() {
    }

    @FXML
    private void ordinaTempiPressed() {
    }

    @FXML
    private void modificaItemPressed() {
    }

    public void setMenuManagementController(KitchenTaskManagement kitchenTaskManagement) {
        this.kitchenTaskManagement = kitchenTaskManagement;
    }
}
