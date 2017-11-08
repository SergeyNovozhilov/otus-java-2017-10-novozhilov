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

    private static final String LOG_FILE_NAME_FORMAT = "./logs/gc_pid_pid%s.log.0.current";

    @Getter
    private GCViewer viewer;

    public GCViewer execute() {
        String pid = ManagementFactory.getRuntimeMXBean().getName().split("@")[0];
        System.out.println("pid: " + pid);
        File file = new File(String.format(LOG_FILE_NAME_FORMAT, pid));
        if (file.exists()) {
            try {
                String string = FileUtils.readFileToString(file, "UTF8");
                Client client = Client.create();
                WebResource webResource = client.resource(REQUEST);
                ClientResponse response = webResource.type("application/json").post(ClientResponse.class, string);
                if (response.getStatus() != 200) {
                    System.out.println("Failed : HTTP error code : " + response.getStatus());
                    return null;
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
