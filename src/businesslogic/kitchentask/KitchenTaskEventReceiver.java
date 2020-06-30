package businesslogic.kitchentask;

import businesslogic.event.ServiceInfo;
import businesslogic.turn.Turn;

public interface KitchenTaskEventReceiver {
    void updateKitchenTaskSummaryCreated(ServiceInfo service, KitchenTaskSummary kitchenTaskSummary);

    void updateKitchenTaskAdded(KitchenTaskSummary kitchenTaskSummary, KitchenTask kitchenTask);

    void updateKitchenTaskRemoved(KitchenTaskSummary kitchenTaskSummary, KitchenTask kitchenTask);

    void updateKitchenTaskAssigned(KitchenTask kitchenTask);

    void updateTurnSetFull(Turn turn);
}
