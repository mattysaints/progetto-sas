package ui.kitchentask;

import businesslogic.CatERing;
import businesslogic.UseCaseLogicException;
import businesslogic.event.EventInfo;
import businesslogic.event.EventItemInfo;
import businesslogic.event.ServiceInfo;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;

public class EventList {

    private KitchenTaskManagement kitchenTaskManagementController;

    @FXML
    public TreeView<EventItemInfo> eventList;
    @FXML
    private Button creaButton;
    @FXML
    private Button apriButton;

    @FXML
    private void createKitchenTaskSummary(ActionEvent actionEvent) {

    }

    @FXML
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


        eventList.getSelectionModel().selectedItemProperty().addListener((obs, oldV, newV) -> {
            apriButton.setDisable(!(newV.getValue() instanceof ServiceInfo)
                    || ((ServiceInfo) newV.getValue()).getKitchenTaskSummary() == null);

            creaButton.setDisable(!(newV.getValue() instanceof ServiceInfo)
                    || ((ServiceInfo) newV.getValue()).getKitchenTaskSummary() != null);
        });

        eventList.setRoot(root);
    }
}
