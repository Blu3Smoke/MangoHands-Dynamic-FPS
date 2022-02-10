package me.bluesmoke.mangodfps.mixin;

import me.bluesmoke.mangodfps.DynamicMenuFPSMod;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.Window;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin({ MinecraftClient.class })
public class MinecraftClientMixin implements DynamicMenuFPSMod.WindowHolder {
    @Shadow
    @Final
    private Window window;

    public Window getWindow() {
        return this.window;
    }
}
