package net.milkev.mealapiaddon.common.registry;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.cloth.clothconfig.shadowed.blue.endless.jankson.annotation.Nullable;
import net.milkev.mealapiaddon.common.MilkevsEssentials;
import net.milkev.mealapiaddon.common.ModConfig;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.PathMatcher;
import java.nio.file.Paths;
import java.util.Arrays;

public class MilkevRecipeRegistry {

    public static JsonObject FLIGHT_CHARM = null;
    public static JsonObject EXTENDO_GRIP_HIGH = null;
    public static JsonObject EXTENDO_GRIP_NORMAL = null;
    public static JsonObject EXTENDO_GRIP_LOW = null;
    public static JsonObject TOOLBELT = null;
    private static final Gson GSON = new Gson();

    //DONT FORGET TO ADD RECIPES TO THE MIXIN AS WELL
    //MilkevRecipeRegistryMixin.java

    /*
    public static void init() {
        ModConfig config = AutoConfig.getConfigHolder(ModConfig.class).getConfig();

        if (config.enableFlightCharm) {
            FLIGHT_CHARM = getRecipe("flight_charm");
        }
        if (config.enableExtendoGrips) {
            EXTENDO_GRIP_LOW = getRecipe("extendo_grip_low");
            EXTENDO_GRIP_NORMAL = getRecipe("extendo_grip_normal");
            EXTENDO_GRIP_HIGH = getRecipe("extendo_grip_high");
        }
        if (config.enableToolBelt) {
            TOOLBELT = getRecipe("toolbelt");
        }
    }
    */


    /*
    public static @Nullable
    JsonObject getRecipe(final String name) {

        System.out.println("===================================================================================================================");

        final String path = "/data/milkevsessentials/dynamic_recipes/" + name + ".json";
        System.out.println("Path; " + path);

        final URL file = MilkevsEssentials.class.getResource(path);
        System.out.println("File; " + file);

        assert file != null;





        try {

            System.out.println("pre assert path");

            assert Path.of(file.getPath()) != null;

            //this needs to not have ':' in it?

            String strong = file.getPath();

            System.out.println("File as String; " + strong);

            String stronger = strong.substring(6, 7) + strong.substring(7, strong.length());
            //strong.substring(0,4) +

            System.out.println("File as String without ':' ; " + stronger);

            System.out.println("File Path of Stronger; " + Path.of(stronger));


            final Path pathToFile = Paths.get(file.getPath());
            System.out.println("Path To File; " + pathToFile);

            final Path pathToFileAbsolute = pathToFile.toAbsolutePath();
            System.out.println("Absolute Path To File; " + pathToFileAbsolute);


            System.out.println("Buffered Reader; " + Files.newBufferedReader(Path.of(stronger)));

            System.out.println("pre try");

            try (final var reader = Files.newBufferedReader(Path.of(stronger))) {
                System.out.println("pro try"); //not reached
                return GSON.fromJson(reader, JsonObject.class);
            }
        } catch (final IOException e) {
        //catch (final IOException | URISyntaxException e) {
            System.out.println("e; " + e);
            System.out.println("recipe reader borked reading: " + path);
            return null;
        }
    }
    */
}
