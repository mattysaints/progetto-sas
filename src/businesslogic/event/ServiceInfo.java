package businesslogic.event;

import businesslogic.kitchentask.KitchenTaskSummary;
import businesslogic.menu.Menu;
import businesslogic.menu.MenuItem;
import businesslogic.recipe.KitchenItem;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import persistence.PersistenceManager;
import persistence.ResultHandler;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class ServiceInfo implements EventItemInfo {
    private int id;
    private final String name;
    private Date date;
    private Time timeStart;
    private Time timeEnd;
    private int participants;
    private KitchenTaskSummary kitchenTaskSummary;
    private Menu menu;

    public ServiceInfo(String name) {
        this.name = name;
    }

    public KitchenTaskSummary getKitchenTaskSummary() {
        return kitchenTaskSummary;
    }

    public KitchenTaskSummary createKitchenTaskSummary(){
        KitchenTaskSummary kitchenTaskSummary = new KitchenTaskSummary();
        this.setKitchenTaskSummary(kitchenTaskSummary);
        return kitchenTaskSummary;
    }

    public String toString() {
        String s = name + ": " + date + " (" + timeStart + "-" + timeEnd + "), " + participants + " pp.";
        if (menu != null)
            s += " - Menu: " + menu.getTitle();
        if (kitchenTaskSummary != null)
            s += " (" + kitchenTaskSummary + ")";
        return s;
    }

    public void setKitchenTaskSummary(KitchenTaskSummary kitchenTaskSummary) {
        this.kitchenTaskSummary = kitchenTaskSummary;
    }

    public int getId() {
        return id;
    }

    void setId(int id) {
        this.id = id;
    }

    public ArrayList<KitchenItem> getMenuKitchenItems() {
        if (menu == null)
            return null;
        return menu.getMenuItems().stream().map(MenuItem::getItemRecipe).collect(Collectors.toCollection(ArrayList::new));
    }

    // STATIC METHODS FOR PERSISTENCE

    public static ObservableList<ServiceInfo> loadServiceInfoForEvent(int event_id) {
        ObservableList<ServiceInfo> result = FXCollections.observableArrayList();
        String query = "SELECT id, name, service_date, time_start, time_end, expected_participants, proposed_menu_id " +
                "FROM Services WHERE event_id = " + event_id;
        PersistenceManager.executeQuery(query, new ResultHandler() {
            @Override
            public void handle(ResultSet rs) throws SQLException {
                String s = rs.getString("name");
                ServiceInfo serv = new ServiceInfo(s);
                serv.id = rs.getInt("id");
                serv.date = rs.getDate("service_date");
                serv.timeStart = rs.getTime("time_start");
                serv.timeEnd = rs.getTime("time_end");
                serv.participants = rs.getInt("expected_participants");
                serv.kitchenTaskSummary = KitchenTaskSummary.loadKitchenTaskSummaryForService(serv.id);
                int menu_id = rs.getInt("proposed_menu_id");
                if (menu_id > 0)
                    serv.menu = Menu.loadAllMenus().filtered(m -> m.getId() == menu_id).get(0);
                result.add(serv);
            }
        });

        return result;
    }

    public Menu getMenu() {
        return menu;
    }
}
