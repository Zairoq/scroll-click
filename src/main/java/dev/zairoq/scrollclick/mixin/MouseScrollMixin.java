package dev.zairoq.scrollclick.mixin;

import dev.zairoq.scrollclick.ScrollClickClient;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.Mouse;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/** Converts vertical wheel movement into vanilla left-button input while enabled. */
@Mixin(Mouse.class)
public abstract class MouseScrollMixin {
    private static final long MIN_CLICK_INTERVAL_NANOS = 50_000_000L;
    private static long lastClickNanos;
    private static boolean hasClicked;

    @Shadow @Final private MinecraftClient client;

    @Inject(method = "onMouseScroll", at = @At("HEAD"), cancellable = true)
    private void zairoq$scrollToLeftClick(long window, double horizontal, double vertical, CallbackInfo ci) {
        if (!ScrollClickClient.isEnabled() || vertical == 0.0) {
            return;
        }

        MinecraftClient minecraft = this.client;
        if (minecraft.player == null
                || minecraft.world == null
                || minecraft.currentScreen != null) {
            return;
        }

        // Consume every non-zero vertical wheel event so it cannot change the hotbar slot.
        ci.cancel();

        // Queue the actual left-mouse key press; vanilla processes the attack binding normally.
        long now = System.nanoTime();
        if (!hasClicked || now - lastClickNanos >= MIN_CLICK_INTERVAL_NANOS) {
            KeyBinding.onKeyPressed(
                    InputUtil.Type.MOUSE.createFromCode(GLFW.GLFW_MOUSE_BUTTON_1)
            );
            lastClickNanos = now;
            hasClicked = true;
        }
    }
}
