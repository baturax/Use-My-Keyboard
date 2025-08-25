package user.my.keyboard.mixin.client;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.GameMenuScreen;
import net.minecraft.client.gui.screen.OpenToLanScreen;
import net.minecraft.client.gui.screen.StatsScreen;
import net.minecraft.client.gui.screen.advancement.AdvancementsScreen;
import net.minecraft.client.gui.screen.option.OptionsScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.util.InputUtil;
import org.jetbrains.annotations.Nullable;
import org.lwjgl.glfw.GLFW;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static user.my.keyboard.Utilities.changeText;

@Mixin(GameMenuScreen.class)
public abstract class GameMenuScreenMixin {

    @Inject(method = "init", at = @At("TAIL"))
    private void init(CallbackInfo ci) {
        GameMenuScreen screen = (GameMenuScreen) (Object) this;

        changeText(screen, "menu.returnToGame", "B");
        changeText(screen, "gui.advancements", "A");
        changeText(screen, "gui.stats", "S");
        changeText(screen, "menu.options", "O");
        changeText(screen, "menu.shareToLan", "L");
        changeText(screen, "menu.returnToMenu", "Q");
    }

    @Shadow
    private @Nullable ButtonWidget exitButton;

    @Inject(method = "render", at = @At("HEAD"))
    private void onRenderHead(CallbackInfo ci) {
        MinecraftClient client = MinecraftClient.getInstance();

        if (InputUtil.isKeyPressed(client.getWindow().getHandle(), GLFW.GLFW_KEY_B)) {
            client.setScreen(null);
        }
        if (InputUtil.isKeyPressed(client.getWindow().getHandle(), GLFW.GLFW_KEY_A)) {
            assert client.player != null;
            client.setScreen(new AdvancementsScreen(client.player.networkHandler.getAdvancementHandler()));
        }
        if (InputUtil.isKeyPressed(client.getWindow().getHandle(), GLFW.GLFW_KEY_S)) {
            assert client.player != null;
            client.setScreen(new StatsScreen(client.currentScreen, client.player.getStatHandler()));
        }
        if (InputUtil.isKeyPressed(client.getWindow().getHandle(), GLFW.GLFW_KEY_O)) {
            client.setScreen(new OptionsScreen(client.currentScreen, client.options));
        }
        if (InputUtil.isKeyPressed(client.getWindow().getHandle(), GLFW.GLFW_KEY_L)) {
            client.setScreen(new OpenToLanScreen(client.currentScreen));
        }
        if (InputUtil.isKeyPressed(client.getWindow().getHandle(), GLFW.GLFW_KEY_Q)) {
            assert exitButton != null;
            exitButton.onPress();
        }
    }
}