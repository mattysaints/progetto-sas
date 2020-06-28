package businesslogic.kitchentask;

import businesslogic.menu.MenuItem;
import businesslogic.menu.Section;
import businesslogic.recipe.KitchenItem;
import businesslogic.turn.Turn;
import businesslogic.user.User;
import persistence.BatchUpdateHandler;
import persistence.PersistenceManager;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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

    public ArrayList<KitchenTask> getKitchenTasks() {
        return kitchenTasks;
    }

    @Override
    public String toString() {
        return "Foglio: " + kitchenTasks.size() + " compiti";
    }

    public static void saveKitchenTaskSummary(KitchenTaskSummary kitchenTaskSummary) {
        String summaryInsert = "INSERT INTO catering.KitchenTaskSummary (.....) VALUES (?, ?, ?);";
        int[] result = PersistenceManager.executeBatchUpdate(summaryInsert, 1, new BatchUpdateHandler() {
            @Override
            public void handleBatchItem(PreparedStatement ps, int batchCount) throws SQLException {
                //Setta parametri query
            }

            @Override
            public void handleGeneratedIds(ResultSet rs, int count) throws SQLException {
                // ottenere id summary
                if (count == 0) {
                    //assegnare id se necessario
                }
            }
        });
    }


}
