package businesslogic.kitchentask;

import businesslogic.recipe.KitchenItem;
import businesslogic.turn.Turn;
import businesslogic.user.User;

import java.util.Comparator;

public class KitchenTask {
    // Some default KitchenTask comparators
    public static final Comparator<KitchenTask> TO_PREPARE_NOT_COMPLETED_FIRST =
            Comparator.comparing(KitchenTask::getToPrepare).reversed()
                    .thenComparing(KitchenTask::isCompleted);
    public static final Comparator<KitchenTask> DIFFICULT_FIRST =
            TO_PREPARE_NOT_COMPLETED_FIRST
                    .thenComparingInt((KitchenTask kt) -> kt.getKitchenItem().getDifficulty())
                    .thenComparing((KitchenTask kt) -> kt.getPreparationTime());
    public static final Comparator<KitchenTask> LONG_FIRST =
            TO_PREPARE_NOT_COMPLETED_FIRST
                    .thenComparing((KitchenTask kt) -> kt.getPreparationTime())
                    .thenComparingInt((KitchenTask kt) -> kt.getKitchenItem().getDifficulty());

    private Turn turn;
    private User cook;
    private int preparationTime;
    private String productQuantity;
    private boolean toPrepare;
    private boolean completed;
    private KitchenItem kitchenItem;

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
}
