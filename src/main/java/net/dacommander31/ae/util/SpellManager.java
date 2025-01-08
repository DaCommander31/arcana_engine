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

    public static void addSpell(Entity caster, SpellBuilder spellBuilder) {
        Identifier spellId = spellBuilder.getSpellId();

        if (isSpellOnCooldown(caster, spellId)) {
            return;
        }

        if (spellBuilder.getDuration() > 0) {
            TickableSpell tickableSpell = new TickableSpell(caster, spellBuilder);
            activeSpells.computeIfAbsent(caster, k -> new ArrayList<>()).add(tickableSpell);
        }

        try {
            SpellRegistry.getSpellBehavior(spellId).cast(caster, spellBuilder);
        } catch (SpellNotFoundException e) {
            throw new RuntimeException(e);
        }
        setCooldown(caster, spellId, spellBuilder.getCooldown());
    }

    public static void removeSpell(Entity caster, SpellBuilder spellBuilder) {
        if (activeSpells.containsKey(caster)) {
            List<TickableSpell> tickableSpells = activeSpells.get(caster);

            for (int i = tickableSpells.size() - 1; i >= 0; i--) {
                TickableSpell tickableSpell = tickableSpells.get(i);

                if (tickableSpell.getSpellBuilder().equals(spellBuilder)) {
                    tickableSpells.remove(i);
                    break;
                }
            }

            if (tickableSpells.isEmpty()) {
                activeSpells.remove(caster);
            }
        }
    }

    public static void tick(World world) {
        activeSpells.forEach((casterEntity, tickableSpells) -> {
            updateSpellBehaviors(casterEntity, (ServerWorld) world);
            updateActiveSpells(casterEntity, (ServerWorld) world);
            updateCooldowns(casterEntity);
        });
    }

    private static void updateSpellBehaviors(Entity caster, ServerWorld world) {
        SpellRegistry.getRegistry().forEach((spellId, spellBuilder) -> {
            try {
                SpellBehavior spellBehavior = SpellRegistry.getSpellBehavior(spellId);
                if (spellBuilder.isTickable()) {
                    activeSpells.forEach((casterEntity, tickableSpells) -> {
                        if (casterEntity.equals(caster)) {
                            for (int i = tickableSpells.size() - 1; i >= 0; i--) {
                                spellBehavior.executeSpellBehavior(caster, spellBuilder.getOffset(), world);
                            }
                        }
                    });

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
                    tickableSpell.tick(world);
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


    public static boolean isSpellOnCooldown(Entity caster, Identifier spellId) {
        return getCooldown(caster, spellId) > 0;
    }

    private static int getCooldown(Entity caster, Identifier spellId) {
        Map<Identifier, Integer> cooldownMap = casterCooldowns.get(caster);
        return cooldownMap != null ? cooldownMap.getOrDefault(spellId, 0) : 0;
    }

    private static void setCooldown(Entity caster, Identifier spellId, int cooldownTicks) {
        casterCooldowns.computeIfAbsent(caster, k -> new HashMap<>()).put(spellId, cooldownTicks);
    }
}
