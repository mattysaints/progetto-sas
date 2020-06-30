package businesslogic.turn;

import persistence.PersistenceManager;
import persistence.ResultHandler;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class TurnBoard {
    private static TurnBoard instance;

    private Map<Integer, Turn> turns;

    private TurnBoard() {
    }

    public static TurnBoard getInstance() {
        if (instance == null)
            instance = new TurnBoard();
        if (instance.turns == null)
            instance.loadTurns();
        return instance;
    }

    public ArrayList<Turn> getTurns() {
        return new ArrayList<>(turns.values());
    }

    private void loadTurns() {
        turns = new HashMap<>();
        String turnQuery = "SELECT * FROM Turns";
        PersistenceManager.executeQuery(turnQuery, new ResultHandler() {
            @Override
            public void handle(ResultSet rs) throws SQLException {
                Turn turn = new Turn();
                turn.setId(rs.getInt(1));
                turn.setTimeStart(rs.getTime(2));
                turn.setTimeEnd(rs.getTime(3));
                turn.setIsFull(rs.getBoolean(4));
                turns.put(turn.getId(), turn);
            }
        });
    }

    public Turn getTurnById(int turn_id) {
        return turns.get(turn_id);
    }

    public void setTurnFull(Turn turn, boolean isFull) {
        if (turns.containsValue(turn)) {
            turn.setIsFull(isFull);
        }
    }
}
