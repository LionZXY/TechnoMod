package ru.glitchless.tpmod;

import net.minecraft.init.Blocks;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.Logger;
import ru.glitchless.tpmod.reflection.ReplaceServerHelper;

@Mod(modid = TpMod.MODID, name = TpMod.NAME, version = TpMod.VERSION)
public class TpMod {
    public static final String MODID = "tpmod";
    public static final String NAME = "TechnoMain util mod";
    public static final String VERSION = "1.0";

    private static Logger logger;
    private ReplaceServerHelper replaceHelper = new ReplaceServerHelper();

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        logger = event.getModLog();
        try {
            replaceHelper.replaceAll();
        } catch (Exception e) {
            logger.error(e);
            e.printStackTrace();
        }
    }
}
