package lod.journal;

import static legend.game.Scus94491BpeSegment_800b.gameState_800babc8;

public class StardustInfo implements Obtainable {

    public int stardustFlag;

    public boolean isObtained;

    public StardustInfo(final int stardustFlag){
        this.stardustFlag = stardustFlag;

        this.setObtained();
    }

    @Override
    public void setObtained() {
        if(gameState_800babc8.scriptFlags2_bc.get(this.stardustFlag)){
            this.isObtained = true;
        }
    }
}
