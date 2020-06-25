package businesslogic.kitchenTask;

import businesslogic.CatERing;
import businesslogic.UseCaseLogicException;
import businesslogic.event.ServiceInfo;
import businesslogic.recipe.KitchenItem;
import businesslogic.user.User;

import java.util.ArrayList;
import java.util.Comparator;

public class KitchenTaskManager {
    private KitchenTaskSummary currentKitchenTaskSummary;
    private final ArrayList<KitchenTaskEventReceiver> eventReceivers;

    public KitchenTaskManager() {
        this.eventReceivers = new ArrayList<>();
    }

    public KitchenTaskSummary selectKitchenTaskSummary(ServiceInfo serviceInfo) throws UseCaseLogicException {
        User user = CatERing.getInstance().getUserManager().getCurrentUser();
        if (!user.isChef() || serviceInfo.getKitchenTaskSummary() == null)
            throw new UseCaseLogicException();

        this.setCurrentKitchenTaskSummary(serviceInfo.getKitchenTaskSummary());
        return currentKitchenTaskSummary;
    }

    public void setCurrentKitchenTaskSummary(KitchenTaskSummary currentKitchenTaskSummary) {
        this.currentKitchenTaskSummary = currentKitchenTaskSummary;
    }

    public KitchenTask addKitchenTask(KitchenItem kitchenItem) throws UseCaseLogicException {
        if (currentKitchenTaskSummary == null)
            throw new UseCaseLogicException();

        KitchenTask kitchenTask = currentKitchenTaskSummary.addKitchenTask(kitchenItem);
        this.notifyKitchenTaskAdded(currentKitchenTaskSummary, kitchenTask);

        return kitchenTask;
    }

    public KitchenTaskSummary sortKitchenTasks(Comparator<KitchenTask> ordering) throws UseCaseLogicException {
        if (currentKitchenTaskSummary == null)
            throw new UseCaseLogicException();

        currentKitchenTaskSummary.sortKitchenTasks(ordering);
        return currentKitchenTaskSummary;
    }

    private void notifyKitchenTaskAdded(KitchenTaskSummary kitchenTaskSummary, KitchenTask kitchenTask) {
        for (KitchenTaskEventReceiver er : eventReceivers)
            er.updateKitchenTaskAdded(kitchenTaskSummary, kitchenTask);
    }
}
