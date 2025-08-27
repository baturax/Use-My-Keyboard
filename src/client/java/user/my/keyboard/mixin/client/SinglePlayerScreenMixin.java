package user.my.keyboard.mixin.client;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.world.CreateWorldScreen;
import net.minecraft.client.gui.screen.world.SelectWorldScreen;
import net.minecraft.client.gui.screen.world.WorldListWidget;
import org.lwjgl.glfw.GLFW;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static user.my.keyboard.Utilities.changeText;
import static user.my.keyboard.Utilities.isKeyPressed;

@Mixin(SelectWorldScreen.class)
public class SinglePlayerScreenMixin {

    @Shadow
    private WorldListWidget levelList;
    @Final
    @Shadow
    protected Screen parent;

    @Inject(method = "init", at = @At("TAIL"))
    private void init(CallbackInfo ci) {
        SelectWorldScreen screen = (SelectWorldScreen) (Object) this;
        changeText(screen, "selectWorld.create", "C");
        changeText(screen, "selectWorld.edit", "E");
        changeText(screen, "selectWorld.delete", "D");
        changeText(screen, "selectWorld.recreate", "R");
        changeText(screen, "gui.back", "B");
    }

    @Inject(method = "render", at = @At("HEAD"))
    private void onKeyPressed(DrawContext context, int mouseX, int mouseY, float deltaTicks, CallbackInfo ci) {
        MinecraftClient client = MinecraftClient.getInstance();

        if (isKeyPressed(GLFW.GLFW_KEY_C)) {
            CreateWorldScreen.show(client, (SelectWorldScreen) (Object) this);
        }
        if (isKeyPressed(GLFW.GLFW_KEY_E)) {
            levelList.getSelectedAsOptional().ifPresent(WorldListWidget.WorldEntry::edit);
        }
        if (isKeyPressed(GLFW.GLFW_KEY_D)) {
            levelList.getSelectedAsOptional().ifPresent(WorldListWidget.WorldEntry::deleteIfConfirmed);
        }
        if (isKeyPressed(GLFW.GLFW_KEY_R)) {
            levelList.getSelectedAsOptional().ifPresent(WorldListWidget.WorldEntry::recreate);
        }
        if (isKeyPressed(GLFW.GLFW_KEY_B)) {
            client.setScreen(parent);
        }
    }

    @Inject(method = "setInitialFocus", at = @At("HEAD"), cancellable = true)
    private void onSetInitialFocus(CallbackInfo ci) {
        ci.cancel();
    }
}
