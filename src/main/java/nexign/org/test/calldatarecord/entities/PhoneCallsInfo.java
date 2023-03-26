package nexign.org.test.calldatarecord.entities;

import nexign.org.test.calldatarecord.enums.TariffType;

import java.time.Duration;
import java.util.PriorityQueue;

public class PhoneCallsInfo {
     private final String phoneNumber;

    private final  PriorityQueue<CallInfo> callsInfo;
    private TariffType tariffType;
    private double totalCallsCost;
    private long totalCallsDurationMills;


    public PhoneCallsInfo(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        callsInfo = new PriorityQueue<>(this::compareStartTime);
        totalCallsDurationMills = 0;
        totalCallsCost = 0;

    }

    public void addCallInfo(CallInfo callInfo) {
        increaseTotalCallsCost(callInfo.getCallCost());
        callsInfo.add(callInfo);
        increaseTotalCallsDurationMills(callInfo.getCallDuration());
    }



    public String getPhoneNumber() {
        return phoneNumber;
    }

    public double getTotalCallsCost() {
        return totalCallsCost;
    }

    public void increaseTotalCallsCost(double additionalCost) {
        this.totalCallsCost += additionalCost;
    }

    public long getTotalCallsDurationMills() {
        return totalCallsDurationMills;
    }

    public long getTotalCallsDurationInMinutes() {
        long callDurationInMinutes = getTotalCallsDurationMills() / 60000;
        if (getTotalCallsDurationMills() % 60000 != 0){
            callDurationInMinutes++;
        }
        return callDurationInMinutes;
    }

    public void increaseTotalCallsDurationMills(Duration additionalCost) {
        this.totalCallsDurationMills += additionalCost.toMillis();
    }

    public TariffType getTariffType() {
        return tariffType;
    }

    public void setTariffType(TariffType tariffType) {
        this.tariffType = tariffType;
    }

    public PriorityQueue<CallInfo> getCallsInfo() {
        return callsInfo;
    }


    private int compareStartTime(CallInfo x, CallInfo y) {
        if (x.getCallBeginDate().getTimeInMillis() > y.getCallBeginDate().getTimeInMillis()){
            return 1;
        }
        return -1;
    }
}
