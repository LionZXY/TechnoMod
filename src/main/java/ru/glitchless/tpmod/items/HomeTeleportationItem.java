package ru.glitchless.tpmod.items;

import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.server.command.TextComponentHelper;
import ru.glitchless.tpmod.config.DimensionBlockPos;
import ru.glitchless.tpmod.config.HomeStorage;

import javax.annotation.Nullable;
import java.util.List;

public class HomeTeleportationItem extends BaseTeleportationItem {
    @Override
    void teleport(EntityPlayer player) {
        final HomeStorage homeStorage = HomeStorage.getInstance(player.getEntityWorld());
        DimensionBlockPos blockPos = homeStorage.getHome(player);
        if (blockPos == null) {
            player.sendMessage(TextComponentHelper.createComponentTranslation(player, "tpmod.nothome_text"));
            return;
        }
        DimensionBlockPos teleportPos = new DimensionBlockPos(blockPos.getX(), blockPos.getY() + 2, blockPos.getZ(), blockPos.getDimension());
        teleportPlayer(player, teleportPos);
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
        super.addInformation(stack, worldIn, tooltip, flagIn);
        tooltip.add(I18n.format("item.tpmod_home.description"));
    }
}
