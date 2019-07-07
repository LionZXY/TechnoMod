package ru.glitchless.tpmod.proxy;

import net.minecraft.item.ItemBlock;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import ru.glitchless.tpmod.TpMod;
import ru.glitchless.tpmod.blocks.HomeBlock;
import ru.glitchless.tpmod.config.Configuration;
import ru.glitchless.tpmod.items.BackTeleportationItem;
import ru.glitchless.tpmod.items.HomeTeleportationItem;
import ru.glitchless.tpmod.items.RandomTeleportationItem;
import ru.glitchless.tpmod.items.SpawnTeleportationItem;
import ru.glitchless.tpmod.reflection.ReplaceServerHelper;

public class CommonInit implements ISide {
    protected ReplaceServerHelper replaceHelper = new ReplaceServerHelper();
    protected RandomTeleportationItem randomTeleportationItem = new RandomTeleportationItem();
    protected BackTeleportationItem backTeleportationItem = new BackTeleportationItem();
    protected HomeTeleportationItem homeTeleportationItem = new HomeTeleportationItem();
    protected SpawnTeleportationItem spawnTeleportationItem = new SpawnTeleportationItem();
    protected HomeBlock homeBlock = new HomeBlock();
    protected ItemBlock homeItemBlock = new ItemBlock(homeBlock);

    @Override
    public void preInit() {
        try {
            replaceHelper.replaceAll();
        } catch (Exception e) {
            TpMod.getInstance().getLogger().error(e);
            e.printStackTrace();
        }

        initItem();
        initBlock();

        Configuration.isFirstLaunch = false;
        ConfigManager.sync(TpMod.MODID, Config.Type.INSTANCE);
        MinecraftForge.EVENT_BUS.register(TpMod.getInstance().getMainLooper());
    }

    private void initItem() {
        randomTeleportationItem.setUnlocalizedName("tpmod_random");
        randomTeleportationItem.setRegistryName("tpmod_random");

        backTeleportationItem.setUnlocalizedName("tpmod_back");
        backTeleportationItem.setRegistryName("tpmod_back");

        homeTeleportationItem.setUnlocalizedName("tpmod_home");
        homeTeleportationItem.setRegistryName("tpmod_home");

        spawnTeleportationItem.setUnlocalizedName("tpmod_spawn");
        spawnTeleportationItem.setRegistryName("tpmod_spawn");

        ForgeRegistries.ITEMS.register(randomTeleportationItem);
        ForgeRegistries.ITEMS.register(backTeleportationItem);
        ForgeRegistries.ITEMS.register(homeTeleportationItem);
        ForgeRegistries.ITEMS.register(spawnTeleportationItem);
    }

    private void initBlock() {
        homeBlock.setUnlocalizedName("tpmod_homeset");
        homeBlock.setRegistryName("tpmod_homeset");

        ForgeRegistries.BLOCKS.register(homeBlock);

        homeItemBlock.setRegistryName(homeBlock.getRegistryName());

        ForgeRegistries.ITEMS.register(homeItemBlock);
    }
}
