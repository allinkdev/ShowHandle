package com.github.allinkdev.showhandle.mixin;

import net.minecraft.client.WindowEventHandler;
import net.minecraft.client.WindowSettings;
import net.minecraft.client.util.MonitorTracker;
import net.minecraft.client.util.Window;
import org.lwjgl.glfw.GLFW;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Window.class)
abstract class WindowMixin {
    @Shadow
    public abstract void setTitle(String title);

    @Redirect(method = "setTitle", at = @At(value = "INVOKE", target = "Lorg/lwjgl/glfw/GLFW;glfwSetWindowTitle(JLjava/lang/CharSequence;)V"))
    private void setTitle$glfwSetWindowTitle(final long window, final CharSequence titleEncoded) {
        GLFW.glfwSetWindowTitle(window, titleEncoded + " (" + window + ")");
    }

    @Inject(method = "<init>", at = @At(value = "INVOKE", target = "Lorg/lwjgl/glfw/GLFW;glfwMakeContextCurrent(J)V"))
    private void init$glfwCreateWindow(final WindowEventHandler eventHandler, final MonitorTracker monitorTracker, final WindowSettings settings, final String videoMode, final String title, final CallbackInfo ci) {
        this.setTitle(title);
    }
}
