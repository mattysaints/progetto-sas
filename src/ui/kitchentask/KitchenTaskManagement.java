package ui.kitchentask;

import ui.Main;

public class KitchenTaskManagement {
    Main mainPaneController;

    public void setMainPaneController(Main main) {
        mainPaneController = main;
    }

    public void endKitchenTaskManagement() {
        mainPaneController.showStartPane();
    }

    public void initialize() {

    }

    public void showCurrentKitchenTaskSummary() {
    }
}
