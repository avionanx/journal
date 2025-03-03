package lod.journal;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Objects;


public class Location {
    public String locationName;
    public String next;
    public String previous;
    public ArrayList<ChestInfo> chests = new ArrayList<>();
    public ArrayList<StardustInfo> stardusts = new ArrayList<>();
    public ArrayList<JournalEntry> journalEntries = new ArrayList<>();

    public Location(String id){
        this.loadConfig(id);
        this.loadChests(id);
        this.loadStardusts(id);
    }
    private void loadChests(final String id){
        try (InputStream inputStream = JournalLocationMenu.class.getClassLoader().getResourceAsStream("locations/%s/chests.csv".formatted(id));
             InputStreamReader streamReader = new InputStreamReader(Objects.requireNonNull(inputStream), StandardCharsets.UTF_8);
             CSVReader csvReader = new CSVReader(streamReader)) {

            String[] values;
            while ((values = csvReader.readNext()) != null) {
                final int chestId = Integer.parseInt(values[0].trim());
                //String chestContent = values[1].trim();
                this.chests.add(new ChestInfo(chestId, "todo"));
            }
        } catch (IOException | NullPointerException | CsvValidationException e) {
            throw new RuntimeException(e);
        }
    }
    private void loadConfig(final String id){
        try (InputStream inputStream = JournalLocationMenu.class.getClassLoader().getResourceAsStream("locations/%s/config.csv".formatted(id));
             InputStreamReader streamReader = new InputStreamReader(Objects.requireNonNull(inputStream), StandardCharsets.UTF_8);
             CSVReader csvReader = new CSVReader(streamReader)) {

            String[] values;
            while ((values = csvReader.readNext()) != null) {
                final String configType = values[0].trim();
                final String configValue = values[1].trim();
                switch (configType){
                    case "name" -> this.locationName = configValue;
                    case "previous" -> this.previous = configValue;
                    case "next" -> this.next = configValue;
                    default -> throw new RuntimeException("Found invalid configuration type");
                }
            }
        } catch (IOException | NullPointerException | CsvValidationException e) {
            throw new RuntimeException(e);
        }
    }
    private void loadStardusts(final String id){
        try (InputStream inputStream = JournalLocationMenu.class.getClassLoader().getResourceAsStream("locations/%s/stardusts.csv".formatted(id));
             InputStreamReader streamReader = new InputStreamReader(Objects.requireNonNull(inputStream), StandardCharsets.UTF_8);
             CSVReader csvReader = new CSVReader(streamReader)) {

            String[] values;
            while ((values = csvReader.readNext()) != null) {
                int stardustFlag = Integer.parseInt(values[0].trim());
                this.stardusts.add(new StardustInfo(stardustFlag));
            }
        } catch (IOException | NullPointerException | CsvValidationException e) {
            throw new RuntimeException(e);
        }
    }

    public String getLocationName() { return this.locationName; }
    public String getNext() { return this.next; }
    public String getPrevious() { return this.previous; }
    public ArrayList<ChestInfo> getChests() { return this.chests; }
    public ArrayList<StardustInfo> getStardusts() { return this.stardusts; }
    public ArrayList<JournalEntry> getJournalEntries() { return this.journalEntries; }
}
