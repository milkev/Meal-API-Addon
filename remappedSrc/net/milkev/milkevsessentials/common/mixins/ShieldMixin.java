package net.milkev.mealapiaddon.common.mixins;

import me.shedaniel.autoconfig.AutoConfig;
import net.milkev.mealapiaddon.common.MilkevsEssentials;
import net.milkev.mealapiaddon.common.ModConfig;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.util.UseAction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(PlayerEntity.class)
public abstract class ShieldMixin extends LivingEntity {

    public ShieldMixin(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }


    ModConfig config = AutoConfig.getConfigHolder(ModConfig.class).getConfig();

    public boolean isBlocking() {
        if(config.enableInstantShieldBlocking) {
            if (this.isUsingItem() && !this.activeItemStack.isEmpty()) {
                Item item = this.activeItemStack.getItem();
                if (item.getUseAction(this.activeItemStack) != UseAction.BLOCK) {
                    return false;
                } else {
                    return true;
                }
            } else {
                return false;
            }
        } else {
            return super.isBlocking();
        }
    }

    protected int computeFallDamage(float fallDistance, float damageMultiplier) {
        if(config.enableShieldBlocksFallDamage) {
            StatusEffectInstance statusEffectInstance = this.getStatusEffect(StatusEffects.JUMP_BOOST);
            float f = statusEffectInstance == null ? 0.0F : (float) (statusEffectInstance.getAmplifier() + 1);
            Item item = this.activeItemStack.getItem();
            return (item.getUseAction(this.activeItemStack) == UseAction.BLOCK) ? 0 : MathHelper.ceil((fallDistance - 3.0F - f) * damageMultiplier);
        } else {
            return super.computeFallDamage(fallDistance, damageMultiplier);
        }
    }

}
