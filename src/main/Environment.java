package main;

import main.error.LispException;
import main.expr.value.Literal;
import main.expr.value.SList;
import main.function.Function;
import main.expr.value.Value;
import main.expr.value.Number;
import main.function.JavaFunctionEnum;

import java.util.HashMap;
import java.util.List;

public class Environment {

    HashMap<String, Value> variables;
    HashMap<String, Function> functions;

    public Environment() {
        this.variables = new HashMap<>();
        this.functions = new HashMap<>();
        JavaFunctionEnum.addFunctionsToMap(this.functions);
    }

    @SuppressWarnings("unchecked")
    public Environment addFunctionParams(List<String> names, List<Value> values) {
        Environment environment = new Environment();
        environment.variables = (HashMap<String, Value>) this.variables.clone();
        environment.functions = (HashMap<String, Function>) this.functions.clone();

        for (int i = 0; i < names.size(); i++) {
            String key = names.get(i);
            Value<?> value = values.get(i);
            environment.variables.put(key, value);
        }
        return environment;
    }

    public Number getNumber(String name) throws LispException {
        Value<?> value = getValue(name);
        if (!(value instanceof Number)) {
            throw new LispException("Argument " + name + " must be a number");
        }
        return (Number) value;
    }

    public Literal getLiteral(String name) throws LispException {
        Value<?> value = getValue(name);
        if (!(value instanceof Literal)) {
            throw new LispException("Argument " + name + " must be a literal");
        }
        return (Literal) value;
    }

    public SList getSList(String name) throws LispException {
        Value<?> value = getValue(name);
        if (!(value instanceof SList)) {
            throw new LispException("Argument " + name + " must be a list");
        }
        return (SList) value;
    }

    public Value<?> getValue(String name) throws LispException {
        Value<?> value = this.variables.get(name);
        if (value == null) {
            throw new LispException("Variable " + name + " not found");
        }
        return value;
    }

    public void setVariable(String name, Value value) {
        this.variables.put(name, value);
    }

    public boolean variableExists(String name) {
        return this.variables.containsKey(name);
    }

    public boolean functionExists(String name) {
        return this.functions.containsKey(name);
    }

    public Function getFunction(String name) throws LispException {
        Function function = this.functions.get(name);
        if (function == null) {
            throw new LispException("Function " + name + " not found");
        }
        return function;
    }

    public void setFunction(String name, Function function) {
        this.functions.put(name, function);
    }

}
