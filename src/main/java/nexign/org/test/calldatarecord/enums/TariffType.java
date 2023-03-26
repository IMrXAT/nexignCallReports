package nexign.org.test.calldatarecord.enums;

public enum TariffType {
    UNLIMITED("06"),
    MINUTE_BY_MINUTE("03"),
    COMMON("11");
    private final String tariffType;


    TariffType(String tariffType) {
        this.tariffType = tariffType;
    }
    public String getTariffType() {
        return tariffType;
    }
}
