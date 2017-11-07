package ru.otus;


import com.tagtraum.perf.gcviewer.imp.DataReaderFacade;
import com.tagtraum.perf.gcviewer.model.ConcurrentGCEvent;
import com.tagtraum.perf.gcviewer.model.GCModel;
import com.tagtraum.perf.gcviewer.model.GCEvent;
import com.tagtraum.perf.gcviewer.imp.DataReaderException;

import java.io.File;
import java.lang.management.ManagementFactory;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;


public class Main {
    public static int SIZE = 1_000_000;

    public static void main(String[] args) {
        List<String> list = new ArrayList<>();
        String pid = ManagementFactory.getRuntimeMXBean().getName().split("@")[0];

        System.out.println("pid: " + pid);
        try {
            while (true) {
                for (int i = 0; i < SIZE; i++) {
                    list.add(UUID.randomUUID().toString());
                }
                for (int i = 0; i < SIZE / 10; i++) {
                    list.set(i, null);
                }
            }
        } catch (OutOfMemoryError ex) {
            String path = "./logs/gc_pid_pid" + pid + ".log.0.current";
            File file = new File(path);
            if (file.exists()) {
                System.out.println("Exists");
                DataReaderFacade drf = new DataReaderFacade();
                try {
                    GCModel model = drf.loadModel(file.toURI().toURL(), true, null);



                    Iterator it = model.getConcurrentGCEvents();
                    if (it.hasNext()) {
                        System.out.println("it has next");
                        while (it.hasNext()) {

                            ConcurrentGCEvent event = (ConcurrentGCEvent) it.next();
                            System.out.println("event type: " + event.getTypeAsString());
                            System.out.println("event duration: " + event.getDuration());
                            System.out.println("event stw: " + event.isStopTheWorld());
                            System.out.println("event is full: " + event.isFull());
                            System.out.println("event generation: " + event.getType().getGeneration().name());
                        }
                    } else {
                        System.out.println("it has not next");
                    }

                    it = model.getGCEvents();
                    if (it.hasNext()) {
                        System.out.println("it has next");
                        while (it.hasNext()) {
                            GCEvent event = (GCEvent) it.next();
                            System.out.println("event type: " + event.getType().getGeneration().name());
                        }
                    } else {
                        System.out.println("it has not next");
                    }


                    it = model.getFullGCEvents();
                    if (it.hasNext()) {
                        System.out.println("it has next");
                        while (it.hasNext()) {
                            GCEvent event = (GCEvent) it.next();
                            System.out.println("event type: " + event.getType().getGeneration().name());
                        }
                    } else {
                        System.out.println("it has not next");
                    }
                } catch (MalformedURLException | DataReaderException e) {
//                    e.printStackTrace();
                }

            }
        }
    }
}
