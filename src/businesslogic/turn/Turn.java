package businesslogic.turn;

import java.sql.Time;

public class Turn {
    private int id;
    private Time timeStart;
    private Time timeEnd;

    public Turn() {
    }

    public boolean isFull() {
        return false;
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
        return "dalle " + timeStart + " alle " + timeEnd;
    }
}
