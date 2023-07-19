package net.milkev.mealapiaddon.common.items.weapons;

import net.milkev.mealapiaddon.common.registry.MilkevTagRegistry;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.item.*;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
//import net.minecraft.tag.Tag;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.UseAction;
import net.minecraft.util.math.Quaternion;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3f;
import net.minecraft.world.World;

import java.util.function.Predicate;

public class AmethystLauncher extends RangedWeaponItem {


    //private final Tag<Item> amethystShardTag = MilkevTagRegistry.AMETHYST_SHARD;
    //private final Tag<Item> amethystShardTag = null;

    public static boolean isCharged(ItemStack stack) {
        NbtCompound nbtCompound = stack.getNbt();
        return nbtCompound != null && nbtCompound.getBoolean("Charged");
    }

    public static void setCharged(ItemStack itemStack, boolean charged) {
        NbtCompound itemNBT = itemStack.getOrCreateNbt();
        itemNBT.putBoolean("Charged", charged);
        //System.out.println(itemNBT);
    }


    public AmethystLauncher(Settings settings) {
        super(settings);
    }


    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack itemStack = user.getStackInHand(hand);
        //System.out.println("You Used " + itemStack + "!");

        if(!itemStack.hasNbt()){
            setCharged(itemStack, false);
            //System.out.println("Added Item NBT");
            //System.out.println("SUS CHARGED WITH NBT");
        }

        //System.out.println(Objects.requireNonNull(itemStack.getNbt().get("Charged")).asString());

        //if charged: fire
        //else: charge
        if(isCharged(itemStack)) {
            //System.out.println(itemStack + " FIRED!");
            setCharged(itemStack, false);

            if(getProjectiles(user) != ItemStack.EMPTY) {
                ItemStack ammo = getProjectiles(user);

                //set projectile entity
                //TODO replace with custom amethyst shot entity
                assert ammo != null;
                ArrowItem arrowItem = (ArrowItem) Items.ARROW;
                PersistentProjectileEntity ammoEntity = arrowItem.createArrow(world, ammo, user);
                //set velocity
                Vec3d vec3d = user.getOppositeRotationVector(1.0F);
                Quaternion quaternion = new Quaternion(new Vec3f(vec3d), 0, true);
                Vec3d vec3d2 = user.getRotationVec(1.0F);
                Vec3f vec3f = new Vec3f(vec3d2);
                vec3f.rotate(quaternion);
                ((ProjectileEntity)ammoEntity).setVelocity(vec3f.getX(), vec3f.getY(), vec3f.getZ(), 1, 0);
                //set sounds
                ammoEntity.setSound(SoundEvents.ITEM_CROSSBOW_HIT);
                ammoEntity.setShotFromCrossbow(true);

                //spawn projectile
                world.spawnEntity(ammoEntity);

                world.playSound(null, user.getX(), user.getY(), user.getZ(), SoundEvents.ITEM_CROSSBOW_SHOOT, SoundCategory.PLAYERS, 1.0F, 1.0F);
            } else {
                System.out.println(itemStack + " ERROR PROJECTILE IS NULL");
            }

            return TypedActionResult.consume(itemStack);
        } else {
            //System.out.println(itemStack + " is not loaded with an amethyst shard");
            user.setCurrentHand(hand); //required for it to charge/channel? lmao
            return TypedActionResult.fail(itemStack);
        }
    }

    public void usageTick(World world, LivingEntity user, ItemStack itemStack, int remainingUseTicks) {
        if (!world.isClient) {
            //int i = EnchantmentHelper.getLevel(Enchantments.QUICK_CHARGE, stack);

            if(!isCharged(itemStack)) {
                //if((remainingUseTicks-getMaxUseTime(itemStack)+20)%5==0)
                //System.out.println("Charging: " + (remainingUseTicks-getMaxUseTime(itemStack)+20) + " ticks remaining");
                if(remainingUseTicks <= getMaxUseTime(itemStack)-20) {

                    setCharged(itemStack, true);

                    //consume ammo
                    ItemStack ammo = getProjectiles((PlayerEntity) user);
                    ammo.decrement(1);
                    //System.out.println("CHARGED!");
                    world.playSound(null, user.getX(), user.getY(), user.getZ(), SoundEvents.ENTITY_GENERIC_EXPLODE, SoundCategory.MASTER, 100, 100);
                }
            }

        }

    }
/*
    @Override
    public void onStoppedUsing(ItemStack stack, World world, LivingEntity user, int remainingUseTicks) {

    }
*/
    public int getMaxUseTime(ItemStack stack) {
        return 72000;
    }

    public UseAction getUseAction(ItemStack stack) {
        return UseAction.CROSSBOW;
    }

    private SoundEvent getQuickChargeSound(int stage) {
        return switch (stage) {
            case 1 -> SoundEvents.ITEM_CROSSBOW_QUICK_CHARGE_1;
            case 2 -> SoundEvents.ITEM_CROSSBOW_QUICK_CHARGE_2;
            case 3 -> SoundEvents.ITEM_CROSSBOW_QUICK_CHARGE_3;
            default -> SoundEvents.ITEM_CROSSBOW_LOADING_START;
        };
    }

    private ItemStack getProjectiles(PlayerEntity playerEntity) {
        //System.out.println(playerEntity.getInventory().getStack(0));
        /*
        if(playerEntity.getInventory().contains(amethystShardTag)) {
            //System.out.println("has amethyst shard");
            for (int i = 0; i < 50;) {
                if (playerEntity.getInventory().getStack(i).isOf(Items.AMETHYST_SHARD)) {
                    return playerEntity.getInventory().getStack(i);
                }
                i = i + 1;
            }
        }
         */
        return ItemStack.EMPTY;
    }

    @Override
    public Predicate<ItemStack> getProjectiles() {
        return null;
    }
    @Override
    public int getRange() {
        return 0;
    }
}
