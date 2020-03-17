package ru.glitchless.tpmod.utils;

import io.sentry.Sentry;
import net.minecraft.crash.CrashReport;
import net.minecraftforge.fml.common.FMLCommonHandler;
import org.dimdev.vanillafix.api.CrashApi;
import org.dimdev.vanillafix.api.CrashListener;

public class CrashReporter implements CrashListener {
    public void init() {
        Sentry.init("https://531a19f9b83f4e5194fa05d6f93d034c@sentry.team.glitchless.ru/20");
        Sentry.getStoredClient().addExtra("side", FMLCommonHandler.instance().getSide());
        CrashApi.addListener(this);
    }

    @Override
    public void onReport(CrashReport report) {
        Sentry.capture(report.getCrashCause());
    }
}
