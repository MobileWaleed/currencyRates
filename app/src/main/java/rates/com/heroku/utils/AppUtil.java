package rates.com.heroku.utils;

import java.text.DecimalFormat;

public class AppUtil {
    public static String formatDoubleToGivenDecimals(double number) {
        DecimalFormat decimalFormat = new DecimalFormat();
        // decimalFormat.setDecimalSeparatorAlwaysShown(true);
        decimalFormat.setMinimumFractionDigits(2);
        decimalFormat.setMaximumFractionDigits(5);
        return decimalFormat.format(number);
    }
}
