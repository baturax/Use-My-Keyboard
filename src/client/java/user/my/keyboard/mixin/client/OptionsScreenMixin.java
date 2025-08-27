package user.my.keyboard.mixin.client;

import net.minecraft.client.gui.screen.option.OptionsScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.screen.ScreenTexts;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static user.my.keyboard.Utilities.changeText;

@Mixin(OptionsScreen.class)
public class OptionsScreenMixin {

    @Inject(method = "init", at = @At("TAIL"))
    private void init(CallbackInfo ci) {
        OptionsScreen screen = (OptionsScreen) (Object) this;

        changeText(screen, "options.skinCustomisation", "S");
        changeText(screen, "options.sounds", "M");
        changeText(screen, "options.video", "V");
        changeText(screen, "options.controls", "C");
        changeText(screen, "options.language", "L");
        changeText(screen, "options.chat", "T");
        changeText(screen, "options.resourcepack", "R");
        changeText(screen, "options.accessibility", "A");
        screen.children().forEach(element -> {
            if (element instanceof ButtonWidget button) {
                String msg = button.getMessage().getString();
                if (button.getMessage().equals(ScreenTexts.DONE)) {
                    button.setMessage(Text.literal(msg + " (B)"));
                }
            }
        });


    }
}
