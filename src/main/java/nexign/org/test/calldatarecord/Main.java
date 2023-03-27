package nexign.org.test.calldatarecord;


import nexign.org.test.calldatarecord.entities.PhoneCallsInfo;
import nexign.org.test.calldatarecord.servises.FileHandler;
import nexign.org.test.calldatarecord.servises.ReportForPhoneNumberGenerator;

import java.io.File;
import java.util.HashMap;
import java.util.Scanner;

//не успел навести красоту в мейне:(
public class Main {
    public static void main(String[] args) {
        ReportForPhoneNumberGenerator reportGenerator = new ReportForPhoneNumberGenerator();
        Scanner scanner = new Scanner(System.in);
        System.out.println("enter path to your data");
        String path = scanner.nextLine();
        File src = new File(path);
        FileHandler fileHandler = new FileHandler();
        HashMap<String, PhoneCallsInfo> everyPhoneCallsData = fileHandler.handleFileData(src);
        for (PhoneCallsInfo callsInfo: everyPhoneCallsData.values()) {
            reportGenerator.generateReport(callsInfo);
        }

    }
}
