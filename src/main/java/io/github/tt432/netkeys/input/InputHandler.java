package io.github.tt432.netkeys.input;

import io.github.tt432.netkeys.KeysRegistry;
import io.github.tt432.netkeys.network.KeyPacket;
import io.github.tt432.netkeys.network.NKNetHandler;
import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.lwjgl.glfw.GLFW;

/**
 * @author TT432
 */
@Mod.EventBusSubscriber(Dist.CLIENT)
public class InputHandler {
    @SubscribeEvent
    public static void onEvent(InputEvent.Key event) {
        if (Minecraft.getInstance().level != null
                && event.getAction() == GLFW.GLFW_RELEASE
                && KeysRegistry.searchCommand(event.getModifiers(), event.getKey()) != null) {
            NKNetHandler.INSTANCE.sendToServer(new KeyPacket(NKKeyParseHandler.getModName(event.getModifiers()), NKKeyParseHandler.getKeyName(event.getKey())));
        }
    }
}
