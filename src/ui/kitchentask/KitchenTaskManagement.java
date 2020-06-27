package ui.kitchentask;

import businesslogic.CatERing;
import businesslogic.kitchentask.KitchenTaskManager;
import javafx.fxml.FXML;
import ui.Main;

public class KitchenTaskManagement {
    Main mainPaneController;

    @FXML
    EventList eventListPaneController;

    public void setMainPaneController(Main main) {
        mainPaneController = main;
    }

    public void endKitchenTaskManagement() {
        mainPaneController.showStartPane();
    }

    public void initialize() {
        eventListPaneController.setParent(this);
    }

    public void showCurrentKitchenTaskSummary() {
        KitchenTaskManager kt = CatERing.getInstance().getKitchenTaskManager();
        return;
    }
}
