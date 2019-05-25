package ru.glitchless.tpmod.config;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ITeleporter;

import javax.annotation.Nullable;

public class DimensionBlockPos extends BlockPos implements ITeleporter {
    private final int dimension;

    public DimensionBlockPos(int x, int y, int z, int dimension) {
        super(x, y, z);
        this.dimension = dimension;
    }

    public DimensionBlockPos(double x, double y, double z, int dimension) {
        super(x, y, z);
        this.dimension = dimension;
    }

    public DimensionBlockPos(Entity source) {
        super(source);
        this.dimension = source.dimension;
    }

    public DimensionBlockPos(Vec3d vec, int dimension) {
        super(vec);
        this.dimension = dimension;
    }

    public DimensionBlockPos(Vec3i source, int dimension) {
        super(source);
        this.dimension = dimension;
    }

    public int getDimension() {
        return dimension;
    }

    @Override
    public void placeEntity(World world, Entity entity, float yaw) {
        entity.motionX = entity.motionY = entity.motionZ = 0D;
        entity.fallDistance = 0F;

        if (entity instanceof EntityPlayerMP && ((EntityPlayerMP) entity).connection != null) {
            ((EntityPlayerMP) entity).connection.setPlayerLocation(getX(), getY(), getZ(), yaw, entity.rotationPitch);
        } else {
            entity.setLocationAndAngles(getX(), getY(), getZ(), yaw, entity.rotationPitch);
        }
    }

    public Entity teleport(@Nullable Entity entity) {
        if (entity == null || entity.world.isRemote) {
            return entity;
        }

        if (dimension != entity.dimension) {
            return entity.changeDimension(dimension, this);
        }

        placeEntity(entity.world, entity, entity.rotationYaw);
        return entity;
    }
}
