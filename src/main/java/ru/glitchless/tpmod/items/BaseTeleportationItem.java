package ru.glitchless.tpmod.items;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.*;
import net.minecraft.world.World;
import net.minecraftforge.common.DimensionManager;
import ru.glitchless.tpmod.config.Configuration;
import ru.glitchless.tpmod.config.DimensionBlockPos;

public abstract class BaseTeleportationItem extends Item {


    public BaseTeleportationItem() {
        this.setCreativeTab(CreativeTabs.MISC);
    }

    public static void teleportPlayer(EntityPlayer entity, DimensionBlockPos dimensionBlockPos) {
        dimensionBlockPos.teleport(entity);

        World world = DimensionManager.getWorld(dimensionBlockPos.getDimension());
        SoundEvent event = SoundEvent.REGISTRY.getObject(new ResourceLocation("entity.endermen.teleport"));
        if (event == null) {
            return;
        }
        world.playSound(null, dimensionBlockPos.getX(), dimensionBlockPos.getY(), dimensionBlockPos.getZ(), event, SoundCategory.BLOCKS, 1.0f, 1.0f);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
        if (Configuration.useRandomTeleportOnlyInOverworld && worldIn.provider.getDimension() != 0) {
            return super.onItemRightClick(worldIn, playerIn, handIn);
        }

        ItemStack stack = playerIn.getHeldItem(handIn);

        SoundEvent event = SoundEvent.REGISTRY.getObject(new ResourceLocation("entity.splash_potion.break"));
        if (event != null) {
            worldIn.playSound(null, playerIn.posX, playerIn.posY, playerIn.posZ, event, SoundCategory.BLOCKS, 1.0f, 1.0f);
        }
        if (!worldIn.isRemote) {
            stack.shrink(1);
            new TeleportDelayThread(playerIn, new Runnable() {
                @Override
                public void run() {
                    teleport(playerIn);
                }
            }).start();
        }

        return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, stack);
    }

    abstract void teleport(EntityPlayer player);
}
