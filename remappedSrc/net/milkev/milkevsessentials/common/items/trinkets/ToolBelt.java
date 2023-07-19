package net.milkev.mealapiaddon.common.items.trinkets;

import dev.emi.trinkets.api.TrinketItem;
import dev.emi.trinkets.api.TrinketsApi;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.registry.Registry;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;

import java.util.List;
import java.util.Objects;

public class ToolBelt extends TrinketItem {

    public ToolBelt(Settings settings) {
        super(settings);
        TrinketsApi.registerTrinket(this, this);
    }

    public void swapItems(ServerPlayerEntity player, ItemStack toolBelt) {

        PlayerInventory inventory = player.getInventory();

        //System.out.println(ID);

        NbtCompound toolBeltNbt = toolBelt.getOrCreateNbt();

        /*
        Item item = player.getInventory().getStack(0).getItem();
        if(item != null) {
            int count = player.getInventory().getStack(0).getCount();
            ItemStack newStack = item.getDefaultStack();
            newStack.setCount(count);
            System.out.println("ITEM: " + newStack);
            System.out.println("ITEM COUNT: " + count);
        } else {
            System.out.println("ITEM WAS NULL");
            */

        for(int i = 0; i < 9; i++) {
            //init variable holding itemstack that is currently in the belt, and will be moved out of the belt at the end
            ItemStack toHotbar = ItemStack.EMPTY;
            //grab identifier
            Identifier toolBeltItemID = makeIdentifier(toolBelt, i);
            //grab nbt
            NbtElement toolBeltItemNbt = toolBelt.getOrCreateNbt().get("Slot_" + i + "_nbt");
            //grab count
            int count = 0;
            assert toolBelt.getNbt() != null;
            if(toolBelt.getNbt().contains("Slot_" + i + "_count")) {
                count = Integer.parseInt(toolBelt.getOrCreateNbt().get("Slot_" + i + "_count").asString());
            }
            if (!toolBeltItemID.equals(new Identifier("minecraft", "air"))) {
                //System.out.println("ITEM TO GO TO HOTBAR, SLOT " + i + ": " + toolBeltItemID + ", COUNT: " + count);
                toHotbar = new ItemStack(Registry.ITEM.get(toolBeltItemID), count);
                if (toolBeltItemNbt != null) {
                    //System.out.println("ITEM TO GO TO HOTBAR, SLOT " + i + ": NBT: " + toolBeltItemNbt);
                    toHotbar.setNbt((NbtCompound) toolBeltItemNbt);
                } else {
                    //System.out.println("ITEM TO GO TO HOTBAR, SLOT " + i + ": NBT EMPTY");
                }
            } else {
                //System.out.println("ITEM TO GO TO HOTBAR, SLOT " + i + ": EMPTY");
            }

            //sus
            ItemStack itemStack = player.getInventory().getStack(i);
            NbtElement itemNbt = itemStack.getNbt();
            //get ID of item we want to put into the toolbelt
            Identifier ID = Registry.ITEM.getId(player.getInventory().getStack(i).getItem());

            if (itemStack != ItemStack.EMPTY) {
                if (itemNbt != null) {
                    toolBeltNbt.put("Slot_" + i + "_nbt", itemNbt);
                    //System.out.println("SETTING TOOLBELT NBT, SLOT " + i + ": " + itemNbt);
                } else {
                    //System.out.println("SETTING TOOLBELT NBT: ITEM HAS NO NBT DATA");
                    toolBeltNbt.remove("Slot_" + i + "_nbt");
                }
                toolBeltNbt.putString("Slot_" + i + "_identifier", String.valueOf(ID));
                toolBeltNbt.putInt("Slot_" + i + "_count", itemStack.getCount());
                //System.out.println("SETTING TOOLBELT ITEM, SLOT " + i + ": " + ID + ", COUNT: " + itemStack.getCount());
            } else {
                //if nothing to put into toolbelt, remove slot nbt data
                toolBeltNbt.remove("Slot_" + i + "_identifier");
                toolBeltNbt.remove("Slot_" + i + "_count");
                toolBeltNbt.remove("Slot_" + i + "_nbt");
            }
            inventory.setStack(i, toHotbar);
        }
    }

    private Identifier makeIdentifier(ItemStack toolBelt, int i) {
        String toolBeltItemIDString = "minecraft:air";
        if(toolBelt.getOrCreateNbt().contains("Slot_" + i + "_identifier")) {
            toolBeltItemIDString = toolBelt.getOrCreateNbt().get("Slot_" + i + "_identifier").toString();
        }
        String toolBeltItemIDMOD_ID = toolBeltItemIDString.substring(1,toolBeltItemIDString.indexOf(":"));
        //System.out.println("MODID OF ITEM TO GO TO HOTBAR: " + toolBeltItemIDMOD_ID);
        String toolBeltItemIDNamespace = toolBeltItemIDString.substring(toolBeltItemIDString.indexOf(":")+1, toolBeltItemIDString.length()-1);
        //System.out.println("NAMESPACE OF ITEM TO GO TO HOTBAR: " + toolBeltItemIDNamespace);
        return new Identifier(toolBeltItemIDMOD_ID, toolBeltItemIDNamespace);
    }

    @Override
    public void appendTooltip(ItemStack itemStack, World world, List<Text> tooltip, TooltipContext tooltipContext) {

        //trinkets wont auto put the 'equip in slot' on this trinket, dunno why :/
        tooltip.add(Text.translatable("item.milkevsessentials.toolbelt.equip_in_slot"));
        //displays that the trinket allows moar hotbar
        tooltip.add(Text.translatable("item.milkevsessentials.toolbelt.when_equip"));
        tooltip.add(Text.translatable("item.milkevsessentials.toolbelt.allow"));
    }

}
