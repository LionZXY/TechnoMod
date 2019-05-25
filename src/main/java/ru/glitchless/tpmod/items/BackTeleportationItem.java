package ru.glitchless.tpmod.items;

import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import ru.glitchless.tpmod.TpMod;

public class BackTeleportationItem extends BaseTeleportationItem {
    @Override
    void teleport(World worldIn, EntityPlayer player) {
        BlockPos blockPos = TpMod.getInstance().getDeathHandler().getLastPlayerDeath(player);
        if (blockPos == null) {
            player.sendMessage(new TextComponentString(I18n.format("tpmod.notback_text")));
            return;
        }
        teleportPlayer(player, blockPos.getX(), blockPos.getY(), blockPos.getZ());
    }
}
