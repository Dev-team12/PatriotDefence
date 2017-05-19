package defencer.service.builder.pdf;

import static com.itextpdf.text.FontFactory.*;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.draw.LineSeparator;
import defencer.model.Project;

import java.util.Comparator;
import java.util.List;

/**
 * @author Igor Gnes on 5/3/17.
 */
public class ProjectReportBuilder {

    private static final float DEFAULT_TABLE_WIDTH = 100.0f;
    private static final int DEFAULT_TABLE_SPACING = 10;

    /**
     * Build project pdf document.
     */
    public Document buildPdfDocument(Document document, List<Project> projects) throws Exception {

        projects.sort(Comparator.comparing(Project::getDateOfCreation));

        final int countNewLine = 8;
        final float imgLogoX = 450f;
        final float imgLogoY = 700f;

        return new ReportDocumentBuilder(document)
                .addImage(Image.getInstance(getClass().getClassLoader().getResource("image/PatriotDefencePDF.jpg")), imgLogoX, imgLogoY)
                .addNewLine(countNewLine)
                .addParagraph(new Paragraph("Project in Patriot Defence"), Paragraph.ALIGN_CENTER)
                .addLineSeparator(new LineSeparator())
                .addParagraph(new Paragraph("Statistics of created project in period from " + projects.get(0).getDateOfCreation()
                        + " to " + projects.get(projects.size() - 1).getDateOfCreation()), Element.ALIGN_CENTER)
                .addTable(getTableWithCountRequestsByPeriod(projects))
                .buildDocument();
    }

    /**
     * Build table for project pdf document.
     */
    private PdfPTable getTableWithCountRequestsByPeriod(List<Project> projects) throws DocumentException {

        final int tableColumnNum = 7;
        final int colorR = 185;
        final int colorG = 247;
        final int colorB = 166;
        PdfPTable table = new PdfPTableBuilder(tableColumnNum, DEFAULT_TABLE_WIDTH, DEFAULT_TABLE_SPACING)
                .addPdfPCells(new BaseColor(colorR, colorG, colorB), getFont(HELVETICA_BOLD),
                        "Project name", "Place", "Start date", "Finish date", "Instructors", "Cars", "Author", "Created")
                .build();
        projects
                .forEach(s -> {
                    table.addCell(s.getNameId());
                    table.addCell(s.getPlace());
                    table.addCell(s.getDateStart().toString());
                    table.addCell(s.getDateFinish().toString());
                    table.addCell(s.getInstructors());
                    table.addCell(s.getCars());
                    table.addCell(s.getAuthor());
                    table.addCell(s.getDateOfCreation().toString());
                });

        return table;
    }
}
