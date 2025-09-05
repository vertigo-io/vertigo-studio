# Shiny

This module provides a set of console UI components for Vertigo Shell.

## Shiny Components

Here's a categorized list of the available Shiny components:

### Data Visualization
*   `ShinyBarChart`: Displays a bar chart.
*   `ShinySparkline`: Displays a sparkline chart.
*   `ShinyGauge`: Displays a gauge.

### Text & Content
*   `ShinyFiglet`: Displays text as ASCII art.
*   `ShinyTitle`: Displays a title.
*   `ShinyTextPath`: Displays a colorized file path.
*   `ShinyJson`: Displays a colorized JSON string.

### Indicators
*   `ShinyProgressBar`: Displays a progress bar.
*   `ShinySpinner`: Displays a spinner animation.
*   `ShinyStatus`: Displays a status line.

### Interactive
*   `ShinyToggle`: Displays a toggle switch.
*   `ShinyRating`: Displays a rating (stars, hearts, etc.).
*   `ShinyMultiSelection`: Allows selecting multiple values from a list.
*   `ShinyInputText`: Allows text input with validation and suggestions.

### Structures
*   `ShinyTree`: Displays a tree structure.
*   `ShinyCalendar`: Displays a calendar.
*   `ShinyList`: Displays a list (ordered, unordered).
*   `ShinyTable`: Displays data in a tabular format.

Shiny is a library for creating beautiful and interactive command-line interfaces. It provides a set of components for displaying tables, progress bars, spinners, trees, and bar charts.

## Components

### Table

The `ShinyTable` component allows you to display data in a tabular format. You can customize the table's title, header, border, and colors.

**Example:**

```java
import io.vertigo.shell.shiny.Shiny;
import io.vertigo.shell.shiny.table.ShinyBorder;
import io.vertigo.shell.shiny.utils.ShinyColors;
import java.util.List;

public class TableExample {
    public static void main(String[] args) {
        List<String[]> data = List.of(
                new String[]{"Austria", "9,000,000"},
                new String[]{"Belgium", "11,700,000"},
                new String[]{"Bulgaria", "6,400,000"}
        );

        Shiny.table()
                .title("European Union Countries - Population")
                .header("Country", "Population")
                .rows(data)
                .beginStyle()
                .border(ShinyBorder.Ascii)
                .titleBackgroundColor(ShinyColors.INVERSE)
                .headerBackgroundColor(ShinyColors.GREEN_BG)
                .altRowBackgroundColor(ShinyColors.CYAN_BG)
                .borderColor(ShinyColors.RED)
                .endStyle()
                .print();
    }
}
```

### Progress Bar

The `ShinyProgressBar` component allows you to display a progress bar. You can set the total value of the progress bar and update its progress.

**Example:**

```java
import io.vertigo.shell.shiny.Shiny;
import io.vertigo.shell.shiny.progressbar.ShinyProgressBar;

public class ProgressBarExample {
    public static void main(String[] args) throws InterruptedException {
        ShinyProgressBar progressBar = Shiny.progressBar().total(100);
        for (int i = 0; i <= 100; i++) {
            progressBar.setProgress(i);
            progressBar.print();
            Thread.sleep(30);
        }
        progressBar.finish();
    }
}
```

### Spinner

The `ShinySpinner` component allows you to display a spinner. You can send messages to be displayed alongside the spinner.

**Example:**

```java
import io.vertigo.shell.shiny.Shiny;
import io.vertigo.shell.shiny.spinner.ShinySpinner;

public class SpinnerExample {
    public static void main(String[] args) throws Exception {
        try (ShinySpinner spinner = Shiny.spinner()) {
            spinner.send("Loading...");
            Thread.sleep(2000);
            spinner.send("Cleaning...");
            Thread.sleep(2000);
            spinner.send("Processing...");
            Thread.sleep(2000);
        }
    }
}
```

### Tree

The `ShinyTree` component allows you to display data in a tree-like structure. You can also add icons to the tree nodes.

**Example:**

