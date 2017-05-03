/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logs.analyze;

import java.util.ArrayList;

/**
 *
 * @author 0x01
 */
public class MyAnalyze {

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";

    public void doMyJob(String data) {
        try {
            ArrayList<ApplicationLogObj> arrayList = reconData(data);
            arrayList.forEach((applicationLogObj) -> {
                //System.out.println("eventID: "+applicationLogObj.getEventID());
                analyzeByEventID(applicationLogObj);
                analyzeEventStrings(applicationLogObj);
            });
        } catch (Exception e) {
            System.out.println("[-] It seems wrong format of logs. Program is exiting ...");
        }
    }

    private ArrayList reconData(String data) {

        ArrayList<ApplicationLogObj> arrayList = new ArrayList<ApplicationLogObj>();

        String[] array = data.split("------------------------------");
        for (String stringElement : array) {
            String[] checkStr = stringElement.split("\\[xxx\\]");
            int eventID = Integer.parseInt(checkStr[1].split(";")[0].split(":")[1].trim());
            String eventType = checkStr[1].split(";")[1];
            // get date time
            String eventTime = checkStr[2];
            // get all string
            String eventStrings = "";
            try {
                eventStrings = checkStr[3];
            } catch (Exception e) {

            }
            //
            ApplicationLogObj appObj = new ApplicationLogObj(eventID, eventType, eventTime, eventStrings);
            arrayList.add(appObj);
        }
        return arrayList;
    }

    private void analyzeEventStrings(ApplicationLogObj obj) {
        String data = obj.getEventStrings();
        String[] str = data.split("\\[ZZZ\\]");
        for (String string : str) {
            if (string.contains(".exe") && (!string.contains("C:\\Windows"))) {
                //System.out.println(ANSI_RED+"[!] ALERT application running:");
                System.out.println("[!] ALERT application running:");
                //System.out.println("\t\tEventID: " + obj.getEventID());
                System.out.println("\t\tEvent Time: " + obj.getEventTime());
                System.out.println("\t\t" + string);
            }

        }
    }

    private void analyzeByEventID(ApplicationLogObj obj) {

        if (obj.getEventID() == 4624) {
            // ID_4624: success full login
            // eventID4624(obj);
        }

        if (obj.getEventID() == 517) {
            // audit log was clear
            eventID517(obj);
        }
        if (obj.getEventID() == 1001) {
            // ID_1001 App Crash
            eventID1001(obj);
        }
        if (obj.getEventID() == 4648) {
            // ID_4648 Logon attempt using explicit credentials
            eventID4648(obj);
        }
        if (obj.getEventID() == 4649) {
            // ID_4649 An replay attack was detected
            eventID4649(obj);
        }
        if (obj.getEventID() == 4657) {
            // ID_4657 registry
            eventID4657(obj);
        }
        if (obj.getEventID() == 4673) {
            // ID_4673 a privileged service was called
            eventID4673(obj);
        }
        if (obj.getEventID() == 4688) {
            // ID_4688 new process
            eventID4688(obj);
        }
        if (obj.getEventID() == 4698) {
            // ID_4698 a scheduled task was created
            eventID4698(obj);
        }
        if (obj.getEventID() == 4717) {
            // ID_4717 System security access was granted to an accout
            eventID4717(obj);
        }
        if (obj.getEventID() == 4720) {
            // ID_4720 user account created
            eventID4720(obj);
        }
        if (obj.getEventID() == 4742) {
            // ID_4742 A computer account was changed
            eventID4742(obj);
        }
        if (obj.getEventID() == 5025) {
            // ID_5025 fw stoped
            eventID5025(obj);
        }
        if (obj.getEventID() == 5140) {
            // ID_5140 a share was accessed
            eventID5140(obj);
        }
         if (obj.getEventID() == 6011) {
            // ID_6011 Host name of this machine have been changed from 
            eventID6011(obj);
        }
        if (obj.getEventID() == 7045) {
            // ID_7045 new service is installed
            eventID7045(obj);
        }
        if (obj.getEventID() == 18456) {
            // ID_18456 Login failed for user in MSSQL
            eventID18456(obj);
        }
    }

