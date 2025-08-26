package user.my.keyboard.mixin.client;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.multiplayer.MultiplayerScreen;
import org.lwjgl.glfw.GLFW;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static user.my.keyboard.Utilities.changeText;
import static user.my.keyboard.Utilities.isKeyPressed;

@Mixin(MultiplayerScreen.class)
public class MultiPlayerScreenMixin {

    @Inject(method = "init", at = @At("TAIL"))
    private void init(CallbackInfo info) {
        MultiplayerScreen screen = (MultiplayerScreen) (Object) (this);
        changeText(screen, "selectServer.select", "J/S");
        changeText(screen, "selectServer.direct", "C");
        changeText(screen, "selectServer.add", "A");
        changeText(screen, "selectServer.edit", "E");
        changeText(screen, "selectServer.delete", "D");
        changeText(screen, "selectServer.refresh", "R");
    }

    @Inject(method = "render", at = @At("HEAD"))
    private void onRenderHead(CallbackInfo ci) {
        MinecraftClient client = MinecraftClient.getInstance();
        if (isKeyPressed(GLFW.GLFW_KEY_J) || isKeyPressed(GLFW.GLFW_KEY_S)) {
            // Join Server
        }
        if (isKeyPressed(GLFW.GLFW_KEY_C)) {
            // Direct Connect
        }
        if (isKeyPressed(GLFW.GLFW_KEY_E)) {
            // Edit Server
        }
        if (isKeyPressed(GLFW.GLFW_KEY_D)) {
            // Delete Server
        }
        if (isKeyPressed(GLFW.GLFW_KEY_R)) {
            // Refresh
        }

    }


}
