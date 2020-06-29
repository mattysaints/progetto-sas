package businesslogic.turn;

import persistence.PersistenceManager;
import persistence.ResultHandler;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class TurnBoard {
    private static TurnBoard instance;

    private ArrayList<Turn> turns;

    private TurnBoard() {
    }

    public static TurnBoard getInstance() {
        if (instance == null)
            instance = new TurnBoard();
        return instance;
    }

    public ArrayList<Turn> getTurns() {
        if (turns == null)
            loadTurns();
        return new ArrayList<>(turns);
    }

    private void loadTurns() {
        turns = new ArrayList<>();
        String turnQuery = "SELECT * FROM Turns";
        PersistenceManager.executeQuery(turnQuery, new ResultHandler() {
            @Override
            public void handle(ResultSet rs) throws SQLException {
                Turn turn = new Turn();
                turn.setId(rs.getInt(1));
                turn.setTimeStart(rs.getTime(2));
                turn.setTimeEnd(rs.getTime(3));
                turns.add(turn);
            }
        });
    }
}
