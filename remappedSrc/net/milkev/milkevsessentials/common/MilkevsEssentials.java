package net.milkev.mealapiaddon.common;

import Z;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.JanksonConfigSerializer;
import me.shedaniel.cloth.clothconfig.shadowed.blue.endless.jankson.annotation.Nullable;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.fabricmc.fabric.api.resource.ResourcePackActivationType;
import net.fabricmc.loader.api.FabricLoader;
import net.milkev.mealapiaddon.common.items.trinkets.ExtendoGrip;
import net.milkev.mealapiaddon.common.items.trinkets.FlightCharm;
import net.milkev.mealapiaddon.common.items.trinkets.ToolBelt;
import net.milkev.mealapiaddon.common.items.weapons.AmethystLauncher;
import net.milkev.mealapiaddon.common.network.ToolBeltNetworking;
import net.milkev.mealapiaddon.common.registry.MilkevRecipeRegistry;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.item.ItemGroup;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import org.lwjgl.system.CallbackI;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Objects;

public class MilkevsEssentials implements ModInitializer {


	public static final String MOD_ID = "milkevsessentials";

	public static FlightCharm FLIGHT_CHARM = null;

	public static ToolBelt TOOL_BELT = null;

	/*
	public static final AmethystLauncher AMETHYST_LAUNCHER = new AmethystLauncher(new FabricItemSettings().maxCount(1).group(ItemGroup.COMBAT));
	public static final EntityType<AmethystShot> AMETHYST_SHOT_ENTITY_TYPE = Registry.register(
			Registry.ENTITY_TYPE,
			new Identifier(MOD_ID, "amethyst_shot"),
			FabricEntityTypeBuilder.<AmethystShot>create(SpawnGroup.MISC, AmethystShot::new)
					.dimensions(EntityDimensions.fixed(0.25F, 0.25F)) // dimensions in Minecraft units of the projectile
					.trackRangeBlocks(4).trackedUpdateRate(10) // necessary for all thrown projectiles (as it prevents it from breaking, lol)
					.build() // VERY IMPORTANT DONT DELETE FOR THE LOVE OF GOD PSLSSSSSS
	);*/


	@Override
	public void onInitialize() {

		AutoConfig.register(ModConfig.class, JanksonConfigSerializer::new);
		ModConfig config = AutoConfig.getConfigHolder(ModConfig.class).getConfig();

		if(config.enableExtendoGrips) {
			DynamicDatapacks("extendo_grips");
			Registry.register(Registry.ITEM, new Identifier(MOD_ID, "extendo_grip_low"), setExtendoGrips(config.extendoGripsLowBlockReach, config.extendoGripsLowAttackReach));
			Registry.register(Registry.ITEM, new Identifier(MOD_ID, "extendo_grip_normal"), setExtendoGrips(config.extendoGripsNormalBlockReach, config.extendoGripsNormalAttackReach));
			Registry.register(Registry.ITEM, new Identifier(MOD_ID, "extendo_grip_high"), setExtendoGrips(config.extendoGripsHighBlockReach, config.extendoGripsHighAttackReach));
		}
		if(config.enableFlightCharm) {
			FLIGHT_CHARM = new FlightCharm(new FabricItemSettings().maxCount(1).group(ItemGroup.TOOLS));
			DynamicDatapacks("flight_charm");
			Registry.register(Registry.ITEM, new Identifier(MOD_ID, "flight_charm"), FLIGHT_CHARM);
		}
		/*
		if(config.enableAmethystLauncher) {
			//DynamicDataRecipe("amethyst_launcher"); //recipe doesnt exist yet
			TagFactory.ITEM.create(new Identifier(MOD_ID, "amethyst_shard"));
			Registry.register(Registry.ITEM, new Identifier(MOD_ID, "amethyst_launcher"), AMETHYST_LAUNCHER);
		}*/
		if(config.enableToolBelt) {
			TOOL_BELT = new ToolBelt(new FabricItemSettings().maxCount(1).group(ItemGroup.TOOLS));
			DynamicDatapacks("toolbelt");
			Registry.register(Registry.ITEM, new Identifier(MOD_ID, "toolbelt"), TOOL_BELT);
			ToolBeltNetworking.init();
		}
		if(config.milkevsCustomRules) {
			DynamicDatapacks("milkevscustomrules");
		}


		//MilkevRecipeRegistry.init();

		System.out.println(MOD_ID + " Initialized");
	}

	public void DynamicDatapacks(String datapackName) {
		FabricLoader.getInstance().getModContainer(MOD_ID).ifPresent(modContainer -> {
			var added = ResourceManagerHelper.registerBuiltinResourcePack(new Identifier(MOD_ID, datapackName), modContainer, ResourcePackActivationType.ALWAYS_ENABLED);
		});

		//System.out.println("Datapack Added: " + datapackName);
	}

	public ExtendoGrip setExtendoGrips(int reach, int attack_reach) {
		return new ExtendoGrip(new FabricItemSettings().maxCount(1).group(ItemGroup.TOOLS), reach, attack_reach);
	}


}
