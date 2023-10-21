package io.github.tt432.netkeys.network;

import io.github.tt432.netkeys.KeysRegistry;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.Map;
import java.util.function.Supplier;

/**
 * @author TT432
 */
public class ConfigSyncPacket {
    Map<String, String> content;

    public ConfigSyncPacket(Map<String, String> content) {
        this.content = content;
    }

    public void encoder(FriendlyByteBuf buffer) {
        buffer.writeMap(content, FriendlyByteBuf::writeUtf, FriendlyByteBuf::writeUtf);
    }

    public static ConfigSyncPacket decoder(FriendlyByteBuf buffer) {
        return new ConfigSyncPacket(buffer.readMap(FriendlyByteBuf::readUtf, FriendlyByteBuf::readUtf));
    }

    public void messageConsumer(Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> KeysRegistry.setupClient(content));
        ctx.get().setPacketHandled(true);
    }
}
