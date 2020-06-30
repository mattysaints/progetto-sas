package ui.kitchentask;

import businesslogic.CatERing;
import businesslogic.event.ServiceInfo;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import ui.Main;

import java.io.IOException;

public class KitchenTaskManagement {
    Main mainPaneController;
    BorderPane kitchenTaskContentPane;
    KitchenTaskSummaryContent kitchenTaskSummaryContentPaneController;

    @FXML
    EventList eventListPaneController;
    @FXML
    private BorderPane containerPane;
    @FXML
    private Label userLabel;
    @FXML
    private BorderPane eventListPane;

    public void setMainPaneController(Main main) {
        mainPaneController = main;
    }

    public void endKitchenTaskManagement() {
        mainPaneController.showStartPane();
    }

    public void initialize() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("kitchentasksummary-content.fxml"));
        try {
            kitchenTaskContentPane = loader.load();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        kitchenTaskSummaryContentPaneController = loader.getController();
        kitchenTaskSummaryContentPaneController.setMenuManagementController(this);

        if (CatERing.getInstance().getUserManager().getCurrentUser() != null) {
            String uname = CatERing.getInstance().getUserManager().getCurrentUser().getUserName();
            userLabel.setText(uname);
        }

        eventListPaneController.setParent(this);
    }

    public void showCurrentKitchenTaskSummary(ServiceInfo service) {
        kitchenTaskSummaryContentPaneController.initialize(service);
        containerPane.setCenter(kitchenTaskContentPane);
    }

    public void showEventList() {
        eventListPaneController.initialize();
        containerPane.setCenter(eventListPane);
    }
}
