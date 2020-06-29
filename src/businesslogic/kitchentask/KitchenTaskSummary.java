package businesslogic.kitchentask;

import businesslogic.recipe.KitchenItem;
import businesslogic.turn.Turn;
import businesslogic.user.User;
import persistence.BatchUpdateHandler;
import persistence.PersistenceManager;
import persistence.ResultHandler;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Comparator;

public class KitchenTaskSummary {

    private int id;
    private ArrayList<KitchenTask> kitchenTasks;

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

    public void assignKitchenTask(KitchenTask kitchenTask, Turn turn, User cook, int time, String quantity, boolean toPrepare, boolean isCompleted) {
        kitchenTask.setTurn(turn);
        kitchenTask.setCook(cook);
        kitchenTask.setPreparationTime(time);
        kitchenTask.setProductQuantity(quantity);
        kitchenTask.setToPrepare(toPrepare);
        kitchenTask.setIsCompleted(isCompleted);
    }

    public ArrayList<KitchenTask> getKitchenTasks() {
        return kitchenTasks;
    }

    @Override
    public String toString() {
        return "Foglio: " + kitchenTasks.size() + " compiti";
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    // STATIC METHODS FOR PERSISTENCE

    public static void saveNewKitchenTaskSummary(int service_id, KitchenTaskSummary kitchenTaskSummary) {
        String summaryInsert = "INSERT INTO KitchenTaskSummaries (service_id) VALUES (?);";
        PersistenceManager.executeBatchUpdate(summaryInsert, 1, new BatchUpdateHandler() {
            @Override
            public void handleBatchItem(PreparedStatement ps, int batchCount) throws SQLException {
                ps.setInt(1, service_id);
            }

            @Override
            public void handleGeneratedIds(ResultSet rs, int count) throws SQLException {
                // solo uno
                if (count == 0) {
                    kitchenTaskSummary.id = rs.getInt(1);
                }
            }
        });
    }

    public static KitchenTaskSummary loadKitchenTaskSummaryForService(int service_id) {
        KitchenTaskSummary result = new KitchenTaskSummary();
        String kitchenTaskSummaryQuery = "SELECT * FROM KitchenTaskSummaries WHERE service_id='" + service_id + "'";
        PersistenceManager.executeQuery(kitchenTaskSummaryQuery, new ResultHandler() {
            @Override
            public void handle(ResultSet rs) throws SQLException {
                result.id = rs.getInt(1);
            }
        });
        if (result.getId() == 0)
            return null;

        result.kitchenTasks = KitchenTask.loadKitchenTasksFromSummary(result.getId());

        return result;
    }
}
