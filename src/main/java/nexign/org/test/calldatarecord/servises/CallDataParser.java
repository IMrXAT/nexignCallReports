package nexign.org.test.calldatarecord.servises;

import nexign.org.test.calldatarecord.entities.CallInfo;
import nexign.org.test.calldatarecord.enums.CallDirection;
import nexign.org.test.calldatarecord.enums.TariffType;
import nexign.org.test.calldatarecord.enums.TokenTypes;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.time.Duration;
import java.util.Calendar;
import java.util.StringTokenizer;
public class CallDataParser {


    public CallInfo parse(String callDataRecord) throws ParseException {
        CallInfo callInfo = new CallInfo();
        StringTokenizer tokenizer = new StringTokenizer(callDataRecord, ", ");
        String[] date = {"", ""};
        if (tokenizer.countTokens() != 5){
            throw new ParseException("not enough information", 0);
        }
        while (tokenizer.hasMoreTokens()){
             String token = tokenizer.nextToken();
             switch (getTokenType(token)){
                 case PHONE_NUMBER :
                     callInfo.setPhoneNumber(token);
                     break;
                 case DATE_AND_TIME:
                     if (token.compareTo(date[0]) < 0 || date[0].equals("")){
                         date[0] = token;
                     }
                     if (token.compareTo(date[1]) > 0){
                         date[1] = token;
                     }
                     break;
                 case TARIFF_TYPE:
                     checkAndSetTariffType(callInfo, token);
                     break;
                 case CALL_DIRECTION:
                     if (token.equals(CallDirection.OUTGOING.getCallType())){
                         callInfo.setCallDirection(CallDirection.OUTGOING);
                     }
                     else callInfo.setCallDirection(CallDirection.INCOMING);
                     break;
             }
        }
        setAllDateAndTimeCallData(callInfo, date);
        return callInfo;
    }

    private static void checkAndSetTariffType(CallInfo callInfo, String token) {

        if (token.equals(TariffType.UNLIMITED.getTariffType())) {
            callInfo.setTariffType(TariffType.UNLIMITED);
        } else if (token.equals(TariffType.MINUTE_BY_MINUTE.getTariffType())) {
            callInfo.setTariffType(TariffType.MINUTE_BY_MINUTE);
        } else if (token.equals(TariffType.COMMON.getTariffType())) {
            callInfo.setTariffType(TariffType.COMMON);
        }
    }

    private static void setAllDateAndTimeCallData(CallInfo callInfo, String[] date) {
        try {
            Calendar begin = Calendar.getInstance();
            Calendar end = Calendar.getInstance();

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddhhmmss");
            begin.setTime(dateFormat.parse(date[0]));
            end.setTime(dateFormat.parse(date[1]));

            callInfo.setCallBeginTime(begin);
            callInfo.setCallEndTime(end);

            Duration duration = Duration.between(callInfo.getCallBeginDate().getTime().toInstant(), callInfo.getCallEndDate().getTime().toInstant());
            callInfo.setCallDuration(duration);
        }
        catch (ParseException e){
            e.printStackTrace();
        }
    }

    private TokenTypes getTokenType(String token) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddhhmmss");
        if (token.length() == 2){
            if (token.equals("01") || token.equals("02")){
                return TokenTypes.CALL_DIRECTION;
            }
            return TokenTypes.TARIFF_TYPE;
        }
        if (token.length() == "yyyyMMddhhmmss".length()
                && token.chars().allMatch(Character::isDigit)){
            try {
                dateFormat.parse(token);
                return TokenTypes.DATE_AND_TIME;
            }
            catch (ParseException e) {
                return TokenTypes.PHONE_NUMBER;
            }
        }
        return TokenTypes.PHONE_NUMBER;
    }
}
