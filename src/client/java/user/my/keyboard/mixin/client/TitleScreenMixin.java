package user.my.keyboard.mixin.client;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.Element;
import net.minecraft.client.gui.screen.TitleScreen;
import net.minecraft.client.gui.screen.multiplayer.MultiplayerScreen;
import net.minecraft.client.gui.screen.option.OptionsScreen;
import net.minecraft.client.gui.screen.world.SelectWorldScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.realms.gui.screen.RealmsMainScreen;
import net.minecraft.client.util.InputUtil;
import net.minecraft.text.Text;
import org.lwjgl.glfw.GLFW;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(TitleScreen.class)
public class TitleScreenMixin {
    @Inject(method = "init", at = @At("TAIL"))
    private void init(CallbackInfo info) {
        for (Element element : ((TitleScreen) (Object) (this)).children()) {
            if (element instanceof ButtonWidget button) {
                String msg = button.getMessage().getString();
                if (msg.equals(Text.translatable("menu.singleplayer").getString())) {
                    button.setMessage(Text.literal(button.getMessage().getString() + " (S)"));
                }
                if (msg.equals(Text.translatable("menu.multiplayer").getString())) {
                    button.setMessage(Text.literal(button.getMessage().getString() + " (M)"));
                }
                if (msg.equals(Text.translatable("menu.online").getString())) {
                    button.setMessage(Text.literal(button.getMessage().getString() + " (R)"));
                }
                if (msg.equals(Text.translatable("menu.options").getString())) {
                    button.setMessage(Text.literal(button.getMessage().getString() + " (O)"));
                }
                if (msg.equals(Text.translatable("menu.quit").getString())) {
                    button.setMessage(Text.literal(button.getMessage().getString() + " (Q)"));
                }
            }
        }
    }

    @Inject(method = "render", at = @At("HEAD"))
    private void onRenderHead(CallbackInfo ci) {
        MinecraftClient client = MinecraftClient.getInstance();

        if (InputUtil.isKeyPressed(client.getWindow().getHandle(), GLFW.GLFW_KEY_S)) {
            client.setScreen(new SelectWorldScreen(client.currentScreen));
        }
        if (InputUtil.isKeyPressed(client.getWindow().getHandle(), GLFW.GLFW_KEY_M)) {
            client.setScreen(new MultiplayerScreen(client.currentScreen));
        }
        if (InputUtil.isKeyPressed(client.getWindow().getHandle(), GLFW.GLFW_KEY_R)) {
            client.setScreen(new RealmsMainScreen(client.currentScreen));
        }
        if (InputUtil.isKeyPressed(client.getWindow().getHandle(), GLFW.GLFW_KEY_O)) {
            client.setScreen(new OptionsScreen(client.currentScreen, client.options));
        }
        if (InputUtil.isKeyPressed(client.getWindow().getHandle(), GLFW.GLFW_KEY_Q)) {
            client.scheduleStop();
        }
    }
}