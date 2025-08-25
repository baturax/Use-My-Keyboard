package user.my.keyboard;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.Element;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.util.InputUtil;
import net.minecraft.text.Text;

public class Utilities {
    public static void changeText(Screen screen, String key, String shortcut) {
        String suffix = " (" + shortcut + ")";
        for (Element element : screen.children()) {
            if (element instanceof ButtonWidget button) {
                String msg = button.getMessage().getString();
                if (msg.equals(Text.translatable(key).getString())) {
                    button.setMessage(Text.literal(msg + suffix));
                }
            }
        }
    }

    public static boolean isKeyPressed(int keyCode) {
        MinecraftClient client = MinecraftClient.getInstance();
        return InputUtil.isKeyPressed(client.getWindow().getHandle(), keyCode);
    }

}