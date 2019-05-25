package ru.glitchless.tpmod.items;

import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import ru.glitchless.tpmod.TpMod;
import ru.glitchless.tpmod.config.DimensionBlockPos;

import javax.annotation.Nullable;
import java.util.List;

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

    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
        super.addInformation(stack, worldIn, tooltip, flagIn);
        tooltip.add(I18n.format("item.tpmod_back.description"));
    }
}
