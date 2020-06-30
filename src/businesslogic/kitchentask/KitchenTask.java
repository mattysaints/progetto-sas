package businesslogic.kitchentask;

import businesslogic.recipe.KitchenItem;
import businesslogic.turn.Turn;
import businesslogic.turn.TurnBoard;
import businesslogic.user.User;
import persistence.BatchUpdateHandler;
import persistence.PersistenceManager;
import persistence.ResultHandler;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

public class KitchenTask {
    // Some default KitchenTask comparators
    public static final Comparator<KitchenTask> TO_PREPARE_NOT_COMPLETED_FIRST =
            Comparator.comparing(KitchenTask::getToPrepare).reversed()
                    .thenComparing(KitchenTask::isCompleted);
    public static final Comparator<KitchenTask> DIFFICULT_FIRST =
            TO_PREPARE_NOT_COMPLETED_FIRST.thenComparingInt((KitchenTask kt) -> kt.getKitchenItem().getDifficulty()).reversed();
    public static final Comparator<KitchenTask> LONG_FIRST =
            TO_PREPARE_NOT_COMPLETED_FIRST.thenComparing(Comparator.comparing(KitchenTask::getPreparationTime).reversed());

    private Turn turn;
    private User cook;
    private int preparationTime;
    private String productQuantity;
    private boolean toPrepare;
    private boolean isCompleted;
    private KitchenItem kitchenItem;
    private int id;


    public KitchenTask(KitchenItem kitchenItem) {
        toPrepare = true;
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
        String updateKitchenTaskQuery = "UPDATE KitchenTasks SET preparation_time=?, product_quantity=?, to_prepare=?, is_completed=? WHERE id=?";
        PersistenceManager.executeBatchUpdate(updateKitchenTaskQuery, 1, new BatchUpdateHandler() {
            @Override
            public void handleBatchItem(PreparedStatement ps, int batchCount) throws SQLException {
                ps.setInt(1, kitchenTask.preparationTime);
                ps.setString(2, kitchenTask.productQuantity);
                ps.setBoolean(3, kitchenTask.toPrepare);
                ps.setBoolean(4, kitchenTask.isCompleted);
                ps.setInt(5, kitchenTask.id);
            }

            @Override
            public void handleGeneratedIds(ResultSet rs, int count) throws SQLException {
            }
        });

        String deleteCookQuery = "DELETE FROM KitchenTaskCookAssignment WHERE kitchen_task_id='" + kitchenTask.getId() + "'";
        PersistenceManager.executeUpdate(deleteCookQuery);

        if (kitchenTask.getCook() != null) {
            String addCookQuery = "INSERT INTO KitchenTaskCookAssignment (kitchen_task_id, user_id) value ('" + kitchenTask.getId() + "','" + kitchenTask.getCook().getId() + "')";
            PersistenceManager.executeUpdate(addCookQuery);
        }

        String deleteTurnQuery = "DELETE FROM KitchenTaskTurnAssignment WHERE kitchen_task_id='" + kitchenTask.getId() + "'";
        PersistenceManager.executeUpdate(deleteTurnQuery);

        if (kitchenTask.getTurn() != null) {
            String addTurnQuery = "INSERT INTO KitchenTaskTurnAssignment (kitchen_task_id, turn_id) value ('" + kitchenTask.getId() + "','" + kitchenTask.getTurn().getId() + "')";
            PersistenceManager.executeUpdate(addTurnQuery);
        }
    }

    public static void removeKitchenTask(KitchenTask kitchenTask) {
        String delTurns = "DELETE FROM KitchenTaskTurnAssignment WHERE kitchen_task_id='" + kitchenTask.getId() + "'";
        PersistenceManager.executeUpdate(delTurns);

        String delCooks = "DELETE FROM KitchenTaskCookAssignment WHERE kitchen_task_id='" + kitchenTask.getId() + "'";
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
        Map<Integer, KitchenTask> result = new HashMap<>();
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
                result.put(kt.id, kt);
            }
        });

        String turnAssignmentQuery = "SELECT * FROM KitchenTaskTurnAssignment";
        PersistenceManager.executeQuery(turnAssignmentQuery, new ResultHandler() {
            @Override
            public void handle(ResultSet rs) throws SQLException {
                int kitchenTask_id = rs.getInt(1);
                int turn_id = rs.getInt(2);
                if (result.containsKey(kitchenTask_id))
                    result.get(kitchenTask_id).setTurn(TurnBoard.getInstance().getTurnById(turn_id));
            }
        });

        String cookAssignmentQuery = "SELECT * FROM KitchenTaskCookAssignment";
        PersistenceManager.executeQuery(cookAssignmentQuery, new ResultHandler() {
            @Override
            public void handle(ResultSet rs) throws SQLException {
                int kitchenTask_id = rs.getInt(1);
                int user_id = rs.getInt(2);
                if (result.containsKey(kitchenTask_id))
                    result.get(kitchenTask_id).setCook(User.loadUserById(user_id));
            }
        });

        return new ArrayList<>(result.values());
    }

    public void setToPrepare(boolean toPrepare) {
        this.toPrepare = toPrepare;
    }

    public void setIsCompleted(boolean isCompleted) {
        this.isCompleted = isCompleted;
    }
}
