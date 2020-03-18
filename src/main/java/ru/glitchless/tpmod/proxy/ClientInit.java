package ru.glitchless.tpmod.proxy;

import ru.glitchless.tpmod.utils.CrashReporter;

public class ClientInit extends CommonInit {
    private CrashReporter reporter;

    @Override
    public void preInit() {
        super.preInit();
        reporter = new CrashReporter();
        reporter.init();
    }
}
