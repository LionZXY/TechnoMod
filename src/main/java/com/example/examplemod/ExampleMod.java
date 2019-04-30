package com.example.examplemod;

import com.mojang.authlib.HttpAuthenticationService;
import com.mojang.authlib.yggdrasil.YggdrasilMinecraftSessionService;
import com.mojang.authlib.yggdrasil.YggdrasilUserAuthentication;
import net.minecraft.client.Minecraft;
import net.minecraft.init.Blocks;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

@Mod(modid = ExampleMod.MODID, name = ExampleMod.NAME, version = ExampleMod.VERSION)
public class ExampleMod {
    public static final String MODID = "examplemod";
    public static final String NAME = "Example Mod";
    public static final String VERSION = "1.0";

    private static Logger logger;

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        logger = event.getModLog();
        try {
            //replaceSession();
            //replaceAuthServer();
        } catch (Exception e) {
            logger.error(e);
            e.printStackTrace();
        }
    }

    private void replaceSession() throws Exception {
        String BASE_URL = "https://minecraft.free.beeceptor.com/session/";
        Field baseUrl = YggdrasilMinecraftSessionService.class.getDeclaredField("BASE_URL");
        Field joinUrl = YggdrasilMinecraftSessionService.class.getDeclaredField("JOIN_URL");
        Field checkUrl = YggdrasilMinecraftSessionService.class.getDeclaredField("CHECK_URL");
        baseUrl.setAccessible(true);
        joinUrl.setAccessible(true);
        checkUrl.setAccessible(true);
        setFinalStatic(baseUrl, BASE_URL);
        setFinalStatic(joinUrl, HttpAuthenticationService.constantURL(BASE_URL + "join"));
        setFinalStatic(checkUrl, HttpAuthenticationService.constantURL(BASE_URL + "hasJoined"));
    }

    private void replaceAuthServer() throws Exception {
        String BASE_URL = "https://minecraft.free.beeceptor.com/authserver/";
        Field baseUrl = YggdrasilUserAuthentication.class.getDeclaredField("BASE_URL");
        Field routeAuthenticate = YggdrasilUserAuthentication.class.getDeclaredField("ROUTE_AUTHENTICATE");
        Field routeRefresh = YggdrasilUserAuthentication.class.getDeclaredField("ROUTE_REFRESH");
        Field routeValidate = YggdrasilUserAuthentication.class.getDeclaredField("ROUTE_VALIDATE");
        Field routeInvalidate = YggdrasilUserAuthentication.class.getDeclaredField("ROUTE_INVALIDATE");
        Field routeSignout = YggdrasilUserAuthentication.class.getDeclaredField("ROUTE_SIGNOUT");
        baseUrl.setAccessible(true);
        routeAuthenticate.setAccessible(true);
        routeRefresh.setAccessible(true);
        routeValidate.setAccessible(true);
        routeInvalidate.setAccessible(true);
        routeSignout.setAccessible(true);

        setFinalStatic(baseUrl, BASE_URL);
        setFinalStatic(routeAuthenticate, HttpAuthenticationService.constantURL(BASE_URL + "authenticate"));
        setFinalStatic(routeRefresh, HttpAuthenticationService.constantURL(BASE_URL + "refresh"));
        setFinalStatic(routeValidate, HttpAuthenticationService.constantURL(BASE_URL + "validate"));
        setFinalStatic(routeInvalidate,  HttpAuthenticationService.constantURL(BASE_URL + "invalidate"));
        setFinalStatic(routeSignout, HttpAuthenticationService.constantURL(BASE_URL + "signout"));
    }
    static void setFinalStatic(Field field, Object newValue) throws Exception {
        field.setAccessible(true);

        Field modifiersField = Field.class.getDeclaredField("modifiers");
        modifiersField.setAccessible(true);
        modifiersField.setInt(field, field.getModifiers() & ~Modifier.FINAL);

        field.set(null, newValue);
    }

    @EventHandler
    public void init(FMLInitializationEvent event) {
        // some example code
        logger.info("DIRT BLOCK >> {}", Blocks.DIRT.getRegistryName());
    }
}
