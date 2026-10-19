package com.meteoritos;

import net.minecraft.world.phys.Vec3;

/** Un meteorito en vuelo. No es una entidad: se dibuja solo con particulas. */
public final class Meteor {
    public final Vec3 start;
    public final Vec3 end;
    public final int total;
    public final int lvl;
    public int age = 0;

    public Meteor(Vec3 start, Vec3 end, int total, int lvl) {
        this.start = start;
        this.end = end;
        this.total = total;
        this.lvl = lvl;
    }

    public Vec3 pos() {
        return start.lerp(end, (double) age / (double) total);
    }

    public Vec3 dir() {
        return end.subtract(start).normalize();
    }
}
