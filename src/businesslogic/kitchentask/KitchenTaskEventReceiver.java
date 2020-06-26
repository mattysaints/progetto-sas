package businesslogic.kitchentask;

public interface KitchenTaskEventReceiver {
    void updateKitchenTaskAdded(KitchenTaskSummary kitchenTaskSummary, KitchenTask kitchenTask);

    void updateKitchenTaskAdded(KitchenTaskSummary summary);

    void updateKitchenTaskRemoved(KitchenTaskSummary currentKitchenTaskSummary, KitchenTask kitchenTask);

    void updateKitchenTaskAssigned(KitchenTask kitchenTask);
}
