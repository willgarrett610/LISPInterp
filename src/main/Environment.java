package main;

import main.error.LispException;
import main.expr.value.Literal;
import main.expr.value.SList;
import main.function.Function;
import main.expr.value.Value;
import main.expr.value.Number;

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
    public Environment addVariables(List<String> names, List<Value> values) {
        Environment environment = new Environment();
        environment.variables = (HashMap<String, Value>) this.variables.clone();
        environment.functions = (HashMap<String, Function>) this.functions.clone();

        if (names.size() != values.size()) {
            System.err.println("Number of function parameters mismatch");
            // TODO Error handling
            return null;
        }

        for (int i = 0; i < names.size(); i++) {
            String key = names.get(i);
            Value value = values.get(i);
            environment.variables.put(key, value);
        }
        return environment;
    }

    public Number getNumber(String name) {
        Value value = getValue(name);
        if (!(value instanceof Number)) {
            // TODO: error handling
            System.err.println("Variable " + name + " must be a number");
            return null;
        }
        return (Number) value;
    }

    public Literal getLiteral(String name) {
        Value value = getValue(name);
        if (!(value instanceof Literal)) {
            // TODO: error handling
            System.err.println("Variable " + name + " must be a literal");
            return null;
        }
        return (Literal) value;
    }

    public SList getSList(String name) {
        Value value = getValue(name);
        if (!(value instanceof SList)) {
            // TODO: error handling
            System.err.println("Variable " + name + " must be a list");
            return null;
        }
        return (SList) value;
    }

    public Value getValue(String name) {
        Value value = this.variables.get(name);
        if (value == null) {
            System.err.println("No variable " + name + " found");
            return null;
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
            throw new LispException("No function found: " + name);
        }
        return function;
    }

    public void setFunction(String name, Function function) {
        this.functions.put(name, function);
    }

}
