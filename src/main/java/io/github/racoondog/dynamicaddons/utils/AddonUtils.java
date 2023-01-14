package io.github.racoondog.dynamicaddons.utils;

import io.github.racoondog.dynamicaddons.DynamicAddon;
import io.github.racoondog.dynamicaddons.DynamicAddons;
import meteordevelopment.meteorclient.addons.AddonManager;
import meteordevelopment.meteorclient.addons.MeteorAddon;
import meteordevelopment.meteorclient.systems.commands.Command;
import meteordevelopment.meteorclient.systems.hud.HudElementInfo;
import meteordevelopment.meteorclient.systems.modules.Module;
import meteordevelopment.meteorclient.utils.PreInit;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;

@Environment(EnvType.CLIENT)
public final class AddonUtils { // TODO add system to make sure tracked, but disabled objects are correctly saved when saving NBT during game close
    private static final List<MeteorAddon> TRACKED_ADDONS = new ArrayList<>();

    @PreInit
    public static void preInit() {
        Collections.copy(TRACKED_ADDONS, AddonManager.ADDONS);

        for (var it = TRACKED_ADDONS.iterator(); it.hasNext();) {
            MeteorAddon addon = it.next();
            if (addon.getClass() == DynamicAddons.class) it.remove();
            else {
                //Stop tracking addons which do not correctly implement 'getPackage()'
                try {
                    String pkg = addon.getPackage();
                    if (!addon.getClass().getName().startsWith(pkg)) {
                        DynamicAddons.LOG.warn("Could not track addon {}: Wrongly implemented 'getPackage' method.", addon.name);
                        it.remove();
                    }
                } catch (AbstractMethodError e) {
                    DynamicAddons.LOG.warn("Could not track addon {}: Does not implement 'getPackage' method.", addon.name);
                    it.remove();
                }
            }
        }
    }

    @Nullable
    public static MeteorAddon fromPackage(String pkgName) {
        for (var addon : TRACKED_ADDONS) {
            if (pkgName.startsWith(addon.getPackage())) return addon;
        }
        return null;
    }

    @Nullable
    public static MeteorAddon fromName(String name) {
        for (var addon : TRACKED_ADDONS) {
            if (name.equals(addon.name)) return addon;
        }
        return null;
    }

    public static List<MeteorAddon> get(Predicate<MeteorAddon> filter) {
        List<MeteorAddon> out = new ArrayList<>();
        for (var addon : TRACKED_ADDONS) if (filter.test(addon)) out.add(addon);
        return out;
    }

    public static void trackModule(Module module, String pkgName) {
        MeteorAddon addon = fromPackage(pkgName);
        if (addon != null) ((DynamicAddon) addon).getModules().add(module);
    }

    public static void trackCommand(Command command, String pkgName) {
        MeteorAddon addon = fromPackage(pkgName);
        if (addon != null) ((DynamicAddon) addon).getCommands().add(command);
    }

    public static void trackHudElement(HudElementInfo<?> hudElement, String pkgName) {
        MeteorAddon addon = fromPackage(pkgName);
        if (addon != null) ((DynamicAddon) addon).getHudElements().add(hudElement);
    }
}
