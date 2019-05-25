package ru.glitchless.tpmod.items;

import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import ru.glitchless.tpmod.config.Configuration;
import ru.glitchless.tpmod.config.DimensionBlockPos;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Random;

public class RandomTeleportationItem extends BaseTeleportationItem {
    private final Random random = new Random();
    private final int DEFAULT_BORDER_RADIUS = 30000000 - 100;

    @Override
    void teleport(EntityPlayer player) {
        World worldIn = player.world;
        // startX, startZ, endX, endZ
        final String randomTeleportRangeString = Configuration.randomTeleportRange;
        int startX;
        int startZ;
        int endX;
        int endZ;
        if (randomTeleportRangeString.length() != 0) {
            final String[] randomTeleportRange = randomTeleportRangeString.split(", ");
            startX = Integer.valueOf(randomTeleportRange[0].trim());
            startZ = Integer.valueOf(randomTeleportRange[1].trim());
            endX = Integer.valueOf(randomTeleportRange[2].trim());
            endZ = Integer.valueOf(randomTeleportRange[3].trim());
        } else {
            startX = worldIn.getSpawnPoint().getX() - DEFAULT_BORDER_RADIUS;
            endX = worldIn.getSpawnPoint().getX() + DEFAULT_BORDER_RADIUS;
            startZ = worldIn.getSpawnPoint().getZ() - DEFAULT_BORDER_RADIUS;
            endZ = worldIn.getSpawnPoint().getZ() + DEFAULT_BORDER_RADIUS;
        }
        int randomX = (int) (startX + random.nextDouble() * (endX - startX));
        int randomZ = (int) (startZ + random.nextDouble() * (endZ - startZ));

        int randomY = findY(worldIn, randomX, randomZ);

        teleportPlayer(player, new DimensionBlockPos(randomX, randomY, randomZ, worldIn.provider.getDimension()));
        SoundEvent event = SoundEvent.REGISTRY.getObject(new ResourceLocation("entity.endermen.teleport"));
        worldIn.playSound(randomX, randomY, randomZ, event, SoundCategory.BLOCKS, 1.0f, 1.0f, true);
    }

    private int findY(World world, int x, int z) {
        for (int y = 255; y > 0; y--) {
            if (!world.isAirBlock(new BlockPos(x, y, z))) {
                return y;
            }
        }
        return 255;
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
        super.addInformation(stack, worldIn, tooltip, flagIn);
        tooltip.add(I18n.format("item.tpmod_random.description"));
    }
}
