package main.expr.parser;

import main.expr.Expr;
import main.expr.Symbol;
import main.expr.value.Literal;
import main.expr.value.Number;
import main.expr.value.SList;
import main.expr.value.Value;

import java.util.ArrayList;
import java.util.List;

public class Parser {

    private static final String WHITESPACE = "[\\s]+";
    private static final String LIST = "'\\(.*\\)";
    private static final String LITERAL = "'.+";
    private static final String NUMBER = "[0-9]+\\.?[0-9]*";
    private static final String SEXPR = "\\(.*\\)";
    private static final String SYMBOL = "[^\\d\\s]+\\S*";

    public static Expr parse(String exprString) {
        System.out.println();
        // TODO values with spaces outside of list eg: 12 .345
        String stripped = exprString.toUpperCase().replaceAll(WHITESPACE, " ");
        stripped = stripped.stripLeading().stripTrailing();

        ExprType exprType = getType(stripped);

        System.out.println("\"" + stripped + "\": " + exprType);

        switch (exprType) {
            case INVALID: {
                System.err.println("Invalid expression");
                return null;
            }
            case SYMBOL: {
                String name = stripped;
                return new Symbol(name);
            }
        }

        return parseValue(stripped, exprType);
    }

    private static Value parseValue(String valueString) {
        ExprType exprType = getType(valueString);
        if (exprType == ExprType.SEXPR) {
            exprType = ExprType.LIST;
            valueString = "'" + valueString;
        } else if (exprType == ExprType.SYMBOL) {
            exprType = ExprType.LITERAL;
            valueString = "'" + valueString;
        }
        return parseValue(valueString, exprType);
    }

    private static Value parseValue(String valueString, ExprType exprType) {
        switch (exprType) {
            case NUMBER: {
                double value = Double.parseDouble(valueString);
                return new Number(value);
            }
            case LITERAL: {
                // TODO: Check constant literals
                String value = valueString.substring(1);
                if (value.matches(NUMBER)) {
                    double numValue = Double.parseDouble(value);
                    return new Number(numValue);
                }
                return new Literal(value);
            }
            case LIST: {
                String listString = valueString.substring(1);
                List<String> split = splitList(listString);
                List<Value> values = new ArrayList<>();
                for (String s : split) {
                    values.add(parseValue(s));
                }
                return new SList(values);
            }
        }
        return null;
    }

    private static ExprType getType(String exprString) {
        if (exprString.matches(LIST)) {
            return ExprType.LIST;
        } else if (exprString.matches(LITERAL)) {
            return ExprType.LITERAL;
        } else if (exprString.matches(NUMBER)) {
            return ExprType.NUMBER;
        } else if (exprString.matches(SEXPR)) {
            return ExprType.SEXPR;
        } else if (exprString.matches(SYMBOL)) {
            return ExprType.SYMBOL;
        }
        return ExprType.INVALID;
    }

    private static int getClosingIndex(String exprString, int opening) {
        int level = 0;
        for (int i = opening; i < exprString.length(); i++) {
            char c = exprString.charAt(i);
            if (c == '(') level++;
            if (c == ')') level--;
            if (level == 0) return i;
        }

        return -1;
    }

    private static List<String> splitList(String listString) {
        String stripped = listString.substring(1,listString.length()-1);
        stripped = stripped.stripLeading().stripTrailing();

//        System.out.println("Stripped: " + stripped);

        List<String> elmts = new ArrayList<>();

        char[] chars = stripped.toCharArray();
        int elmtStart = 0;
        boolean inLiteral = false;
        boolean litHitChars = false;
        for (int i = 0; i < chars.length; i++) {
            char c = chars[i];
            if ((!inLiteral || litHitChars) && (c == ' ' || c == '\'' || c == '(') && i != elmtStart) {
                String elmt = stripped.substring(elmtStart,i);
                elmts.add(elmt);
            }
            if (c == '(') {
                int closing = getClosingIndex(stripped, i);
                if (closing == -1) {
                    System.err.println("Closing parenthesis not found");
                    return null;
                }

                int j = i;
                if (inLiteral && !litHitChars) {
                    j = elmtStart;
                    inLiteral = false;
                    litHitChars = false;
                }
                String value = stripped.substring(j, closing + 1);
                elmts.add(value);

                i = closing;
                elmtStart = i + 1;
                continue;
            }

            if (c == '\'') {
                if (!inLiteral || litHitChars) {
                    elmtStart = i;
                    inLiteral = true;
                    litHitChars = false;
                }
                continue;
            }

            if (c == ' ') {
                if (!inLiteral || litHitChars) {
                    // Move i to start of next element
                    elmtStart = i+1;
                    inLiteral = false;
                    litHitChars = false;
                }
                continue;
            } else if (inLiteral && !litHitChars) {
                litHitChars = true;
            }

            if (i == chars.length - 1) {
                String elmt = stripped.substring(elmtStart, chars.length);
                elmts.add(elmt);
            }
        }

        return elmts;
    }

}
