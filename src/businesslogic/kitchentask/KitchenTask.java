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

public class KitchenTask {
    // Some default KitchenTask comparators
    public static final Comparator<KitchenTask> TO_PREPARE_NOT_COMPLETED_FIRST =
            Comparator.comparing(KitchenTask::getToPrepare).reversed()
                    .thenComparing(KitchenTask::isCompleted);
    public static final Comparator<KitchenTask> DIFFICULT_FIRST =
            TO_PREPARE_NOT_COMPLETED_FIRST
                    .thenComparingInt((KitchenTask kt) -> kt.getKitchenItem().getDifficulty())
                    .thenComparing(KitchenTask::getPreparationTime);
    public static final Comparator<KitchenTask> LONG_FIRST =
            TO_PREPARE_NOT_COMPLETED_FIRST
                    .thenComparing(KitchenTask::getPreparationTime)
                    .thenComparingInt((KitchenTask kt) -> kt.getKitchenItem().getDifficulty());

    private Turn turn;
    private User cook;
    private int preparationTime;
    private String productQuantity;
    private boolean toPrepare;
    private boolean isCompleted;
    private KitchenItem kitchenItem;
    private int id;


    public KitchenTask(KitchenItem kitchenItem) {
        this.setKitchenItem(kitchenItem);
    }

    public void setKitchenItem(KitchenItem kitchenItem) {
        this.kitchenItem = kitchenItem;
    }

    public KitchenItem getKitchenItem() {
        return kitchenItem;
    }

    public int getPreparationTime() {
        return preparationTime;
    }

    public boolean getToPrepare() {
        return toPrepare;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public Turn getTurn() {
        return turn;
    }

    public void setTurn(Turn turn) {
        this.turn = turn;
    }

    public User getCook() {
        return cook;
    }

    public void setCook(User cook) {
        this.cook = cook;
    }

    public void setPreparationTime(int preparationTime) {
        this.preparationTime = preparationTime;
    }

    public String getProductQuantity() {
        return productQuantity;
    }

    public void setProductQuantity(String productQuantity) {
        this.productQuantity = productQuantity;
    }

    private int getId() {
        return id;
    }

    @Override
    public String toString() {
        String result = kitchenItem.toString() + " (";
        if (!toPrepare)
            result += "non ";
        result += "da prerarare, ";

        if (!isCompleted)
            result += "non ";
        result += "completato)";
        return result;
    }

    // STATIC METHODS FOR PERSISTENCE

    public static void updateKitchenTask(KitchenTask kitchenTask) {
        // TODO
    }

    public static void removeKitchenTask(KitchenTask kitchenTask) {
        String delTurns = "DELETE FROM KitchenTasksTurnAssignment WHERE kitchen_task_id='" + kitchenTask.getId() + "'";
        PersistenceManager.executeUpdate(delTurns);

        String delCooks = "DELETE FROM KitchenTasksCookAssignment WHERE kitchen_task_id='" + kitchenTask.getId() + "'";
        PersistenceManager.executeUpdate(delCooks);

        String removeKitchenTask = "DELETE FROM KitchenTasks WHERE id='" + kitchenTask.getId() + "'";
        PersistenceManager.executeUpdate(removeKitchenTask);
    }

    public static void saveNewKitchenTask(int kitchenTaskSummary_id, KitchenTask kitchenTask) {
        String newKitchenTaskQuery = "INSERT INTO KitchenTasks (kitchen_task_summary_id, recipe_id) VALUES (?,?)";
        PersistenceManager.executeBatchUpdate(newKitchenTaskQuery, 1, new BatchUpdateHandler() {
            @Override
            public void handleBatchItem(PreparedStatement ps, int batchCount) throws SQLException {
                ps.setInt(1, kitchenTaskSummary_id);
                ps.setInt(2, kitchenTask.getKitchenItem().getId());
            }

            @Override
            public void handleGeneratedIds(ResultSet rs, int count) throws SQLException {
                // should be only one
                if (count == 0) {
                    kitchenTask.id = rs.getInt(1);
                }
            }
        });
    }


    public static ArrayList<KitchenTask> loadKitchenTasksFromSummary(int summary_id) {
        ArrayList<KitchenTask> result = new ArrayList<>();
        String kitchenTasksQuery = "SELECT * FROM KitchenTasks WHERE kitchen_task_summary_id = '" + summary_id + "'";
        PersistenceManager.executeQuery(kitchenTasksQuery, new ResultHandler() {
            @Override
            public void handle(ResultSet rs) throws SQLException {
                KitchenItem item = KitchenItem.loadKitchenItem(rs.getInt(7));
                KitchenTask kt = new KitchenTask(item);
                kt.id = rs.getInt(1);
                kt.preparationTime = rs.getInt(2);
                kt.productQuantity = rs.getString(3);
                kt.toPrepare = rs.getBoolean(4);
                kt.isCompleted = rs.getBoolean(5);
                result.add(kt);
            }
        });
        return result;
    }

}
