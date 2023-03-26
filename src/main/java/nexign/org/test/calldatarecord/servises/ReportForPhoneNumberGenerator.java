package nexign.org.test.calldatarecord.servises;

import nexign.org.test.calldatarecord.entities.CallInfo;
import nexign.org.test.calldatarecord.entities.PhoneCallsInfo;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;


public class ReportForPhoneNumberGenerator {

    public void generateReport(PhoneCallsInfo phoneCallsInfo){

        String fileSeparator = System.getProperty("file.separator");
        buildReportsDirectory();
        String path = "reports" + fileSeparator + "Report_for_phone_number_" + phoneCallsInfo.getPhoneNumber();
        File reportFile = new File(path);
        try {
            reportFile.createNewFile();
            PrintStream printStream = new PrintStream(new FileOutputStream(reportFile));

            writeReportTable(phoneCallsInfo, printStream);
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

    private static void writeReportTable(PhoneCallsInfo phoneCallsInfo, PrintStream printStream) {
        printStream.println("Tariff name: " + phoneCallsInfo.getTariffType() );
        writeTableDelimiter(printStream);
        printStream.println("Report for phone number " + phoneCallsInfo.getPhoneNumber() + ":");
        writeTableDelimiter(printStream);
        printStream.printf("|%-10s|%-20s|%-20s|%-10s|%-5s|%n",
                "Call Type",
                "Start Time",
                "End Time",
                "Duration",
                "Cost");
        writeTableDelimiter(printStream);
        for (CallInfo info: phoneCallsInfo.getCallsInfo()){
            printStream.printf("|%-10s" +
                            "|%2$tY-%2$tm-%2$td %-9tT" +
                            "|%2$tY-%2$tm-%2$td %-9tT" +
                            "|%d:%d:%-7d" +
                            "|%s|" +
                            "%n",
                    info.getCallDirection(),
                    info.getCallBeginDate().getTime(),
                    info.getCallEndDate().getTime(),
                    info.getCallDurationInMinutes() / 60, info.getCallDuration().toMinutes()%60, (info.getCallDuration().getSeconds()%60),
                    info.getCallCost());
            writeTableDelimiter(printStream);
        }
        printStream.printf("| %68s | %-6.2f |%n", "Total Cost:", phoneCallsInfo.getTotalCallsCost());
        writeTableDelimiter(printStream);
    }

    private static void writeTableDelimiter(PrintStream printStream) {
        for (int i = 0; i < 70; i++) printStream.print("-");
        printStream.println();
    }

    private static void buildReportsDirectory() {
        try {
            Path reports = Path.of("reports");
            if (!Files.exists(reports)){
                Files.createDirectories(reports);
            }
        }
        catch (IOException ignored){
        }
    }

}
