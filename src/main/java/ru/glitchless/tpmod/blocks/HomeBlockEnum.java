package ru.glitchless.tpmod.blocks;

import net.minecraft.util.IStringSerializable;

public enum HomeBlockEnum implements IStringSerializable {
    DISACTIVATE(0),
    ACTIVATE(1);

    private int id = 0;

    HomeBlockEnum(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    @Override
    public String getName() {
        return super.name().toLowerCase();
    }
}
