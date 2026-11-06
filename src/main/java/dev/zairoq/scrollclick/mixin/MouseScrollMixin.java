package dev.zairoq.scrollclick.mixin;

import dev.zairoq.scrollclick.ScrollClickClient;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.Mouse;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/** Converts vertical wheel events into queued vanilla attack-key presses. */
@Mixin(Mouse.class)
public abstract class MouseScrollMixin {

    @Inject(method = "onMouseScroll", at = @At("HEAD"), cancellable = true)
    private void zairoq$scrollToLeftClick(long window, double horizontal, double vertical, CallbackInfo ci) {
        MinecraftClient minecraft = MinecraftClient.getInstance();

        if (window != minecraft.getWindow().getHandle()
                || !ScrollClickClient.isEnabled()
                || vertical == 0.0
                || minecraft.player == null
                || minecraft.currentScreen != null) {
            return;
        }

        ScrollClickClient.queueScrollClick();
        ci.cancel();
    }
}
