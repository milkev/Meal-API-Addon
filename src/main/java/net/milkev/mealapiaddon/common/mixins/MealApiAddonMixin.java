package net.milkev.mealapiaddon.common.mixins;

import io.github.foundationgames.mealapi.api.v0.PlayerFullnessUtil;
import net.minecraft.entity.player.HungerManager;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.FoodComponent;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerEntity.class)
public class MealApiAddonMixin {


    @Inject(at = @At("HEAD"), method = "eatFood")
    public void eatFood(World world, ItemStack itemStack, CallbackInfoReturnable<ItemStack> cir) {

        //Reference;
        //Max hunger = 20
        //Max saturation = 40

        if(!world.isClient) {
            PlayerEntity thisO = ((PlayerEntity) (Object) this);
            ServerPlayerEntity thisServerPlayer = (ServerPlayerEntity) thisO;
            HungerManager thisHunger = thisO.getHungerManager();
            FoodComponent thisFood = itemStack.getItem().getFoodComponent();

            if (thisHunger.getFoodLevel() + thisFood.getHunger() >= 20) {
                float totalSaturation = thisHunger.getSaturationLevel() + thisFood.getSaturationModifier() * (float)40;
                if (totalSaturation > (float)40) {
                    //no, i dont know why its 39.5... from my tests, this simply resulted in getting the correct saturation amount after overflowing into fullness & healing
                    totalSaturation = totalSaturation - (float)39.5;
                    //System.out.println("Overflowing " + totalSaturation + " Saturation into meal api fullness!");
                    PlayerFullnessUtil.instance().setPlayerFullness(thisServerPlayer,
                            PlayerFullnessUtil.instance().getPlayerFullness(thisServerPlayer) + Math.round(totalSaturation));
                }
            }
        }
    }
}
