package user.my.keyboard.mixin.client;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.TitleScreen;
import net.minecraft.client.gui.screen.multiplayer.MultiplayerScreen;
import net.minecraft.client.gui.screen.option.OptionsScreen;
import net.minecraft.client.gui.screen.world.SelectWorldScreen;
import net.minecraft.client.realms.gui.screen.RealmsMainScreen;
import org.lwjgl.glfw.GLFW;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static user.my.keyboard.Utilities.changeText;
import static user.my.keyboard.Utilities.isKeyPressed;

@Mixin(TitleScreen.class)
public class TitleScreenMixin {
    @Inject(method = "init", at = @At("TAIL"))
    private void init(CallbackInfo info) {
        TitleScreen screen = (TitleScreen) (Object) this;
        changeText(screen, "menu.singleplayer", "S");
        changeText(screen, "menu.multiplayer", "M");
        changeText(screen, "menu.online", "R");
        changeText(screen, "menu.options", "O");
        changeText(screen, "menu.quit", "Q");
    }

    @Inject(method = "render", at = @At("HEAD"))
    private void onRenderHead(CallbackInfo ci) {
        MinecraftClient client = MinecraftClient.getInstance();

        if (isKeyPressed(GLFW.GLFW_KEY_S)) {
            client.setScreen(new SelectWorldScreen(client.currentScreen));
        }
        if (isKeyPressed(GLFW.GLFW_KEY_M)) {
            client.setScreen(new MultiplayerScreen(client.currentScreen));
        }
        if (isKeyPressed(GLFW.GLFW_KEY_R)) {
            client.setScreen(new RealmsMainScreen(client.currentScreen));
        }
        if (isKeyPressed(GLFW.GLFW_KEY_O)) {
            client.setScreen(new OptionsScreen(client.currentScreen, client.options));
        }
        if (isKeyPressed(GLFW.GLFW_KEY_Q)) {
            client.scheduleStop();
        }
    }
}