package io.github.racoondog.dynamicaddons.mixininterface;

import meteordevelopment.meteorclient.systems.commands.Command;
import meteordevelopment.meteorclient.systems.hud.HudElementInfo;
import meteordevelopment.meteorclient.systems.modules.Category;
import meteordevelopment.meteorclient.systems.modules.Module;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

import java.util.List;

@Environment(EnvType.CLIENT)
public interface DynamicAddon {
    List<Module> getModules();
    List<HudElementInfo<?>> getHudElements();
    List<Command> getCommands();
    List<Category> getCategories();

    void setEnabled(boolean bool);
    boolean getEnabled();
}
