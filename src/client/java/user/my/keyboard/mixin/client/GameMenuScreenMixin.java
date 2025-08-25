package user.my.keyboard.mixin.client;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.Element;
import net.minecraft.client.gui.screen.GameMenuScreen;
import net.minecraft.client.gui.screen.OpenToLanScreen;
import net.minecraft.client.gui.screen.StatsScreen;
import net.minecraft.client.gui.screen.advancement.AdvancementsScreen;
import net.minecraft.client.gui.screen.option.OptionsScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.util.InputUtil;
import net.minecraft.text.Text;
import org.jetbrains.annotations.Nullable;
import org.lwjgl.glfw.GLFW;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GameMenuScreen.class)
public abstract class GameMenuScreenMixin {

    @Shadow

    @Inject(method = "init", at = @At("TAIL"))
    private void init(CallbackInfo ci) {
        for (Element element : ((GameMenuScreen) (Object) (this)).children()) {
            if (element instanceof ButtonWidget button) {
                String msg = button.getMessage().getString();
                if (msg.equals(Text.translatable("menu.returnToGame").getString())) {
                    button.setMessage(Text.literal(button.getMessage().getString() + " (B)"));
                }
                if (msg.equals(Text.translatable("gui.advancements").getString())) {
                    button.setMessage(Text.literal(button.getMessage().getString() + " (A)"));
                }
                if (msg.equals(Text.translatable("gui.stats").getString())) {
                    button.setMessage(Text.literal(button.getMessage().getString() + " (S)"));
                }
                if (msg.equals(Text.translatable("menu.options").getString())) {
                    button.setMessage(Text.literal(button.getMessage().getString() + " (O)"));
                }
                if (msg.equals(Text.translatable("menu.shareToLan").getString())) {
                    button.setMessage(Text.literal(button.getMessage().getString() + " (L)"));
                }
                if (msg.equals(Text.translatable("menu.returnToMenu").getString())) {
                    button.setMessage(Text.literal(button.getMessage().getString() + " (Q)"));
                }
            }
        }
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