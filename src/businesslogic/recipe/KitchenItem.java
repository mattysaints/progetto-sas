package businesslogic.recipe;

import persistence.PersistenceManager;
import persistence.ResultHandler;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface KitchenItem {

    int getDifficulty();

    void setName(String string);

    String getName();

    void setId(int id);

    int getId();

    // STATIC METHOS FOR PERSISTENCE

    static KitchenItem loadKitchenItem(int item_id) {
        KitchenPreparation preparation = new KitchenPreparation(null);
        Recipe recipe = new Recipe(null);
        String kitchenItemQuery = "SELECT * FROM Recipes WHERE id = '" + item_id + "'";
        PersistenceManager.executeQuery(kitchenItemQuery, new ResultHandler() {
            @Override
            public void handle(ResultSet rs) throws SQLException {
                if (rs.getBoolean(3)) {
                    preparation.setId(rs.getInt(1));
                    preparation.setName(rs.getString(2));
                } else {
                    recipe.setId(rs.getInt(1));
                    recipe.setName(rs.getString(2));
                }
            }
        });

        if (preparation.getId() == 0)
            return recipe;
        else
            return preparation;
    }
}
