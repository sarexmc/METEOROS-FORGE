package com.meteoritos;

import net.minecraft.server.MinecraftServer;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.server.ServerStoppingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.server.ServerLifecycleHooks;

@Mod.EventBusSubscriber(modid = MeteoritosMod.MOD_ID)
public final class MeteorEvents {
    private MeteorEvents() {}

    @SubscribeEvent
    public static void onServerTick(TickEvent.ServerTickEvent event) {
        if (event.phase != TickEvent.Phase.END) return;
        MinecraftServer server = ServerLifecycleHooks.getCurrentServer();
        if (server != null) MeteorManager.tick(server);
    }

    @SubscribeEvent
    public static void onCommands(RegisterCommandsEvent event) {
        MeteorCommands.register(event.getDispatcher());
    }

    @SubscribeEvent
    public static void onStopping(ServerStoppingEvent event) {
        MeteorManager.reset();
    }
}
