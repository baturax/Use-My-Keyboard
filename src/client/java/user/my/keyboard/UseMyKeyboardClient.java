package user.my.keyboard;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.option.*;
import net.minecraft.client.gui.screen.pack.PackScreen;
import net.minecraft.client.util.InputUtil;
import net.minecraft.text.Text;
import org.lwjgl.glfw.GLFW;

public class UseMyKeyboardClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {

        // Options Screen
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            Screen current = client.currentScreen;
            if (!(current instanceof OptionsScreen)) return;

            long handle = client.getWindow().getHandle();

            if (InputUtil.isKeyPressed(handle, GLFW.GLFW_KEY_S)) {
                client.setScreen(new SkinOptionsScreen(current, client.options));
            }
            if (InputUtil.isKeyPressed(handle, GLFW.GLFW_KEY_M)) {
                client.setScreen(new SoundOptionsScreen(current, client.options));
            }
            if (InputUtil.isKeyPressed(handle, GLFW.GLFW_KEY_V)) {
                client.setScreen(new VideoOptionsScreen(current, client, client.options));
            }
            if (InputUtil.isKeyPressed(handle, GLFW.GLFW_KEY_C)) {
                client.setScreen(new ControlsOptionsScreen(current, client.options));
            }
            if (InputUtil.isKeyPressed(handle, GLFW.GLFW_KEY_L)) {
                client.setScreen(new LanguageOptionsScreen(current, client.options, client.getLanguageManager()));
            }
            if (InputUtil.isKeyPressed(handle, GLFW.GLFW_KEY_T)) {
                client.setScreen(new ChatOptionsScreen(current, client.options));
            }
            if (InputUtil.isKeyPressed(handle, GLFW.GLFW_KEY_R)) {
                client.setScreen(new PackScreen(
                        client.getResourcePackManager(),
                        manager -> {
                            client.options.refreshResourcePacks(manager);
                            client.setScreen(current);
                        },
                        client.getResourcePackDir(),
                        Text.translatable("resourcePack.title")
                ));
            }
            if (InputUtil.isKeyPressed(handle, GLFW.GLFW_KEY_A)) {
                client.setScreen(new ControlsOptionsScreen(client.currentScreen, client.options));
            }
            if (InputUtil.isKeyPressed(handle, GLFW.GLFW_KEY_B)) {
                // Can somebody fix this
                client.setScreen(null);
            }
        });
    }
}