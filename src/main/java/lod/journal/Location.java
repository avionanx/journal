package lod.journal;

import java.util.ArrayList;

public class Location {
    private final String locationName;
    private final ArrayList<ChestInfo> chests;
    private final ArrayList<Integer> stardusts;
    private final ArrayList<JournalEntry> journalEntries;
    public Location(final String locationName, ArrayList<ChestInfo> chests, ArrayList<Integer> stardusts, ArrayList<JournalEntry> journalEntries){
        this.locationName = locationName;
        this.chests = chests;
        this.stardusts = stardusts;
        this.journalEntries = journalEntries;
    }


}
