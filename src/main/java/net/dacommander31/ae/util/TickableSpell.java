package net.dacommander31.ae.util;

import net.dacommander31.ae.event.SpellTickCallback;
import net.dacommander31.ae.lang.SpellNotFoundException;
import net.dacommander31.ae.registry.SpellRegistry;
import net.minecraft.entity.Entity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.Vec3d;

import java.util.List;

public class TickableSpell {
    private final Entity caster;
    private final Vec3d offset;
    private final SpellBuilder spellBuilder;
    private final SpellType spellType;
    private final SpellBehavior spellBehavior;
    private int ticksElapsed;

    public TickableSpell(Entity caster, SpellBuilder spellBuilder) {
        this.caster = caster;
        this.spellBuilder = spellBuilder;
        this.offset = this.spellBuilder.getOffset();
        this.ticksElapsed = 0;
        try {
            this.spellType = SpellRegistry.getSpellType(spellBuilder.getSpellId());
            this.spellBehavior = SpellRegistry.getSpellBehavior(spellBuilder.getSpellId());
        } catch (SpellNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void tick(ServerWorld world) {
        Vec3d origin = caster.getPos().add(offset);

        double currentAreaOfEffect = spellBuilder.getAreaOfEffect() + spellBuilder.getAreaGrowthPerTick() * ticksElapsed;
        List<Entity> targets = spellType.getTargets(origin, world, currentAreaOfEffect);

        spellBehavior.setTargets(targets);

        SpellTickCallback.EVENT.invoker().onSpellTick(spellBuilder);

        ticksElapsed ++;
    }

    public boolean isExpired() {
        return ticksElapsed >= spellBuilder.getDuration();
    }

}
