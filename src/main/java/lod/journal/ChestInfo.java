package lod.journal;



import static legend.game.Scus94491BpeSegment_800b.gameState_800babc8;

public class ChestInfo implements Obtainable {
    private final int chestFlag;
    private final String name;

    private boolean isObtained;


    public ChestInfo(final int chestFlag, final String name){
        this.chestFlag = chestFlag;
        this.name = name;

        this.setObtained();
    }

    public String getName() { return this.name; }

    public boolean getIsObtained() { return this.isObtained; }

    @Override
    public void setObtained() {
        final int shift = this.chestFlag & 0x1f;
        final int index = this.chestFlag >>> 5;
        if ((gameState_800babc8.chestFlags_1c4[index] & (1 << shift)) != 0) {
            this.isObtained = true;
        }
    }
}
