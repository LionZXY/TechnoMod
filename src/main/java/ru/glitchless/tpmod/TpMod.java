package ru.glitchless.tpmod;

import net.minecraft.client.gui.recipebook.RecipeList;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.relauncher.Side;
import org.apache.logging.log4j.Logger;
import ru.glitchless.tpmod.cmds.SetSpawnCommand;
import ru.glitchless.tpmod.proxy.ClientInit;
import ru.glitchless.tpmod.proxy.ISide;
import ru.glitchless.tpmod.proxy.ServerInit;
import ru.glitchless.tpmod.utils.CrashReporter;
import ru.glitchless.tpmod.utils.IC2LaserDisabler;

@Mod(modid = TpMod.MODID, name = TpMod.NAME, version = TpMod.VERSION)
public class TpMod {
    public static final String MODID = "tpmod";
    public static final String NAME = "TechnoMain util mod";
    public static final String VERSION = "1.3.4";

    private static TpMod INSTANCE;
    private static Logger logger;
    private ISide side;

    public TpMod() {
        INSTANCE = this;
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
        MinecraftForge.EVENT_BUS.register(IC2LaserDisabler.class);
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        side.postInit();
    }

    @Mod.EventHandler
    public void onServerStarting(FMLServerStartingEvent event) {
        event.registerServerCommand(new SetSpawnCommand());
    }

    public static TpMod getInstance() {
        return INSTANCE;
    }

    public Logger getLogger() {
        return logger;
    }
}
