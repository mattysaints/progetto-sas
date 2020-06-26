package ui.kitchentask;

import businesslogic.CatERing;
import businesslogic.UseCaseLogicException;
import businesslogic.event.EventInfo;
import businesslogic.event.EventItemInfo;
import businesslogic.event.ServiceInfo;
import businesslogic.menu.Menu;
import businesslogic.menu.MenuException;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import ui.menu.MenuManagement;

public class EventList {

    private KitchenTaskManagement kitchenTaskManagementController;

    @FXML
    public TreeView<EventItemInfo> eventList;

    public void createKitchenTaskSummary(ActionEvent actionEvent) {
    }

    public void openKitchenTaskSummary(ActionEvent actionEvent) {
        try {
            EventItemInfo eventItemInfo = eventList.getSelectionModel().getSelectedItem().getValue();
            CatERing.getInstance().getKitchenTaskManager().selectKitchenTaskSummary((ServiceInfo) eventItemInfo);
            kitchenTaskManagementController.showCurrentKitchenTaskSummary();
        } catch (UseCaseLogicException ex) {
            ex.printStackTrace();
        }
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

        eventList.setRoot(root);
    }

}
