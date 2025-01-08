package net.dacommander31.ae.event;

import net.dacommander31.ae.util.SpellBuilder;
import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.entity.Entity;

public interface SpellFinishCallback {
    Event<SpellFinishCallback> EVENT = EventFactory.createArrayBacked(SpellFinishCallback.class,
            (listeners) -> (caster, spell) -> {
                for (SpellFinishCallback listener : listeners) {
                    listener.onSpellFinish(caster, spell);
                }
            }
    );

    void onSpellFinish(Entity caster, SpellBuilder spell);
}
