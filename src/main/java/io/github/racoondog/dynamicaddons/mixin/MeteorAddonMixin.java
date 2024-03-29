package io.github.racoondog.dynamicaddons.mixin;

import io.github.racoondog.dynamicaddons.mixininterface.DynamicAddon;
import meteordevelopment.meteorclient.addons.MeteorAddon;
import meteordevelopment.meteorclient.systems.commands.Command;
import meteordevelopment.meteorclient.systems.hud.HudElementInfo;
import meteordevelopment.meteorclient.systems.modules.Category;
import meteordevelopment.meteorclient.systems.modules.Module;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

import java.util.ArrayList;
import java.util.List;

@Environment(EnvType.CLIENT)
@Mixin(value = MeteorAddon.class, remap = false)
public abstract class MeteorAddonMixin implements DynamicAddon {
    @Unique private final List<Module> modules = new ArrayList<>();
    @Unique private final List<HudElementInfo<?>> hudElements = new ArrayList<>();
    @Unique private final List<Command> commands = new ArrayList<>();
    @Unique private final List<Category> categories = new ArrayList<>();
    @Unique private boolean enabled;

    @Override
    public List<Module> getModules() {
        return this.modules;
    }

    @Override
    public List<HudElementInfo<?>> getHudElements() {
        return this.hudElements;
    }

    @Override
    public List<Command> getCommands() {
        return this.commands;
    }

    @Override
    public List<Category> getCategories() {
        return this.categories;
    }

    @Override
    public void setEnabled(boolean bool) {
        this.enabled = bool;
    }

    @Override
    public boolean getEnabled() {
        return this.enabled;
    }
}
