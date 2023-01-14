package io.github.racoondog.dynamicaddons.mixin;

import io.github.racoondog.dynamicaddons.DynamicAddons;
import io.github.racoondog.dynamicaddons.utils.AddonUtils;
import meteordevelopment.meteorclient.systems.commands.Command;
import meteordevelopment.meteorclient.systems.commands.Commands;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Environment(EnvType.CLIENT)
@Mixin(value = Commands.class, remap = false)
public abstract class CommandsMixin {
    @Inject(method = "add", at = @At("HEAD"))
    private void trackCommand(Command command, CallbackInfo ci) {
        AddonUtils.trackCommand(command, DynamicAddons.STACK_WALKER.getCallerClass().getName());
    }
}
