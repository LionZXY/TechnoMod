package ru.glitchless.tpmod.items;

import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.text.TextComponentString;
import ru.glitchless.tpmod.TpMod;
import ru.glitchless.tpmod.config.DimensionBlockPos;

public class BackTeleportationItem extends BaseTeleportationItem {
    @Override
    void teleport(EntityPlayer player) {
        DimensionBlockPos blockPos = TpMod.getInstance().getDeathHandler().getLastPlayerDeath(player);
        if (blockPos == null) {
            player.sendMessage(new TextComponentString(I18n.format("tpmod.notback_text")));
            return;
        }
        teleportPlayer(player, blockPos);
    }
}
