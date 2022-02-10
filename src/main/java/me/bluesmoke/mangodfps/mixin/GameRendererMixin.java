package me.bluesmoke.mangodfps.mixin;

import me.bluesmoke.mangodfps.DynamicMenuFPSMod;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.GameMenuScreen;
import net.minecraft.client.gui.screen.SplashScreen;
import net.minecraft.client.render.GameRenderer;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({ GameRenderer.class })
public class GameRendererMixin {
    @Shadow
    @Final
    private MinecraftClient client;

//    @Inject(at = { @At("HEAD") }, method = { "render" }, cancellable = true)
    @Inject(method = "render", at = @At("HEAD"), cancellable = true)
    private void onRender(float tickDelta, long startTime, boolean tick, CallbackInfo callbackInfo) {
        if (this.client.currentScreen instanceof GameMenuScreen && !DynamicMenuFPSMod.checkForRender()) {
            callbackInfo.cancel();
        }
    }

//    @Inject(at = { @At("HEAD") }, method = { "renderWorld" }, cancellable = true)
    @Inject(method = "renderWorld", at = @At("HEAD"), cancellable = true)
    private void onRenderWorld(CallbackInfo callbackInfo) {
        if (this.client.getOverlay() instanceof SplashScreen) {
            callbackInfo.cancel();
        }
    }
}
