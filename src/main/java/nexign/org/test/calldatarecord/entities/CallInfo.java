package nexign.org.test.calldatarecord.entities;

import nexign.org.test.calldatarecord.enums.CallDirection;
import nexign.org.test.calldatarecord.enums.TariffType;

import java.time.Duration;
import java.util.Calendar;

public class CallInfo {
    private String phoneNumber;
    private CallDirection callDirection;

    private Calendar callBeginDate;
    private Calendar callEndDate;

    private TariffType tariffType;

    private Duration callDuration;

    private double callCost = 0;


    public double getCallCost() {
        return callCost;
    }

    public void setCallCost(double callCost) {
        this.callCost = callCost;
    }

    public void increaseCallCost(double callCost){
        this.callCost += callCost;
    }
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setCallDirection(CallDirection callDirection) {
        this.callDirection = callDirection;
    }

    public void setCallBeginTime(Calendar callBeginDate) {
        this.callBeginDate = callBeginDate;
    }


    public void setCallEndTime(Calendar callEndDate) {
        this.callEndDate = callEndDate;
    }

    public void setTariffType(TariffType tariffType) {
        this.tariffType = tariffType;
    }

    public void setCallDuration(Duration duration) {
        this.callDuration = duration;
    }


    public Duration getCallDuration() {
        return callDuration;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public CallDirection getCallDirection() {
        return callDirection;
    }

    public Calendar getCallBeginDate() {
        return callBeginDate;
    }

    public Calendar getCallEndDate() {
        return callEndDate;
    }

    public TariffType getTariffType() {
        return tariffType;
    }
    public long getCallDurationInMinutes() {
        long callDurationInMinutes = callDuration.toMinutes();
        if (callDuration.toMillis() % 60000 != 0){
            callDurationInMinutes++;
        }
        return callDurationInMinutes;
    }


}