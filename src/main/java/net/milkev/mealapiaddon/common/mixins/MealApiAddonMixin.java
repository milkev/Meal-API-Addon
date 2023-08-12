package net.milkev.mealapiaddon.common.mixins;

import io.github.foundationgames.mealapi.api.v0.PlayerFullnessUtil;
import net.minecraft.entity.player.HungerManager;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.FoodComponent;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;
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
        //Max saturation = 20

            if (!world.isClient) {

                PlayerEntity thisO = ((PlayerEntity) (Object) this);
                ServerPlayerEntity thisServerPlayer = (ServerPlayerEntity) thisO;

                if(!thisServerPlayer.getStackInHand(thisServerPlayer.getActiveHand()).getRegistryEntry().getKey().get().getValue().toString().equals("sandwichable:sandwich")) {

                    HungerManager thisHunger = thisO.getHungerManager();
                    FoodComponent thisFood = itemStack.getItem().getFoodComponent();

                    if (thisHunger.getFoodLevel() + thisFood.getHunger() >= 20) {

                        float totalSaturation = thisHunger.getSaturationLevel() + thisFood.getSaturationModifier() * thisFood.getHunger() * 2;

                        //System.out.println("Saturation total is: " + totalSaturation);
                        //System.out.println("Saturation modifier of eaten food: " + thisFood.getSaturationModifier());

                        if (totalSaturation > (float) 20) {

                            //System.out.println("Saturation overflowed, total: " + totalSaturation);

                            totalSaturation = totalSaturation - (float) 20;

                            //System.out.println("Overflowing " + totalSaturation + " Saturation into meal api fullness!");

                            PlayerFullnessUtil.instance().setPlayerFullness(thisServerPlayer,
                                    PlayerFullnessUtil.instance().getPlayerFullness(thisServerPlayer) + Math.round(totalSaturation));
                        }
                    }
                } else {
                    //System.out.println("Not applying Meal API Addon logic to sandwich as sandwichable has its own logic");
                }
            }
    }
}
