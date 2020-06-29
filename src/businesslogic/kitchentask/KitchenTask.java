package businesslogic.kitchentask;

import businesslogic.recipe.KitchenItem;
import businesslogic.turn.Turn;
import businesslogic.user.User;
import persistence.BatchUpdateHandler;
import persistence.PersistenceManager;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
    private boolean completed;
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
        return completed;
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

    @Override
    public String toString() {
        return "KitchenTask{" +
                "turn=" + turn +
                ", cook=" + cook +
                ", preparationTime=" + preparationTime +
                ", productQuantity='" + productQuantity + '\'' +
                ", toPrepare=" + toPrepare +
                ", completed=" + completed +
                ", kitchenItem=" + kitchenItem +
                '}';
    }

    // STATIC METHODS FOR PERSISTENCE

    public static void updateKitchenTask(KitchenTask kitchenTask) {
        // TODO
    }

    public static void removeKitchenTask(int kitchenTaskSummary_id, KitchenTask kitchenTask) {
        // TODO
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
                    kitchenTask.id = rs.getInt("id");
                }
            }
        });
    }
}
