package com.test.iris.card_validation_service.util;


import org.slf4j.Logger;

import java.time.DateTimeException;
import java.time.YearMonth;

public class CardValidationUtil {

    private Logger logger = org.slf4j.LoggerFactory.getLogger(CardValidationUtil.class);

    public static String maskCardNumber(String cardNumber) {
        return "**** **** **** " + cardNumber.substring(cardNumber.length() - 4);
    }

    public  static boolean isValidCardNumber(String cardNumber) {
        int sum = 0;
        boolean alternate = false;
        for (int i = cardNumber.length() - 1; i >= 0; i--) {
            int n = Integer.parseInt(String.valueOf(cardNumber.charAt(i)));
            if (alternate) {
                n *= 2;
                if (n > 9) n -= 9;
            }
            sum += n;
            alternate = !alternate;
        }
        return (sum % 10 == 0);
    }

    public static boolean validateCardNotExpired(String cardExpiryMonth, String cardExpiryYear) {
        try {
            int month = Integer.parseInt(cardExpiryMonth);
            int year = Integer.parseInt(cardExpiryYear);

            YearMonth cardExpiry = YearMonth.of(year, month);
            return cardExpiry.isAfter(YearMonth.now());
        } catch (NumberFormatException | DateTimeException e) {
            return false; // Invalid month/year format
        }
    }
    public static boolean isValidCardType(String cardNumber, String cardType) {
        cardType = cardType.toUpperCase();
        switch (cardType) {
            case "VISA":
                return cardNumber.startsWith("4");
            case "MASTERCARD":
                return cardNumber.startsWith("51") || cardNumber.startsWith("52")
                        || cardNumber.startsWith("53") || cardNumber.startsWith("54")
                        || cardNumber.startsWith("55");
            case "AMEX":
                return cardNumber.startsWith("34") || cardNumber.startsWith("37");
            case "DISCOVER":
                return cardNumber.startsWith("6011") || cardNumber.startsWith("65");
            default:
                return false; // Unsupported card type
        }
    }
    /*public static boolean iscardTypeValid(CardDetailsRequst cardDetailsRequst)  {
        new CardTypeValidator().isValid(cardDetailsRequst, null);
    }*/

}
