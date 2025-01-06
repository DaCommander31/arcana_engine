package net.dacommander31.ae.util;

import net.dacommander31.ae.event.SpellCastCallback;
import net.dacommander31.ae.registry.SpellRegistry;
import net.minecraft.entity.Entity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public abstract class SpellBehavior {
    public abstract void executeSpellBehavior(Entity caster, Vec3d offset, World world);
    public void cast(Entity caster, SpellBuilder spell) {
        SpellCastCallback.EVENT.invoker().onSpellCast(caster, spell);
    }
}
