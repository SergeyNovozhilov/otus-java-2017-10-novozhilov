package ru.otus;

import ru.otus.GCLogAnalyzer.GCLogAnalyzer;
import ru.otus.GCLogAnalyzer.GCViewer;

import java.util.*;
import ru.otus.GCLogAnalyzer.GcStatistics;


public class Main {
    public static int SIZE = 1_000_000;

    public static void main(String[] args) {

        GCLogAnalyzer gca = new GCLogAnalyzer();

        try {
            List<String> list = new ArrayList<>();
            while (true) {
                for (int i = 0; i < SIZE; i++) {
                    list.add(UUID.randomUUID().toString());
                }
                for (int i = 0; i < SIZE / 2; i++) {
                    list.set(i, null);
                }
            }
        } catch (OutOfMemoryError ex) {
            GCViewer viewer = gca.execute();
            if (viewer != null) {
                GcStatistics stat = viewer.getGcStatistics();
                System.out.println("fullGC: count " + stat.getFullGCCount() + " average time " + stat.getFullGCAvgTime());
                System.out.println("minorGC: count " + stat.getMinorGCCount() + " average time " + stat.getMinorGCAvgTime());
            } else {
                System.out.println("No data");
            }
        }
    }
}
