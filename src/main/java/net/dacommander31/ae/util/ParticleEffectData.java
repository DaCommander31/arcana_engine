package net.dacommander31.ae.util;

import net.dacommander31.ae.util.filter.PlayerFilter;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.Vec3d;

import java.util.List;
import java.util.stream.Collectors;

public class ParticleEffectData {
    private ParticleEffect particleEffect;
    private Vec3d delta;
    private int count;
    private float speed;
    private boolean isForce;
    private boolean visible;
    private PlayerFilter viewers;

    public ParticleEffectData(ParticleEffect particleEffect, Vec3d delta, int count, float speed, boolean force, boolean visible, PlayerFilter viewers) {
        this.particleEffect = particleEffect;
        this.delta = delta;
        this.count = count;
        this.speed = speed;
        this.isForce = force;
        this.visible = visible;
        this.viewers = viewers;
    }

    public ParticleEffectData setParticleEffect(ParticleEffect particleEffect) {
        this.particleEffect = particleEffect;
        return this;
    }

    public ParticleEffectData setDelta(Vec3d delta) {
        this.delta = delta;
        return this;
    }

    public ParticleEffectData setCount(int count) {
        this.count = count;
        return this;
    }

    public ParticleEffectData setSpeed(float speed) {
        this.speed = speed;
        return this;
    }

    public ParticleEffectData setForce(boolean isForce) {
        this.isForce = isForce;
        return this;
    }

    public ParticleEffectData setVisible(boolean visible) {
        this.visible = visible;
        return this;
    }

    public ParticleEffectData setViewers(PlayerFilter viewers) {
        this.viewers = viewers;
        return this;
    }

    public ParticleEffect getParticleEffect() {
        return particleEffect;
    }

    public Vec3d getDelta() {
        return delta;
    }

    public int getCount() {
        return count;
    }

    public float getSpeed() {
        return speed;
    }

    public boolean isForce() {
        return isForce;
    }

    public boolean isVisible() {
        return visible;
    }

    public List<PlayerEntity> getViewers(ServerWorld world) {
        return world.getPlayers().stream().filter(viewers::shouldIncludePlayer).collect(Collectors.toList());
    }
}
