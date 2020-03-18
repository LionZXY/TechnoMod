package ru.glitchless.tpmod.proxy;

import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;
import ru.glitchless.tpmod.TpMod;
import ru.glitchless.tpmod.config.Configuration;
import ru.glitchless.tpmod.reflection.ReplaceServerHelper;
import ru.glitchless.tpmod.utils.IC2LaserDisabler;

public class CommonInit implements ISide {
    protected ReplaceServerHelper replaceHelper = new ReplaceServerHelper();

    @Override
    public void preInit() {
        try {
            replaceHelper.replaceAll();
        } catch (Exception e) {
            TpMod.getInstance().getLogger().error(e);
            e.printStackTrace();
        }

        Configuration.isFirstLaunch = false;
        ConfigManager.sync(TpMod.MODID, Config.Type.INSTANCE);
    }

    @Override
    public void postInit() {

    }
}
