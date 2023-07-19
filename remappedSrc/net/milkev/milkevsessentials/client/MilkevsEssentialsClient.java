package net.milkev.mealapiaddon.client;

import io.netty.buffer.Unpooled;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.milkev.mealapiaddon.client.entities.amethystshot.AmethystShotInit;
import net.milkev.mealapiaddon.common.network.ToolBeltNetworking;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.network.PacketByteBuf;
import org.lwjgl.glfw.GLFW;

public class MilkevsEssentialsClient implements ClientModInitializer {

    private static KeyBinding TOOL_BELT;
    private static boolean wasPressed = false;

    @Override
    public void onInitializeClient() {

        AmethystShotInit.init();

        TOOL_BELT = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.milkevsessentials.tool_belt",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_G,
                "category.milkevsessentials.keybind_category"
        ));

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if(TOOL_BELT.isPressed() && !wasPressed) {
                //System.out.println("tool belt was pressed!");
                wasPressed = true;
                ClientPlayNetworking.send(ToolBeltNetworking.USE_TOOLBELT, new PacketByteBuf(Unpooled.buffer()));
            }
            if(!TOOL_BELT.isPressed()) {
                wasPressed = false;
            }
        }
        );

    }
}
