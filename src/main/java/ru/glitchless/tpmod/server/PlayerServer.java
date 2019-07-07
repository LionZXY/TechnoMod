package ru.glitchless.tpmod.server;

import com.google.gson.Gson;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.fml.common.FMLCommonHandler;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class PlayerServer implements HttpHandler {
    public void init() {
        if(FMLCommonHandler.instance().getMinecraftServerInstance() == null) {
            return;
        }
        try {
            HttpServer server = HttpServer.create();
            server.bind(new InetSocketAddress(8765), 0);
            server.createContext("/players/", this);
            server.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        final MinecraftServer is = FMLCommonHandler.instance().getMinecraftServerInstance();
        if (is == null) {
            return;
        }
        final Gson gson = new Gson();
        final List<String> playerList = new ArrayList<String>();
        for(EntityPlayerMP player : is.getPlayerList().getPlayers()) {
            playerList.add(player.getGameProfile().getName());
        }
        final byte[] answer = gson.toJson(playerList).getBytes(StandardCharsets.UTF_8);

        Headers headers = exchange.getRequestHeaders();
        exchange.sendResponseHeaders(200, answer.length);

        OutputStream os = exchange.getResponseBody();
        os.write(answer);
        os.close();
    }
}
