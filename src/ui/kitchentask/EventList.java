package ui.kitchentask;

import businesslogic.CatERing;
import businesslogic.UseCaseLogicException;
import businesslogic.event.EventInfo;
import businesslogic.event.EventItemInfo;
import businesslogic.event.ServiceInfo;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;

public class EventList {

    private KitchenTaskManagement kitchenTaskManagementController;
    private TreeItem<EventItemInfo> currentTreeItem;

    @FXML
    public TreeView<EventItemInfo> eventList;
    @FXML
    private Button creaButton;
    @FXML
    private Button apriButton;

    @FXML
    private void creaButtonPressed() {
        try {
            currentTreeItem = eventList.getSelectionModel().getSelectedItem();
            CatERing.getInstance().getKitchenTaskManager().createKitchenTaskSummary((ServiceInfo) currentTreeItem.getValue());
            kitchenTaskManagementController.showCurrentKitchenTaskSummary((ServiceInfo) currentTreeItem.getValue());
        } catch (UseCaseLogicException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void apriButtonPressed() {
        try {
            currentTreeItem = eventList.getSelectionModel().getSelectedItem();
            CatERing.getInstance().getKitchenTaskManager().selectKitchenTaskSummary((ServiceInfo) currentTreeItem.getValue());
            kitchenTaskManagementController.showCurrentKitchenTaskSummary((ServiceInfo) currentTreeItem.getValue());
        } catch (UseCaseLogicException ex) {
            ex.printStackTrace();
        }
    }

    public void setParent(KitchenTaskManagement kitchenTaskManagement) {
        kitchenTaskManagementController = kitchenTaskManagement;
    }

    public void initialize() {
        ObservableList<EventInfo> all = CatERing.getInstance().getEventManager().getEventInfo();
        eventList.setShowRoot(false);
        TreeItem<EventItemInfo> root = new TreeItem<>(null);

        for (EventInfo e : all) {
            TreeItem<EventItemInfo> node = new TreeItem<>(e);
            root.getChildren().add(node);
            ObservableList<ServiceInfo> serv = e.getServices();
            for (ServiceInfo s : serv) {
                node.getChildren().add(new TreeItem<>(s));
            }
        }

        eventList.getSelectionModel().selectedItemProperty().addListener((obs, oldV, newV) -> {
            if (newV == null)
                return;
            apriButton.setDisable(!(newV.getValue() instanceof ServiceInfo)
                    || ((ServiceInfo) newV.getValue()).getKitchenTaskSummary() == null);
            creaButton.setDisable(!(newV.getValue() instanceof ServiceInfo)
                    || ((ServiceInfo) newV.getValue()).getKitchenTaskSummary() != null);
        });

        eventList.setRoot(root);
    }

    @FXML
    private void fineButtonPressed() {
        kitchenTaskManagementController.endKitchenTaskManagement();
    }
}
