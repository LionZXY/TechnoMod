package ru.glitchless.tpmod;

import net.minecraft.client.resources.ResourcePackRepository;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.relauncher.Side;
import org.apache.logging.log4j.Logger;
import ru.glitchless.tpmod.config.Config;
import ru.glitchless.tpmod.reflection.ReplaceServerHelper;
import ru.glitchless.tpmod.server.PlayerServer;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Mod(modid = TpMod.MODID, name = TpMod.NAME, version = TpMod.VERSION)
public class TpMod {
    public static final String MODID = "tpmod";
    public static final String NAME = "TechnoMain util mod";
    public static final String VERSION = "1.1";

    private static Logger logger;
    private ReplaceServerHelper replaceHelper = new ReplaceServerHelper();
    private PlayerServer playerServer = new PlayerServer();
    private Config config = new Config();

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        logger = event.getModLog();
        try {
            replaceHelper.replaceAll();
        } catch (Exception e) {
            logger.error(e);
            e.printStackTrace();
        }
        playerServer.init();
        if (FMLCommonHandler.instance().getSide() == Side.CLIENT) {
            addTexturePack();
        }
    }

    private void addTexturePack() {
        if (!new File("resourcepacks/F32-1.12.2.zip").exists()) {
            return;
        }
        if (!config.isFirstLaunch()) {
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
