package lod.journal;

import legend.core.QueuedModelStandard;
import legend.core.gpu.Bpp;
import legend.core.gte.MV;
import legend.core.opengl.MeshObj;
import legend.core.opengl.QuadBuilder;
import legend.core.opengl.Texture;
import legend.game.inventory.screens.*;
import legend.game.inventory.screens.controls.Button;
import legend.game.types.Translucency;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import static legend.core.GameEngine.RENDERER;
import static legend.game.Scus94491BpeSegment_8002.playMenuSound;
import static legend.game.Scus94491BpeSegment_8002.renderText;

public class JournalMenu extends MenuScreen {

    private final List<Button> menuButtons = new ArrayList<>();

    private final MV matrix;
    private final MeshObj bg;
    private final Texture texture;

    private final FontOptions menuTitleFontOpts;
    public JournalMenu(){
        this.menuTitleFontOpts = new FontOptions().horizontalAlign(HorizontalAlign.CENTRE).colour(TextColour.WHITE).shadowColour(TextColour.BLACK);
        this.addButton("Overview", this::showOverviewScreen);
        this.addButton("Locations", this::showLocationScreen);
        this.addButton("Bestiary", this::showBestiaryScreen);
        this.setFocus(this.menuButtons.getFirst());

        this.bg = new QuadBuilder("")
                .uvSize(1.0f,1.0f)
                .bpp(Bpp.BITS_24)
                .size(1.0f,1.0f)
                .translucency(Translucency.HALF_B_PLUS_HALF_F)
                .build();
        this.texture = Texture.png(Path.of("gfx","book.png"));
        this.matrix = new MV();
        float sizeY = 180.0f;
        float scaleX = 1.55f;
        this.matrix.scaling(sizeY * scaleX,sizeY, 1.0f);
        this.matrix.transfer.set((368.0f - (sizeY * scaleX)) / 2.0f,(240 - sizeY) / 2.0f,200.0f);
    }


    @Override
    protected void render() {
        renderText("Journal", 185, 25, this.menuTitleFontOpts);

        final var queued = RENDERER.queueOrthoModel(this.bg,this.matrix, QueuedModelStandard.class);
        queued.texture(this.texture).useTextureAlpha();
    }

    private void addButton(final String text, final Runnable onClick) {
        final int index = this.menuButtons.size();

        final Button button = this.addControl(new Button(text));
        button.setPos(75 + index * 72, 200);
        button.setWidth(72);

        button.onHoverIn(() -> {
            playMenuSound(1);
            this.setFocus(button);
        });
        button.setTextColour(TextColour.GREY);
        button.onLostFocus(() -> button.setTextColour(TextColour.GREY));
        button.onGotFocus(() -> button.setTextColour(TextColour.WHITE));

        button.onPressed(onClick::run);

        button.onPressedWithRepeatPulse(inputAction -> {
            switch(inputAction) {
                case DPAD_RIGHT, JOYSTICK_LEFT_BUTTON_DOWN -> {
                    for(int i = 1; i < this.menuButtons.size(); i++) {
                        final Button otherButton = this.menuButtons.get(Math.floorMod(index + i, this.menuButtons.size()));

                        if(!otherButton.isDisabled() && otherButton.isVisible()) {
                            playMenuSound(1);
                            this.setFocus(otherButton);
                            break;
                        }
                    }
                }
                case DPAD_LEFT, JOYSTICK_LEFT_BUTTON_UP -> {
                    for(int i = 1; i < this.menuButtons.size(); i++) {
                        final Button otherButton = this.menuButtons.get(Math.floorMod(index - i, this.menuButtons.size()));

                        if(!otherButton.isDisabled() && otherButton.isVisible()) {
                            playMenuSound(1);
                            this.setFocus(otherButton);
                            break;
                        }
                    }
                }
            }

            return InputPropagation.HANDLED;
        });

        this.menuButtons.add(button);
    }

    private void showOverviewScreen() {
        this.showScreen(JournalOverviewMenu::new);
    }
    private void showLocationScreen(){
        this.showScreen(JournalLocationMenu::new);
    }
    private void showBestiaryScreen(){
        this.showScreen(JournalBestiaryMenu::new);
    }
    private void showScreen(final Function<Runnable, MenuScreen> screen) {
        this.getStack().pushScreen(screen.apply(() -> {
            this.getStack().popScreen();
        }));
    }
}
