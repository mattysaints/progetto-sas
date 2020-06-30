package businesslogic.recipe;

import persistence.PersistenceManager;
import persistence.ResultHandler;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public interface KitchenItem {

    int getDifficulty();

    void setName(String string);

    String getName();

    void setId(int id);

    int getId();

    // STATIC METHOS FOR PERSISTENCE

    static KitchenItem loadKitchenItem(int item_id) {
        ArrayList<KitchenItem> result = new ArrayList<>();
        String kitchenItemQuery = "SELECT * FROM Recipes WHERE id = '" + item_id + "'";
        PersistenceManager.executeQuery(kitchenItemQuery, new ResultHandler() {
            @Override
            public void handle(ResultSet rs) throws SQLException {
                if (rs.getBoolean(3)) {
                    KitchenPreparation prep = new KitchenPreparation(rs.getString(2));
                    prep.setId(rs.getInt(1));
                    result.add(prep);
                } else {
                    Recipe recipe = new Recipe(rs.getString(2));
                    recipe.setId(rs.getInt(1));
                    result.add(recipe);
                }
            }
        });
        return result.get(0);
    }

    static ArrayList<KitchenItem> loadAllKitchenItems() {
        ArrayList<KitchenItem> result = new ArrayList<>();
        String kitchenItemQuery = "SELECT * FROM Recipes";
        PersistenceManager.executeQuery(kitchenItemQuery, new ResultHandler() {
            @Override
            public void handle(ResultSet rs) throws SQLException {
                if (rs.getBoolean(3)) {
                    KitchenPreparation prep = new KitchenPreparation(rs.getString(2));
                    prep.setId(rs.getInt(1));
                    result.add(prep);
                } else {
                    Recipe recipe = new Recipe(rs.getString(2));
                    recipe.setId(rs.getInt(1));
                    result.add(recipe);
                }
            }
        });
        return result;
    }
}
