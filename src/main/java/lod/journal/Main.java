package lod.journal;


import legend.game.EngineStateEnum;
import legend.game.input.InputAction;
import legend.game.inventory.screens.MenuStack;
import legend.game.modding.events.RenderEvent;
import legend.game.modding.events.input.InputReleasedEvent;
import legend.game.saves.ConfigEntry;
import legend.game.saves.ConfigRegistryEvent;
import org.legendofdragoon.modloader.Mod;
import org.legendofdragoon.modloader.events.EventListener;
import org.legendofdragoon.modloader.registries.Registrar;
import org.legendofdragoon.modloader.registries.RegistryId;

import java.util.HashMap;

import static legend.core.GameEngine.*;
import static legend.game.Scus94491BpeSegment_8002.playMenuSound;
import static legend.game.Scus94491BpeSegment_8004.engineState_8004dd20;
import static legend.game.Scus94491BpeSegment_800b.gameState_800babc8;
import static legend.game.Scus94491BpeSegment_800b.scriptStatePtrArr_800bc1c0;

@Mod(id = Main.MOD_ID, version = "3.0.0")
public class Main {
  public static final String MOD_ID = "journal";

  public static final Registrar<ConfigEntry<?>, ConfigRegistryEvent> JOURNAL_CONFIG_REGISTRAR = new Registrar<>(REGISTRIES.config, MOD_ID);

  public Main() {
    EVENTS.register(this);
  }

  public static RegistryId id(final String entryId) {
    return new RegistryId(MOD_ID, entryId);
  }

  private static HashMap<Integer, String> cutMaps;
  private static HashMap<String, Location> cutInfos;
  private boolean menuIsOpen = false;
  private final MenuStack menuStack = new MenuStack();

  private void initializeAndOpenMenu(){
    SCRIPTS.pause();
    menuStack.pushScreen(new JournalMenu());
    playMenuSound(2);
    menuIsOpen = true;
  }
  private void clearAndCloseMenu(){
    menuStack.reset();
    playMenuSound(3);
    menuIsOpen = false;
    SCRIPTS.resume();
  }
  @EventListener
  public void InputHandler(final InputReleasedEvent event){
    if(engineState_8004dd20 == EngineStateEnum.SUBMAP_05 && !gameState_800babc8.indicatorsDisabled_4e3 && event.inputAction == InputAction.KILL_STUCK_SOUNDS && this.menuIsOpen == false){
      gameState_800babc8.indicatorsDisabled_4e3 = true;
      initializeAndOpenMenu();
    }else if(event.inputAction == InputAction.KILL_STUCK_SOUNDS && menuIsOpen == true){
      gameState_800babc8.indicatorsDisabled_4e3 = false;
      clearAndCloseMenu();
    }
  }
  @EventListener
  public void RenderMenu(final RenderEvent event){
    if(menuIsOpen == false) return;
    menuStack.render();
  }
}