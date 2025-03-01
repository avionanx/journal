package lod.journal;

import legend.game.input.InputAction;
import legend.game.inventory.screens.InputPropagation;
import legend.game.inventory.screens.MenuScreen;

import static legend.game.SItem.UI_TEXT_CENTERED;
import static legend.game.Scus94491BpeSegment_8002.playMenuSound;
import static legend.game.Scus94491BpeSegment_8002.renderText;

public class JournalLocationMenu extends MenuScreen {

    final Runnable unload;
    public JournalLocationMenu(Runnable unload) {
        this.unload = unload;
    }

    @Override
    protected void render() {
        renderText("Location", 90, 38, UI_TEXT_CENTERED);
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
