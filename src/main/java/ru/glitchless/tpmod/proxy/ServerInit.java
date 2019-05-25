package ru.glitchless.tpmod.proxy;

import ru.glitchless.tpmod.server.PlayerServer;

public class ServerInit extends CommonInit {
    private PlayerServer playerServer = new PlayerServer();

    @Override
    public void preInit() {
        super.preInit();

        playerServer.init();
    }
}