```java
import io.vertigo.shell.shiny.Shiny;
import io.vertigo.shell.shiny.tree.ShinyIcon;
import io.vertigo.shell.shiny.tree.ShinyTree;

public class TreeExample {
    public static void main(String[] args) {
        ShinyTree tree = Shiny.tree("Video Games");
        tree.getRoot()
                .addNode("RPG", ShinyIcon.FOLDER_OPEN)
                .addNode("Final Fantasy", ShinyIcon.FILE)
                .addNode("The Elder Scrolls", ShinyIcon.FILE);
        tree.getRoot()
                .addNode("FPS", ShinyIcon.FOLDER_OPEN)
                .addNode("Halo", ShinyIcon.FILE)
                .addNode("Call of Duty", ShinyIcon.FILE);
        tree.getRoot()
                .addNode("Adventure", ShinyIcon.FOLDER_OPEN)
                .addNode("The Legend of Zelda", ShinyIcon.FILE)
                .addNode("Uncharted", ShinyIcon.FILE);

        tree.print();
    }
}
```

**Icons:**

The `ShinyIcon` enum provides a set of predefined icons that you can use in the tree.

| Icon | Meaning |
|---|
| `FOLDER_OPEN` |  |
| `FOLDER_CLOSED` |  |
| `FILE` |  |
| `DB` |  |
| `USER` |  |
| `SUCCESS` |  |
| `ERROR` |  |
| `WARNING` |  |
| `INFO` |  |
| `QUESTION` |  |
| `CLOCK` |  |
| `ARROW_UP` |  |
| `ARROW_DOWN` |  |
| `ARROW_LEFT` |  |
| `ARROW_RIGHT` |  |
| `STAR` |  |
| `HEART` |  |
| `SMILEY` |  |

**Note:** To display the icons correctly, you need to use a terminal that supports Unicode and a font that includes the Font Awesome icons.

### Bar Chart

The `ShinyBarChart` component allows you to display data as a bar chart. You can customize the chart's title, header, and sort mode.

**Example:**

```java
import io.vertigo.shell.shiny.Shiny;
import io.vertigo.shell.shiny.barchart.ShinySortMode;

public class BarChartExample {
    public static void main(String[] args) {
        Shiny.barChart()
                .title("Population in European Countries")
                .header("Austria", "Belgium", "Bulgaria")
                .rows(9000000, 11700000, 6400000)
                .sort(ShinySortMode.VALUE_DESC)
                .print(100);
    }
}
```

### Gauge

The `ShinyGauge` component allows you to display a value as a linear gauge. You can customize the gauge's title, value, max value, length, and color.

**Example:**

```java
import io.vertigo.shell.shiny.Shiny;
import io.vertigo.shell.shiny.utils.ShinyColors;

public class GaugeExample {
    public static void main(String[] args) {
        Shiny.gauge()
                .title("CPU Usage")
                .value(50)
                .print();

        Shiny.gauge()
                .title("Memory Usage")
                .value(75)
                .color(ShinyColors.YELLOW)
                .print();

        Shiny.gauge()
                .title("Disk Usage")
                .value(90)
                .color(ShinyColors.RED)
                .print();

        Shiny.gauge()
                .title("Download")
                .value(25)
                .length(20)
                .color(ShinyColors.BLUE)
                .print();
    }
}
```

### Sparkline

The `ShinySparkline` component allows you to display a series of data as a compact sparkline. You can also set the color of the sparkline.

**Example:**

```java
import java.util.List;
import io.vertigo.shell.shiny.Shiny;
import io.vertigo.shell.shiny.utils.ShinyColors;

public class SparklineExample {
    public static void main(String[] args) {
        Shiny.sparkline()
                .title("Temperature")
                .data(List.of(10.0, 12.0, 15.0, 13.0, 11.0, 10.0, 9.0, 10.0, 12.0, 14.0, 16.0, 15.0))
                .color(ShinyColors.GREEN)
                .print();

        Shiny.sparkline()
                .title("Stock Price")
                .data(List.of(100.0, 102.0, 105.0, 103.0, 101.0, 100.0, 99.0, 100.0, 102.0, 104.0, 106.0, 105.0))
                .color(ShinyColors.BLUE)
                .print();

        Shiny.sparkline()
                .title("CPU Load")
                .data(List.of(0.1, 0.2, 0.5, 0.8, 0.7, 0.6, 0.5, 0.4, 0.3, 0.2, 0.1, 0.0))
                .color(ShinyColors.RED)
                .print();
    }
}
```

### Status

The `ShinyStatus` component allows you to display a series of statuses with color codes, using square or circle shapes.

**Example:**

