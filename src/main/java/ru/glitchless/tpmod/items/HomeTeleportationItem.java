package ru.glitchless.tpmod.items;

import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import ru.glitchless.tpmod.TpMod;

public class HomeTeleportationItem extends BaseTeleportationItem {
    @Override
    void teleport(World worldIn, EntityPlayer player) {
        BlockPos blockPos = TpMod.getInstance().getHomeStorage().getHome(player);
        if (blockPos == null) {
            player.sendMessage(new TextComponentString(I18n.format("tpmod.nothome_text")));
            return;
        }
        teleportPlayer(worldIn, player, blockPos.getX(), blockPos.getY(), blockPos.getZ());
    }
}
