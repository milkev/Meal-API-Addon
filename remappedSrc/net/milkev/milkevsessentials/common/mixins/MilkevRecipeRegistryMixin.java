package net.milkev.mealapiaddon.common.mixins;

import com.google.gson.JsonElement;
import net.milkev.mealapiaddon.common.registry.MilkevRecipeRegistry;
import net.minecraft.recipe.RecipeManager;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.Identifier;
import net.minecraft.util.profiler.Profiler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Map;

@Mixin(RecipeManager.class)
public class MilkevRecipeRegistryMixin {

        /*
        String MOD_ID = "milkevsessentials";

        @Inject(method = "apply", at = @At("HEAD"))
        public void interceptApply(Map<Identifier, JsonElement> map, ResourceManager resourceManager, Profiler profiler, CallbackInfo info) {
            if (MilkevRecipeRegistry.FLIGHT_CHARM != null) {
                map.putIfAbsent(new Identifier(MilkevRecipeRegistry.FLIGHT_CHARM.getAsJsonObject("result").get("item").getAsString()), MilkevRecipeRegistry.FLIGHT_CHARM);
            }
            if (MilkevRecipeRegistry.EXTENDO_GRIP_HIGH != null && MilkevRecipeRegistry.EXTENDO_GRIP_NORMAL != null && MilkevRecipeRegistry.EXTENDO_GRIP_LOW != null) {
                map.putIfAbsent(new Identifier(MOD_ID, "extendo_grip_high"), MilkevRecipeRegistry.EXTENDO_GRIP_HIGH);
                map.putIfAbsent(new Identifier(MOD_ID, "extendo_grip_normal"), MilkevRecipeRegistry.EXTENDO_GRIP_NORMAL);
                map.putIfAbsent(new Identifier(MOD_ID, "extendo_grip_low"), MilkevRecipeRegistry.EXTENDO_GRIP_LOW);
            }
            if(MilkevRecipeRegistry.TOOLBELT != null) {
                map.putIfAbsent(new Identifier(MOD_ID, "toolbelt"), MilkevRecipeRegistry.TOOLBELT);
            }
        }
        */

}

