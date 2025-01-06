package net.dacommander31.ae.spells.behavior;

import net.dacommander31.ae.lang.SpellNotFoundException;
import net.dacommander31.ae.registry.SpellRegistry;
import net.dacommander31.ae.util.BlockFilter;
import net.dacommander31.ae.util.SpellBehavior;
import net.dacommander31.ae.util.SpellType;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class RaycastSpellBehavior extends SpellBehavior {
    private final float maxDistance;
    private final float delta;
    private final boolean includeFluids;
    private final BlockFilter blockFilter;

    public RaycastSpellBehavior(float maxDistance, float delta, BlockFilter blockFilter, boolean includeFluids) {
        this.maxDistance = maxDistance;
        this.delta = delta;
        this.includeFluids = includeFluids;
        this.blockFilter = blockFilter;
    }
    public RaycastSpellBehavior(float maxDistance, float delta, BlockFilter blockFilter) {
        this.maxDistance = maxDistance;
        this.delta = delta;
        this.includeFluids = false;
        this.blockFilter = blockFilter;
    }

    @Override
    public void executeSpellBehavior(Entity caster, Vec3d offset, World world) {

        try {
            SpellType spellType = SpellRegistry.getSpellTypeFromBehavior(this);
        } catch (SpellNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public float getMaxDistance() {
        return maxDistance;
    }

    public float getDelta() {
        return delta;
    }

    public boolean doesIncludeFluids() {
        return includeFluids;
    }
}
