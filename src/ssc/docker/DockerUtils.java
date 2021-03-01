/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ssc.docker;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author PC
 */
public class DockerUtils {

    public static String makeNewHub(String name){
        makeDocker(4444, name, "selenium/hub");
        makeViewer(5900, name);
        return "";
    }
    
    public static String makeSeleniumHub(String name) {
        return makeDocker(4444, name, "selenium/hub");
    }

    public static String makeDocker(int port, String name, String image) {
        try {
            //docker run -d -p 4444:4444 --name zalenium1 selenium/hub
            String[] cmd_array = {"docker", "run", "-d", "-p", port + ":4444", "--name", name, image};
            runProgress(cmd_array);
        } catch (Exception e) {
        }
        return "";
    }

    public static String makeViewer(int port, String name) {
        try {
            //docker run -d -p 4444:4444 --name zalenium1 selenium/hub
            String[] cmd_array = {"docker", "run", "-d", "-P", "-p", port+":"+port, "--name", name+"_node", "-e", "SCREEN_WIDTH=1366", "-e", "SCREEN_HEIGHT=768", "--link", name+":hub", "selenium/node-chrome-debug"};
            runProgress(cmd_array);
        } catch (Exception e) {
        }
        return "";
    }

    public static void runProgress(String[] cmd_array) {
        try {
            List<String> arr = new ArrayList<>();
            //arr.add("cmd");
            //arr.add("/c");
            arr.addAll(Arrays.asList(cmd_array));
            ProcessBuilder pb = new ProcessBuilder(arr);
            pb.redirectErrorStream(true);
            Process process = pb.start();
            BufferedReader input = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = input.readLine()) != null) {
                System.out.println("docker progress " + line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
