package ru.glitchless.tpmod.proxy;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import ru.glitchless.tpmod.TpMod;
import ru.glitchless.tpmod.config.Configuration;
import ru.glitchless.tpmod.items.RandomTeleportationItem;
import ru.glitchless.tpmod.reflection.ReplaceServerHelper;

public class CommonInit implements ISide {
    private ReplaceServerHelper replaceHelper = new ReplaceServerHelper();
    private RandomTeleportationItem baseTeleportationItem = new RandomTeleportationItem();

    @Override
    public void preInit() {
        try {
            replaceHelper.replaceAll();
        } catch (Exception e) {
            TpMod.getLogger().error(e);
            e.printStackTrace();
        }

        initItem();

        Configuration.isFirstLaunch = false;
        ConfigManager.sync(TpMod.MODID, Config.Type.INSTANCE);
        MinecraftForge.EVENT_BUS.register(TpMod.getMainLooper());
    }

    private void initItem() {
        baseTeleportationItem.setUnlocalizedName("tpmod_random");
        baseTeleportationItem.setRegistryName("tpmod_random_registry_name");
        ForgeRegistries.ITEMS.register(baseTeleportationItem);
    }
}
