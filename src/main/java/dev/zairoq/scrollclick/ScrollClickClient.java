package dev.zairoq.scrollclick;

import dev.zairoq.scrollclick.mixin.KeyBindingAccessor;
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
 * Scroll events are queued and converted to vanilla attack-key presses on client ticks.
 */
public final class ScrollClickClient implements ClientModInitializer {
    private static boolean enabled = true;
    private static KeyBinding toggleKey;
    private static int pendingClicks;

    @Override
    public void onInitializeClient() {
        KeyBinding.Category category = KeyBinding.Category.create(
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
                if (!enabled) {
                    pendingClicks = 0;
                }
                if (client.player != null) {
                    client.player.sendMessage(
                            Text.literal("Scroll Click: " + (enabled ? "ON" : "OFF")),
                            true
                    );
                }
            }

            if (!enabled || client.player == null || client.currentScreen != null) {
                pendingClicks = 0;
                return;
            }

            if (pendingClicks > 0) {
                // Match Fire Client's cadence: at most one queued click per client tick.
                pendingClicks = Math.min(3, pendingClicks - 1);
                KeyBinding.onKeyPressed(
                        ((KeyBindingAccessor) client.options.attackKey).zairoq$getBoundKey()
                );
            }
        });
    }

    public static boolean isEnabled() {
        return enabled;
    }

    public static void queueScrollClick() {
        // One click is emitted now; at most three additional clicks may remain queued.
        pendingClicks = Math.min(4, pendingClicks + 1);
    }
}
