package com.meteoritos;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.BoolArgumentType;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.phys.Vec3;

public final class MeteorCommands {
    private MeteorCommands() {}

    private static final String[] NAMES = {"Leve", "Moderada", "Fuerte", "Extrema", "APOCALIPSIS"};

    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("meteoritos")
                .requires(src -> src.hasPermission(2))

                .then(Commands.literal("iniciar")
                        .executes(ctx -> {
                            MeteorManager.start(ctx.getSource().getServer(), 2);
                            return 1;
                        })
                        .then(Commands.argument("minutos", IntegerArgumentType.integer(1, 180))
                                .executes(ctx -> {
                                    int min = IntegerArgumentType.getInteger(ctx, "minutos");
                                    MeteorManager.start(ctx.getSource().getServer(), min);
                                    return 1;
                                })))

                .then(Commands.literal("detener")
                        .executes(ctx -> {
                            MeteorManager.stop(ctx.getSource().getServer());
                            return 1;
                        }))

                .then(Commands.literal("intensidad")
                        .then(Commands.argument("nivel", IntegerArgumentType.integer(1, 5))
                                .executes(ctx -> {
                                    int n = IntegerArgumentType.getInteger(ctx, "nivel");
                                    MeteorManager.intensity = n;
                                    ctx.getSource().getServer().getPlayerList().broadcastSystemMessage(
                                            Component.literal("Intensidad de meteoritos: " + n + " - " + NAMES[n - 1])
                                                    .withStyle(ChatFormatting.RED, ChatFormatting.BOLD), false);
                                    return 1;
                                })))

                .then(Commands.literal("destruir")
                        .then(Commands.argument("activado", BoolArgumentType.bool())
                                .executes(ctx -> {
                                    boolean v = BoolArgumentType.getBool(ctx, "activado");
                                    MeteorManager.destroy = v;
                                    ctx.getSource().sendSuccess(() -> Component.literal(
                                            v ? "Las explosiones ahora rompen bloques." : "Las explosiones ya no rompen bloques."), true);
                                    return 1;
                                })))

                .then(Commands.literal("uno")
                        .executes(ctx -> {
                            CommandSourceStack src = ctx.getSource();
                            ServerPlayer p = src.getPlayerOrException();
                            ServerLevel w = src.getLevel();
                            Vec3 look = p.getLookAngle();
                            double hx = look.x, hz = look.z;
                            double len = Math.sqrt(hx * hx + hz * hz);
                            if (len < 1.0e-4) { hx = 1; hz = 0; len = 1; }
                            hx /= len;
                            hz /= len;
                            double min = MeteorManager.safeDistance(MeteorManager.intensity) + 8.0;
                            boolean ok = false;
                            double[] dists = {min + 30.0, min + 15.0, min};
                            for (double d : dists) {
                                double yaw = w.random.nextDouble() * Math.PI * 2.0;
                                if (MeteorManager.spawnAt(w, p.getX() + hx * d, p.getZ() + hz * d, yaw, MeteorManager.intensity)) {
                                    ok = true;
                                    break;
                                }
                            }
                            final boolean fok = ok;
                            src.sendSuccess(() -> Component.literal(fok
                                    ? "Meteorito en camino, mira al frente."
                                    : "No hay chunks cargados en esa direccion; prueba mirando hacia otro lado."), false);
                            return 1;
                        }))

                .then(Commands.literal("estado")
                        .executes(ctx -> {
                            String msg = "Lluvia: " + (MeteorManager.isActive() ? "activa (" + MeteorManager.secondsLeft() + " s restantes)" : "apagada")
                                    + " | Intensidad: " + MeteorManager.intensity
                                    + " | Romper bloques: " + (MeteorManager.destroy ? "si" : "no")
                                    + " | Meteoritos en vuelo: " + MeteorManager.flying();
                            ctx.getSource().sendSuccess(() -> Component.literal(msg), false);
                            return 1;
                        })));
    }
}
