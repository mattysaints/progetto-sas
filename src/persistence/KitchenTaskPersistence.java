package persistence;

import businesslogic.kitchentask.KitchenTask;
import businesslogic.kitchentask.KitchenTaskEventReceiver;
import businesslogic.kitchentask.KitchenTaskSummary;

public class KitchenTaskPersistence implements KitchenTaskEventReceiver {

    @Override
    public void updateKitchenTaskAdded(KitchenTaskSummary kitchenTaskSummary, KitchenTask kitchenTask) {

    }

    @Override
    public void updateKitchenTaskAdded(KitchenTaskSummary summary) {

    }

    @Override
    public void updateKitchenTaskRemoved(KitchenTaskSummary currentKitchenTaskSummary, KitchenTask kitchenTask) {

    }

    @Override
    public void updateKitchenTaskAssigned(KitchenTask kitchenTask) {

    }
}
