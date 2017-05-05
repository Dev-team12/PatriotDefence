package defencer.service.builder;

import static com.itextpdf.text.FontFactory.*;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.draw.LineSeparator;
import defencer.model.Apprentice;

import java.util.Comparator;
import java.util.List;

/**
 * @author Igor Gnes on 5/3/17.
 */
public class ApprenticeReportBuilder {

    private static final float DEFAULT_TABLE_WIDTH = 100.0f;
    private static final int DEFAULT_TABLE_SPACING = 10;

    /**
     * Build instructor pdf document.
     */
    public Document buildPdfDocument(Document document, List<Apprentice> apprentices) throws Exception {

        apprentices.sort(Comparator.comparing(Apprentice::getDateOfAdded));

        final int countNewLine = 8;
        final float imgLogoX = 450f;
        final float imgLogoY = 700f;

        return new ReportDocumentBuilder(document)
                .addImage(Image.getInstance(getClass().getClassLoader().getResource("image/PatriotDefencePDF.jpg")), imgLogoX, imgLogoY)
                .addNewLine(countNewLine)
                .addParagraph(new Paragraph("Instructors in Patriot Defence"), Paragraph.ALIGN_CENTER)
                .addLineSeparator(new LineSeparator())
                .addParagraph(new Paragraph("Statistics of added apprentice in period from " + apprentices.get(0).getDateOfAdded()
                        + " to " + apprentices.get(apprentices.size() - 1).getDateOfAdded()), Element.ALIGN_CENTER)
                .addTable(getTableWithCountRequestsByPeriod(apprentices))
                .buildDocument();
    }

    /**
     * Build table for instructor pdf document.
     */
    private PdfPTable getTableWithCountRequestsByPeriod(List<Apprentice> apprentices) throws DocumentException {

        final int tableColumnNum = 6;
        final int colorR = 185;
        final int colorG = 247;
        final int colorB = 166;
        PdfPTable table = new PdfPTableBuilder(tableColumnNum, DEFAULT_TABLE_WIDTH, DEFAULT_TABLE_SPACING)
                .addPdfPCells(new BaseColor(colorR, colorG, colorB), getFont(HELVETICA_BOLD),
                        "Name", "Email", "Phone", "Project name", "Occupation", "Added date")
                .build();
        apprentices
                .forEach(s -> {
                    table.addCell(s.getName());
                    table.addCell(s.getEmail());
                    table.addCell(s.getPhone());
                    table.addCell(s.getProjectName());
                    table.addCell(s.getOccupation());
                    table.addCell(s.getDateOfAdded().toString());
                });

        return table;
    }
}
