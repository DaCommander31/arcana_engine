package net.dacommander31.ae.event;

import net.dacommander31.ae.lang.SpellNotFoundException;
import net.dacommander31.ae.registry.SpellRegistry;
import net.dacommander31.ae.util.SpellBehavior;
import net.dacommander31.ae.util.SpellBuilder;
import net.dacommander31.ae.util.SpellManager;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;

public class ServerEvents {
    public static void registerServerEvents() {
        ServerTickEvents.END_WORLD_TICK.register((world -> {
            for (SpellBuilder spell : SpellRegistry.getRegistry().values()) {
                if (spell.isTickable()) {
                    try {
                        SpellBehavior spellBehavior = SpellRegistry.getSpellBehavior(spell.getSpellId());
                        SpellManager.tick(world);

                    } catch (SpellNotFoundException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }));
    }
}
