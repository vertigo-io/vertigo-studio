
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