package io.github.racoondog.dynamicaddons;

import com.mojang.logging.LogUtils;
import io.github.racoondog.dynamicaddons.command.DynamicAddonCommand;
import meteordevelopment.meteorclient.addons.MeteorAddon;
import meteordevelopment.meteorclient.systems.commands.Commands;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import org.slf4j.Logger;

@Environment(EnvType.CLIENT)
public class DynamicAddons extends MeteorAddon {
    public static final Logger LOG = LogUtils.getLogger();
    public static final StackWalker STACK_WALKER = StackWalker.getInstance(StackWalker.Option.RETAIN_CLASS_REFERENCE);

    @Override
    public void onInitialize() {
        Commands.get().add(new DynamicAddonCommand());
    }

    @Override
    public String getPackage() {
        return "io.github.racoondog.dynamicaddons";
    }
}
