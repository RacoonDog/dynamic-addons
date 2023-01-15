package io.github.racoondog.dynamicaddons.mixin;

import meteordevelopment.meteorclient.systems.modules.Category;
import meteordevelopment.meteorclient.systems.modules.Modules;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.List;

@Environment(EnvType.CLIENT)
@Mixin(value = Modules.class, remap = false)
public interface IModules {
    @Accessor("CATEGORIES")
    static List<Category> getCategories() {
        throw new AssertionError();
    }
}
