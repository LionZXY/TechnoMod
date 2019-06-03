package ru.glitchless.tpmod.proxy;

import net.minecraft.util.text.translation.LanguageMap;
import ru.glitchless.tpmod.server.PlayerServer;

import java.io.InputStream;

public class ServerInit extends CommonInit {
    private PlayerServer playerServer = new PlayerServer();

    @Override
    public void preInit() {
        super.preInit();

        playerServer.init();
        changeLanguageForMap();
    }

    private void changeLanguageForMap() {
        InputStream is = LanguageMap.class.getResourceAsStream("/assets/minecraft/lang/ru_ru.lang");
        LanguageMap.inject(is);
    }
}
