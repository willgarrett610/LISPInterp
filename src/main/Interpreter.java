package main;

import main.error.LispException;
import main.error.ParsingException;
import main.expr.Expr;
import main.expr.value.Literal;
import main.expr.value.Value;
import main.parser.Parser;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.Scanner;

public class Interpreter {

    Environment environment;
    BufferedWriter fileOut;

    public Interpreter(BufferedWriter fileOut) {
        this.environment = new Environment();
        this.fileOut = fileOut;
    }

    private void log(String message) {
        if (fileOut == null) return;

        try {
            fileOut.write(message);
        } catch (IOException e) {
            fileOut = null;
        }
    }

    private void output(String message) {
        System.out.print(message);
        log(message);
    }

    private void error(String message) {
        System.err.print(message);
        log("ERROR: " + message);
    }

    public Value<?> evaluate(String exprString) {
        Expr expr = null;
        try {
            expr = Parser.parse(exprString);
        } catch (ParsingException e) {
            error("Error while parsing\n");
            error(e.getMessage() + "\n");
            return Literal.NIL;
        }

        if (expr == null) return Literal.NIL;

        Value<?> result = Literal.NIL;
        try {
            result = expr.evaluate(this.environment);
        } catch (LispException e) {
            error(e.getMessage() + "\n");
            return Literal.NIL;
        }
        return result;
    }

    public void start() {
        Scanner in = new Scanner(System.in);

        output("WILLISP V1.0\n>");
        do {
            String line = in.nextLine();

            log(line + "\n");

            Value<?> value = evaluate(line);

            // Handle (quit)
            if (value == null) {
                output("bye\n");
                break;
            }

            output(value + "\n");
            output(">");
        } while (in.hasNext());
        output("Exiting...\n");

        in.close();
    }

}
