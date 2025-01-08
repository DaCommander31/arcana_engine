package net.dacommander31.ae.spells.behavior;

import net.dacommander31.ae.lang.SpellNotFoundException;
import net.dacommander31.ae.registry.SpellRegistry;
import net.dacommander31.ae.util.*;
import net.minecraft.entity.Entity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.Vec3d;

import java.util.List;

public class RaycastSpellBehavior extends SpellBehavior {
    private final float maxDistance;
    private final float speed;
    private final BlockFilter blockFilter;
    private final EntityFilter entityFilter;
    private final boolean includeFluids;
    private List<Entity> targets;

    public RaycastSpellBehavior(float maxDistance, float speed, BlockFilter blockFilter, EntityFilter entityFilter, boolean includeFluids) {
        this.maxDistance = maxDistance;
        this.speed = speed;
        this.blockFilter = blockFilter;
        this.entityFilter = entityFilter;
        this.includeFluids = includeFluids;
    }
    public RaycastSpellBehavior(float maxDistance, float speed, BlockFilter blockFilter, EntityFilter entityFilter) {
        this(maxDistance, speed, blockFilter, entityFilter, false);
    }

    @Override
    public void executeSpellBehavior(Entity caster, Vec3d offset, ServerWorld world, float areaOfEffect) {
        if (world.isClient()) return;

        try {
            SpellType spellType = SpellRegistry.getSpellTypeFromBehavior(this);

            Vec3d start = caster.getPos().add(offset);
            Vec3d direction = caster.getRotationVector().normalize().multiply(speed);

            for (float distance = 0; distance <= maxDistance; distance += speed) {
                Vec3d currentPosition = start.add(direction.multiply(distance / speed));

                if (!blockFilter.shouldIgnoreBlock(world, caster.getBlockPos())) {
                    break;
                }
                for (Entity entity : this.targets)
                    spellType.applyEffect(caster, currentPosition, world, entity);
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
