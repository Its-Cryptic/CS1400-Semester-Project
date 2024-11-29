package org.physicsengine.core;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.config.plugins.validation.constraints.NotBlank;
import org.json.JSONArray;
import org.json.JSONObject;
import org.jspecify.annotations.NonNull;
import org.physicsengine.util.LogUtils;

import javax.script.ScriptException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class JsonParser {
    private static Logger LOGGER = LogUtils.getLogger();
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
            LOGGER.info("Parsed constant: " + name + " = " + value);
        }
        double test = EquationEvaluator.eval("g*a", constants);
        LOGGER.info("Test: " + test);
        JSONArray objectsJson = jsonObject.getJSONArray("objects");
        //LOGGER.info("Parsed JSON: " + jsonObject);
        LOGGER.info("Parsed constants: " + constants);
        LOGGER.info("Parsed objects: " + objectsJson);
        return new PhysicsEnvironment(constants, physicsObjects);
    }

    public static PhysicsEnvironment loadPhysicsEnvironment(@NonNull String path) throws Exception {
        String json = loadResource(path);
        return parseJson(json);
    }
}
