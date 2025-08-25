package user.my.keyboard;

import net.minecraft.client.gui.Element;
import net.minecraft.client.gui.screen.option.OptionsScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;

public class Utilities {
    public static void changeText(OptionsScreen screen, String key, String shortcut) {
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

}