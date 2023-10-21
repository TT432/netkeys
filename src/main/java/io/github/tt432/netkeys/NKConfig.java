package io.github.tt432.netkeys;

import com.electronwill.nightconfig.core.CommentedConfig;
import com.electronwill.nightconfig.toml.TomlFormat;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.config.ModConfigEvent;

import java.util.HashMap;
import java.util.Map;

@Mod.EventBusSubscriber(modid = Netkeys.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class NKConfig {
    private static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();

    private static final ForgeConfigSpec.ConfigValue<CommentedConfig> KEYS_STRINGS = BUILDER
            .comment("Set of keys.")
            .define("keyMap", TomlFormat.newConfig(() -> new HashMap<>(Map.of("CONTROL + C", "say 1"))));

    static final ForgeConfigSpec SPEC = BUILDER.build();

    private static Map<String, String> keys;

    public static Map<String, String> getKeys() {
        return keys;
    }

    @SubscribeEvent
    public static void onLoad(final ModConfigEvent event) {
        keys = new HashMap<>();
        KEYS_STRINGS.get().valueMap().forEach((key, value) -> keys.put(key, value.toString()));
    }
}
