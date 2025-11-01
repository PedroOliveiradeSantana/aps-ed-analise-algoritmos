package main;

import java.io.File;
import java.io.PrintWriter;
import java.util.Random;

public class GenerateDataFile {
    public static void main(String[] args) throws Exception {
        int N = 5000; // número de inteiros
        Random rnd = new Random();
        File out = new File("dados.txt"); // na raiz do projeto
        try (PrintWriter pw = new PrintWriter(out)) {
            for (int i = 0; i < N; i++) {
                pw.print(rnd.nextInt(1_000_000));
                if (i < N - 1) pw.print(" ");
            }
        }
        System.out.println("Arquivo dados.txt gerado com " + N + " números em: " + out.getAbsolutePath());
    }
}
