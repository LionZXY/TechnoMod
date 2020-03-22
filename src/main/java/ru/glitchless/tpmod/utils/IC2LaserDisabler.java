package ru.glitchless.tpmod.utils;

import ic2.core.item.tool.ItemToolMiningLaser;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class IC2LaserDisabler {
    @SubscribeEvent
    public static void onRightClick(PlayerInteractEvent.RightClickItem entityInteract) {
        final ItemStack itemStack = entityInteract.getItemStack();
        if (itemStack == null) {
            return;
        }
        final Item item = itemStack.getItem();
        if (item == null) {
            return;
        }
        if (item instanceof ItemToolMiningLaser) {
            entityInteract.setCanceled(true);
        }
    }

    @SubscribeEvent
    public static void onBlockPlaced(BlockEvent.EntityPlaceEvent event) {
        final IBlockState blockState = event.getPlacedBlock();
        if (blockState == null) {
            return;
        }
        final Block block = blockState.getBlock();
        if (block == null) {
            return;
        }
        if (block != Blocks.HOPPER) {
            return;
        }

        event.setCanceled(true);

        if (event.getWorld().isRemote) {
            return;
        }

        final Entity entity =  event.getEntity();
        if (entity instanceof EntityPlayer) {
            entity.sendMessage(new TextComponentString("На сервере запрещена установка загрузочных воронок (hopper), " +
                    "так как они лагают. Используйте вместо них воронки из Quark, транспортную систему Extra Utilites " +
                    "или транспортную систему из IC2"));
        }
    }
}
