package org.dimdev.vanillafix.api;

import net.minecraft.crash.CrashReport;

public interface CrashListener {
    void onReport(CrashReport report);
}
