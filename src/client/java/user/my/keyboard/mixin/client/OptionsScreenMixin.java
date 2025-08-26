package user.my.keyboard.mixin.client;

import net.minecraft.client.gui.screen.option.OptionsScreen;
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

        changeText(screen, "options.video", "V");
        changeText(screen, "options.controls", "C");
        changeText(screen, "options.language", "L");
        changeText(screen, "options.resourcepack", "R");
        changeText(screen, "options.skinCustomisation", "S");
        changeText(screen, "options.accessibility", "A"); // dikkat: spelling
    }
    // Will do here later idk
}
