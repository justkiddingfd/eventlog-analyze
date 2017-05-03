/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logs.tmp;

import java.io.File;
import java.io.FilenameFilter;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;
import logs.analyze.MyAnalyze;
import logs.read.ReadLogs;
import logs.utils.MyUtils;

/**
 *
 * @author 0x01
 */
public class MyMain {

    static MyUtils m = new MyUtils();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("*********************************************");
        System.out.println("     ***********************************");
        System.out.println("          *************************");
        System.out.println("               ***************");
        System.out.println("                    *****");
        System.out.println("                      *");
        System.out.println("Welcome to my Logs Analyze System");
        welcomeOptions(scanner);

    }

    private static String welcomeOptions(Scanner s) {
        System.out.println("Get your choose:");
        System.out.println("\t1.Get Logs value");
        System.out.println("\t2.Analyze logs");
        System.out.println("\t3.Exit");
        System.out.println("");
        System.out.print("[?] Your choice is? (1 or 2 - dont use 3) ");
        String welcomeChose;
        while (true) {
            welcomeChose = s.next();
            if (!((welcomeChose.equals("1") || (welcomeChose.equals("2")) || (welcomeChose.equals("3"))))) {
                System.out.print("[?] choose again, 1 or 2 - dont use 3) ");

            } else if (welcomeChose.equals("1")) {
                getLogsValue(s);
                System.out.print("[?] Do you want to analyze? [Y/n] ");
                String scanString1 = s.next();
                if (scanString1.equals("n")) {
                    System.exit(0);
                } else {
                    System.out.println("[+] Great! Let's analyze your logs!");
                    System.out.print("[?] Logs in [" + outputLocation + "]? [Y/n] ");
                    String scanString2 = s.nextLine();
                    if ((!scanString2.equals("n"))) {
                        analyzeLogs(outputLocation, s);
                    } else {
                        System.out.print("[?] Where is your log? ");
                        String scanString3 = s.next();
                        if (!scanString3.endsWith("\\")) {
                            scanString3 = scanString3 + "\\";
                        }
                        int check = checkLocation(scanString3);
                        if (check == 0) {
                            analyzeLogs(scanString3, s);
                        } else if (check == 1) {
                            welcomeOptions(s);
                        }
                    }
                }
            } else if (welcomeChose.equals("2")) {
                System.out.print("[?] You chose 2, where is your log? ");
                String scanString3 = s.next();
                if (!scanString3.endsWith("\\")) {
                    scanString3 = scanString3 + "\\";
                }
                int check = checkLocation(scanString3);
                if (check == 0) {
                    analyzeLogs(scanString3, s);
                } else if ((check == 2) || (check == 1)) {
                    welcomeOptions(s);
                }

            } else if (welcomeChose.equals("3")) {
                System.out.println("[+] Good bye and see you soon ... ");
                System.exit(0);
            }

        }
        // return welcomeChose;
    }

    private static int checkLocation(String outputLocation) {
        System.out.println("[+] Your working folder: " + outputLocation);
        Path path = null;
        try {
            path = Paths.get(outputLocation);
            if (Files.exists(path)) {
                return 0; // folder da ton tai
            } else {
                return 1; // folder chua ton tai
            }
        } catch (InvalidPathException e) {
            System.out.println("[-] Folder unavaiable!");
            return 2; // folder sai dinh dang
        }

    }

    private static String outputLocation;

    private static void getLogsValue(Scanner s) {
        System.out.print("[?] You chose 1, input your output location: ");
        outputLocation = s.next();
        if (!outputLocation.endsWith("\\")) {
            outputLocation = outputLocation + "\\";
        }
        int check = checkLocation(outputLocation);
        System.out.println(check);
        switch (check) {
            case 1:
                try {
                    System.out.println("\t[+] Create folder " + outputLocation);
                    new File(outputLocation).mkdir();
                    ReadLogs readLogs = new ReadLogs(outputLocation);
                    readLogs.readLogSecurity();
                    readLogs.readLogApplication();
                    readLogs.readLogSystem();
                    System.out.println("[+] Log saved! ");
                } catch (Exception e) {
                    System.out.println("[-] Error! You must run as administrator!");
                    System.out.println("[+] Program is exiting ...");
                    System.exit(0);
                    //getLogsValue(s);
                }
                break;
            case 0:
                try {
                    ReadLogs readLogs = new ReadLogs(outputLocation);
                    readLogs.readLogSecurity();
                    readLogs.readLogApplication();
                    readLogs.readLogSystem();
                } catch (Exception e) {
                    System.out.println("[-] Error! You must run as administrator!");
                    System.out.println("[+] Program is exiting ...");
                    System.exit(0);
                }
                break;
            case 2:
                welcomeOptions(s);
                break;
            default:
                break;
        }
    }

    private static void analyzeLogs(String folderLogs, Scanner s) {

        MyAnalyze analyze = new MyAnalyze();
        File[] fileList = finder(folderLogs);
        for (File file : fileList) {
            System.out.println("[+] Analyze file: " + file.getName());
            analyze.doMyJob(m.readFile(folderLogs + file.getName()));
        }
        welcomeOptions(s);
    }

    public static File[] finder(String folderName) {
        File dir = new File(folderName);
        return dir.listFiles(new FilenameFilter() {
            public boolean accept(File dir, String filename) {
                return filename.endsWith(".txt");
            }
        });
    }
}
