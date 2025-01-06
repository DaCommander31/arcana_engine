package net.dacommander31.ae.util;

import net.minecraft.entity.Entity;
import net.minecraft.world.World;

public abstract class SpellType {

    public abstract void applyEffect(Entity caster, World world, Entity target);

}