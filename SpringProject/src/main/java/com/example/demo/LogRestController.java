package com.example.demo;

import org.springframework.web.bind.annotation.*;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
public class LogRestController {

    static Map<String, Integer> LogCounts =  new HashMap<String, Integer>();

    @GetMapping({"/datapoints"})
    public Map<String, Integer> getLogs(){

        return LogCounts;
    }

    @PostMapping({"/writeToFile"})
    public String writeToFile(@RequestBody String incomingLog) throws IOException {
        String tmp[] = incomingLog.split("&");
        String city = tmp[0].split("=")[1];
        String[] details_array = tmp[1].split("=");
        String details = String.join("=", Arrays.copyOfRange(details_array, 1, details_array.length));
        details = details.replace('+', ' ');
        System.out.println(details);
        String type = tmp[tmp.length-1].split("=")[1];
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
        String now = sdf.format(cal.getTime());
        String fn = "/data/Log.txt";
        File f5 = new File(fn);
        double fileSizeInMB = f5.length() / (1024 * 1024);
        if(fileSizeInMB > 2.00){
            SimpleDateFormat filesdf = new SimpleDateFormat("yyy_MM_dd_HH_mm_ss");
            File f1 = new File("/data/Log.txt");
            File f2 = new File("/data/Log-" + filesdf + ".txt");
            f1.renameTo(f2);
        }
        File f3 = new File(fn);
        BufferedWriter writer = new BufferedWriter(new FileWriter(f3, true));
        PrintWriter out = new PrintWriter(writer);
        String log  = now + " " + type + " " + city + " " + details;
        log.replace("+", " ");
        out.println(log);
        out.close();
        if(!LogCounts.containsKey(city)) {
            LogCounts.put(city, 1);
        }
        else {
            LogCounts.replace(city, LogCounts.get(city)+1);
        }
        System.out.println("Log Created: " + now + " " + type + " " + city + " " + details);
        return "Log created successfully!";
    }


}
