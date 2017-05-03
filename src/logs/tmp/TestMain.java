/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logs.tmp;

import com.sun.jna.Memory;
import com.sun.jna.Native;
import com.sun.jna.Pointer;
import com.sun.jna.platform.win32.Advapi32;
import com.sun.jna.platform.win32.Advapi32Util.EventLogIterator;
import com.sun.jna.platform.win32.Advapi32Util.EventLogRecord;
import com.sun.jna.platform.win32.Kernel32;
import com.sun.jna.platform.win32.W32Errors;
import com.sun.jna.platform.win32.WinNT;
import com.sun.jna.platform.win32.WinNT.EVENTLOGRECORD;
import com.sun.jna.platform.win32.WinNT.HANDLE;
import com.sun.jna.ptr.IntByReference;
import java.util.ArrayList;

/**
 *
 * Error | Information EventLogIterator: Application | Security | Setup | System
 * |
 *
 * @author 0x01
 */
public class TestMain {

    public static void main(String[] args) {
        //String outputLocation = "C:\\demo\\";
        //System.out.println(outputLocation.endsWith("\\"));
//        System.out.println("START!");
        TestMain main = new TestMain();
//        //main.readLogApplication();
//        //System.out.println("*** --- Log of Application --- ***");
//        //main.readLogApplication();
//        //System.out.println("*** --- Log of Setup --- ***");
//        //main.readLogSetup();
//        //System.out.println("*** --- Log of System --- ***");
//        //main.readLogSystem();
        System.out.println("*** --- Log of Security --- ***");
        main.testReadEventLogEntries();
//        main.readLogSecurity();
//        //System.out.println("*** --- Log of Powershell --- ***");
//        //main.readLogWindowsPowerShell();
    }

    public void testReadEventLogEntries() {
        HANDLE h = Advapi32.INSTANCE.OpenEventLog(null, "Application");
        IntByReference pnBytesRead = new IntByReference();
        IntByReference pnMinNumberOfBytesNeeded = new IntByReference();
        Memory buffer = new Memory(1024 * 64);
        IntByReference pOldestRecord = new IntByReference();
        int dwRecord = pOldestRecord.getValue();
        int rc = 0;
        while (true) {
            if (!Advapi32.INSTANCE.ReadEventLog(h,
                    WinNT.EVENTLOG_SEQUENTIAL_READ | WinNT.EVENTLOG_FORWARDS_READ,
                    0, buffer, (int) buffer.size(), pnBytesRead, pnMinNumberOfBytesNeeded)) {
                rc = Kernel32.INSTANCE.GetLastError();
                if (rc == W32Errors.ERROR_INSUFFICIENT_BUFFER) {
                    buffer = new Memory(pnMinNumberOfBytesNeeded.getValue());
                    continue;
                }
                break;
            }
            int dwRead = pnBytesRead.getValue();
            Pointer pevlr = buffer;
            while (dwRead > 0) {
                EVENTLOGRECORD record = new EVENTLOGRECORD(pevlr);
                System.out.println(dwRecord
                        + " Event ID: " + record.EventID
                        + " Event Type: " + record.EventType.intValue()
                        + " Event Source: " + pevlr.getString(record.size(), true));
                String datetime = new java.text.SimpleDateFormat("dd-MM-yyyy HH:mm:ss z").
                format(new java.util.Date(record.TimeGenerated.longValue() * 1000));
                System.out.println(datetime);
                try {
                    //System.out.println(new String(record.getData()));
                    if (record.NumStrings.intValue() > 0) {
                        ArrayList<String> strings = new ArrayList<String>();
                        int count = record.NumStrings.intValue();
                        long offset = record.StringOffset.intValue();
                        while (count > 0) {
                            String s = pevlr.getWideString(offset);
                            System.out.println(s);
                            strings.add(s);
                            offset += s.length() * Native.WCHAR_SIZE;
                            offset += Native.WCHAR_SIZE;
                            count--;
                        }

                    }

                } catch (Exception e) {
                    System.out.println("error: "+e.toString());
                }
                System.out.println("------------------------------------------------------------------");
                dwRecord++;
                dwRead -= record.Length.intValue();
                pevlr = pevlr.share(record.Length.intValue());
            }
        }
    }

    //
    public void readLogWindowsPowerShell() {
        EventLogIterator iter
                = new EventLogIterator("Windows PowerShell");
        while (iter.hasNext()) {
            EventLogRecord record = iter.next();
            System.out.println(record.getRecordNumber()
                    + ": Event ID: " + record.getEventId()
                    //Advapi32Util.EventLogType: Error, Warning, Informational, AuditSuccess, AuditFailure
                    + ", Event Type: " + record.getType()
                    + ", Event Source: " + record.getSource());
            System.out.println(getDateTime(record));
            try {
                for (String eventString : record.getStrings()) {
                    System.out.println(eventString);
                }
            } catch (Exception e) {
            }
            System.out.println("------------------------------------------------------------------");
        }
    }

