package ru.glitchless.tpmod.config;

import net.minecraftforge.common.config.Config;
import ru.glitchless.tpmod.TpMod;


@Config(modid = TpMod.MODID)
public class Configuration {
    @Config.Comment("internal thing")
    public static boolean isFirstLaunch = true;
}