```java
import java.util.List;
import io.vertigo.shell.shiny.Shiny;
import io.vertigo.shell.shiny.status.ShinyStatus.StatusShape;
import io.vertigo.shell.shiny.status.ShinyStatus.StatusType;

public class StatusExample {
    public static void main(String[] args) {
        Shiny.status()
                .title("Last 5 Matches")
                .statuses(List.of(StatusType.SUCCESS, StatusType.SUCCESS, StatusType.NEUTRAL, StatusType.ERROR, StatusType.SUCCESS))
                .shape(StatusShape.SQUARE)
                .print();

        Shiny.status()
                .title("Server Status")
                .statuses(List.of(StatusType.SUCCESS, StatusType.SUCCESS, StatusType.ERROR, StatusType.SUCCESS, StatusType.SUCCESS))
                .shape(StatusShape.CIRCLE)
                .print();

        Shiny.status()
                .title("Build Status")
                .statuses(List.of(StatusType.SUCCESS, StatusType.WARNING, StatusType.ERROR, StatusType.INFO))
                .shape(StatusShape.SQUARE)
                .print();
    }
}
```

### Figlet

The `ShinyFiglet` component allows you to display text in large ASCII art fonts, with color support.

**Example:**

```java
import io.vertigo.shell.shiny.Shiny;
import io.vertigo.shell.shiny.utils.ShinyColors;
import io.vertigo.shell.shiny.figlet.ShinyFigletFonts;

public class FigletExample {
    public static void main(String[] args) {
        Shiny.figlet()
                .text("Vertigo")
                .print();

        Shiny.figlet()
                .text("Hello")
                .font(ShinyFigletFonts.BIG)
                .color(ShinyColors.BLUE)
                .print();

        Shiny.figlet()
                .text("World")
                .font(ShinyFigletFonts.SLANT)
                .color(ShinyColors.RED)
                .print();

        Shiny.figlet()
                .text("Figlet")
                .font(ShinyFigletFonts.STANDARD)
                .color(ShinyColors.GREEN)
                .print();
    }
}
```
**Note:** The font names are now constants from `io.vertigo.shell.shiny.figlet.ShinyFigletFonts`. You need to ensure that the corresponding `.flf` font files (e.g., `standard.flf`, `big.flf`, `slant.flf`) are present in your project's `src/main/resources` directory.

### TextPath

The `ShinyTextPath` component allows you to display a text path with different colors for the root, intermediate nodes, and the leaf. You can also customize the separator and its color.

**Example:**

```java
import io.vertigo.shell.shiny.Shiny;
import io.vertigo.shell.shiny.utils.ShinyColors;

public class TextPathExample {
    public static void main(String[] args) {
        Shiny.textPath()
                .path("/home/user/documents/report.pdf")
                .rootColor(ShinyColors.GREEN) // Updated default
                .nodeColor(ShinyColors.YELLOW) // Updated default
                .leafColor(ShinyColors.BLUE_BRIGHT) // Updated default
                .separatorColor(ShinyColors.RED) // Updated default
                .print();

        Shiny.textPath()
                .path("C:\\Program Files\\Java\\jdk-17\\bin")
                .separator("\\") // Custom separator
                .rootColor(ShinyColors.CYAN)
                .nodeColor(ShinyColors.WHITE)
                .leafColor(ShinyColors.MAGENTA)
                .separatorColor(ShinyColors.YELLOW)
                .print();
    }
}
```

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

### Json

The `ShinyJson` component allows you to display JSON data with customizable colors for different elements.

**Example:**

```java
import io.vertigo.shell.shiny.Shiny;
import io.vertigo.shell.shiny.utils.ShinyColors;

public class JsonExample {
    public static void main(String[] args) {
        String json = "{\"name\": \"John Doe\", \"age\": 30, \"isStudent\": true, \"courses\": [\"Math\", \"Science\"]}";
        Shiny.json()
                .json(json)
                .print();

        String complexJson = "{\n  \"orderId\": \"12345\",\n  \"customer\": {\n    \"id\": 1,\n    \"name\": \"Alice\"\n  },\n  \"items\": [\n    {\n      \"itemId\": 101,\n      \"quantity\": 2\n    },\n    {\n      \"itemId\": 102,\n      \"quantity\": 1\n    }\n  ],\n  \"totalAmount\": 150.75,\n  \"isPaid\": false,\n  \"notes\": null\n}";
        Shiny.json()
                .json(complexJson)
                .labelColor(ShinyColors.CYAN)
                .stringColor(ShinyColors.YELLOW)
                .numberColor(ShinyColors.GREEN_BRIGHT)
                .separatorColor(ShinyColors.MAGENTA)
                .defaultColor(ShinyColors.WHITE)
                .print();
    }
}
```