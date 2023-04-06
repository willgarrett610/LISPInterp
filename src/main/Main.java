package main;

import main.expr.value.Value;
import main.parser.Parser;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Interpreter interpreter = new Interpreter();

        Scanner in = new Scanner(System.in);

        System.out.print("WILLISP V1.0\n>");
        do {
            String line = in.nextLine();
            Value value = interpreter.evaluate(line);
            System.out.println(value);
            System.out.print(">");
        } while (in.hasNext());
        System.out.println("Exiting...");

        in.close();

//        System.out.println(Parser.parse("(defun f (var s 'l))"));
//        System.out.println(Parser.parse("var*"));
//        System.out.println(Parser.parse("*+"));
//        System.out.println(Parser.parse("'123"));
//        System.out.println(Parser.parse("  12.432"));
//        System.out.println(Parser.parse("(lit a b)"));
//        System.out.println(Parser.parse("'text123"));
//        System.out.println(Parser.parse("(* 1 2)"));
//        System.out.println(Parser.parse("(+ 3 (* 6 4))"));
//        System.out.println(Parser.parse("'(a( b d c (  k))'(f))"));
//        System.out.println(Parser.parse("'('a b c)"));
    }
}
