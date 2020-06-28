package businesslogic.kitchentask;

import businesslogic.event.ServiceInfo;

public interface KitchenTaskEventReceiver {
    void updateKitchenTaskSummaryCreated(ServiceInfo service, KitchenTaskSummary kitchenTaskSummary);

    void updateKitchenTaskAdded(KitchenTaskSummary kitchenTaskSummary, KitchenTask kitchenTask);

    void updateKitchenTaskRemoved(KitchenTaskSummary kitchenTaskSummary, KitchenTask kitchenTask);

    void updateKitchenTaskAssigned(KitchenTask kitchenTask);
}
