package scoutingtest4590.util.scouter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class ScoutingEntry {
    private ArrayList<ScoutingData<Object>> gameEntry;

    public ScoutingEntry(ArrayList<ScoutingData<Object>> gameEntry) {
        this.gameEntry = gameEntry;
    }

    public ScoutingEntry(ScoutingData<Object>... gameEntry) {
        ArrayList<ScoutingData<Object>> a = new ArrayList<>();
        Collections.addAll(a, gameEntry);
        this.gameEntry = a;
    }
}
