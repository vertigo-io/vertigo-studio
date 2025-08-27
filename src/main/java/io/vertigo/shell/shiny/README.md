# Shiny

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

The `ShinyTree` component allows you to display data in a tree-like structure.

**Example:**

```java
import io.vertigo.shell.shiny.Shiny;
import io.vertigo.shell.shiny.tree.ShinyTree;

public class TreeExample {
    public static void main(String[] args) {
        ShinyTree tree = Shiny.tree("Video Games");
        tree.getRoot().addNode("RPG")
                .addNode("Final Fantasy")
                .addNode("The Elder Scrolls");
        tree.getRoot().addNode("FPS")
                .addNode("Halo")
                .addNode("Call of Duty");
        tree.getRoot().addNode("Adventure")
                .addNode("The Legend of Zelda")
                .addNode("Uncharted");

        tree.print();
    }
}
```

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