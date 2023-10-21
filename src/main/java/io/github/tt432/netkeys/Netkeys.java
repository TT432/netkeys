package io.github.tt432.netkeys;

import io.github.tt432.netkeys.network.NKNetHandler;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;

@Mod(Netkeys.MOD_ID)
public class Netkeys {
    public static final String MOD_ID = "netkeys";

    public Netkeys() {
        ModLoadingContext.get().registerConfig(ModConfig.Type.SERVER, NKConfig.SPEC);
        NKNetHandler.setup();
    }
}
