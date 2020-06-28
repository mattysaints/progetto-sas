package persistence;

import businesslogic.event.ServiceInfo;
import businesslogic.kitchentask.KitchenTask;
import businesslogic.kitchentask.KitchenTaskEventReceiver;
import businesslogic.kitchentask.KitchenTaskSummary;

public class KitchenTaskPersistence implements KitchenTaskEventReceiver {
    @Override
    public void updateKitchenTaskSummaryCreated(ServiceInfo service, KitchenTaskSummary kitchenTaskSummary) {
        KitchenTaskSummary.saveNewKitchenTaskSummary(service.getId(), kitchenTaskSummary);
    }

    @Override
    public void updateKitchenTaskAdded(KitchenTaskSummary kitchenTaskSummary, KitchenTask kitchenTask) {
        KitchenTask.saveNewKitchenTask(kitchenTaskSummary.getId(), kitchenTask);
    }

    @Override
    public void updateKitchenTaskRemoved(KitchenTaskSummary kitchenTaskSummary, KitchenTask kitchenTask) {
        KitchenTask.removeKitchenTask(kitchenTaskSummary.getId(), kitchenTask);
    }

    @Override
    public void updateKitchenTaskAssigned(KitchenTask kitchenTask) {
        KitchenTask.updateKitchenTask(kitchenTask);
    }
}
