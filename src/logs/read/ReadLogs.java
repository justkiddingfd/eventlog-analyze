/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logs.read;

import logs.tmp.MyAdvapi32Util;
import logs.utils.MyUtils;

/**
 *
 * @author 0x01
 */
public class ReadLogs {
    static MyUtils m = new MyUtils();
    private String outputLocation;
    public ReadLogs(String _outputLocation) {
        outputLocation = _outputLocation;
        System.out.println("\t[+] We are going to saving log of "+m.getLocalHostName());
    }
    
    public void readLogSecurity() {
        MyAdvapi32Util.EventLogIterator iter
                = new MyAdvapi32Util.EventLogIterator("Security");
        StringBuilder strBuff = new StringBuilder("------ Log of Security ------");
        while (iter.hasNext()) {
            MyAdvapi32Util.EventLogRecord record = iter.next();
            showEvent(record, strBuff);
        }
        m.writeToFile(outputLocation,strBuff.toString(), "Security");
    }
    //
    public void readLogSystem() {
        MyAdvapi32Util.EventLogIterator iter
                = new MyAdvapi32Util.EventLogIterator("System");
        StringBuilder strBuff = new StringBuilder("------ Log of System ------");
        while (iter.hasNext()) {
            MyAdvapi32Util.EventLogRecord record = iter.next();
            showEvent(record, strBuff);
        }
        m.writeToFile(outputLocation,strBuff.toString(), "System");
    }
    
    public void readLogApplication() {
        MyAdvapi32Util.EventLogIterator iter
                = new MyAdvapi32Util.EventLogIterator("Application");
        StringBuilder strBuff = new StringBuilder("------ Log of Application ------");
        while (iter.hasNext()) {
            MyAdvapi32Util.EventLogRecord record = iter.next();
            showEvent(record, strBuff);
        }
        m.writeToFile(outputLocation,strBuff.toString(), "Application");
    }
    
    private void showEvent(MyAdvapi32Util.EventLogRecord record, StringBuilder strBuff) {
        
        strBuff.append("\r\n").append(record.getRecordNumber()).append("\r\n").append("[xxx] Event ID: ").append(record.getRecord().EventID.getLow()).append("; Event Type: ").append(record.getType()).append("; Event Source: ").append(record.getSource()).append("\r\n").append("[xxx]"+m.getDateTime(record)+"[xxx]");
 try {
            for (int i = 0; i < record.getStrings().length; i++) {
                strBuff.append("\r\n").append(record.getStrings()[i]).append("[ZZZ]");
            }
        } catch (Exception e) {
        }
        strBuff.append("\r\n").append("------------------------------"); // "-"*30
        
    }
}
