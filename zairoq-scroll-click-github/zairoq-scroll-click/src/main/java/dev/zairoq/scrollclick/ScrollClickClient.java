package dev.zairoq.scrollclick;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.lwjgl.glfw.GLFW;

/**
 * Client initializer for Zairoq Scroll Click.
 * The feature starts enabled. Its toggle can be assigned in Options -> Controls.
 */
public final class ScrollClickClient implements ClientModInitializer {
    private static boolean enabled = true;
    private static KeyBinding toggleKey;

    @Override
    public void onInitializeClient() {
        KeyBinding.Category category = KeyBinding.Category.register(
                Identifier.of("zairoq_scrollclick", "general")
        );

        toggleKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.zairoq_scrollclick.toggle",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_UNKNOWN,
                category
        ));

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (toggleKey.wasPressed()) {
                enabled = !enabled;
                if (client.player != null) {
                    client.player.sendMessage(
                            Text.literal("Scroll Click: " + (enabled ? "ON" : "OFF")),
                            true
                    );
                }
            }
        });
    }

    public static boolean isEnabled() {
        return enabled;
    }
}
