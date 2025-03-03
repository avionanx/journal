package lod.journal;


import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import legend.game.input.InputAction;
import legend.game.inventory.screens.FontOptions;
import legend.game.inventory.screens.HorizontalAlign;
import legend.game.inventory.screens.InputPropagation;
import legend.game.inventory.screens.MenuScreen;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Objects;

import static legend.game.Scus94491BpeSegment_8002.playMenuSound;
import static legend.game.Scus94491BpeSegment_8002.renderText;
import static legend.game.Scus94491BpeSegment_8005.submapCut_80052c30;

public class JournalLocationMenu extends MenuScreen {

    final Runnable unload;
    private final FontOptions shadowlessLeftFontOpts;
    private final FontOptions shadowlessRightFontOpts;

    private Location locationData;
    private final HashMap<Integer, String> submapCutLocationMapping; // maps submap cuts to internal location names

    private final FontOptions smallLeftFontOpts;
    private final FontOptions smallRightFontOpts;
    public JournalLocationMenu(Runnable unload) {
        this.unload = unload;
        this.shadowlessLeftFontOpts = new FontOptions().horizontalAlign(HorizontalAlign.LEFT);
        this.shadowlessRightFontOpts = new FontOptions().horizontalAlign(HorizontalAlign.RIGHT);

        this.submapCutLocationMapping = new HashMap<>();

        this.smallLeftFontOpts = new FontOptions().size(0.6f).horizontalAlign(HorizontalAlign.LEFT);
        this.smallRightFontOpts = new FontOptions().size(0.6f).horizontalAlign(HorizontalAlign.RIGHT);

        this.registerMappings();
        this.loadLocationData(this.resolveCut(submapCut_80052c30));
    }

    private void registerMappings(){
        try (InputStream inputStream = JournalLocationMenu.class.getClassLoader().getResourceAsStream("cut_mapping.csv");
             InputStreamReader streamReader = new InputStreamReader(Objects.requireNonNull(inputStream), StandardCharsets.UTF_8);
             CSVReader csvReader = new CSVReader(streamReader)) {

            String[] values;
            while ((values = csvReader.readNext()) != null) {
                int id = Integer.parseInt(values[0].trim());
                String name = values[1].trim();
                this.submapCutLocationMapping.put(id,name);
            }
        } catch (IOException | NullPointerException | CsvValidationException e) {
            throw new RuntimeException(e);
        }
    }
    private String resolveCut(int cut){
        return submapCutLocationMapping.getOrDefault(cut, submapCutLocationMapping.values().stream().findFirst().get());
    }
    private void loadLocationData(String id){
        if(id == null) { return; }
        this.locationData = new Location(id);
        /*
        InputStream inputStream = JournalLocationMenu.class.getClassLoader().getResourceAsStream("locations/%s.json".formatted(id));
        ObjectMapper mapper = new ObjectMapper();
        try{
            this.locationData = mapper.readValue(inputStream, Location.class);
        } catch (IOException e){
            throw new RuntimeException(e);
        }
        */
    }
    ///TODO cache strings for some speedup
    @Override
    protected void render() {
        renderText(this.locationData.getLocationName(), 80, 58, this.shadowlessLeftFontOpts);
        renderText("Story", 256, 58, this.shadowlessLeftFontOpts);

        renderText("Chests:", 80, 88, this.smallLeftFontOpts);
        renderText("%d/%d".formatted(this.locationData.chests.stream().filter(ChestInfo::getIsObtained).count(), this.locationData.chests.size()), 180, 88, this.smallRightFontOpts);

        renderText("Stardust:", 80, 98, this.smallLeftFontOpts);
        renderText("%d/%d".formatted(this.locationData.stardusts.stream().filter(e -> e.isObtained).count(), this.locationData.stardusts.size()), 180, 98, this.smallRightFontOpts);

    }
    private void menuEscape() {
        playMenuSound(3);
        this.unload.run();
    }
    @Override
    public boolean propagateRender(){
        return true;
    }
    @Override
    public InputPropagation pressedWithRepeatPulse(final InputAction inputAction) {
        if(super.pressedWithRepeatPulse(inputAction) == InputPropagation.HANDLED) {
            return InputPropagation.HANDLED;
        }
        switch(inputAction){
            case BUTTON_EAST -> {
                this.menuEscape();
                return InputPropagation.HANDLED;
            }
            case DPAD_RIGHT -> {
                final String next = this.locationData.getNext();
                if(next == null){
                    playMenuSound(0);
                }else{
                    playMenuSound(1);
                    this.loadLocationData(next);
                }
                return InputPropagation.HANDLED;
            }
            case DPAD_LEFT -> {
                final String previous = this.locationData.getPrevious();
                if(previous == null){
                    playMenuSound(0);
                }else{
                    playMenuSound(1);
                    this.loadLocationData(previous);
                }
                return InputPropagation.HANDLED;
            }
            default -> {
                return InputPropagation.PROPAGATE;
            }
        }
    }
}
