package ru.glitchless.tpmod.items;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public class SpawnTeleportationItem extends BaseTeleportationItem {
    @Override
    void teleport(World worldIn, EntityPlayer player) {
        teleportPlayer(worldIn, player, worldIn.getSpawnPoint().getX(), worldIn.getSpawnPoint().getY(), worldIn.getSpawnPoint().getZ());
    }
}
