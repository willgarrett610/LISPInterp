package main;

import java.io.*;

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

        if (fileOut == null)
            return;

        try {
            fileOut.close();
        } catch (IOException e) {
            System.err.println("Could not write outputs to \"./results.file\"");
        }
    }

}
