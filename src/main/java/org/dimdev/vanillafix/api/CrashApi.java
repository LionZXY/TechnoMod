package org.dimdev.vanillafix.api;

import net.minecraft.crash.CrashReport;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class CrashApi {
    private static final Logger log = LogManager.getLogger("CrashApi");
    private static final List<CrashListener> listeners = new ArrayList<>();

    public static void addListener(CrashListener crashListener) {
        listeners.add(crashListener);
    }

    public static void outputReport(CrashReport report) {
        for (int i = 0; i < listeners.size(); i++) {
            try {
                listeners.get(i).onReport(report);
            } catch (Throwable e) {
                log.fatal("Failed report crash to listener", e);
            }
        }
    }
}
