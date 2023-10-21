package io.github.tt432.netkeys;

import io.github.tt432.netkeys.input.KeyDef;
import io.github.tt432.netkeys.input.NKKeyParseHandler;
import io.github.tt432.netkeys.network.ConfigSyncPacket;
import io.github.tt432.netkeys.network.NKNetHandler;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.network.PacketDistributor;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

/**
 * @author TT432
 */
@Mod.EventBusSubscriber
public class KeysRegistry {
    public record ServerKeyDef(
            String modifiers,
            String keyCode
    ) {
    }

    /**
     * server / client
     */
    private static final Map<ServerKeyDef, String> strDefKeys = new HashMap<>();

    /**
     * client
     */
    private static final Map<KeyDef, String> intDefKeys = new HashMap<>();

    /**
     * @param event server
     */
    @SubscribeEvent
    public static void onEvent(PlayerEvent.PlayerLoggedInEvent event) {
        strDefKeys.clear();
        NKConfig.getKeys().forEach((key, value) -> strDefKeys.put(parse(key), value));
        NKNetHandler.INSTANCE.send(PacketDistributor.PLAYER.with(() -> (ServerPlayer) event.getEntity()), new ConfigSyncPacket(NKConfig.getKeys()));
    }

    public static void setupClient(Map<String, String> content) {
        intDefKeys.clear();
        intDefKeys.putAll(NKKeyParseHandler.setupAllKeys(content));
        content.forEach((key, value) -> strDefKeys.put(parse(key), value));
    }

    private static ServerKeyDef parse(String keyStr) {
        String[] split = keyStr.split("\\+");

        if (split.length == 1) {
            return new ServerKeyDef("", split[0].trim());
        } else if (split.length == 2) {
            return new ServerKeyDef(split[0].trim(), split[1].trim());
        } else {
            return null;
        }
    }

    @Nullable
    public static String searchCommand(int modifiers, int keycode) {
        return intDefKeys.get(new KeyDef(modifiers, keycode));
    }

    @Nullable
    public static String searchCommand(String modifiers, String keycode) {
        return strDefKeys.get(new ServerKeyDef(modifiers, keycode));
    }
}
