package scoutingtest4590.util.scouter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class ScoutingEntry {
    private ArrayList<ScoutingData<Object>> gameEntry;
    public static final String dataSubdomain = "/scouter/send";

    public ScoutingEntry(ArrayList<ScoutingData<Object>> gameEntry) {
        this.gameEntry = gameEntry;
    }

    public ScoutingEntry(ScoutingData<Object>... gameEntry) {
        ArrayList<ScoutingData<Object>> a = new ArrayList<>();
        Collections.addAll(a, gameEntry);
        this.gameEntry = a;
    }

    public ArrayList<ScoutingData<Object>> getGameEntry() {
        return gameEntry;
    }

    public void resetEntry() {
        this.gameEntry = new ArrayList<>();
    }

    public <T> void addData(ScoutingData<T> data) {
        gameEntry.add(data);
    }

    public void sendData() {
        
    }
}
