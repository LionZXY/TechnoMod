package ru.glitchless.tpmod.config;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import javax.annotation.Nullable;


@Mod.EventBusSubscriber
public class DeathHandler {
    private TableWorldData worldTable = null;

    public DeathHandler() {
        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void onPlayerDeath(LivingDeathEvent event) {
        if (worldTable == null) {
            return;
        }

        if (!(event.getEntityLiving() instanceof EntityPlayerMP)) {
            return;
        }

        EntityPlayerMP entityPlayerMP = (EntityPlayerMP) event.getEntityLiving();
        worldTable.set(entityPlayerMP.getGameProfile().getId().toString(), entityPlayerMP.getPosition());
    }

    @SubscribeEvent
    public void onWorldLoad(WorldEvent.Load event) {
        worldTable = new TableWorldData(event.getWorld().getSaveHandler().getWorldDirectory(), "playerdeath");
        worldTable.load();
    }

    @SubscribeEvent
    public void onWorldSave(WorldEvent.Save event) {
        if (worldTable == null) {
            return;
        }
        worldTable.save();
    }

    @Nullable
    public BlockPos getLastPlayerDeath(EntityPlayer player) {
        if (worldTable == null) {
            return null;
        }
        return worldTable.get(player.getGameProfile().getId().toString());
    }
}
