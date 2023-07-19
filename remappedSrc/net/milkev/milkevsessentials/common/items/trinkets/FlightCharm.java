package net.milkev.mealapiaddon.common.items.trinkets;

import dev.emi.trinkets.api.SlotReference;
import dev.emi.trinkets.api.TrinketItem;
import dev.emi.trinkets.api.TrinketsApi;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.world.World;

import java.util.List;

public class FlightCharm extends TrinketItem {

    public FlightCharm(Settings settings) {
        super(settings);
        TrinketsApi.registerTrinket(this, this);
    }

    @Override
    public void onEquip(ItemStack stack, SlotReference slot, LivingEntity entity) {
        if (entity instanceof PlayerEntity player) {
            player.getAbilities().allowFlying = true;
        }
        super.onEquip(stack, slot, entity);
    }

    @Override
    public void onUnequip(ItemStack stack, SlotReference slot, LivingEntity entity) {
        if (entity instanceof PlayerEntity player) {
            if (!player.isSpectator() || !player.isCreative()) {
                player.getAbilities().allowFlying = false;
                player.getAbilities().flying = false;
            }
        }
        super.onUnequip(stack, slot, entity);
    }

    @Override
    public void appendTooltip(ItemStack itemStack, World world, List<Text> tooltip, TooltipContext tooltipContext) {

        //trinkets wont auto put the 'equip in slot' on this charm, dunno why :/
        tooltip.add(Text.translatable("item.milkevsessentials.flight_charm.equip_in_slot"));
        //displays that the charm enables flight
        tooltip.add(Text.translatable("item.milkevsessentials.flight_charm.when_equip"));
        tooltip.add(Text.translatable("item.milkevsessentials.flight_charm.allows_flight"));
    }
}
