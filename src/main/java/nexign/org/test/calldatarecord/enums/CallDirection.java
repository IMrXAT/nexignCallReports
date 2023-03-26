package nexign.org.test.calldatarecord.enums;

public enum CallDirection {
    INCOMING("01"),
    OUTGOING("02");

    private final String callType;

    CallDirection(String callType){
        this.callType = callType;
    }

    public String getCallType() {
        return callType;
    }


}
