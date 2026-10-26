package dev.zairoq.scrollclick.mixin;

import net.minecraft.client.MinecraftClient;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

/** Accesses Minecraft's own attack routine to follow vanilla interaction logic. */
@Mixin(MinecraftClient.class)
public interface MinecraftClientAccessor {
    @Invoker("doAttack")
    boolean zairoq$invokeDoAttack();
}
