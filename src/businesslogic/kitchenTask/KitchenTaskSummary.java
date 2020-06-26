package businesslogic.kitchenTask;

import businesslogic.recipe.KitchenItem;
import businesslogic.turn.Turn;
import businesslogic.user.User;

import java.util.ArrayList;
import java.util.Comparator;

public class KitchenTaskSummary {
    private final ArrayList<KitchenTask> kitchenTasks;

    public KitchenTaskSummary() {
        this.kitchenTasks = new ArrayList<>();
    }

    public KitchenTask addKitchenTask(KitchenItem kitchenItem) {
        KitchenTask kitchenTask = new KitchenTask(kitchenItem);
        this.kitchenTasks.add(kitchenTask);

        return kitchenTask;
    }

    public void sortKitchenTasks(Comparator<KitchenTask> ordering) {
        this.kitchenTasks.sort(ordering);
    }

    public boolean hasKitchenTask(KitchenTask kitchenTask) {
        return kitchenTasks.contains(kitchenTask);
    }

    public void removeKitchenTask(KitchenTask kitchenTask) {
        kitchenTasks.remove(kitchenTask);
    }

    public void assignKitchenTask(KitchenTask kitchenTask, Turn turn, User cook, int time, String quantity) {
        kitchenTask.setTurn(turn);
        kitchenTask.setCook(cook);
        kitchenTask.setPreparationTime(time);
        kitchenTask.setProductQuantity(quantity);
    }
}