    private void eventID4624(ApplicationLogObj obj) {
        System.out.println("[+] ID_4624: success full login");
        String data = obj.getEventStrings();
        String[] str = data.split("\\[ZZZ\\]");
        System.out.println("\t\tTime: " + obj.getEventTime());
        //System.out.println("\t\tSubjectUserSid:" + str[0]);
        System.out.println("\t\tSubjectUserName:" + str[1]);
        System.out.println("\t\tSubjectDomainName:" + str[2]);
        //System.out.println("SubjectLogonId" +str[3]);
        //System.out.println("LogonGuid" +str[4]);
        System.out.println("\t\tTargetUserName:" + str[5]);
        System.out.println("\t\tTargetDomainName:" + str[6]);
        // System.out.println("TargetLogonGuid:" +str[7]);
        //System.out.println("\t\tTargetServerName:" + str[8]);
        //System.out.println("\t\tTargetInfo:" + str[9]);
        //System.out.println("ProcessId:" +str[10]);
        System.out.println("\t\tProcessName:" + str[11]);
        System.out.println("\t\tIP Address:" + str[12]);
        System.out.println("\t\tIP Port:" + str[13]);
        System.out.println("========================================================");
    }

    private void eventID1001(ApplicationLogObj obj) {
        System.out.println("[+] ID_1001 App Crash:");
        String data = obj.getEventStrings();
        String[] str = data.split("\\[ZZZ\\]");
        System.out.println("\t\tTime: " + obj.getEventTime());
        for (String string : str) {
            if (string.contains(".exe")) {
                System.out.println("\t\t" + string);
            }
        }
        System.out.println("========================================================");
    }

    private void eventID7045(ApplicationLogObj obj) {
        System.out.println("[+] ID_7045 new service is installed");
        String data = obj.getEventStrings();
        String[] str = data.split("\\[ZZZ\\]");
        System.out.println("\t\tTime: " + obj.getEventTime());
        for (String string : str) {
            if (string.contains(".exe") && (!string.contains("C:\\Windows"))) {
                System.out.println("\t\t" + string);
            }
        }
        System.out.println("========================================================");

    }

    private void eventID4648(ApplicationLogObj obj) {
        System.out.println("[!] ID_4648 Logon attempt using explicit credentials");
        String data = obj.getEventStrings();
        String[] str = data.split("\\[ZZZ\\]");
        System.out.println("\t\tTime: " + obj.getEventTime());
        // System.out.println("\t\tSubjectUserSid:" + str[0]);
        System.out.println("\t\tSubjectUserName:" + str[1]);
        System.out.println("\t\tSubjectDomainName:" + str[2]);
        //System.out.println("SubjectLogonId" +str[3]);
        //System.out.println("LogonGuid" +str[4]);
        System.out.println("\t\tTargetUserName:" + str[5]);
        System.out.println("\t\tTargetDomainName:" + str[6]);
        // System.out.println("TargetLogonGuid:" +str[7]);
        System.out.println("\t\tTargetServerName:" + str[8]);
        System.out.println("\t\tTargetInfo:" + str[9]);
        //System.out.println("ProcessId:" +str[10]);
        System.out.println("\t\tProcessName:" + str[11]);
        System.out.println("\t\tIP Address:" + str[12]);
        System.out.println("\t\tIP Port:" + str[13]);
        System.out.println("========================================================");
    }

    private void eventID5140(ApplicationLogObj obj) {
        System.out.println("[+] ID_5140 a share was accessed");
        String data = obj.getEventStrings();
        String[] str = data.split("\\[ZZZ\\]");
        System.out.println("\t\tTime: " + obj.getEventTime());
        for (String string : str) {

            System.out.println("\t\t" + string);

        }
        System.out.println("========================================================");
    }

    private void eventID5025(ApplicationLogObj obj) {
        System.out.println("[+] ID_5025 fw stoped");
        String data = obj.getEventStrings();
        String[] str = data.split("\\[ZZZ\\]");
        System.out.println("\t\tTime: " + obj.getEventTime());
        for (String string : str) {

            System.out.println("\t\t" + string);

        }
        System.out.println("========================================================");
    }

    private void eventID4720(ApplicationLogObj obj) {
        System.out.println("[+] ID_4720 user account created");
        String data = obj.getEventStrings();
        String[] str = data.split("\\[ZZZ\\]");
        System.out.println("\t\tTime: " + obj.getEventTime());
        for (String string : str) {
            System.out.println("\t\t" + string);
        }
        System.out.println("========================================================");
    }

