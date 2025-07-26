package managers.utiles;

import java.time.format.DateTimeFormatter;

public class DateTimeFormatPatterns {
    public static DateTimeFormatter oldFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    public static DateTimeFormatter format = DateTimeFormatter.ofPattern("dd.MM.yy|HH:mm:ss");
}
