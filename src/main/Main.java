package main;

import main.parser.Parser;

public class Main {

    public static void main(String[] args) {
//        Scanner in = new Scanner(System.in);
//
//        in.close();

        System.out.println(Parser.parse("(defun f (var s 'l))"));
        System.out.println(Parser.parse("var*"));
        System.out.println(Parser.parse("*+"));
        System.out.println(Parser.parse("'123"));
        System.out.println(Parser.parse("  12.432"));
        System.out.println(Parser.parse("(lit a b)"));
        System.out.println(Parser.parse("'text123"));
        System.out.println(Parser.parse("(* 1 2)"));
        System.out.println(Parser.parse("(+ 3 (* 6 4))"));
        System.out.println(Parser.parse("'(a( b d c (  k))'(f))"));
        System.out.println(Parser.parse("'('a b c)"));
    }
}
