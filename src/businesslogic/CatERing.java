package businesslogic;

import businesslogic.event.EventManager;
import businesslogic.kitchentask.KitchenTaskManager;
import businesslogic.menu.MenuManager;
import businesslogic.recipe.RecipeManager;
import businesslogic.user.UserManager;
import persistence.KitchenTaskPersistence;
import persistence.MenuPersistence;

public class CatERing {
    private static CatERing singleInstance;

    public static CatERing getInstance() {
        if (singleInstance == null) {
            singleInstance = new CatERing();
        }
        return singleInstance;
    }

    private final MenuManager menuMgr;
    private final RecipeManager recipeMgr;
    private final UserManager userMgr;
    private final EventManager eventMgr;
    private final KitchenTaskManager kitchentTaskMgr;

    private final MenuPersistence menuPersistence;
    private final KitchenTaskPersistence kitchenTaskPersistence;

    private CatERing() {
        menuMgr = new MenuManager();
        recipeMgr = new RecipeManager();
        userMgr = new UserManager();
        eventMgr = new EventManager();
        kitchentTaskMgr = new KitchenTaskManager();
        menuPersistence = new MenuPersistence();
        kitchenTaskPersistence = new KitchenTaskPersistence();
        menuMgr.addEventReceiver(menuPersistence);
        kitchentTaskMgr.addEventReceiver(kitchenTaskPersistence);
    }


    public MenuManager getMenuManager() {
        return menuMgr;
    }

    public RecipeManager getRecipeManager() {
        return recipeMgr;
    }

    public UserManager getUserManager() {
        return userMgr;
    }

    public EventManager getEventManager() {
        return eventMgr;
    }

    public KitchenTaskManager getKitchenTaskManager() {
        return kitchentTaskMgr;
    }
}
