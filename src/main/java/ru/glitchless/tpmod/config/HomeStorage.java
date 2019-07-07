package ru.glitchless.tpmod.config;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraft.world.storage.MapStorage;
import net.minecraft.world.storage.WorldSavedData;
import ru.glitchless.tpmod.TpMod;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;

public class HomeStorage extends WorldSavedData {
    private static final String DATA_NAME = TpMod.MODID + "_homeset";
    private Map<String, DimensionBlockPos> dimensionBlockPosMap = new HashMap<>();
    private Map<DimensionBlockPos, String> dimensionBlockPosPlayerMap = new HashMap<>();

    public HomeStorage() {
        super(DATA_NAME);
    }

    public HomeStorage(String dataName) {
        super(dataName);
    }

    public static HomeStorage getInstance(World world) {
        final MapStorage mapStorage = world.getMapStorage();
        HomeStorage instance = (HomeStorage) mapStorage.getOrLoadData(HomeStorage.class, DATA_NAME);

        if (instance == null) {
            instance = new HomeStorage();
            mapStorage.setData(DATA_NAME, instance);
        }
        return instance;
    }

    public void setHome(EntityPlayer player, @Nullable DimensionBlockPos blockPos) {
        final String key = player.getGameProfile().getId().toString();
        setHome(key, blockPos);
    }

    public void setHome(String key, @Nullable DimensionBlockPos blockPos) {
        if (blockPos == null) {
            dimensionBlockPosPlayerMap.remove(dimensionBlockPosMap.get(key));
            dimensionBlockPosMap.remove(key);
        } else {
            dimensionBlockPosPlayerMap.remove(dimensionBlockPosMap.get(key));
            dimensionBlockPosPlayerMap.put(blockPos, key);
            dimensionBlockPosMap.put(key, blockPos);
        }
        markDirty();
    }

    @Nullable
    public DimensionBlockPos getHome(EntityPlayer player) {
        return dimensionBlockPosMap.get(player.getGameProfile().getId().toString());
    }

    @Nullable
    public String getHomeByPos(DimensionBlockPos pos) {
        return dimensionBlockPosPlayerMap.get(pos);
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt) {
        for (String key : nbt.getKeySet()) {
            final DimensionBlockPos pos = DimensionBlockPos.readFromNBT(nbt.getCompoundTag(key));
            dimensionBlockPosMap.put(key, pos);
            dimensionBlockPosPlayerMap.put(pos, key);
        }
    }

    @Override
    public NBTTagCompound writeToNBT(@Nonnull NBTTagCompound compound) {
        for (Map.Entry<String, DimensionBlockPos> entry : dimensionBlockPosMap.entrySet()) {
            final NBTTagCompound nbtTagCompound = new NBTTagCompound();
            entry.getValue().writeToNBT(nbtTagCompound);
            compound.setTag(entry.getKey(), nbtTagCompound);
        }
        return compound;
    }
}
