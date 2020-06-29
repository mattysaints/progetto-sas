package businesslogic.recipe;

public class KitchenPreparation implements KitchenItem {

    private String name;
    private int id;

    public KitchenPreparation(String name) {
        this.name = name;
    }

    @Override
    public int getDifficulty() {
        return 0;
    }

    @Override
    public void setName(String string) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setId(int id) {
        this.id = id;
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
