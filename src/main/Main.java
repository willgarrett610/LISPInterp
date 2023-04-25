package main;

import main.expr.value.Literal;
import main.expr.value.Value;
import main.parser.Parser;

import java.io.*;
import java.util.Formatter;
import java.util.Scanner;
import java.util.logging.*;

public class Main {

    public static void main(String[] args) {
        BufferedWriter fileOut = null;
        try {
            fileOut = new BufferedWriter(new FileWriter("./results.file"));
        } catch (IOException e) {
            System.err.println("Could not open \"./results.file\" for logging");
            System.err.println("Continuing without... :(");
        }

        Interpreter interpreter = new Interpreter(fileOut);
        interpreter.start();

        if (fileOut == null) return;

        try {
            fileOut.close();
        } catch (IOException e) {
            System.err.println("Could not write outputs to \"./results.file\"");
        }

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
