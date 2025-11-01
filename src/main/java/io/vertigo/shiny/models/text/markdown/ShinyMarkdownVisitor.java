package io.vertigo.shiny.models.text.markdown;

import java.util.ArrayList;
import java.util.List;

import org.commonmark.ext.gfm.tables.TableBody;
import org.commonmark.ext.gfm.tables.TableCell;
import org.commonmark.ext.gfm.tables.TableHead;
import org.commonmark.ext.gfm.tables.TableRow;
import org.commonmark.node.AbstractVisitor;
import org.commonmark.node.BulletList;
import org.commonmark.node.CustomBlock;
import org.commonmark.node.Heading;
import org.commonmark.node.ListItem;
import org.commonmark.node.Node;
import org.commonmark.node.OrderedList;
import org.commonmark.node.Paragraph;
import org.commonmark.renderer.text.TextContentRenderer;

import io.vertigo.shiny.Shiny;
import io.vertigo.shiny.ShinyWriter;

class ShinyMarkdownVisitor extends AbstractVisitor {
    private final TextContentRenderer textContentRenderer = TextContentRenderer.builder().build();
    private final ShinyWriter writer;

    public ShinyMarkdownVisitor(final ShinyWriter writer) {
        this.writer = writer;
    }

    @Override
    public void visit(final Heading heading) {
        final String title = textContentRenderer.render(heading);
        Shiny.title().level(heading.getLevel()).text(title).render(writer);
    }

    @Override
    public void visit(final Paragraph paragraph) {
        final String text = textContentRenderer.render(paragraph);
        Shiny.paragraph().text(text).render(writer);
    }

    @Override
    public void visit(final BulletList bulletList) {
        final List<String> items = new ArrayList<>();
        for (Node child = bulletList.getFirstChild(); child != null; child = child.getNext()) {
            if (child instanceof ListItem) {
                items.add(textContentRenderer.render(child));
            }
        }
        Shiny
                .list()
                .items(items)
                .render(writer);
    }

    @Override
    public void visit(final OrderedList orderedList) {
        final List<String> items = new ArrayList<>();
        for (Node child = orderedList.getFirstChild(); child != null; child = child.getNext()) {
            if (child instanceof ListItem) {
                items.add(textContentRenderer.render(child));
            }
        }
        Shiny.list()
                .ordered()
                .items(items)
                .render(writer);
    }

    @Override
    public void visit(final CustomBlock tableBlock) {
        final List<String> headers = new ArrayList<>();
        final List<String[]> rows = new ArrayList<>();

        final TableHead tableHead = (TableHead) tableBlock.getFirstChild();
        if (tableHead != null) {
            final TableRow headerRow = (TableRow) tableHead.getFirstChild();
            for (Node child = headerRow.getFirstChild(); child != null; child = child.getNext()) {
                if (child instanceof TableCell) {
                    headers.add(textContentRenderer.render(child));
                }
            }
        }

        final TableBody tableBody = (TableBody) tableHead.getNext();
        if (tableBody != null) {
            for (Node rowNode = tableBody.getFirstChild(); rowNode != null; rowNode = rowNode.getNext()) {
                if (rowNode instanceof TableRow) {
                    final List<String> cells = new ArrayList<>();
                    for (Node cellNode = rowNode.getFirstChild(); cellNode != null; cellNode = cellNode.getNext()) {
                        if (cellNode instanceof TableCell) {
                            cells.add(textContentRenderer.render(cellNode));
                        }
                    }
                    rows.add(cells.toArray(new String[0]));
                }
            }
        }
        Shiny.table()
                .header(headers.toArray(new String[0]))
                .rows(rows)
                .render(writer);
    }
}