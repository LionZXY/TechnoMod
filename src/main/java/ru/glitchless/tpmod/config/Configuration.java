package ru.glitchless.tpmod.config;

import net.minecraftforge.common.config.Config;
import ru.glitchless.tpmod.TpMod;


@Config(modid = TpMod.MODID)
public class Configuration {
    @Config.Comment("internal thing")
    public static boolean isFirstLaunch = true;

    @Config.Comment("delay teleport")
    public static int tpDelay = 5;

    @Config.Comment("random teleport range in blocks. startX, startZ, endX, endZ Example: -100, 100, -100, -100")
    public static String randomTeleportRange = "";
    public static boolean useRandomTeleportOnlyInOverworld = true;
}
