package io.github.racoondog.dynamicaddons.command;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import io.github.racoondog.dynamicaddons.utils.AddonUtils;
import meteordevelopment.meteorclient.addons.MeteorAddon;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.command.CommandSource;
import net.minecraft.text.Text;

import java.util.concurrent.CompletableFuture;
import java.util.function.Predicate;

@Environment(EnvType.CLIENT)
public class AddonArgumentType implements ArgumentType<MeteorAddon> {
    private static final DynamicCommandExceptionType NO_SUCH_ADDON = new DynamicCommandExceptionType(name -> Text.literal("Could not find tracked addon with name " + name + "."));
    private final Predicate<MeteorAddon> filter;

    public AddonArgumentType(Predicate<MeteorAddon> filter) {
        this.filter = filter;
    }


    public static AddonArgumentType create(Predicate<MeteorAddon> filter) {
        return new AddonArgumentType(filter);
    }

    public static AddonArgumentType create() {
        return create(a -> true);
    }

    public static MeteorAddon get(CommandContext<?> context) {
        return context.getArgument("addon", MeteorAddon.class);
    }

    @Override
    public MeteorAddon parse(StringReader reader) throws CommandSyntaxException {
        String argument = reader.readString();
        MeteorAddon addon = AddonUtils.fromName(argument);
        if (addon == null) throw NO_SUCH_ADDON.create(argument);
        return addon;
    }

    @Override
    public <S> CompletableFuture<Suggestions> listSuggestions(CommandContext<S> context, SuggestionsBuilder builder) {
        return CommandSource.suggestMatching(AddonUtils.get(this.filter), builder, addon -> addon.name, null);
    }
}
