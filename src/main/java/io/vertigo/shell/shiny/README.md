### Calendar

The `ShinyCalendar` component allows you to display a calendar for a given month and year, respecting the locale for day and month names. You can also highlight specific dates and add borders.

**Example:**

```java
import java.time.LocalDate;
import java.time.Month;
import java.util.Locale;
import io.vertigo.shell.shiny.Shiny;
import io.vertigo.shell.shiny.table.ShinyBorder;
import io.vertigo.shell.shiny.utils.ShinyColors;

public class CalendarExample {
    public static void main(String[] args) {
        // Current month
        Shiny.calendar()
                .print();

        // Specific month and year
        Shiny.calendar()
                .year(2024)
                .month(Month.JULY.getValue())
                .print();

        // With highlighted dates
        Shiny.calendar()
                .year(2024)
                .month(Month.AUGUST.getValue())
                .highlight(LocalDate.of(2024, Month.AUGUST, 15))
                .highlight(LocalDate.of(2024, Month.AUGUST, 20))
                .print();

        // French locale
        Shiny.calendar()
                .year(2024)
                .month(Month.SEPTEMBER.getValue())
                .locale(Locale.FRENCH)
                .highlight(LocalDate.of(2024, Month.SEPTEMBER, 1))
                .print();

        // With SQUARE border and custom highlight color
        Shiny.calendar()
                .year(2024)
                .month(Month.OCTOBER.getValue())
                .border(ShinyBorder.SQUARE)
                .highlight(LocalDate.of(2024, Month.OCTOBER, 10))
                .highlight(LocalDate.of(2024, Month.OCTOBER, 20))
                .highlightColor(ShinyColors.MAGENTA_BRIGHT)
                .print();

        // With ROUNDED border
        Shiny.calendar()
                .year(2024)
                .month(Month.NOVEMBER.getValue())
                .border(ShinyBorder.ROUNDED)
                .print();
    }
}
```