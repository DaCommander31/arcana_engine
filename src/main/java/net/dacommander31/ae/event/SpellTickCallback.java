package net.dacommander31.ae.event;

import net.dacommander31.ae.util.SpellBuilder;
import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;

public interface SpellTickCallback {
    Event<SpellTickCallback> EVENT = EventFactory.createArrayBacked(SpellTickCallback.class,
            (listeners) -> (spell) -> {
                for (SpellTickCallback listener : listeners) {
                    listener.onSpellTick(spell);
                }
            }
    );

    void onSpellTick(SpellBuilder spell);
}
