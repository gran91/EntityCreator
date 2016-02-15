package tools;

import java.text.Normalizer;
import java.util.concurrent.TimeUnit;

/**
 *
 * @author chautj
 */
public class ToolString {

    public static String sansAccents(String source) {
        return Normalizer.normalize(source, Normalizer.Form.NFD).replaceAll("[\u0300-\u036F]", "");
    }

    public static String formatSQLValue(String source) {
        String s = "";
        String[] tab = source.split("'");
        for (int i = 0; i < tab.length; i++) {
            s += (i == tab.length - 1) ? tab[i] : tab[i] + "''";
        }
        if (source.length() > 1) {
            s += (source.substring(source.length() - 1).equals("'")) ? "''" : "";
        } else if (source.length() == 1 && source.equals("'")) {
            s += "''";
        }
        return s;
    }

    public static String getDurationBreakdown(long millis) {
        if (millis < 0) {
            throw new IllegalArgumentException("Duration must be greater than zero!");
        }

        long days = TimeUnit.MILLISECONDS.toDays(millis);
        millis -= TimeUnit.DAYS.toMillis(days);
        long hours = TimeUnit.MILLISECONDS.toHours(millis);
        millis -= TimeUnit.HOURS.toMillis(hours);
        long minutes = TimeUnit.MILLISECONDS.toMinutes(millis);
        millis -= TimeUnit.MINUTES.toMillis(minutes);
        long seconds = TimeUnit.MILLISECONDS.toSeconds(millis);

        StringBuilder sb = new StringBuilder(64);
        if (days > 0) {
            sb.append(days);
            sb.append(" d ");
        }
        if (hours > 0) {
            sb.append(hours);
            sb.append(" h ");
        }
        if (minutes > 0) {
            sb.append(minutes);
            sb.append(" min ");
        }
        sb.append(seconds);
        sb.append(" sec");

        return (sb.toString());
    }
}
