package businesslogic.kitchentask;

import businesslogic.CatERing;
import businesslogic.StaffException;
import businesslogic.TurnException;
import businesslogic.UseCaseLogicException;
import businesslogic.event.ServiceInfo;
import businesslogic.recipe.KitchenItem;
import businesslogic.turn.Turn;
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

    public KitchenTaskSummary createKitchenTaskSummary(ServiceInfo service) throws UseCaseLogicException {
        User user = CatERing.getInstance().getUserManager().getCurrentUser();
        if(service.getKitchenTaskSummary()!=null && user.isChef())
            throw new UseCaseLogicException();

        KitchenTaskSummary summary = service.createKitchenTaskSummary();
        this.notifyKitchenTaskSummeryCreated(summary);
        return summary;
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

    public void removeKitchenTask(KitchenTask kitchenTask) throws UseCaseLogicException {
        if(currentKitchenTaskSummary == null && !currentKitchenTaskSummary.hasKitchenTask(kitchenTask))
            throw new UseCaseLogicException();
        currentKitchenTaskSummary.removeKitchenTask(kitchenTask);
        this.notifyKitchenTaskRemoved(currentKitchenTaskSummary,kitchenTask);
    }

    public KitchenTaskSummary sortKitchenTasks(Comparator<KitchenTask> ordering) throws UseCaseLogicException {
        if (currentKitchenTaskSummary == null)
            throw new UseCaseLogicException();

        currentKitchenTaskSummary.sortKitchenTasks(ordering);
        return currentKitchenTaskSummary;
    }

    public void assignKitchenTask(KitchenTask kitchenTask, Turn turn, User cook, int time, String quantity) throws UseCaseLogicException, TurnException, StaffException {
        if (currentKitchenTaskSummary == null)
            throw new UseCaseLogicException();
        if (turn.isFull())
            throw new TurnException();
        if (!cook.isAvailable(turn))
            throw new StaffException();

        currentKitchenTaskSummary.assignKitchenTask(kitchenTask, turn, cook, time, quantity);
        this.notifyKitchenTaskAssigned(kitchenTask);
    }

    private void notifyKitchenTaskAdded(KitchenTaskSummary kitchenTaskSummary, KitchenTask kitchenTask) {
        for (KitchenTaskEventReceiver er : eventReceivers)
            er.updateKitchenTaskAdded(kitchenTaskSummary, kitchenTask);
    }

    private void notifyKitchenTaskSummeryCreated(KitchenTaskSummary summary) {
        for (KitchenTaskEventReceiver er : eventReceivers)
            er.updateKitchenTaskAdded(summary);
    }

    private void notifyKitchenTaskRemoved(KitchenTaskSummary currentKitchenTaskSummary, KitchenTask kitchenTask) {
        for (KitchenTaskEventReceiver er : eventReceivers)
            er.updateKitchenTaskRemoved(currentKitchenTaskSummary,kitchenTask);
    }

    private void notifyKitchenTaskAssigned(KitchenTask kitchenTask) {
        for (KitchenTaskEventReceiver er : eventReceivers)
            er.updateKitchenTaskAssigned(kitchenTask);
    }

}
