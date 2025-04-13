package src.core;

import java.util.regex.Pattern;

public class InputValidator {

    private static final Pattern AMOUNT_PATTERN = Pattern.compile("^\\d+(\\.\\d{1,2})?$");
    private static final Pattern ACCOUNT_NUMBER_PATTERN = Pattern.compile("^\\d{10}$");

    public static boolean isValidAmount(String amount) {
        return AMOUNT_PATTERN.matcher(amount).matches();
    }

    public static boolean isValidAccountNumber(String accountNumber) {
        return ACCOUNT_NUMBER_PATTERN.matcher(accountNumber).matches();
    }
}
