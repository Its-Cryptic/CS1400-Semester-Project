package org.physicsengine.core;

import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import java.util.Map;
import java.util.function.BiFunction;

import javax.script.*;

public class EquationEvaluator {

    public static double eval(@NonNull String equation) throws ScriptException {
        ScriptEngineManager mgr = new ScriptEngineManager();
        mgr.getEngineFactories().forEach(System.out::println);
        ScriptEngine engine = mgr.getEngineByName("nashorn");
        return (double) engine.eval(equation);
    }

    public static double eval(@NonNull String equation, Map<String, Double> variables) throws ScriptException {
        ScriptEngineManager mgr = new ScriptEngineManager();
        mgr.getEngineFactories().forEach(System.out::println);
        ScriptEngine engine = mgr.getEngineByName("nashorn");
        Bindings bindings = engine.createBindings();
        bindings.putAll(variables);
        return (double) engine.eval(equation, bindings);
    }
}
