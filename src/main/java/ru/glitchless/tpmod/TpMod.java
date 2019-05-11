package ru.glitchless.tpmod;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.Logger;
import ru.glitchless.tpmod.reflection.ReplaceServerHelper;
import ru.glitchless.tpmod.server.PlayerServer;

@Mod(modid = TpMod.MODID, name = TpMod.NAME, version = TpMod.VERSION)
public class TpMod {
    public static final String MODID = "tpmod";
    public static final String NAME = "TechnoMain util mod";
    public static final String VERSION = "1.1";

    private static Logger logger;
    private ReplaceServerHelper replaceHelper = new ReplaceServerHelper();
    private PlayerServer playerServer = new PlayerServer();

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
    }


}
