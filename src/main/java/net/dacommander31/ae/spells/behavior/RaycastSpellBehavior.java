package net.dacommander31.ae.spells.behavior;

import net.dacommander31.ae.lang.SpellNotFoundException;
import net.dacommander31.ae.registry.SpellRegistry;
import net.dacommander31.ae.util.*;
import net.dacommander31.ae.util.filter.BlockFilter;
import net.dacommander31.ae.util.filter.EntityFilter;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

import java.util.List;

public class RaycastSpellBehavior extends SpellBehavior {
    private final float maxDistance;
    private final float speed;
    private final BlockFilter blockFilter;
    private final EntityFilter entityFilter;
    private final boolean includeFluids;
    private final ParticleEffectData particleEffectData;
    private List<Entity> targets;
    private Vec3d currentPosition;
    private float distanceTraveled;

    public RaycastSpellBehavior(float maxDistance, float speed, BlockFilter blockFilter, EntityFilter entityFilter, boolean includeFluids, ParticleEffectData particleEffectData) {
        this.maxDistance = maxDistance;
        this.speed = speed;
        this.blockFilter = blockFilter;
        this.entityFilter = entityFilter;
        this.includeFluids = includeFluids;
        this.particleEffectData = particleEffectData;
        this.currentPosition = null;
        this.distanceTraveled = 0;
    }
    public RaycastSpellBehavior(float maxDistance, float speed, BlockFilter blockFilter, EntityFilter entityFilter, ParticleEffectData particleEffectData) {
        this(maxDistance, speed, blockFilter, entityFilter, false, particleEffectData);
    }

    @Override
    public void executeSpellBehavior(Entity caster, Vec3d offset, ServerWorld world) {
        if (world.isClient()) return;

        try {
            if (this.currentPosition == null) {
                this.currentPosition = caster.getPos().add(offset);
            }

            spawnParticles(world, this.currentPosition);

            SpellBuilder spellBuilder = SpellRegistry.getSpell(SpellRegistry.getSpellBehaviorId(this));
            SpellType spellType = SpellRegistry.getSpellTypeFromBehavior(this);

            Vec3d direction = caster.getRotationVector().normalize().multiply(this.speed);
            this.currentPosition = this.currentPosition.add(direction);
            this.distanceTraveled += speed;

            BlockPos blockPos = new BlockPos((int) currentPosition.x, (int) currentPosition.y, (int) currentPosition.z);
            if (blockFilter.shouldIncludeBlock(world, blockPos)) {
                super.endSpellBehavior(caster, spellBuilder);
                return;
            }

            for (Entity entity : this.targets) {
                if (entityFilter.shouldIncludeEntity(entity) && entity.getBoundingBox().contains(currentPosition)) {
                    spellType.applyEffect(caster, currentPosition, world, spellBuilder, entity);
                }
            }

            if (distanceTraveled >= maxDistance) {
                super.endSpellBehavior(caster, spellBuilder);
            }

        } catch (SpellNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void cast(Entity caster, SpellBuilder spell) {
        entityFilter.ignoreEntity(caster);

        super.cast(caster, spell);
    }

    @Override
    public void setTargets(List<Entity> targets) {
        this.targets = targets;
    }

    @Override
    public List<Entity> getTargets() {
        return this.targets;
    }

    @Override
    public void spawnParticles(ServerWorld world, Vec3d pos) {
        if (this.particleEffectData.isVisible()) {
            List<PlayerEntity> players = particleEffectData.getViewers(world);
            for (PlayerEntity player : players) {
                world.spawnParticles(
                        (ServerPlayerEntity) player,
                        particleEffectData.getParticleEffect(),
                        particleEffectData.isForce(),
                        pos.x, pos.y, pos.z,
                        particleEffectData.getCount(),
                        particleEffectData.getDelta().x,
                        particleEffectData.getDelta().y,
                        particleEffectData.getDelta().z,
                        particleEffectData.getSpeed()
                );
            }
        }
    }

    public float getMaxDistance() {
        return this.maxDistance;
    }

    public float getSpeed() {
        return this.speed;
    }

    public boolean doesIncludeFluids() {
        return this.includeFluids;
    }
}
