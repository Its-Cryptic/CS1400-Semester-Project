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

    public enum Operator {
        ADD('+', (a, b) -> a + b),
        SUBTRACT('-', (a, b) -> a - b),
        MULTIPLY('*', (a, b) -> a * b),
        DIVIDE('/', (a, b) -> a / b),
        EXPONENT('^', Math::pow),
        MODULUS('%', (a, b) -> a % b);
        private final char operator;
        private final BiFunction<Double, Double, Double> operation;
        Operator(char operator, BiFunction<Double, Double, Double> operation) {
            this.operator = operator;
            this.operation = operation;
        }

        public double operate(@NonNull double a, @NonNull double b) {
            return operation.apply(a, b);
        }

        @Nullable
        public static Operator fromChar(char operator) {
            for (Operator op : values()) {
                if (op.operator == operator) {
                    return op;
                }
            }
            return null;
        }
    }
}
