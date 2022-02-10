package me.bluesmoke.mangodfps;

import net.fabricmc.api.ModInitializer;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.Window;
import net.minecraft.util.Util;
import org.jetbrains.annotations.Nullable;
import org.lwjgl.glfw.GLFW;

import java.util.concurrent.locks.LockSupport;

public class DynamicMenuFPSMod implements ModInitializer {
    private static long lastRender;

    public void onInitialize() {
    }

    public static boolean checkForRender() {
        long currentTime = Util.getMeasuringTimeMs();
        long timeSinceLastRender = currentTime - lastRender;

        if (!checkForRender(timeSinceLastRender))
            return false;

        lastRender = currentTime;
        return true;
    }

    private static boolean checkForRender(long timeSinceLastRender) {
        Integer fpsOverride = fpsOverride();
        if (fpsOverride == null) {
            return true;
        }

        if (fpsOverride.intValue() == 0) {
            idle(1000L);
            return false;
        }

        long frameTime = (1000 / fpsOverride.intValue());
        boolean shouldSkipRender = (timeSinceLastRender < frameTime);
        if (!shouldSkipRender)
            return true;

        idle(frameTime);
        return false;
    }

    private static void idle(long waitMillis) {
        waitMillis = Math.min(waitMillis, 30L);
        LockSupport.parkNanos("waiting to render", waitMillis * 1000000L);
    }

    @Nullable
    private static Integer fpsOverride() {
        MinecraftClient client = MinecraftClient.getInstance();
        Window window = ((WindowHolder) client).getWindow();

        boolean isHovered = (GLFW.glfwGetWindowAttrib(window.getHandle(), 131083) != 0);
        if (isHovered)
            return null;

        if (!client.isWindowFocused())
            return Integer.valueOf(1);

        return null;
    }

    public static interface WindowHolder {
        Window getWindow();
    }
}