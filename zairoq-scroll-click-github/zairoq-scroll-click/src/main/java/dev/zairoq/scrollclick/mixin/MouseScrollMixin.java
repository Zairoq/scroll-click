package dev.zairoq.scrollclick.mixin;

import dev.zairoq.scrollclick.ScrollClickClient;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.Mouse;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/** Converts vertical wheel events into left-click actions while enabled. */
@Mixin(Mouse.class)
public abstract class MouseScrollMixin {
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

        // One vanilla attack action per vertical wheel event, in either direction.
        ((MinecraftClientAccessor) minecraft).zairoq$invokeDoAttack();

        // Prevent the same wheel event from changing the selected hotbar slot.
        ci.cancel();
    }
}
