package ru.glitchless.tpmod;

import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.relauncher.Side;
import org.apache.logging.log4j.Logger;
import ru.glitchless.tpmod.proxy.ClientInit;
import ru.glitchless.tpmod.proxy.ISide;
import ru.glitchless.tpmod.proxy.ServerInit;
import ru.glitchless.tpmod.utils.MainLooper;

@Mod(modid = TpMod.MODID, name = TpMod.NAME, version = TpMod.VERSION)
public class TpMod {
    public static final String MODID = "tpmod";
    public static final String NAME = "TechnoMain util mod";
    public static final String VERSION = "1.1";

    private static MainLooper mainLooper = new MainLooper();
    private static Logger logger;
    private ISide side;

    public static Logger getLogger() {
        return logger;
    }

    public static MainLooper getMainLooper() {
        return mainLooper;
    }

    public static void setMainLooper(MainLooper mainLooper) {
        TpMod.mainLooper = mainLooper;
    }

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        logger = event.getModLog();
        if (FMLCommonHandler.instance().getSide() == Side.CLIENT) {
            side = new ClientInit();
        } else {
            side = new ServerInit();
        }
        side.preInit();
    }
}
