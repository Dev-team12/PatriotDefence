package defencer.service.builder.pdf;

import static com.itextpdf.text.FontFactory.*;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.draw.LineSeparator;
import defencer.model.Instructor;

import java.util.List;

/**
 * @author Igor Gnes on 5/3/17.
 */
public class InstructorReportBuilder {

    private static final float DEFAULT_TABLE_WIDTH = 100.0f;
    private static final int DEFAULT_TABLE_SPACING = 10;

    /**
     * Build instructor pdf document.
     */
    public void buildPdfDocument(Document document, List<Instructor> instructors) throws Exception {

        new ReportDocumentBuilder(document)
                .addParagraph(new Paragraph("Instructors in Patriot Defence"), Paragraph.ALIGN_CENTER)
                .addLineSeparator(new LineSeparator())
                .addTable(getTableWithCountRequestsByPeriod(instructors))
                .buildDocument();
    }

    /**
     * Build table for instructor pdf document.
     */
    private PdfPTable getTableWithCountRequestsByPeriod(List<Instructor> instructors) throws DocumentException {

        final int tableColumnNum = 6;
        final int colorR = 185;
        final int colorG = 247;
        final int colorB = 166;
        PdfPTable table = new PdfPTableBuilder(tableColumnNum, DEFAULT_TABLE_WIDTH, DEFAULT_TABLE_SPACING)
                .addPdfPCells(new BaseColor(colorR, colorG, colorB), getFont(HELVETICA_BOLD),
                        "First name", "Last name", "Email", "Phone", "Role", "Qualification")
                .build();
        instructors
                .forEach(s -> {
                    table.addCell(s.getFirstName());
                    table.addCell(s.getLastName());
                    table.addCell(s.getEmail());
                    table.addCell(s.getPhone());
                    table.addCell(s.getRole());
                    table.addCell(s.getQualification());
                });

        return table;
    }
}
