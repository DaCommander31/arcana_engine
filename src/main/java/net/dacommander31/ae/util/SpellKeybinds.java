package net.dacommander31.ae.util;

import net.dacommander31.ae.ArcanaEngine;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

public class SpellKeybinds {
    public static KeyBinding activation1Keybind = KeyBindingHelper.registerKeyBinding(new KeyBinding(
            "key.ae.activation1",
            InputUtil.Type.KEYSYM,
            GLFW.GLFW_KEY_V,
            "category.ae.spells"
    ));
    public static KeyBinding activation2Keybind = KeyBindingHelper.registerKeyBinding(new KeyBinding(
            "key.ae.activation2",
            InputUtil.Type.KEYSYM,
            GLFW.GLFW_KEY_G,
            "category.ae.spells"
    ));
    public static KeyBinding activation3Keybind = KeyBindingHelper.registerKeyBinding(new KeyBinding(
            "key.ae.activation3",
            InputUtil.Type.KEYSYM,
            GLFW.GLFW_KEY_Y,
            "category.ae.spells"
    ));
    public static KeyBinding activation4Keybind = KeyBindingHelper.registerKeyBinding(new KeyBinding(
            "key.ae.activation4",
            InputUtil.Type.KEYSYM,
            GLFW.GLFW_KEY_H,
            "category.ae.spells"
    ));
    public static KeyBinding activation5Keybind = KeyBindingHelper.registerKeyBinding(new KeyBinding(
            "key.ae.activation5",
            InputUtil.Type.KEYSYM,
            GLFW.GLFW_KEY_B,
            "category.ae.spells"
    ));

    public static void registerKeybinds() {
        ArcanaEngine.LOGGER.info("Registering Keybinds for " + ArcanaEngine.MOD_ID);
    }
}