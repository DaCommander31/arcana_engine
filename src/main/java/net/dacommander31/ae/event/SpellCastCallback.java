package net.dacommander31.ae.event;

import net.dacommander31.ae.util.SpellBuilder;
import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.entity.Entity;

public interface SpellCastCallback {
    Event<SpellCastCallback> EVENT = EventFactory.createArrayBacked(SpellCastCallback.class,
        (listeners) -> (caster, spell) -> {
            for (SpellCastCallback listener : listeners) {
                listener.onSpellCast(caster, spell);
            }
        }
    );

    void onSpellCast(Entity caster, SpellBuilder spell);
}
