package ru.glitchless.tpmod.proxy;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.resources.ResourcePackRepository;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.client.FMLClientHandler;
import ru.glitchless.tpmod.blocks.HomeBlockEnum;
import ru.glitchless.tpmod.config.Configuration;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ClientInit extends CommonInit {

    @Override
    public void preInit() {
        super.preInit();
        addTexturePack();
        initItemTexture();
        initBlockTexture();
    }

    private void initItemTexture() {
        ModelResourceLocation backModelResourceLocation = new ModelResourceLocation("tpmod:tpmod_back", "inventory");
        ModelResourceLocation homeModelResourceLocation = new ModelResourceLocation("tpmod:tpmod_home", "inventory");
        ModelResourceLocation randomModelResourceLocation = new ModelResourceLocation("tpmod:tpmod_random", "inventory");
        ModelResourceLocation spawnModelResourceLocation = new ModelResourceLocation("tpmod:tpmod_spawn", "inventory");
        ModelLoader.setCustomModelResourceLocation(backTeleportationItem, 0, backModelResourceLocation);
        ModelLoader.setCustomModelResourceLocation(homeTeleportationItem, 0, homeModelResourceLocation);
        ModelLoader.setCustomModelResourceLocation(randomTeleportationItem, 0, randomModelResourceLocation);
        ModelLoader.setCustomModelResourceLocation(spawnTeleportationItem, 0, spawnModelResourceLocation);
    }

    private void initBlockTexture() {
        ModelResourceLocation homeModelResourceLocation = new ModelResourceLocation("tpmod:tpmod_homeset", "inventory");
        ModelLoader.setCustomModelResourceLocation(homeItemBlock, HomeBlockEnum.DISACTIVATE.getId(), homeModelResourceLocation);
        ModelResourceLocation activeHomeModelResourceLocation = new ModelResourceLocation("tpmod:tpmod_homeset_active", "inventory");
        ModelLoader.setCustomModelResourceLocation(homeItemBlock, HomeBlockEnum.ACTIVATE.getId(), activeHomeModelResourceLocation);
    }

    private void addTexturePack() {
        if (!new File("resourcepacks/F32-1.12.2.zip").exists()) {
            return;
        }
        if (!Configuration.isFirstLaunch) {
            return;
        }
        String f32Name = "F32-1.12.2.zip";

        ResourcePackRepository repository = FMLClientHandler.instance().getClient().getResourcePackRepository();


        ResourcePackRepository.Entry faithfulEntry = null;
        for (ResourcePackRepository.Entry entry : repository.getRepositoryEntriesAll()) {
            if (entry.getResourcePackName().equals(f32Name)) {
                faithfulEntry = entry;
            }
        }

        if (faithfulEntry == null) {
            return;
        }

        List<ResourcePackRepository.Entry> currentList = new ArrayList<ResourcePackRepository.Entry>(repository.getRepositoryEntries());
        for (ResourcePackRepository.Entry entry : currentList) {
            if (entry.getResourcePackName().equals(f32Name)) {
                return;
            }
        }
        currentList.add(faithfulEntry);
        repository.setRepositories(currentList);
    }
}
