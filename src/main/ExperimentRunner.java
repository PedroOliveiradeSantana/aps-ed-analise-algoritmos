package main;

import algorithms.BubbleSort;
import algorithms.MergeSort;
import algorithms.QuickSort;
import data.DataGenerator;
import utils.Exporter;
import utils.Statistics;
import utils.Timer;
import utils.Validator;

import java.util.Locale;

public class ExperimentRunner {

    private static final int[] SIZES = {1_000, 10_000, 50_000};
    private static final int REPEAT = 10;
    private static final String CSV_PATH = "results.csv";

    public static void main(String[] args) {
        Locale.setDefault(Locale.US);
        Exporter exporter = new Exporter(CSV_PATH);

        for (int size : SIZES) {
            runScenario(size, "ORDERED", exporter, () -> DataGenerator.generateSortedArray(size));
            runScenario(size, "PARTIAL", exporter, () -> DataGenerator.generatePartiallySortedArray(size));
            runScenario(size, "RANDOM", exporter, () -> DataGenerator.generateRandomArray(size));
        }

        int[] fileBase = DataGenerator.loadFromFile("dados.txt");
        if (fileBase != null && fileBase.length > 0) {
            runFileScenario(fileBase, "FILE", exporter);
        } else {
            System.out.println("Arquivo dados.txt não encontrado ou vazio. Pulei cenário FILE.");
        }

        System.out.println("Execução concluída. Verifique " + CSV_PATH + " para os resultados.");
    }

    private static void runScenario(int size, String scenario, Exporter exporter, DataSupplier supplier) {
        System.out.printf("Executando: size=%d scenario=%s%n", size, scenario);

        long[] bubbleTimes = new long[REPEAT];
        long[] mergeTimes = new long[REPEAT];
        long[] quickTimes = new long[REPEAT];

        for (int r = 0; r < REPEAT; r++) {
            int[] base = supplier.get();

            // Bubble
            int[] arrB = DataGenerator.copyArray(base);
            bubbleTimes[r] = Timer.measureNanos(() -> BubbleSort.sort(arrB));

            if (!Validator.isSorted(arrB)) System.err.println("BubbleSort falhou na ordenação.");
            // Merge
            int[] arrM = DataGenerator.copyArray(base);
            mergeTimes[r] = Timer.measureNanos(() -> MergeSort.sort(arrM));
            if (!Validator.isSorted(arrM)) System.err.println("MergeSort falhou na ordenação.");

            // Quick
            int[] arrQ = DataGenerator.copyArray(base);
            quickTimes[r] = Timer.measureNanos(() -> QuickSort.sort(arrQ));
            if (!Validator.isSorted(arrQ)) System.err.println("QuickSort falhou na ordenação.");
        }

        double bMean = Statistics.mean(bubbleTimes);
        double bStd = Statistics.stddev(bubbleTimes);
        double mMean = Statistics.mean(mergeTimes);
        double mStd = Statistics.stddev(mergeTimes);
        double qMean = Statistics.mean(quickTimes);
        double qStd = Statistics.stddev(quickTimes);

        exporter.save(scenario, size, "BubbleSort", bMean, bStd);
        exporter.save(scenario, size, "MergeSort", mMean, mStd);
        exporter.save(scenario, size, "QuickSort", qMean, qStd);

        System.out.printf("Resumo size=%d scenario=%s -> Bubble=%.0f±%.0f ns | Merge=%.0f±%.0f ns | "
        		+ "Quick=%.0f±%.0f ns%n",
                size, scenario, bMean, bStd, mMean, mStd, qMean, qStd);
    }

    private static void runFileScenario(int[] baseData, String scenario, Exporter exporter) {
        int size = baseData.length;
        System.out.printf("Executando: size=%d scenario=%s (FILE)%n", size, scenario);

        long[] bubbleTimes = new long[REPEAT];
        long[] mergeTimes  = new long[REPEAT];
        long[] quickTimes  = new long[REPEAT];

        for (int r = 0; r < REPEAT; r++) {
            int[] arrB = DataGenerator.copyArray(baseData);
            bubbleTimes[r] = Timer.measureNanos(() -> BubbleSort.sort(arrB));
            if (!Validator.isSorted(arrB)) System.err.println("BubbleSort falhou na ordenação (FILE).");

            int[] arrM = DataGenerator.copyArray(baseData);
            mergeTimes[r] = Timer.measureNanos(() -> MergeSort.sort(arrM));
            if (!Validator.isSorted(arrM)) System.err.println("MergeSort falhou na ordenação (FILE).");

            int[] arrQ = DataGenerator.copyArray(baseData);
            quickTimes[r] = Timer.measureNanos(() -> QuickSort.sort(arrQ));
            if (!Validator.isSorted(arrQ)) System.err.println("QuickSort falhou na ordenação (FILE).");
        }

        double bMean = Statistics.mean(bubbleTimes);
        double bStd  = Statistics.stddev(bubbleTimes);
        double mMean = Statistics.mean(mergeTimes);
        double mStd  = Statistics.stddev(mergeTimes);
        double qMean = Statistics.mean(quickTimes);
        double qStd  = Statistics.stddev(quickTimes);

        exporter.save(scenario, size, "BubbleSort", bMean, bStd);
        exporter.save(scenario, size, "MergeSort", mMean, mStd);
        exporter.save(scenario, size, "QuickSort", qMean, qStd);

        System.out.printf("Resumo size=%d scenario=%s -> Bubble=%.0f±%.0f ns | Merge=%.0f±%.0f ns | "
        		+ "Quick=%.0f±%.0f ns%n",
                size, scenario, bMean, bStd, mMean, mStd, qMean, qStd);
    }

    private interface DataSupplier { int[] get(); }
}