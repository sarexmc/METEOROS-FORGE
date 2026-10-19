package com.meteoritos;

import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import org.joml.Vector3f;

/** Particulas enviadas a larga distancia para que se vean desde lejos. */
public final class Fx {
    private Fx() {}

    public static final DustParticleOptions ORANGE = new DustParticleOptions(new Vector3f(1.0f, 0.42f, 0.06f), 4.0f);
    public static final DustParticleOptions YELLOW = new DustParticleOptions(new Vector3f(1.0f, 0.78f, 0.22f), 4.0f);
    public static final DustParticleOptions RED = new DustParticleOptions(new Vector3f(0.72f, 0.07f, 0.03f), 4.0f);
    public static final DustParticleOptions SOOT = new DustParticleOptions(new Vector3f(0.20f, 0.17f, 0.15f), 4.0f);
    public static final DustParticleOptions ASHGREY = new DustParticleOptions(new Vector3f(0.48f, 0.44f, 0.40f), 4.0f);
    public static final DustParticleOptions HAZE = new DustParticleOptions(new Vector3f(0.85f, 0.18f, 0.05f), 4.0f);

    private static final double RANGE = 450.0;

    public static <T extends ParticleOptions> void p(ServerLevel w, T type, double x, double y, double z,
                                                     int count, double dx, double dy, double dz, double speed) {
        for (ServerPlayer pl : w.players()) {
            double ax = pl.getX() - x;
            double ay = pl.getY() - y;
            double az = pl.getZ() - z;
            if (ax * ax + ay * ay + az * az < RANGE * RANGE) {
                w.sendParticles(pl, type, true, x, y, z, count, dx, dy, dz, speed);
            }
        }
    }

    public static boolean loaded(ServerLevel w, double x, double z) {
        int cx = ((int) Math.floor(x)) >> 4;
        int cz = ((int) Math.floor(z)) >> 4;
        return w.getChunkSource().hasChunk(cx, cz);
    }
}
