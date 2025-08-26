package user.my.keyboard.mixin.client;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.multiplayer.*;
import net.minecraft.client.network.ServerAddress;
import net.minecraft.client.network.ServerInfo;
import net.minecraft.client.option.ServerList;
import net.minecraft.client.resource.language.I18n;
import org.lwjgl.glfw.GLFW;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static user.my.keyboard.Utilities.changeText;
import static user.my.keyboard.Utilities.isKeyPressed;

@Mixin(MultiplayerScreen.class)
public abstract class MultiPlayerScreenMixin {

    @Inject(method = "init", at = @At("TAIL"))
    private void init(CallbackInfo info) {
        MultiplayerScreen screen = (MultiplayerScreen) (Object) (this);
        changeText(screen, "selectServer.select", "J/S");
        changeText(screen, "selectServer.direct", "C");
        changeText(screen, "selectServer.add", "A");
        changeText(screen, "selectServer.edit", "E");
        changeText(screen, "selectServer.delete", "D");
        changeText(screen, "selectServer.refresh", "R");
    }

    @Inject(method = "render", at = @At("HEAD"))
    private void onRenderHead(CallbackInfo ci) {
        MinecraftClient client = MinecraftClient.getInstance();

        if (isKeyPressed(GLFW.GLFW_KEY_J) || isKeyPressed(GLFW.GLFW_KEY_S)) {
            connect();
        }
        if (isKeyPressed(GLFW.GLFW_KEY_C)) {
            selectedEntry = new ServerInfo(I18n.translate("selectServer.defaultName"), "", ServerInfo.ServerType.OTHER);
            client.setScreen(new DirectConnectScreen(client.currentScreen, this::directConnect, selectedEntry));
        }
        if (isKeyPressed(GLFW.GLFW_KEY_A)) {
            selectedEntry = new ServerInfo(I18n.translate("selectServer.defaultName"), "", ServerInfo.ServerType.OTHER);
            client.setScreen(new AddServerScreen(client.currentScreen, this::addEntry, selectedEntry));
        }
        if (isKeyPressed(GLFW.GLFW_KEY_E)) {
            MultiplayerServerListWidget.Entry entry = serverListWidget.getSelectedOrNull();

            if (entry instanceof MultiplayerServerListWidget.ServerEntry) {
                ServerInfo serverInfo = ((MultiplayerServerListWidget.ServerEntry)entry).getServer();
                selectedEntry = new ServerInfo(serverInfo.name, serverInfo.address, ServerInfo.ServerType.OTHER);
                selectedEntry.copyWithSettingsFrom(serverInfo);
                client.setScreen(new AddServerScreen(client.currentScreen, this::editEntry, selectedEntry));
            }
        }
        if (isKeyPressed(GLFW.GLFW_KEY_D)) {
            // Delete Server
        }
        if (isKeyPressed(GLFW.GLFW_KEY_R)) {
            client.setScreen(new MultiplayerScreen(client.currentScreen));
        }
    }

    @Unique
    private void editEntry(boolean confirmedAction) {
        MultiplayerServerListWidget.Entry entry = serverListWidget.getSelectedOrNull();
        if (confirmedAction && entry instanceof MultiplayerServerListWidget.ServerEntry) {
            ServerInfo serverInfo = ((MultiplayerServerListWidget.ServerEntry)entry).getServer();
            serverInfo.name = selectedEntry.name;
            serverInfo.address = selectedEntry.address;
            serverInfo.copyWithSettingsFrom(selectedEntry);
            serverList.saveFile();
            serverListWidget.setServers(serverList);
        }

        client.setScreen(new MultiplayerScreen(client.currentScreen));
    }

    @Unique
    private void addEntry(boolean confirmedAction) {
        if (confirmedAction) {
            ServerInfo serverInfo = serverList.tryUnhide(selectedEntry.address);
            if (serverInfo != null) {
                serverInfo.copyFrom(selectedEntry);
                this.serverList.saveFile();
            } else {
                serverList.add(selectedEntry, false);
                serverList.saveFile();
            }

            serverListWidget.setSelected(null);
            serverListWidget.setServers(serverList);
        }

        client.setScreen(new MultiplayerScreen(client.currentScreen));
    }

    @Shadow
    protected MultiplayerServerListWidget serverListWidget;

    @Shadow
    private ServerList serverList;

    @Shadow
    public abstract void connect();

    @Unique
    private ServerInfo selectedEntry;
    @Unique
    private final MinecraftClient client = MinecraftClient.getInstance();

    @Unique
    private void directConnect(boolean confirmedAction) {
        if (confirmedAction) {
            ServerInfo serverInfo = serverList.get(selectedEntry.address);
            if (serverInfo == null) {
                serverList.add(selectedEntry, true);
                serverList.saveFile();
                connect(selectedEntry);
            } else {
                connect(serverInfo);
            }
        } else {
            client.setScreen(null);
        }
    }

    @Unique
    private void connect(ServerInfo entry) {
        ConnectScreen.connect(client.currentScreen, client, ServerAddress.parse(entry.address), entry, false, null);
    }
}
