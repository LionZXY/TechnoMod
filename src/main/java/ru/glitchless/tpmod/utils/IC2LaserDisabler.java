package ru.glitchless.tpmod.utils;

import ic2.core.item.tool.ItemToolMiningLaser;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
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
}
