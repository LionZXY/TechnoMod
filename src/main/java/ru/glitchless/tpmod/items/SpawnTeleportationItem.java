package ru.glitchless.tpmod.items;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraftforge.common.DimensionManager;
import ru.glitchless.tpmod.config.DimensionBlockPos;

public class SpawnTeleportationItem extends BaseTeleportationItem {
    @Override
    void teleport(EntityPlayer player) {
        World overworld = DimensionManager.getWorld(0);
        teleportPlayer(player, new DimensionBlockPos(overworld.getSpawnPoint(), overworld.provider.getDimension()));
    }
}
