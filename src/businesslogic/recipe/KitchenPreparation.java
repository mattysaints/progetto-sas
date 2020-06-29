package businesslogic.recipe;

public class KitchenPreparation implements KitchenItem{

    private String name;
    private int id;

    @Override
    public int getDifficulty() {
        return 0;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return "KitchenPreparation{" +
                "name='" + name + '\'' +
                '}';
    }

}
