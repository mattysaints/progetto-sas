package businesslogic.turn;

import persistence.PersistenceManager;

import java.sql.Time;
import java.util.Objects;

public class Turn {
    private int id;
    private Time timeStart;
    private Time timeEnd;
    private boolean isFull;

    public Turn() {
    }

    public boolean isFull() {
        return isFull;
    }

    public void setIsFull(boolean isFull) {
        this.isFull = isFull;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Time getTimeStart() {
        return timeStart;
    }

    public void setTimeStart(Time timeStart) {
        this.timeStart = timeStart;
    }

    public Time getTimeEnd() {
        return timeEnd;
    }

    public void setTimeEnd(Time timeEnd) {
        this.timeEnd = timeEnd;
    }

    @Override
    public String toString() {
        return "dalle " + timeStart + " alle " + timeEnd + (isFull ? ", pieno" : ", non pieno");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Turn turn = (Turn) o;
        return id == turn.id &&
                isFull == turn.isFull &&
                timeStart.equals(turn.timeStart) &&
                timeEnd.equals(turn.timeEnd);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, timeStart, timeEnd, isFull);
    }

    // STATIC METHODS FOR PERSISTENCE

    public static void saveTurn(Turn turn) {
        String saveTurnQuery = "UPDATE Turns SET is_full = '" + (turn.isFull ? 1 : 0) + "' WHERE id = '" + turn.id + "'";
        PersistenceManager.executeUpdate(saveTurnQuery);
    }
}