    //
    public void readLogSecurity() {
        EventLogIterator iter
                = new EventLogIterator("Security");

        while (iter.hasNext()) {
            EventLogRecord record = iter.next();
            //showEvent(record);
            // new process has been created 4688
            //showEvent(record, 4688, "new process has been created");
            // A service was installed in the system 4697
            //showEvent(record, 4697, "A service was installed in the system");
            // A scheduled task was created 4698
            //showEvent(record, 4698, "A scheduled task was created");
            // login fail 4845
            //showEvent(record, 4648, "login fail");
            //new ShowByEventID().eID4648(record);
            //System.out.println("------------------------------------------------------------------");
        }
    }

    public void showEvent(EventLogRecord record) {
        System.out.println("thuoc tinh");
        System.out.println(record.getRecordNumber()
                + ": Event ID: " + record.getEventId()
                //Advapi32Util.EventLogType: Error, Warning, Informational, AuditSuccess, AuditFailure
                + ", Event Type: " + record.getType()
                + ", Event Source: " + record.getSource());
        System.out.println(getDateTime(record));

        try {
            //System.out.println(new String(record.getData()));
            System.out.println(record.getStrings().length);

            for (int i = 0; i < record.getStrings().length; i++) {
                System.out.println(record.getStrings()[i]);
            }
        } catch (Exception e) {
        }
        System.out.println("------------------------------------------------------------------");

    }

    public void showEvent(EventLogRecord record, int eventID, String str) {
        if (record.getEventId() == eventID) {
            System.out.println("Thong bao:" + str);
            System.out.println(record.getRecordNumber()
                    + ": Event ID: " + record.getEventId()
                    //Advapi32Util.EventLogType: Error, Warning, Informational, AuditSuccess, AuditFailure
                    + ", Event Type: " + record.getType()
                    + ", Event Source: " + record.getSource());
            System.out.println(getDateTime(record));

            try {
                //System.out.println(new String(record.getData()));
                System.out.println(record.getStrings().length);

                for (int i = 0; i < record.getStrings().length; i++) {
                    System.out.println(record.getStrings()[i]);
                }
            } catch (Exception e) {
            }
            System.out.println("------------------------------------------------------------------");
        }

    }

    //
    public void readLogSystem() {
        EventLogIterator iter
                = new EventLogIterator("System");
        while (iter.hasNext()) {
            EventLogRecord record = iter.next();
            System.out.println(record.getRecordNumber()
                    + ": Event ID: " + record.getEventId()
                    //Advapi32Util.EventLogType: Error, Warning, Informational, AuditSuccess, AuditFailure
                    + ", Event Type: " + record.getType()
                    + ", Event Source: " + record.getSource());
            System.out.println(getDateTime(record));
            try {
                for (String eventString : record.getStrings()) {
                    System.out.println(eventString);
                }
            } catch (Exception e) {
            }
            System.out.println("------------------------------------------------------------------");
        }
    }

    //
    public void readLogSetup() {
        EventLogIterator iter
                = new EventLogIterator("Setup");
        while (iter.hasNext()) {
            EventLogRecord record = iter.next();
            System.out.println(record.getRecordNumber()
                    + ": Event ID: " + record.getEventId()
                    //Advapi32Util.EventLogType: Error, Warning, Informational, AuditSuccess, AuditFailure
                    + ", Event Type: " + record.getType()
                    + ", Event Source: " + record.getSource());
            System.out.println(getDateTime(record));
            try {
                for (String eventString : record.getStrings()) {
                    System.out.println(eventString);
                }
            } catch (Exception e) {
            }
            System.out.println("------------------------------------------------------------------");
        }
    }

    public void readLogApplication() {
        EventLogIterator iter
                = new EventLogIterator("Application");
        while (iter.hasNext()) {
            EventLogRecord record = iter.next();
            // appcrash 1001

            //
            System.out.println(record.getRecordNumber()
                    + ": Event ID: " + record.getEventId()
                    //Advapi32Util.EventLogType: Error, Warning, Informational, AuditSuccess, AuditFailure
                    + ", Event Type: " + record.getType()
                    + ", Event Source: " + record.getSource());
            System.out.println(getDateTime(record));
            try {
                for (String eventString : record.getStrings()) {
                    System.out.println(eventString);
                }
            } catch (Exception e) {
            }
            System.out.println("------------------------------------------------------------------");
        }
    }

    public String getDateTime(EventLogRecord record) {
        String datetime = new java.text.SimpleDateFormat("dd-MM-yyyy HH:mm:ss z").
                format(new java.util.Date(record.getRecord().TimeGenerated.longValue() * 1000));
        return datetime;
    }

}