    private void eventID4717(ApplicationLogObj obj) {
        System.out.println("[+] ID_4717 System security access was granted to an accout");
        String data = obj.getEventStrings();
        String[] str = data.split("\\[ZZZ\\]");
        System.out.println("\t\tTime: " + obj.getEventTime());
        for (String string : str) {

            System.out.println("\t\t" + string);

        }
        System.out.println("========================================================");
    }

    private void eventID4698(ApplicationLogObj obj) {
        System.out.println("[+] ID_4698 a scheduled task was created");
        String data = obj.getEventStrings();
        String[] str = data.split("\\[ZZZ\\]");
        System.out.println("\t\tTime: " + obj.getEventTime());
        for (String string : str) {

            System.out.println("\t\t" + string);

        }
        System.out.println("========================================================");
    }

    private void eventID4688(ApplicationLogObj obj) {
        System.out.println("[+] ID_4688 New process is called");
        String data = obj.getEventStrings();
        String[] str = data.split("\\[ZZZ\\]");
        System.out.println("\t\tTime: " + obj.getEventTime());
        for (String string : str) {
            if (string.contains(".exe") && (!string.contains("C:\\Windows"))) {
                System.out.println("\t\t" + string);
            }
        }
        System.out.println("========================================================");
    }

    private void eventID4673(ApplicationLogObj obj) {
        System.out.println("[+] ID_4673 a privileged service was called");
        String data = obj.getEventStrings();
        String[] str = data.split("\\[ZZZ\\]");
        System.out.println("\t\tTime: " + obj.getEventTime());
        for (String string : str) {

            System.out.println("\t\t" + string);

        }
        System.out.println("========================================================");
    }

    private void eventID4657(ApplicationLogObj obj) {
        System.out.println("[+] ID_4657 registry");
        String data = obj.getEventStrings();
        String[] str = data.split("\\[ZZZ\\]");
        System.out.println("\t\tTime: " + obj.getEventTime());
        for (String string : str) {

            System.out.println("\t\t" + string);

        }
        System.out.println("========================================================");
    }

    private void eventID4649(ApplicationLogObj obj) {
        System.out.println("[+] ID_4649 An replay attack was detected");
        String data = obj.getEventStrings();
        String[] str = data.split("\\[ZZZ\\]");
        System.out.println("\t\tTime: " + obj.getEventTime());
        for (String string : str) {

            System.out.println("\t\t" + string);

        }
        System.out.println("========================================================");
    }

    private void eventID517(ApplicationLogObj obj) {
        System.out.println("[!] ID_517 Audit log was clear");
        String data = obj.getEventStrings();
        String[] str = data.split("\\[ZZZ\\]");
        System.out.println("\t\tTime: " + obj.getEventTime());
        for (String string : str) {

            System.out.println("\t\t" + string);

        }
        System.out.println("========================================================");
    }

    private void eventID18456(ApplicationLogObj obj) {
        System.out.println("[!] ID_18456 Login failed for user in MSSQL");
        String data = obj.getEventStrings();
        String[] str = data.split("\\[ZZZ\\]");
        System.out.println("\t\tTime: " + obj.getEventTime());
        for (String string : str) {

            System.out.println("\t\t" + string);

        }
        System.out.println("========================================================");
    }

    private void eventID4742(ApplicationLogObj obj) {
        // ID_4742 A computer account was changed
        System.out.println("[!] ID_4742 A computer account was changed");
        String data = obj.getEventStrings();
        String[] str = data.split("\\[ZZZ\\]");
        System.out.println("\t\tTime: " + obj.getEventTime());
        for (String string : str) {

            System.out.println("\t\t" + string);

        }
        System.out.println("========================================================");
    }

    private void eventID6011(ApplicationLogObj obj) {
        // ID_6011 Host name of this machine have been changed from 
        System.out.println("[!] ID_6011 Host name of this machine have been changed ");
        String data = obj.getEventStrings();
        String[] str = data.split("\\[ZZZ\\]");
        System.out.println("\t\tTime: " + obj.getEventTime());
        System.out.println("\t\tChange from: "+str[0]);
        System.out.println("\t\tTo: "+str[1]);
        System.out.println("========================================================");
    }
}
