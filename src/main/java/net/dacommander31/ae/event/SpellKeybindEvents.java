package net.dacommander31.ae.event;

import net.dacommander31.ae.util.SpellKeybinds;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.text.Text;

public class SpellKeybindEvents {
    public static void registerKeybindEvents() {
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (SpellKeybinds.activation1Keybind.wasPressed()) {
                assert client.player != null;
                client.player.sendMessage(Text.literal("Activation 1 Pressed"));
            }
        });
    }
}
