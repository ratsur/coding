package com.ratsur.search;

import java.io.File;
import java.util.Scanner;
public class Main {
    public static void main(String[] args) {
        if (args.length == 0) {
            throw new IllegalArgumentException("No directory given to index.");
        }
        final File indexableDirectory = new File(args[0]);
        FileIndexer fi = new FileIndexer(indexableDirectory);
        int numOfIndexedFiles = fi.indexDir();
        System.out.println(numOfIndexedFiles +" files read in directory "+indexableDirectory);
        try (Scanner keyboard = new Scanner(System.in)) {
            while (true) {
                System.out.print("search> ");
                final String line = keyboard.nextLine();
                if(line.equals(":quit")) break;
                fi.search(line).display();
            }
        }
    }
}