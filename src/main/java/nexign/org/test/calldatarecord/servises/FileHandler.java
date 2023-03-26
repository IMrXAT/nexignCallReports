package nexign.org.test.calldatarecord.servises;

import nexign.org.test.calldatarecord.entities.CallInfo;
import nexign.org.test.calldatarecord.entities.PhoneCallsInfo;
import nexign.org.test.calldatarecord.enums.TariffType;

import java.io.*;
import java.text.ParseException;
import java.util.HashMap;

public class FileHandler{
    private final CallDataParser parser = new CallDataParser();
    private final CallCostHandler costHandler = new CallCostHandler();


    public HashMap<String, PhoneCallsInfo> handleFileData(File file){
        HashMap<String, PhoneCallsInfo> everyPhoneCallsData = new HashMap<>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            while(true) {
                String line = reader.readLine();
                if (line == null) break;
                if (line.equals("\n")) continue;
                try {
                    CallInfo callInfo = parser.parse(line);
                    String phoneNumber = callInfo.getPhoneNumber();

                    if (!everyPhoneCallsData.containsKey(phoneNumber)){
                        everyPhoneCallsData.put(phoneNumber, new PhoneCallsInfo(callInfo.getPhoneNumber()));
                        everyPhoneCallsData.get(phoneNumber).setTariffType(callInfo.getTariffType());
                        if (callInfo.getTariffType() == TariffType.UNLIMITED){
                            everyPhoneCallsData.get(phoneNumber).increaseTotalCallsCost(100);
                        }
                    }
                    PhoneCallsInfo phoneCallsInfo = everyPhoneCallsData.get(phoneNumber);
                    costHandler.handle(callInfo, phoneCallsInfo.getTotalCallsDurationInMinutes());
                    phoneCallsInfo.addCallInfo(callInfo);
                }
                catch (ParseException e){
                    e.printStackTrace();
                }
            }

        }
        catch(IOException e){
            e.printStackTrace();
        }
        return everyPhoneCallsData;
    }


}
