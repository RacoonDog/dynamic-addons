package io.github.racoondog.dynamicaddons.mixin;

import io.github.racoondog.dynamicaddons.DynamicAddons;
import io.github.racoondog.dynamicaddons.utils.AddonUtils;
import meteordevelopment.meteorclient.systems.hud.Hud;
import meteordevelopment.meteorclient.systems.hud.HudElementInfo;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Environment(EnvType.CLIENT)
@Mixin(value = Hud.class, remap = false)
public abstract class HudMixin {
    @Inject(method = "register", at = @At("HEAD"))
    private void trackHudElement(HudElementInfo<?> info, CallbackInfo ci) {
        AddonUtils.trackHudElement(info, DynamicAddons.STACK_WALKER.getCallerClass().getName());
    }
}
