package io.github.racoondog.dynamicaddons.command;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import io.github.racoondog.dynamicaddons.DynamicAddon;
import io.github.racoondog.dynamicaddons.utils.ArgumentUtils;
import meteordevelopment.meteorclient.addons.MeteorAddon;
import meteordevelopment.meteorclient.systems.commands.Command;
import meteordevelopment.meteorclient.utils.network.MeteorExecutor;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.command.CommandSource;

@Environment(EnvType.CLIENT)
public class DynamicAddonCommand extends Command {
    public DynamicAddonCommand() {
        super("addon", "Control dynamic addons.");
    }

    @Override
    public void build(LiteralArgumentBuilder<CommandSource> builder) {
        builder.then(literal("enable").then(argument("addon", AddonArgumentType.create(ArgumentUtils.DISABLED_ADDONS)).executes(this::enable)));
        builder.then(literal("disable").then(argument("addon", AddonArgumentType.create(ArgumentUtils.ENABLED_ADDONS)).executes(this::disable)));
        builder.then(literal("reload").then(argument("addon", AddonArgumentType.create()).executes(this::reload)));
    }

    private int enable(CommandContext<CommandSource> ctx) { //TODO keep tracking, add to meteor
        MeteorAddon addon = AddonArgumentType.get(ctx);
        DynamicAddon dynamic = (DynamicAddon) addon;

        if (dynamic.getEnabled()) {
            warning("Addon already enabled.");
            return 1;
        }

        info("Addon enabled.");
        return 1;
    }

    private int disable(CommandContext<CommandSource> ctx) { //TODO keep tracking, remove from meteor
        MeteorAddon addon = AddonArgumentType.get(ctx);
        DynamicAddon dynamic = (DynamicAddon) addon;

        if (!dynamic.getEnabled()) {
            warning("Addon already disabled.");
            return 1;
        }

        info("Addon disabled.");
        return 1;
    }

    private int reload(CommandContext<CommandSource> ctx) { //TODO for each tracked object, save NBT, remove from meteor, untrack, create new, load NBT, add to meteor, track
        info("Reloading addon.");

        MeteorExecutor.execute(() -> {
            MeteorAddon addon = AddonArgumentType.get(ctx);
            DynamicAddon dynamic = (DynamicAddon) addon;

            for (var module : dynamic.getModules()) module.fromTag(module.toTag());
            //for (var element : dynamic.getHudElements())

            info("Successfully reloaded addon.");
        });
        return 1;
    }
}
