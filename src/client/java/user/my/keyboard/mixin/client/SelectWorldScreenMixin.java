package user.my.keyboard.mixin.client;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.world.CreateWorldScreen;
import net.minecraft.client.gui.screen.world.SelectWorldScreen;
import net.minecraft.client.gui.screen.world.WorldListWidget;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.text.Text;
import org.lwjgl.glfw.GLFW;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

import static net.minecraft.world.level.storage.LevelSummary.SELECT_WORLD_TEXT;
import static user.my.keyboard.Utilities.changeText;
import static user.my.keyboard.Utilities.isKeyPressed;

@Mixin(SelectWorldScreen.class)
public class SelectWorldScreenMixin {
    @Shadow
    private WorldListWidget levelList;
    @Final
    @Shadow
    protected Screen parent;
    @Shadow
    protected TextFieldWidget searchBox;


    @Inject(method = "init", at = @At("TAIL"))
    private void init(CallbackInfo ci) {
        SelectWorldScreen screen = (SelectWorldScreen) (Object) this;
        changeText(screen, "selectWorld.create", "C");
        changeText(screen, "selectWorld.edit", "E");
        changeText(screen, "selectWorld.delete", "D");
        changeText(screen, "selectWorld.recreate", "R");
        changeText(screen, "gui.back", "B");
        screen.children().forEach(element -> {
            if (element instanceof ButtonWidget button) {
                String msg = button.getMessage().getString();

                if (button.getMessage().equals(SELECT_WORLD_TEXT)) {
                    button.setMessage(Text.literal(msg + " (J/P)"));
                }


            }
        });
    }

    @Inject(method = "render", at = @At("HEAD"))
    private void onKeyPressed(DrawContext context, int mouseX, int mouseY, float deltaTicks, CallbackInfo ci) {
        MinecraftClient client = MinecraftClient.getInstance();

        if (isKeyPressed(GLFW.GLFW_KEY_J) || isKeyPressed(GLFW.GLFW_KEY_P)) {
            levelList.getSelectedAsOptional().ifPresent(WorldListWidget.WorldEntry::play);
        }
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
        if (isKeyPressed(GLFW.GLFW_KEY_S)) {
            searchBox.setFocused(true);
            searchBox.setCentered(true);
        }

        // Select
        if (isKeyPressed(GLFW.GLFW_KEY_1)) selectWorld(0);
        if (isKeyPressed(GLFW.GLFW_KEY_2)) selectWorld(1);
        if (isKeyPressed(GLFW.GLFW_KEY_3)) selectWorld(2);
        if (isKeyPressed(GLFW.GLFW_KEY_1)) selectWorld(3);
        if (isKeyPressed(GLFW.GLFW_KEY_2)) selectWorld(4);
        if (isKeyPressed(GLFW.GLFW_KEY_3)) selectWorld(5);
        if (isKeyPressed(GLFW.GLFW_KEY_1)) selectWorld(6);
        if (isKeyPressed(GLFW.GLFW_KEY_2)) selectWorld(7);
        if (isKeyPressed(GLFW.GLFW_KEY_3)) selectWorld(8);
        if (isKeyPressed(GLFW.GLFW_KEY_1)) selectWorld(9);
    }

    @Unique
    private void selectWorld(int worldnum) {
        List<WorldListWidget.Entry> worlds = levelList.children();
        if (worlds.size() > worldnum) levelList.setSelected(worlds.get(worldnum));
    }

    @Inject(method = "setInitialFocus", at = @At("HEAD"), cancellable = true)
    private void onSetInitialFocus(CallbackInfo ci) {
        ci.cancel();
    }
}
