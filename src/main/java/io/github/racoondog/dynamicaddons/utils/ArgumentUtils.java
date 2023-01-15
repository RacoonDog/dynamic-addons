package io.github.racoondog.dynamicaddons.utils;

import io.github.racoondog.dynamicaddons.mixininterface.DynamicAddon;
import meteordevelopment.meteorclient.addons.MeteorAddon;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

import java.util.function.Predicate;

@Environment(EnvType.CLIENT)
public final class ArgumentUtils {
    public static final Predicate<MeteorAddon> ENABLED_ADDONS = addon -> ((DynamicAddon) addon).getEnabled();
    public static final Predicate<MeteorAddon> DISABLED_ADDONS = addon -> !((DynamicAddon) addon).getEnabled();
}
