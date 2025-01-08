package net.dacommander31.ae.util;

import net.minecraft.entity.Entity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;

import java.util.List;

public abstract class SpellType {

    public abstract void applyEffect(Entity caster, Vec3d pos, ServerWorld world, Entity target);

    public List<Entity> getTargets(Vec3d origin, ServerWorld world, double areaOfEffect) {
        Box boundingBox = new Box(
                origin.subtract(areaOfEffect, areaOfEffect, areaOfEffect),
                origin.add(areaOfEffect, areaOfEffect, areaOfEffect)
        );

        return (world.getEntitiesByClass(Entity.class, boundingBox, entity ->
                entity.isAlive() && !entity.isSpectator()
        ));
    }
}