package ru.glitchless.tpmod.config;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ITeleporter;
import ru.glitchless.tpmod.TpMod;

import javax.annotation.Nullable;
import java.util.Objects;

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

    public DimensionBlockPos(Vec3i source, World world) {
        super(source);
        this.dimension = world.provider.getDimension();
    }

    public int getDimension() {
        return dimension;
    }

    public static DimensionBlockPos readFromNBT(NBTTagCompound nbt) {
        int x = nbt.getInteger("x");
        int y = nbt.getInteger("y");
        int z = nbt.getInteger("z");
        int dimension = nbt.getInteger("dimension");
        return new DimensionBlockPos(x, y, z, dimension);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DimensionBlockPos)) return false;
        if (!super.equals(o)) return false;
        DimensionBlockPos that = (DimensionBlockPos) o;
        return dimension == that.dimension;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), dimension);
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

        TpMod.getInstance().getLogger().info("Teleport " + entity.getName() + " to " + this);

        if (dimension != entity.dimension) {
            return entity.changeDimension(dimension, this);
        }

        placeEntity(entity.world, entity, entity.rotationYaw);
        return entity;
    }

    @Override
    public String toString() {
        return String.format("DimensionBlockPos[x=%s, y=%s, z=%s, dimension=%s]", getX(), getY(), getZ(), getDimension());
    }

    public NBTTagCompound writeToNBT(NBTTagCompound tagCompound) {
        tagCompound.setInteger("x", getX());
        tagCompound.setInteger("y", getY());
        tagCompound.setInteger("z", getZ());
        tagCompound.setInteger("dimension", getDimension());
        return tagCompound;
    }
}
