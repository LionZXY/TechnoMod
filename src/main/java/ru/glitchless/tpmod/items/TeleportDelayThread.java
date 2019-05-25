package ru.glitchless.tpmod.items;

import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import ru.glitchless.tpmod.TpMod;
import ru.glitchless.tpmod.config.Configuration;

import java.util.concurrent.TimeUnit;

public class TeleportDelayThread extends Thread {
    final private Runnable onTeleport;
    final private int delay = Configuration.tpDelay;
    final private EntityPlayer entityPlayer;
    final private BlockPos initPos;
    final private float initHp;


    public TeleportDelayThread(EntityPlayer player, Runnable onTeleport) {
        this.onTeleport = onTeleport;
        this.entityPlayer = player;
        this.initPos = entityPlayer.getPosition();
        this.initHp = entityPlayer.getHealth();
    }

    @Override
    public void run() {
        super.run();
        try {
            int delayRemain = delay;
            long timestamp = TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis());
            while (delayRemain > 0) {
                delayRemain = delay - (int) (TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis()) - timestamp);

                final BlockPos currentPos = entityPlayer.getPosition();
                if (!currentPos.equals(initPos) || initHp > entityPlayer.getHealth()) {
                    String message = I18n.format("tpmod.moving_teleport_text");
                    entityPlayer.sendMessage(new TextComponentString(message));
                    entityPlayer.sendStatusMessage(new TextComponentString(message), true);
                    return;
                }

                entityPlayer.sendStatusMessage(new TextComponentString(I18n.format("tpmod.delay_teleport_text", delayRemain)), true);
                Thread.sleep(TimeUnit.SECONDS.toMillis(1));
            }
            entityPlayer.sendStatusMessage(new TextComponentString(I18n.format("tpmod.teleporting_text")), true);
            TpMod.getInstance().getMainLooper().handle(onTeleport);
        } catch (Exception e) {
            TpMod.getInstance().getLogger().error(e);
        }
    }
}
