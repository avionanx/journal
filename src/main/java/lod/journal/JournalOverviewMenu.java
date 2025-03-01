package lod.journal;

import legend.core.QueuedModelStandard;
import legend.core.gpu.Bpp;
import legend.core.gte.MV;
import legend.core.opengl.MeshObj;
import legend.core.opengl.Obj;
import legend.core.opengl.QuadBuilder;
import legend.core.opengl.Texture;
import legend.game.Scus94491BpeSegment_800b;
import legend.game.input.InputAction;
import legend.game.inventory.screens.FontOptions;
import legend.game.inventory.screens.HorizontalAlign;
import legend.game.inventory.screens.InputPropagation;
import legend.game.inventory.screens.MenuScreen;
import legend.game.types.Translucency;
import org.joml.Matrix3f;

import java.nio.file.Path;

import static legend.core.GameEngine.RENDERER;
import static legend.game.SItem.UI_TEXT_CENTERED;
import static legend.game.Scus94491BpeSegment_8002.playMenuSound;
import static legend.game.Scus94491BpeSegment_8002.renderText;

public class JournalOverviewMenu extends MenuScreen {

    private final Runnable unload;
    private final int unlockedChestCount;
    private final int totalChestCount = 232;
    private final FontOptions shadowlessFontOpts;
    private final FontOptions smallFontOpts;
    private final FontOptions smallLeftFontOpts;
    private final FontOptions smallRightFontOpts;

    public JournalOverviewMenu(Runnable unload) {
        this.unload = unload;
        this.unlockedChestCount = this.getUnlockedChestCount();
        this.shadowlessFontOpts = new FontOptions().horizontalAlign(HorizontalAlign.CENTRE);
        this.smallFontOpts = new FontOptions().size(0.6f).horizontalAlign(HorizontalAlign.CENTRE);
        this.smallLeftFontOpts = new FontOptions().size(0.6f).horizontalAlign(HorizontalAlign.LEFT);
        this.smallRightFontOpts = new FontOptions().size(0.6f).horizontalAlign(HorizontalAlign.RIGHT);
    }
    private int getUnlockedChestCount(){
        int count = 0;
        for(int i = 0; i < Scus94491BpeSegment_800b.gameState_800babc8.chestFlags_1c4.length; i++){
            count += Integer.bitCount(Scus94491BpeSegment_800b.gameState_800babc8.chestFlags_1c4[i]);
        }
        return count;
    }
    @Override
    protected void render() {
        renderText("Overview", 130, 58, this.shadowlessFontOpts);
        renderText("Book Completion:", 80, 88, this.smallLeftFontOpts);
        renderText("0.0%", 180, 88, this.smallRightFontOpts);

        renderText("Chests:", 80, 98, this.smallLeftFontOpts);
        renderText("%d/%d".formatted(this.unlockedChestCount, this.totalChestCount), 180, 98, this.smallRightFontOpts);

        renderText("Beastiary:", 80, 108, this.smallLeftFontOpts);
        renderText("0/0", 180, 108, this.smallRightFontOpts);

        renderText("Optional Events:", 80, 118, this.smallLeftFontOpts);
        renderText("0/0", 180, 118, this.smallRightFontOpts);

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
