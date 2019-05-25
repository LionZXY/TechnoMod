package ru.glitchless.tpmod.config;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import ru.glitchless.tpmod.TpMod;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

public class TableWorldData {
    private final File file;
    private final Gson gson = new Gson();
    private Map<String, DimensionBlockPos> userToObject = new HashMap<>();
    private Type objectType = new TypeToken<Map<String, DimensionBlockPos>>() {
    }.getType();

    public TableWorldData(File worldDir, String name) {
        this(new File(worldDir, name + ".json"));
    }

    public TableWorldData(File srcFile) {
        this.file = srcFile;
    }

    public DimensionBlockPos get(String key) {
        return userToObject.get(key);
    }

    public void set(String key, DimensionBlockPos object) {
        userToObject.put(key, object);
        save();
    }

    public void load() {
        if (!file.exists()) {
            return;
        }
        FileReader fileReader = null;
        try {
            fileReader = new FileReader(file);
            userToObject = gson.fromJson(fileReader, objectType);
            fileReader.close();
        } catch (IOException e) {
            TpMod.getInstance().getLogger().error(e);
        }
    }

    public void save() {
        try {
            file.createNewFile();
            FileWriter fileWriter = new FileWriter(file);
            gson.toJson(userToObject, fileWriter);
            fileWriter.close();
        } catch (IOException e) {
            TpMod.getInstance().getLogger().error(e);
        }
    }
}
