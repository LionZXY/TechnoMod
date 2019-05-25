package ru.glitchless.tpmod.proxy;

import net.minecraft.client.resources.ResourcePackRepository;
import net.minecraftforge.fml.client.FMLClientHandler;
import ru.glitchless.tpmod.config.Configuration;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ClientInit extends CommonInit {

    @Override
    public void preInit() {
        super.preInit();
        addTexturePack();
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
