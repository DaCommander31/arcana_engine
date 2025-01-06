package net.dacommander31.ae.event;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;

public class ServerEvents {
    public void registerServerEvents() {
        ServerTickEvents.END_WORLD_TICK.register((world -> {

        }));
    }
}
