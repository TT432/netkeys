package io.github.tt432.netkeys.input;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import org.jetbrains.annotations.Nullable;
import org.lwjgl.glfw.GLFW;

import java.util.HashMap;
import java.util.Map;

/**
 * @author TT432
 */
@Mod.EventBusSubscriber(value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class NKKeyParseHandler {
    private static final Map<String, Integer> keyMap = new HashMap<>();
    private static final Map<Integer, String> rKeyMap = new HashMap<>();

    private static final Map<String, Integer> modMap = new HashMap<>();
    private static final Map<Integer, String> rModMap = new HashMap<>();

    @Nullable
    public static String getModName(int modifiers) {
        return rModMap.get(modifiers);
    }

    @Nullable
    public static String getKeyName(int keycode) {
        return rKeyMap.get(keycode);
    }

    public static Map<KeyDef, String> setupAllKeys(Map<String, String> keysToCommand) {
        final Map<KeyDef, String> result = new HashMap<>();

        keysToCommand.forEach((key, value) -> result.put(parse(key), value));

        return result;
    }

    private static KeyDef parse(String keyStr) {
        String[] split = keyStr.split("\\+");

        if (split.length == 1) {
            return new KeyDef(0, keyMap.getOrDefault(split[0].trim(), 0));
        } else if (split.length == 2) {
            return new KeyDef(modMap.getOrDefault(split[0].trim(), 0), keyMap.getOrDefault(split[1].trim(), 0));
        } else {
            return null;
        }
    }

    @SubscribeEvent
    public static void onEvent(FMLClientSetupEvent event) {
        var fields = GLFW.class.getFields();

        for (var field : fields) {
            if (field.getName().startsWith("GLFW_KEY_")) {
                try {
                    keyMap.put(field.getName().replace("GLFW_KEY_", ""), field.getInt(null));
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }

            if (field.getName().startsWith("GLFW_MOD_")) {
                try {
                    modMap.put(field.getName().replace("GLFW_MOD_", ""), field.getInt(null));
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }

        for (Map.Entry<String, Integer> entry : keyMap.entrySet()) {
            rKeyMap.put(entry.getValue(), entry.getKey());
        }

        for (Map.Entry<String, Integer> entry : modMap.entrySet()) {
            rModMap.put(entry.getValue(), entry.getKey());
        }
    }
}
