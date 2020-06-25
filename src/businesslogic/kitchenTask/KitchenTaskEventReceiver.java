package businesslogic.kitchenTask;

public interface KitchenTaskEventReceiver {
    void updateKitchenTaskAdded(KitchenTaskSummary kitchenTaskSummary, KitchenTask kitchenTask);
}
