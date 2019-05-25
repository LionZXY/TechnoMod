package ru.glitchless.tpmod.proxy;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import ru.glitchless.tpmod.TpMod;
import ru.glitchless.tpmod.config.Configuration;
import ru.glitchless.tpmod.items.BackTeleportationItem;
import ru.glitchless.tpmod.items.RandomTeleportationItem;
import ru.glitchless.tpmod.reflection.ReplaceServerHelper;

public class CommonInit implements ISide {
    private ReplaceServerHelper replaceHelper = new ReplaceServerHelper();
    private RandomTeleportationItem randomTeleportationItem = new RandomTeleportationItem();
    private BackTeleportationItem backTeleportationItem = new BackTeleportationItem();

    @Override
    public void preInit() {
        try {
            replaceHelper.replaceAll();
        } catch (Exception e) {
            TpMod.getInstance().getLogger().error(e);
            e.printStackTrace();
        }

        initItem();

        Configuration.isFirstLaunch = false;
        ConfigManager.sync(TpMod.MODID, Config.Type.INSTANCE);
        MinecraftForge.EVENT_BUS.register(TpMod.getInstance().getMainLooper());
    }

    private void initItem() {
        randomTeleportationItem.setUnlocalizedName("tpmod_random");
        randomTeleportationItem.setRegistryName("tpmod_random");

        backTeleportationItem.setUnlocalizedName("tpmod_back");
        backTeleportationItem.setRegistryName("tpmod_back");

        ForgeRegistries.ITEMS.register(randomTeleportationItem);
        ForgeRegistries.ITEMS.register(backTeleportationItem);
    }
}
