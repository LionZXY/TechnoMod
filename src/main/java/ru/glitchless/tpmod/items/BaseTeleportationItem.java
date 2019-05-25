package ru.glitchless.tpmod.items;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.*;
import net.minecraft.world.World;
import ru.glitchless.tpmod.config.Configuration;

public abstract class BaseTeleportationItem extends Item {


    public BaseTeleportationItem() {
        this.setCreativeTab(CreativeTabs.MISC);
    }

    public static void teleportPlayer(EntityPlayer entity, int x, int y, int z) {
        entity.world.getChunkProvider().provideChunk(x, z); //Prerender teleport chunk

        entity.motionX = entity.motionY = entity.motionZ = 0D;
        entity.fallDistance = 0F;

        if (entity instanceof EntityPlayerMP && ((EntityPlayerMP) entity).connection != null) {
            ((EntityPlayerMP) entity).connection.setPlayerLocation(x, y, z, entity.rotationYaw, entity.rotationPitch);
        } else {
            entity.setLocationAndAngles(x, y, z, entity.rotationYaw, entity.rotationPitch);
        }
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
        if (Configuration.useRandomTeleportOnlyInOverworld && worldIn.provider.getDimension() != 0) {
            return super.onItemRightClick(worldIn, playerIn, handIn);
        }

        ItemStack stack = playerIn.getHeldItem(handIn);
        if (stack.getCount() == 1) {
            stack = ItemStack.EMPTY;
        } else {
            stack.setCount(stack.getCount() - 1);
        }


        SoundEvent event = SoundEvent.REGISTRY.getObject(new ResourceLocation("entity.splash_potion.break"));
        worldIn.playSound(playerIn.posX, playerIn.posY, playerIn.posZ, event, SoundCategory.BLOCKS, 1.0f, 1.0f, true);

        if (!worldIn.isRemote) {
            new TeleportDelayThread(playerIn, new Runnable() {
                @Override
                public void run() {
                    teleport(worldIn, playerIn);
                }
            }).start();
        }

        return new ActionResult<ItemStack>(EnumActionResult.PASS, stack);
    }

    abstract void teleport(World worldIn, EntityPlayer player);
}
