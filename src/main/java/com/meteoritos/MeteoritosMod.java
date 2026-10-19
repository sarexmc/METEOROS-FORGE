package com.meteoritos;

import net.minecraftforge.fml.IExtensionPoint;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.network.NetworkConstants;

@Mod(MeteoritosMod.MOD_ID)
public class MeteoritosMod {
    public static final String MOD_ID = "meteoritos";

    public MeteoritosMod() {
        // Mod solo de servidor: los clientes sin el mod pueden entrar igual
        ModLoadingContext.get().registerExtensionPoint(IExtensionPoint.DisplayTest.class,
                () -> new IExtensionPoint.DisplayTest(() -> NetworkConstants.IGNORESERVERONLY, (remote, isServer) -> true));
    }
}
