package businesslogic.kitchenTask;

import businesslogic.recipe.KitchenItem;

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
}
