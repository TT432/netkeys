package io.github.tt432.netkeys.network;

import io.github.tt432.netkeys.Netkeys;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;

/**
 * @author TT432
 */
public class NKNetHandler {
    private static final String PROTOCOL_VERSION = "1";

    public static final SimpleChannel INSTANCE = NetworkRegistry.newSimpleChannel(
            new ResourceLocation(Netkeys.MOD_ID, "main"),
            () -> PROTOCOL_VERSION,
            PROTOCOL_VERSION::equals,
            PROTOCOL_VERSION::equals
    );

    static int id;

    public static void setup() {
        INSTANCE.messageBuilder(KeyPacket.class, id++)
                .encoder(KeyPacket::encoder)
                .decoder(KeyPacket::decoder)
                .consumerMainThread(KeyPacket::messageConsumer)
                .add();

        INSTANCE.messageBuilder(ConfigSyncPacket.class, id++)
                .encoder(ConfigSyncPacket::encoder)
                .decoder(ConfigSyncPacket::decoder)
                .consumerMainThread(ConfigSyncPacket::messageConsumer)
                .add();
    }
}
