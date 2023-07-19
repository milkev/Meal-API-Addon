package net.milkev.mealapiaddon.client.entities.amethystshot;

import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.milkev.mealapiaddon.common.MilkevsEssentials;
import net.minecraft.client.render.entity.FlyingItemEntityRenderer;
import net.minecraft.client.render.entity.ProjectileEntityRenderer;
import net.minecraft.entity.projectile.ArrowEntity;
import net.minecraft.util.Identifier;

public class AmethystShotInit {

    public static final Identifier PacketID = new Identifier(MilkevsEssentials.MOD_ID, "spawn_packet");

    public static void init() {

        //EntityRendererRegistry.register(MilkevsEssentials.AMETHYST_SHOT_ENTITY_TYPE, (dispatcher, context) -> new AmethystShotRenderer(context));
        //EntityRendererRegistry.INSTANCE.register
        receiveEntityPacket();
    }

    private static void receiveEntityPacket() {
    }
}
