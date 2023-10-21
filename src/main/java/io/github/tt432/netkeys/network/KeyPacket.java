package io.github.tt432.netkeys.network;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import io.github.tt432.netkeys.KeysRegistry;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

/**
 * @author TT432
 */
public class KeyPacket {
    String modifiers;
    String keycode;

    public KeyPacket(String modifiers, String keycode) {
        this.modifiers = modifiers;
        this.keycode = keycode;

        if (modifiers == null) this.modifiers = "";
        if (keycode == null) this.keycode = "";
    }

    public void encoder(FriendlyByteBuf buffer) {
        buffer.writeUtf(modifiers);
        buffer.writeUtf(keycode);
    }

    public static KeyPacket decoder(FriendlyByteBuf buffer) {
        return new KeyPacket(buffer.readUtf(), buffer.readUtf());
    }

    public void messageConsumer(Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            ServerPlayer sender = ctx.get().getSender();
            String s = KeysRegistry.searchCommand(modifiers, keycode);

            if (s != null && sender != null) {
                MinecraftServer server = sender.server;

                try {
                    server.getCommands().getDispatcher().execute(s, server.createCommandSourceStack());
                } catch (CommandSyntaxException e) {
                    e.printStackTrace();
                }
            }
        });
        ctx.get().setPacketHandled(true);
    }
}
