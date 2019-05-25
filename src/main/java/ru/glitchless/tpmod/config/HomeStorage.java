package ru.glitchless.tpmod.config;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import javax.annotation.Nullable;

@Mod.EventBusSubscriber
public class HomeStorage {
    private TableWorldData worldTable = null;

    public HomeStorage() {
        MinecraftForge.EVENT_BUS.register(this);
    }

    public void setHome(EntityPlayer player, DimensionBlockPos blockPos) {
        if (worldTable == null) {
            return;
        }
        worldTable.set(player.getGameProfile().getId().toString(), blockPos);
    }

    public void setHome(String userId, DimensionBlockPos blockPos) {
        if (worldTable == null) {
            return;
        }
        worldTable.set(userId, blockPos);
    }

    @Nullable
    public DimensionBlockPos getHome(EntityPlayer player) {
        if (worldTable == null) {
            return null;
        }
        return worldTable.get(player.getGameProfile().getId().toString());
    }

    @Nullable
    public DimensionBlockPos getHome(String userId) {
        if (worldTable == null) {
            return null;
        }
        return worldTable.get(userId);
    }

    @SubscribeEvent
    public void onWorldLoad(WorldEvent.Load event) {
        if (event.getWorld().isRemote) {
            return;
        }
        worldTable = new TableWorldData(event.getWorld().getSaveHandler().getWorldDirectory(), "playerhomes");
        worldTable.load();
    }

    @SubscribeEvent
    public void onWorldSave(WorldEvent.Save event) {
        if (event.getWorld().isRemote) {
            return;
        }
        if (worldTable == null) {
            return;
        }
        worldTable.save();
    }

}
