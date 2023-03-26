package nexign.org.test.calldatarecord.servises;

import nexign.org.test.calldatarecord.entities.CallInfo;
import nexign.org.test.calldatarecord.enums.CallDirection;

public class CallCostHandler {
    public void handle(CallInfo callInfo, long totalCallsDuration) {
        switch (callInfo.getTariffType()){
            case COMMON:
                setCostCommonTariff(callInfo, totalCallsDuration);
                break;
            case UNLIMITED:
                setCostUnlimitedTariff(callInfo, totalCallsDuration);
                break;
            case MINUTE_BY_MINUTE:
                setCostMBMTariff(callInfo, callInfo.getCallDurationInMinutes());
                break;
        }
    }
    private void setCostMBMTariff(CallInfo callInfo, long duration) {
        callInfo.increaseCallCost(1.5*duration);
    }

    private void setCostUnlimitedTariff(CallInfo callInfo, long totalCallsDuration) {

        long currentCallDuration = callInfo.getCallDurationInMinutes();

        if (totalCallsDuration > 300){
            callInfo.increaseCallCost(currentCallDuration);
        }
        else if (currentCallDuration + totalCallsDuration > 300){
            callInfo.increaseCallCost(currentCallDuration + totalCallsDuration - 300);
        }
        else callInfo.increaseCallCost(0);
    }

    private void setCostCommonTariff(CallInfo callInfo, long totalCallsDuration) {
        long currentCallDuration = callInfo.getCallDurationInMinutes();
        if (callInfo.getCallDirection() == CallDirection.INCOMING){
            callInfo.increaseCallCost(0);
            return;
        }
        if (totalCallsDuration + currentCallDuration < 100){
            callInfo.setCallCost(0.5*currentCallDuration);
        }
        else {
            if (totalCallsDuration < 100){
                callInfo.increaseCallCost(0.5*(100-totalCallsDuration));
            }
            setCostMBMTariff(callInfo, totalCallsDuration + currentCallDuration - 100);
        }
    }
}
