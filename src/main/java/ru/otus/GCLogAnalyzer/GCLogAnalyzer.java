package ru.otus.GCLogAnalyzer;

import com.google.gson.Gson;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import lombok.Getter;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.lang.management.ManagementFactory;

public class GCLogAnalyzer {

    private static final String REQUEST = "http://api.gceasy.io/analyzeGC?apiKey=b98e81c8-128b-4524-84bd-3bb3929392a8";

    @Getter
    private GCViewer viewer;

    public GCViewer execute() {
        String pid = ManagementFactory.getRuntimeMXBean().getName().split("@")[0];
        System.out.println("pid: " + pid);
        File file = new File("./logs/gc_pid_pid" + pid + ".log.0.current");
        if (file.exists()) {
            try {
                String string = FileUtils.readFileToString(file, "UTF8");
                Client client = Client.create();
                WebResource webResource = client.resource(REQUEST);
                ClientResponse response = webResource.type("application/json").post(ClientResponse.class, string);
                if (response.getStatus() != 200) {
                    throw new RuntimeException("Failed : HTTP error code : " + response.getStatus());
                }

                System.out.println("Server response " + response.getStatus());
                String output = response.getEntity(String.class);
                GCViewer viewer = new Gson().fromJson(output, GCViewer.class);

                return viewer;
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("File " + file.getAbsolutePath() + " doesn't exist");
        }
        return null;
    }
}
