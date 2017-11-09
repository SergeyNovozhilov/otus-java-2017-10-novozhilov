package ru.otus;

import ru.otus.GCLogAnalyzer.GcLogAnalyzer;
import ru.otus.GCLogAnalyzer.GcViewer;

import java.io.File;
import java.util.*;
import ru.otus.GCLogAnalyzer.GcStatistics;

/*
*  -Xloggc:./logs/gc_pid_%p.log
*   folder /logs must be created before run
*/

public class Main {
    public static int SIZE = 100_000;

    public static void main(String[] args) {

        GcLogAnalyzer gca = new GcLogAnalyzer();

        try {
            List<String> list = new ArrayList<>();
            while (true) {
                for (int i = 0; i < SIZE; i++) {
                    list.add(UUID.randomUUID().toString());
                }
                for (int i = 0; i < SIZE / 2 ; i++) {
                    list.set(i, null);
                }
                Thread.sleep(3000);
            }
        } catch (OutOfMemoryError ex) {
            GcViewer viewer = gca.execute();
            if (viewer != null) {
                GcStatistics stat = viewer.getGcStatistics();
                System.out.println("fullGC: count " + stat.getFullGCCount() + " average time " + stat.getFullGCAvgTime());
                System.out.println("minorGC: count " + stat.getMinorGCCount() + " average time " + stat.getMinorGCAvgTime());
            } else {
                System.out.println("No data");
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
