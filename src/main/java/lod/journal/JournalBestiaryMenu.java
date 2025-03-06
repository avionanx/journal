package lod.journal;

import legend.game.characters.Element;
import legend.game.i18n.I18n;
import legend.game.input.InputAction;
import legend.game.inventory.screens.FontOptions;
import legend.game.inventory.screens.HorizontalAlign;
import legend.game.inventory.screens.InputPropagation;
import legend.game.inventory.screens.MenuScreen;

import static legend.game.SItem.UI_TEXT_CENTERED;
import static legend.game.Scus94491BpeSegment_8002.playMenuSound;
import static legend.game.Scus94491BpeSegment_8002.renderText;
import static legend.game.combat.Monsters.*;

public class JournalBestiaryMenu extends MenuScreen {

    final Runnable unload;

    private final int[] validMonsterIds = {0};
    private final int page = 0;
    private final FontOptions shadowlessFontOpts;
    private final FontOptions smallLeftFontOpts;
    private final FontOptions smallRightFontOpts;
    private final FontOptions smallFontOpts;

    public JournalBestiaryMenu(Runnable unload) {
        this.unload = unload;

        this.shadowlessFontOpts = new FontOptions().horizontalAlign(HorizontalAlign.CENTRE);
        this.smallFontOpts = new FontOptions().size(0.6f).horizontalAlign(HorizontalAlign.CENTRE);
        this.smallLeftFontOpts = new FontOptions().size(0.6f).horizontalAlign(HorizontalAlign.LEFT);
        this.smallRightFontOpts = new FontOptions().size(0.6f).horizontalAlign(HorizontalAlign.RIGHT);
    }
    
    @Override
    protected void render() {
        renderText(monsterNames_80112068[validMonsterIds[page]], 130, 58, this.shadowlessFontOpts);
        renderText(I18n.translate(Element.fromFlag(monsterStats_8010ba98[validMonsterIds[page]].elementFlag_0f).getTranslationKey()), 130, 68, this.smallFontOpts);
        renderText("Stats", 245, 58, this.shadowlessFontOpts);

        renderText("No. defeated", 80, 138, this.smallLeftFontOpts);
        renderText("0", 175, 138, this.smallRightFontOpts);

        renderText("EXP", 80, 148, this.smallLeftFontOpts);
        renderText(String.valueOf(enemyRewards_80112868[validMonsterIds[page]].xp_00), 125, 148, this.smallRightFontOpts);

        renderText("Gold", 130, 148, this.smallLeftFontOpts);
        renderText(String.valueOf(enemyRewards_80112868[validMonsterIds[page]].gold_02), 175, 148, this.smallRightFontOpts);

        if(enemyRewards_80112868[validMonsterIds[page]].itemDrop_05.get() != null){
            renderText("Drop", 80, 158, this.smallLeftFontOpts);
            renderText(I18n.translate(enemyRewards_80112868[validMonsterIds[page]].itemDrop_05.get().getNameTranslationKey()), 175, 158, this.smallRightFontOpts);
            renderText("Drop Rate %", 80, 168, this.smallLeftFontOpts);
            renderText(String.valueOf(enemyRewards_80112868[validMonsterIds[page]].itemChance_04), 175, 168, this.smallRightFontOpts);
        }


        renderText("Health", 195, 88, this.smallLeftFontOpts);
        renderText(String.valueOf(monsterStats_8010ba98[validMonsterIds[page]].hp_00), 290, 88, this.smallRightFontOpts);

        renderText("Speed", 195, 98, this.smallLeftFontOpts);
        renderText(String.valueOf(monsterStats_8010ba98[validMonsterIds[page]].speed_08), 240, 98, this.smallRightFontOpts);

        renderText("ATT", 195, 108, this.smallLeftFontOpts);
        renderText(String.valueOf(monsterStats_8010ba98[validMonsterIds[page]].attack_04), 240, 108, this.smallRightFontOpts);
        renderText("MAT", 245, 108, this.smallLeftFontOpts);
        renderText(String.valueOf(monsterStats_8010ba98[validMonsterIds[page]].magicAttack_06), 290, 108, this.smallRightFontOpts);

        renderText("DEF", 195, 118, this.smallLeftFontOpts);
        renderText(String.valueOf(monsterStats_8010ba98[validMonsterIds[page]].defence_09), 240, 118, this.smallRightFontOpts);
        renderText("MDEF", 245, 118, this.smallLeftFontOpts);
        renderText(String.valueOf(monsterStats_8010ba98[validMonsterIds[page]].magicDefence_0a), 290, 118, this.smallRightFontOpts);

        renderText("AV%", 195, 128, this.smallLeftFontOpts);
        renderText(String.valueOf(monsterStats_8010ba98[validMonsterIds[page]].attackAvoid_0b), 240, 128, this.smallRightFontOpts);
        renderText("MAV%", 245, 128, this.smallLeftFontOpts);
        renderText(String.valueOf(monsterStats_8010ba98[validMonsterIds[page]].magicAvoid_0c), 290, 128, this.smallRightFontOpts);
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
    public InputPropagation pressedThisFrame(final InputAction inputAction) {
        if(super.pressedThisFrame(inputAction) == InputPropagation.HANDLED) {
            return InputPropagation.HANDLED;
        }

        if(inputAction == InputAction.BUTTON_EAST) {
            this.menuEscape();
            return InputPropagation.HANDLED;
        }

        return InputPropagation.PROPAGATE;
    }
}
