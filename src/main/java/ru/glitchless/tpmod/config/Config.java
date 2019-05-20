package ru.glitchless.tpmod.config;

import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;
import net.minecraftforge.fml.common.Loader;

import java.io.File;

public class Config {
    private Configuration config;
    private boolean isFirstLaunch;

    public Config() {
        File configFile = new File(Loader.instance().getConfigDir(), "tpmod.cfg");

        // initialize your configuration object with your configuration file values.
        config = new Configuration(configFile);

        config.load();
        Property firstLaunch = config.get("general", "first_launch", true);
        isFirstLaunch = firstLaunch.getBoolean();
        firstLaunch.set(false);
        config.save();
    }

    public boolean isFirstLaunch() {
        return isFirstLaunch;
    }
}
