package org.physicsengine.core;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.joml.Vector3f;
import org.json.JSONArray;
import org.json.JSONObject;
import org.jspecify.annotations.NonNull;

import javax.script.ScriptException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class JsonParser {
    private static Logger LOGGER = LogManager.getLogger(JsonParser.class);
    public static String loadResource(String path) throws Exception {
        String result;
        try (InputStream in = JsonParser.class.getResourceAsStream("/" + path)) {
            if (in == null) throw new Exception("Resource not found: " + path);
            Scanner scanner = new Scanner(in, StandardCharsets.UTF_8);
            result = scanner.useDelimiter("\\A").next();
            LOGGER.info("Loaded resource: " + path);
            return result;
        }
    }

    public static String loadExternalFile(String path) throws Exception {
        Path filePath = Paths.get(path);
        if (!Files.exists(filePath)) {
            throw new Exception("File not found: " + path);
        }
        String content = new String(Files.readAllBytes(filePath), StandardCharsets.UTF_8);
        LOGGER.info("Loaded external file: " + path);
        return content;
    }

    private static PhysicsEnvironment parseJson(String json) throws ScriptException {
        Map<String, Double> constants = new HashMap<>();
        List<PhysicsObject> physicsObjects = new ArrayList<>();

        JSONObject jsonObject = new JSONObject(json);
        JSONArray constantsJson = jsonObject.getJSONArray("constants");
        for (int i = 0; i < constantsJson.length(); i++) {
            JSONObject constant = constantsJson.getJSONObject(i);
            String name = constant.getString("name");
            Double value = constant.getDouble("value");
            constants.put(name, value);
        }
        JSONArray objectsJson = jsonObject.getJSONArray("objects");

        for (int i = 0; i < objectsJson.length(); i++) {
            JSONObject object = objectsJson.getJSONObject(i);
            PhysicsObject physicsObject = PhysicsObject.fromJson(object);
            physicsObjects.add(physicsObject);
        }
        return new PhysicsEnvironment(constants, physicsObjects);
    }

    public static PhysicsEnvironment loadPhysicsEnvironment(@NonNull String path) throws Exception {
        String json;
        Path filePath = Paths.get(path);

        if (Files.exists(filePath)) {
            // Load from external file system
            json = loadExternalFile(path);
        } else {
            // Load from resources inside the JAR
            json = loadResource(path);
        }

        return parseJson(json);
    }
}
