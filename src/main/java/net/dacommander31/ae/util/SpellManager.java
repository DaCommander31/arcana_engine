package net.dacommander31.ae.util;

import net.dacommander31.ae.lang.SpellNotFoundException;
import net.dacommander31.ae.registry.SpellRegistry;
import net.minecraft.entity.Entity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SpellManager {
    private static final Map<Entity, Map<Identifier, Integer>> casterCooldowns = new HashMap<>();
    private static final Map<Entity, List<TickableSpell>> activeSpells = new HashMap<>();

    public void addSpell(Entity caster, SpellBuilder spellBuilder) {
        Identifier spellId = spellBuilder.getSpellId();

        if (isSpellOnCooldown(caster, spellId)) {
            return;
        }

        if (spellBuilder.getDuration() > 0) {
            TickableSpell tickableSpell = new TickableSpell(caster, spellBuilder);
            activeSpells.computeIfAbsent(caster, k -> new ArrayList<>()).add(tickableSpell);
        }

        setCooldown(caster, spellId, spellBuilder.getCooldown());
    }

    public static void tick(Entity caster, World world, float areaOfEffect) {
        updateSpellBehaviors(caster, (ServerWorld) world, areaOfEffect);
        updateActiveSpells(caster, (ServerWorld) world);
        updateCooldowns(caster);
    }

    private static void updateSpellBehaviors(Entity caster, ServerWorld world, float areaOfEffect) {
        SpellRegistry.getRegistry().forEach((spellId, spellBuilder) -> {
            try {
                SpellBehavior spellBehavior = SpellRegistry.getSpellBehavior(spellId);
                if (spellBuilder.isTickable()) {
                    spellBehavior.executeSpellBehavior(caster, spellBuilder.getOffset(), world, areaOfEffect);
                }
            } catch (SpellNotFoundException e) {
                throw new RuntimeException(e);
            }
        });
    }

    private static void updateActiveSpells(Entity caster, ServerWorld world) {
        activeSpells.forEach((casterEntity, tickableSpells) -> {
            if (casterEntity.equals(caster)) {
                for (int i = tickableSpells.size() - 1; i >= 0; i--) {
                    TickableSpell tickableSpell = tickableSpells.get(i);
                    tickableSpell.tick((ServerWorld) world);
                    if (tickableSpell.isExpired()) {
                        tickableSpells.remove(i);
                    }
                }
            }
        });
    }

    private static void updateCooldowns(Entity caster) {
        casterCooldowns.forEach((casterEntity, cooldownMap) -> {
            if (casterEntity.equals(caster)) {
                cooldownMap.forEach((spellId, cooldown) -> {
                    if (cooldown > 0) {
                        cooldownMap.put(spellId, cooldown - 1);  // Decrease cooldown by 1 tick
                    }
                });
            }
        });
    }


    public boolean isSpellOnCooldown(Entity caster, Identifier spellId) {
        return getCooldown(caster, spellId) > 0;
    }

    private int getCooldown(Entity caster, Identifier spellId) {
        Map<Identifier, Integer> cooldownMap = casterCooldowns.get(caster);
        return cooldownMap != null ? cooldownMap.getOrDefault(spellId, 0) : 0;
    }

    private void setCooldown(Entity caster, Identifier spellId, int cooldownTicks) {
        casterCooldowns.computeIfAbsent(caster, k -> new HashMap<>()).put(spellId, cooldownTicks);
    }
}
