/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logs.utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import logs.tmp.MyAdvapi32Util;

/**
 *
 * @author 0x01
 */
public class MyUtils {

    public String getDateTime(MyAdvapi32Util.EventLogRecord record) {
        String datetime = new java.text.SimpleDateFormat("dd-MM-yyyy HH:mm:ss z").
                format(new java.util.Date(record.getRecord().TimeGenerated.longValue() * 1000));
        return datetime;
    }

    public String readFile(String filename){
        StringBuilder strBuild = new StringBuilder();
         try {
            FileReader reader = new FileReader(filename);
            BufferedReader bufferedReader = new BufferedReader(reader);
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                strBuild.append(line);
            }
            reader.close();
 
        } catch (IOException e) {
            e.printStackTrace();
            strBuild.append("NULL");
        }
        return strBuild.toString();
    }
    public void writeToFile(String location,String str,String logOfWhat) {
        long currentTimeMillis = System.currentTimeMillis();
        String filename = location + getLocalHostName()+"_"+logOfWhat+"_"+currentTimeMillis+".txt";
        
         try {
            try (FileWriter writer = new FileWriter(filename, false)) {
                writer.write(str);
            }
            System.out.println("\t[+] Log was saved to: "+filename);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getLocalHostName() {
        String hostname = "Unknown";
        try {
            InetAddress addr;
            addr = InetAddress.getLocalHost();
            hostname = addr.getHostName();
            
        } catch (UnknownHostException ex) {
            System.out.println("Hostname can not be resolved");
        }
        
        return hostname;
    }
    
//    public static void main(String[] args) {
//        new MyUtils().writeToFile(null,null);
//    }
}
