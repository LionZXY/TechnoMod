package ru.glitchless.tpmod.exceptions;

import net.minecraft.util.text.ITextComponent;

public class MinecraftTextFormattedException extends Exception {
    private final ITextComponent reason;

    public MinecraftTextFormattedException(ITextComponent textComponent) {
        reason = textComponent;
    }

    public ITextComponent getReason() {
        return reason;
    }
}
