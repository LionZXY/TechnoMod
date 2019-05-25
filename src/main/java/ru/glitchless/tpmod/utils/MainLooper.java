package ru.glitchless.tpmod.utils;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.util.ArrayList;
import java.util.List;

@Mod.EventBusSubscriber
public class MainLooper {
    private final List<Runnable> queue = new ArrayList<>();

    @SubscribeEvent
    public void onPlayerTick(TickEvent.PlayerTickEvent event) {
        for (int i = 0; i < queue.size(); i++) {
            queue.get(i).run();
        }
    }

    public void handle(Runnable runnable) {
        queue.add(runnable);
    }


}
