package io.github.racoondog.dynamicaddons.mixin;

import io.github.racoondog.dynamicaddons.DynamicAddons;
import io.github.racoondog.dynamicaddons.utils.AddonUtils;
import meteordevelopment.meteorclient.systems.modules.Module;
import meteordevelopment.meteorclient.systems.modules.Modules;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Environment(EnvType.CLIENT)
@Mixin(value = Modules.class, remap = false)
public abstract class ModulesMixin {
    @Inject(method = "add", at = @At("HEAD"))
    private void trackModule(Module _module, CallbackInfo ci) {
        AddonUtils.trackModule(_module, DynamicAddons.STACK_WALKER.getCallerClass().getName());
    }
}
