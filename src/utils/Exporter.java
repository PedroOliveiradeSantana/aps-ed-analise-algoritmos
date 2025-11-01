package utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Locale;

public class Exporter {
    private final File file;
    private boolean headerWritten = false;

    public Exporter(String filePath) {
        this.file = new File(filePath);
        if (this.file.exists()) {
            this.file.delete();
        }
    }

    private synchronized void ensureHeader() {
        if (headerWritten) return;
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(file, true))) {
            if (file.length() == 0) {
                bw.write("size;scenario;algorithm;mean_ns;stddev_ns");
                bw.newLine();
            }
            headerWritten = true;
        } catch (IOException e) {
            System.err.println("Erro ao criar cabeçalho CSV: " + e.getMessage());
        }
    }

    public synchronized void save(String scenario, int size, String algorithm,
    		double meanNs, double stddevNs) {
        ensureHeader();
        String line = String.format(Locale.US, "%d;%s;%s;%.0f;%.0f", size, scenario,
        		algorithm, meanNs, stddevNs);
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(file, true))) {
            bw.write(line);
            bw.newLine();
        } catch (IOException e) {
            System.err.println("Erro ao salvar resultado: " + e.getMessage());
        }
    }
}